package com.xs.dzyxh.manager.job;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.common.DesSecurityUtil;
import com.xs.common.FormatUtil;
import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingApplyId;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.entity.system.ScanDataLog;
import com.xs.dzyxh.entity.system.ScanJobLog;
import com.xs.dzyxh.manager.driverimg.IDrivingPhotoManager;
import com.xs.dzyxh.manager.sys.IScanDataLogManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@Service("dataScanJob")
public class DataScanJob {

	static Logger logger = Logger.getLogger(DataScanJob.class);

	// @Value("${scan.data.out}")
	String outPath = "G:\\data\\out";

	// @Value("${scan.data.in}")
	String inPath = "G:\\data\\in";
	// @Value("${scan.data.error}")
	String errorPath = "G:\\data\\error";
	File outFile = null;
	File inFile = null;

	// 获得指定文件的byte数组
	public static byte[] getBytes(File file) {
		byte[] buffer = null;
		try {
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
				// System.out.println(filelist[i]);
				byte[] data = getBytes(new File(d.outPath + "\\" + filelist[i]));
				// Character c=Character.valueOf(Integer.valueOf("1"));
				d.transformationData(new String(data));
			}
		}

	}

	@PostConstruct
	public void init() {
		outFile = new File(outPath);
		if (!outFile.exists() && !outFile.isDirectory()) {
			outFile.mkdirs();
		}
		inFile = new File(inPath);
		if (!inFile.exists() && !inFile.isDirectory()) {
			inFile.mkdirs();
		}

	}

	@Resource(name = "dataScanJobManagerImpl")
	private IDataScanJobManager dataScanJobManager;
	@Resource(name = "scanDataLogManager")
	private IScanDataLogManager scanDataLogManager;
	
	BASE64Decoder decoder = new BASE64Decoder();

	/*
	 * @Resource(name="dataQueryManager") private IDataQueryManager
	 * dataQueryManager;
	 */
	@Scheduled(fixedDelay = 1000)
	public void readData() {
		logger.info("定时扫描任务开始！");
		//ScanJobLog joblog = new ScanJobLog();
		//joblog.setKssj(new Date());
		String[] filelist = inFile.list();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].lastIndexOf(".data") > 0) {
				File file = new File(inPath + "\\" + filelist[i]);
				byte[] data = getBytes(file);

				try {
					this.transformationData(new String(data));
					//file.delete();
				} catch (Exception e) {
					File errorfile = new File(
							errorPath + "\\" + FormatUtil.date.format(new Date(System.currentTimeMillis())));
					if (!errorfile.exists() && !errorfile.isDirectory()) {
						errorfile.mkdirs();
					}
					DataScanJobUtil.cutFile(file, errorfile);
					//e.printStackTrace();
				}
				// DrivingBasemapper.readValue(p.toString(),new
				// TypeReference<DrivingBase>() {} );
				// System.out.println(m.get(0).getXm());
			}
		}
	}

	public void transformationData(String data) throws Exception {
		if (data == null) {
			return ;
		}
		ScanDataLog dataLog=new ScanDataLog();
		try {
			JSONObject jsonObject = JSONObject.fromObject(new String(data));
			DrivingBase dribase = new DrivingBase();
			DrivingApply apply = new DrivingApply();
			DrivingExamination examination = new DrivingExamination();
			Map<String, DrivingPhoto> imgs = new HashMap<String, DrivingPhoto>();
			if (jsonObject.containsKey("DRV_TEMP_MID")) {
				JSONObject base = jsonObject.getJSONObject("DRV_TEMP_MID");
				dribase.setHmcd(getCharValue("HMCD", base));// 号码长度标志
				dribase.setLxzsxzqh(getStringValue("LXZSXZQH", base));// 联系住所行政区划
				dribase.setLxzsxxdz(getStringValue("LXZSXXDZ", base));// 联系住所详细地址
				dribase.setQh(getStringValue("QH", base));// 期号
				dribase.setJxdm(getStringValue("JXDM", base));// 期号
				dribase.setZzzm(getStringValue("ZZZM", base));// 暂住证明
				dribase.setGj(getStringValue("GJ", base));// 国际
				dribase.setXb(getStringValue("XB", base));// 性别
				dribase.setSfzmhm(getStringValue("SFZMHM", base));// 身份证明号码
				dribase.setLxdh(getStringValue("LXDH", base));// 联系电话
				dribase.setCsrq(getDateValue("CSRQ", base));// 出生日期
				dribase.setDjzsxzqh(getStringValue("DJZSXZQH", base));// 登记住所行政区划
				dribase.setXm(getStringValue("XM", base));// 姓名
				dribase.setDjzsxxdz(getStringValue("DJZSXXDZ", base));// 登记住所详细地址
				dribase.setSfzmmc(getCharValue("SFZMMC", base));// 身份证明名称
				dribase.setLxzsyzbm(getStringValue("LXZSYZBM", base));// 邮政编码

				examination.setZsz(getCharValue("ZSZ", base));
				examination.setYsz(getCharValue("YSZ", base));
				examination.setZsl(getBigDecimalValue("ZSL", base));
				examination.setYsl(getBigDecimalValue("YSL", base));
				examination.setZysfjz(getCharValue("ZYSFJZ", base));
				examination.setSfjyjb(getCharValue("SFJYJB", base));
				examination.setZetl(getCharValue("ZETL", base));
				examination.setSfjyjb(getCharValue("SFJYJB", base));
				examination.setTjrq(getDateValue("TJRQ", base));
				examination.setSg(getBigDecimalValue("SG", base));
				examination.setBsl(getCharValue("BSL", base));
				examination.setZxz(getCharValue("ZXZ", base));
				examination.setYxz(getCharValue("YXZ", base));
				examination.setQgjb(getCharValue("QGJB", base));
				examination.setYetl(getCharValue("YETL", base));
				examination.setJyjbqk(getStringValue("JYJBQK", base));
				examination.setYysfjz(getCharValue("YYSFJZ", base));
				examination.setTlsfjz(getCharValue("TLSFJZ", base));
				examination.setSfzmhm(dribase.getSfzmhm());
				examination.setTjyymc(getStringValue("TJYYMC", base));
				examination.setXm(dribase.getXm());
				examination.setXb(examination.getXb());
				examination.setGj(dribase.getGj());
				examination.setCsrq(dribase.getCsrq());
				examination.setLxdh(dribase.getLxdh());

				apply.setJxmc(getStringValue("JXMC", base));
				apply.setSqzjcxdh(getStringValue("ZKCX", base));
				apply.setLy(getCharValue("LY", base));
				apply.setDyslze(getCharValue("DYSLZE", base));
				apply.setYyspsy(getBigDecimalValue("YYSPSY", base));
				apply.setSg(examination.getSg());
				apply.setId(new DrivingApplyId());
				apply.getId().setQh(dribase.getQh());
				apply.getId().setJxdm(dribase.getJxdm());
				apply.getId().setSfzmhm(dribase.getSfzmhm());
				apply.setBsl(examination.getBsl());
				apply.setZxz(examination.getZxz());
				apply.setYxz(examination.getYxz());
				apply.setQgjb(examination.getQgjb());
				apply.setTjyymc(examination.getTjyymc());

			}
			if (jsonObject.containsKey("TJ_PHOTOS")) {
				JSONArray imgsJson = jsonObject.getJSONArray("TJ_PHOTOS");
				int count = imgsJson.size();
				for (int i = 0; i < count; i++) {
					JSONObject imgJson = imgsJson.getJSONObject(i);
					Date tjsj = getDateValue("TJSJ", imgJson);
					DrivingPhoto photoXyqm = new DrivingPhoto(null, dribase.getSfzmhm(), null,
							getBytesValue("PHOTO_XYQM", imgJson), "10", tjsj, null, null, null, null, null,
							dribase.getQh(), dribase.getJxdm());
					DrivingPhoto photoSfz = new DrivingPhoto(null, dribase.getSfzmhm(), null,
							getBytesValue("PHOTO_SFZ", imgJson), "03", tjsj, null, null, null, null, null,
							dribase.getQh(), dribase.getJxdm());
					DrivingPhoto photoXy = new DrivingPhoto(null, dribase.getSfzmhm(), null,
							getBytesValue("PHOTO_XY", imgJson), "14", tjsj, null, null, null, null, null,
							dribase.getQh(), dribase.getJxdm());
					DrivingPhoto photoYsqm = new DrivingPhoto(null, dribase.getSfzmhm(), null,
							getBytesValue("PHOTO_YSQM", imgJson), "11", tjsj, null, null, null, null, null,
							dribase.getQh(), dribase.getJxdm());
					imgs.put("10", photoXyqm);
					imgs.put("03", photoSfz);
					imgs.put("14", photoXy);
					imgs.put("11", photoYsqm);
				}
			}
			if (jsonObject.containsKey("ZW_INFO_DATA")) {
				JSONArray imgsJson = jsonObject.getJSONArray("ZW_INFO_DATA");
				int count = imgsJson.size();
				for (int i = 0; i < count; i++) {
					JSONObject imgJson = imgsJson.getJSONObject(i);
					String subKey = getStringValue("SUB_KEY", imgJson);
					subKey = subKey.equals("L1") ? "07" : "08";
					DrivingPhoto photo = new DrivingPhoto(null, dribase.getSfzmhm(), null,
							getBytesValue("INFO", imgJson), subKey, getDateValue("CREATE_TIME", imgJson),
							getDateValue("MODIFY_TIME", imgJson), null, null, null, null, dribase.getQh(),
							dribase.getJxdm());
					imgs.put(subKey, photo);
				}
			}
			dataLog.setSfzmhm(dribase.getSfzmhm());
			dataLog.setQh(dribase.getQh());
			dataLog.setJxdm(dribase.getJxdm());
			dataLog.setClzt(202);
			dataLog.setCjsj(new Date(System.currentTimeMillis()));
			dataScanJobManager.saveAll(dribase, apply, examination, imgs);
		} catch (Exception e) {
			dataLog.setClzt(404);
			dataLog.setCwxx(e.getMessage());
			throw new Exception(e.getMessage());
		}finally{
			String strData =  DataScanJobUtil.mapper.writeValueAsString(dataLog);
			createStrFile(strData);
			scanDataLogManager.save(dataLog);
		}
	}

	private byte[] getBytesValue(String key, JSONObject obj) throws IOException {
		if (obj.containsKey(key)) {
			return decoder.decodeBuffer(obj.getString(key));
		}
		return null;
	}

	private String getStringValue(String key, JSONObject obj) throws IOException {
		if (obj.containsKey(key) && !(obj.get(key) instanceof JSONNull)) {
			return obj.getString(key);
		}
		return null;
	}

	private Character getCharValue(String key, JSONObject obj) throws IOException {
		if (obj.containsKey(key) && obj.getString(key) != null) {
			return obj.getString(key).charAt(0);
		}
		return null;
	}

	private BigDecimal getBigDecimalValue(String key, JSONObject obj) throws IOException {
		if (obj.containsKey(key) && obj.getString(key) != null) {
			return new BigDecimal(obj.getString(key));
		}
		return null;
	}

	private Date getDateValue(String key, JSONObject obj) throws IOException {
		if (obj.containsKey(key) && obj.getString(key) != null) {
			return new Date(obj.getLong(key));
		}
		return null;
	}


	private void createStrFile(String str) throws IOException {
		System.out.println("dddaaa");
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
