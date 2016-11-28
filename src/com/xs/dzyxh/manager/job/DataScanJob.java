package com.xs.dzyxh.manager.job;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.common.DesSecurityUtil;
import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.entity.system.ScanDataLog;
import com.xs.dzyxh.entity.system.ScanJobLog;
import com.xs.dzyxh.manager.base.IBaseManager;

import net.sf.json.JSONObject;

@Service("dataScanJob")
public class DataScanJob {

	static Logger logger = Logger.getLogger(DataScanJob.class);

	// @Value("${scan.data.out}")
	String outPath = "G:\\data";

	// @Value("${scan.data.in}")
	String inPath = "G:\\data";
	File outFile = null;
	File inFile = null;

	// 获得指定文件的byte数组
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void main(String[] arg) throws Exception {
		DataScanJob d = new DataScanJob();
		d.init();

		String[] filelist = d.outFile.list();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].lastIndexOf(".data") > 0) {
				System.out.println(filelist[i]);
				byte[] data = getBytes(d.outPath + "\\" + filelist[i]);

				JSONObject jsonObject = new JSONObject();
				jsonObject = jsonObject.fromObject(DesSecurityUtil.decrypt(new String(data)));

				List m = ObjectMapperUtil.mapper.readValue(jsonObject.getString("data"),
						d.getTypeReferenceForType(jsonObject.getString("sjlx")));
				// DrivingBasemapper.readValue(p.toString(),new
				// TypeReference<DrivingBase>() {} );
				System.out.println(m.get(0));
			}
		}

		/*
		 * ScanDataLog l=new ScanDataLog(); l.setClsj(new
		 * Date(System.currentTimeMillis())); l.setJxdm("01"); l.setClzt(1);
		 * l.setSfzmhm("123456789"); l.setQh("1015"); Map<String,Object>
		 * dataMap=new HashMap<String,Object>(); List<ScanDataLog> ls=new
		 * ArrayList<ScanDataLog>(); ls.add(l);
		 * 
		 * l.setClsj(new Date(System.currentTimeMillis())); l.setJxdm("01");
		 * l.setClzt(0); l.setSfzmhm("123456788"); l.setQh("1016"); ls.add(l);
		 * dataMap.put("sjlx", "01"); dataMap.put("data", ls); ObjectMapper
		 * mapper = new ObjectMapper();
		 * mapper.setSerializationInclusion(Include.NON_NULL); String
		 * json=mapper.writeValueAsString(dataMap); System.out.println(json);
		 */
	}

	@PostConstruct
	public void init() {
		outFile = new File(outPath);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		inFile = new File(inPath);
		if (!inFile.exists()) {
			inFile.mkdirs();
		}

	}

	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	/*
	 * @Resource(name = "driimgHibernateTemplate") private HibernateTemplate
	 * driimgHibernateTemplate;
	 * 
	 * @Resource(name = "driverHibernateTemplate") private HibernateTemplate
	 * driverHibernateTemplate;
	 */

	@Resource(name = "sysBaseManager")
	private IBaseManager<Object> sysBaseManager;
	@Resource(name = "driverImgBaseManager")
	private IBaseManager<Object> driimgBaseManager;
	@Resource(name = "driverBaseManager")
	private IBaseManager<Object> driverBaseManager;

	/*
	 * @Resource(name="dataQueryManager") private IDataQueryManager
	 * dataQueryManager;
	 */
	@Scheduled(fixedDelay = 1000)
	public void readData() {
		logger.info("定时扫描任务开始！");
		ScanJobLog joblog = new ScanJobLog();
		joblog.setKssj(new Date());
		String[] filelist = inFile.list();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].lastIndexOf(".data") > 0) {
				System.out.println(filelist[i]);
				byte[] data = getBytes(inPath + "\\" + filelist[i]);

				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject = jsonObject.fromObject(DesSecurityUtil.decrypt(new String(data)));
					String sjlx = jsonObject.getString("sjlx");
					List datas = ObjectMapperUtil.mapper.readValue(jsonObject.getString("data"),
							getTypeReferenceForType(sjlx));
					saveDatas(datas, sjlx);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// DrivingBasemapper.readValue(p.toString(),new
				// TypeReference<DrivingBase>() {} );
				// System.out.println(m.get(0).getXm());
			}
		}
	}

	TypeReference getTypeReferenceForType(String type) throws Exception {
		switch (type) {
		case ScanDataLog.SJLX_BASE:
			return new TypeReference<List<DrivingBase>>() {
			};
		case ScanDataLog.SJLX_APPLY:
			return new TypeReference<List<DrivingApply>>() {
			};
		case ScanDataLog.SJLX_EXAMINATION:
			return new TypeReference<List<DrivingExamination>>() {
			};
		case ScanDataLog.SJLX_DRIVINGPHOTO:
			return new TypeReference<List<DrivingPhoto>>() {
			};
		default:
			throw new Exception("未知的数据类型：" + type);
		}
	}

	private IBaseManager getIBaseManagerForType(String type) throws Exception {
		switch (type) {
		case ScanDataLog.SJLX_BASE:
		case ScanDataLog.SJLX_APPLY:
		case ScanDataLog.SJLX_EXAMINATION:
			return this.driverBaseManager;
		case ScanDataLog.SJLX_DRIVINGPHOTO:
			return this.driimgBaseManager;
		default:
			throw new Exception("未知的数据类型：" + type);
		}
	}

	private Integer saveDatas(List datas, String sjlx) {
		int count = 0;
		IBaseManager template = null;
		try {
			template = this.getIBaseManagerForType(sjlx);
		} catch (Exception e) {

		}
		if (datas != null) {
			for (Object obj : datas) {
				try {
					template.saveOrUpdate(obj);
				} catch (DataAccessException e) {
					// TODO: handle exception
				}

			}
		}
		return count;
	}

	@Scheduled(fixedDelay = 1000 * 60 * 10)
	public void writerDrivingBaseJob() throws Exception {
		createDate(DrivingBase.class, ScanDataLog.SJLX_BASE, "驾驶人基础数据");
	}

	@Scheduled(fixedDelay = 1000 * 60 * 10)
	public void writerDrivingApplyJob() throws Exception {
		createDate(DrivingApply.class, ScanDataLog.SJLX_BASE, "驾驶人申请表数据");
	}

	@Scheduled(fixedDelay = 1000 * 60 * 10)
	public void writerDrivingExaminationJob() throws Exception {
		createDate(DrivingApply.class, ScanDataLog.SJLX_BASE, "驾驶人体检表数据");
	}

	private void createDate(Class c, String sjlx, String title) throws Exception {

		logger.info(title + "交换定时任务开始！");
		ScanJobLog joblog = new ScanJobLog();
		try {
			joblog.setKssj(new Date());
			DetachedCriteria dc = DetachedCriteria.forClass(c);
			// dc.add(Restrictions.eq("csbj", DataSign.CSBJ_WCS));
			List<DrivingBase> datas = (List<DrivingBase>) hibernateTemplate.findByCriteria(dc, 0, 100);

			if (datas != null && !datas.isEmpty()) {
				// dataQueryManager.createScanDatalogs(datas);
				Map<String, Object> dataMap = new HashMap<String, Object>();

				ObjectMapper mapper = new ObjectMapper();
				dataMap.put("sjlx", sjlx);
				dataMap.put("data", datas);
				String strData = mapper.writeValueAsString(dataMap);
				String encData = DesSecurityUtil.encrypt(strData);
				createStrFile(encData);
			}
			joblog.setRwlx(sjlx);
			joblog.setSjl(datas.size());
			joblog.setJssj(new Date());
		} catch (Exception e) {
			joblog.setYcxx(e.getMessage());
			joblog.setJssj(new Date());
			throw e;
		} finally {
			// this.dataQueryManager.createScanJobLog(joblog);
			logger.info(title + "交换定时任务结束！");
		}

	}

	private void createStrFile(String str) throws IOException {

		FileOutputStream fos = null;
		try {
			String fileName = UUID.randomUUID() + ".data";
			File txt = new File(outFile, fileName);
			if (!txt.exists()) {
				txt.createNewFile();
			}
			byte bytes[] = str.getBytes();

			fos = new FileOutputStream(txt);
			fos.write(bytes);
			fos.close();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

}
