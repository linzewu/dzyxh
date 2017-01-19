package com.xs.dzyxh.manager.job;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.xs.common.FormatUtil;
import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingApplyId;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.entity.system.ScanDataLog;
import com.xs.dzyxh.entity.tongan.DrvTempMid;
import com.xs.dzyxh.entity.tongan.DrvTempMidId;
import com.xs.dzyxh.entity.tongan.SqPhotos;
import com.xs.dzyxh.entity.tongan.SqPhotosId;
import com.xs.dzyxh.entity.tongan.TjPhotos;
import com.xs.dzyxh.entity.tongan.ZwEnrollTemp;
import com.xs.dzyxh.entity.tongan.ZwEnrollTempId;
import com.xs.dzyxh.entity.tongan.ZwInfoData;
import com.xs.dzyxh.entity.tongan.ZwInfoDataId;
import com.xs.dzyxh.entity.tongan.ZwUserInfo;
import com.xs.dzyxh.manager.sys.IScanDataLogManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@Service("dataScanJob")
public class DataScanJob {

	static Logger logger = Logger.getLogger(DataScanJob.class);

	@Value("${scan.data.out}")
	String outPath = "D:\\data\\out";

	@Value("${scan.data.in}")
	String inPath = "D:\\data\\in";
	@Value("${scan.data.error}")
	String errorPath = "D:\\data\\error";
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
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return buffer;
	}

	public static String readFile(File file) {
		String fileContent = "";
		try {

			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "gbk");
				BufferedReader reader = new BufferedReader(read);
				String line;
				while ((line = reader.readLine()) != null) {
					fileContent += line;
				}
				read.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public static void main(String[] arg) throws Exception {
		
		String[] temp="fdsfasd.img".split("\\.");
		
		System.out.println(temp[temp.length-1]);

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
/*	@Resource(name = "tonGanManager")
	private ITonGanManager tonGanManager;*/

	BASE64Decoder decoder = new BASE64Decoder();

	/*
	 * @Resource(name="dataQueryManager") private IDataQueryManager
	 * dataQueryManager;
	 */
	@Scheduled(fixedDelay = 1000)
	public void readData() {
		String[] filelist = inFile.list();
		for (int i = 0; i < filelist.length; i++) {
			
			String[] temp=filelist[i].split("\\.");
			
			if (temp[temp.length-1].equals("data")) {
				File file = new File(inPath + "\\" + filelist[i]);
				// byte[] data = getBytes(file);

				try {
					String data = readFile(file);
					this.transformationData(data);
					file.delete();
				} catch (Exception e) {
					File errorfile = new File(
							errorPath + "\\" + FormatUtil.date.format(new Date(System.currentTimeMillis())));
					if (!errorfile.exists() && !errorfile.isDirectory()) {
						errorfile.mkdirs();
					}
					// 数据文件剪切到错误文件夹
					DataScanJobUtil.cutFile(file, errorfile);
				}
			}
		}
	}
	
		

	@Scheduled(fixedDelay = 1000)
	public void readImgData() {
		String[] filelist = inFile.list();
		for (int i = 0; i < filelist.length; i++) {
			String[] temp=filelist[i].split("\\.");
			
			if (temp[temp.length-1].equals("img")) {
				File file = new File(inPath + "\\" + filelist[i]);
				// byte[] data = getBytes(file);

				try {
					this.transformationImgData(readFile(file));
					file.delete();
				} catch (Exception e) {
					File errorfile = new File(
							errorPath + "\\" + FormatUtil.date.format(new Date(System.currentTimeMillis())));
					if (!errorfile.exists() && !errorfile.isDirectory()) {
						errorfile.mkdirs();
					}
					// 数据文件剪切到错误文件夹
					DataScanJobUtil.cutFile(file, errorfile);
				}
			}
		}
	}

	public void transformationImgData(String data) throws Exception {
		if (data == null) {
			return;
		}
		ScanDataLog dataLog = new ScanDataLog();
		try {
			JSONObject jsonObject = JSONObject.fromObject(new String(data));
			SqPhotos sqPhotos = getSqPhotos(jsonObject);
			Map<String, DrivingPhoto> imgs = getDrivingPhotos(jsonObject);
			dataScanJobManager.saveImg(sqPhotos, imgs);
			dataLog.setSfzmhm(sqPhotos.getId().getSfzmhm());
			dataLog.setQh(sqPhotos.getId().getQh());
			dataLog.setJxdm(sqPhotos.getId().getJxdm());
			dataLog.setClzt(200);
			dataLog.setCjsj(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			dataLog.setClzt(500);
			dataLog.setCwxx("tongan数据入库失败:" + e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			String strData = DataScanJobUtil.mapper.writeValueAsString(dataLog);
			createStrFile(strData, ".img");
			scanDataLogManager.save(dataLog);
		}
	}

	private SqPhotos getSqPhotos(JSONObject imgJson) throws IOException {
		SqPhotos data = new SqPhotos();
		String sfz = getStringValue("SFZMHM", imgJson);
		String qh = getStringValue("QH", imgJson);
		String jxdm = getStringValue("JXDM", imgJson);
		String sqrq = getStringValue("SQRQ", imgJson);
		
		String xm=getStringValue("XM",imgJson);
		String ywlx=getStringValue("YWLX",imgJson);
		String zkcx=getStringValue("ZKCX",imgJson);
		
		SqPhotosId id = new SqPhotosId();
		id.setJxdm(jxdm);
		id.setQh(qh);
		id.setSfzmhm(sfz);
		data.setId(id);
		data.setPhotoDlrqm(getBytesValue("PHOTO_DLRQM", imgJson));
		data.setPhotoDlrsfzfm(getBytesValue("PHOTO_DLRSFZFM", imgJson));
		data.setPhotoDlrsfzzm(getBytesValue("PHOTO_DLRSFZZM", imgJson));
		data.setPhotoJzzfm(getBytesValue("PHOTO_JZZFM", imgJson));
		data.setPhotoJzzzm(getBytesValue("PHOTO_JZZZM", imgJson));
		data.setPhotoMgzp(getBytesValue("PHOTO_MGZP", imgJson));
		data.setPhotoSqrqm(getBytesValue("PHOTO_SQRQM", imgJson));
		data.setPhotoSfzfm(getBytesValue("PHOTO_SFZFM", imgJson));
		data.setPhotoSfzzm(getBytesValue("PHOTO_SFZZM", imgJson));
		data.setPhotoSqb(getBytesValue("PHOTO_SQB", imgJson));
		data.setPhotoTjb(getBytesValue("PHOTO_TJB", imgJson));
		data.setXm(xm);
		data.setZkcx(zkcx);
		data.setYwlx(ywlx);
		data.setSqrq(sqrq);
		return data;
	}

	private Map<String, DrivingPhoto> getDrivingPhotos(JSONObject imgJson) throws IOException, ParseException {
		Map<String, DrivingPhoto> imgs = new HashMap<String, DrivingPhoto>();
		String sfz = getStringValue("SFZMHM", imgJson);
		String qh = getStringValue("QH", imgJson);
		String jxdm = getStringValue("JXDM", imgJson);
		
		String rq = getStringValue("SQRQ", imgJson);
		Date sqrq =null;
		if(rq!=null){
			SimpleDateFormat sd=new SimpleDateFormat("yyyy/MM/dd");
			 sqrq = sd.parse(rq);
		}
		
		
		// 申请人签名
		byte[] blob = getBytesValue("PHOTO_SQRQM", imgJson);
		if (blob != null) {
			DrivingPhoto photosqrqm = new DrivingPhoto(null, sfz, null, blob, "05", sqrq, null, null, null, null, null,
					qh, jxdm);
			imgs.put("05", photosqrqm);
			DrivingPhoto phototjsqrqm = new DrivingPhoto(null, sfz, null, blob, "10", sqrq, null, null, null, null,
					null, qh, jxdm);
			imgs.put("10", phototjsqrqm);
		}
		// 代理人签名
		blob = getBytesValue("PHOTO_DLRQM", imgJson);
		if (blob != null) {
			DrivingPhoto photosqrqm = new DrivingPhoto(null, sfz, null, blob, "06", sqrq, null, null, null, null, null,
					qh, jxdm);
			imgs.put("06", photosqrqm);
			DrivingPhoto phototjsqrqm = new DrivingPhoto(null, sfz, null, blob, "12", sqrq, null, null, null, null,
					null, qh, jxdm);
			imgs.put("12", phototjsqrqm);
		}
		// 身份证正面
		blob = getBytesValue("PHOTO_SFZZM", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "15", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("15", photo);
		}
		// 身份证反面
		blob = getBytesValue("PHOTO_SFZFM", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "16", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("16", photo);
		}
		// 居住证正面
		blob = getBytesValue("PHOTO_JZZZM", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "17", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("17", photo);
		}
		// 居住证反面
		blob = getBytesValue("PHOTO_JZZFM", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "18", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("18", photo);
		}
		// 代理人身份证正面
		blob = getBytesValue("PHOTO_DLRSFZZM", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "19", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("19", photo);
		}
		// 代理人身份证反面
		blob = getBytesValue("PHOTO_DLRSFZFM", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "20", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("20", photo);
		}
		// 免冠照片
		blob = getBytesValue("PHOTO_MGZP", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "04", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("04", photo);
		}
		// 申请表
		blob = getBytesValue("PHOTO_SQB", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "91", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("91", photo);
		}
		// 体检表
		blob = getBytesValue("PHOTO_TJB", imgJson);
		if (blob != null) {
			DrivingPhoto photo = new DrivingPhoto(null, sfz, null, blob, "92", sqrq, null, null, null, null, null, qh,
					jxdm);
			imgs.put("92", photo);
		}
		return imgs;
	}

	public void transformationData(String data) throws Exception {
		if (data == null) {
			return;
		}
		ScanDataLog dataLog = new ScanDataLog();
		try {
			List<Object> tonGanDatas=this.getTonGanDatas(data);
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
				examination.setSqfs(getStringValue("SQFS", base));
				examination.setXm(dribase.getXm());
				examination.setXb(dribase.getXb());
				examination.setGj(dribase.getGj());
				examination.setCsrq(dribase.getCsrq());
				examination.setLxdh(dribase.getLxdh());
				examination.setYjdz(dribase.getLxzsxxdz());
				examination.setLxdh(dribase.getLxdh());
				examination.setSqzjcxdh(getStringValue("ZKCX", base));
				examination.setDyslze(getCharValue("DYSLZE", base));
				examination.setYyspsy(getBigDecimalValue("YYSPSY", base));

				apply.setJxmc(getStringValue("JXMC", base));
				apply.setSqzjcxdh(examination.getSqzjcxdh());
				apply.setLy(getCharValue("LY", base));
				apply.setDyslze(examination.getDyslze());
				apply.setYyspsy(examination.getYyspsy());
				apply.setYwlx(getStringValue("YWLX", base));
				apply.setSg(examination.getSg());
				apply.setId(new DrivingApplyId());
				apply.getId().setQh(dribase.getQh());
				apply.getId().setJxdm(dribase.getJxdm());
				apply.getId().setSfzmhm(dribase.getSfzmhm());
				apply.setYjdz(dribase.getLxzsxxdz());
				apply.setBsl(examination.getBsl());
				apply.setZxz(examination.getZxz());
				apply.setYxz(examination.getYxz());
				apply.setQgjb(examination.getQgjb());
				apply.setTjyymc(examination.getTjyymc());
				

			}
			if (jsonObject.containsKey("SQ_PHOTOS") && !(jsonObject.get("SQ_PHOTOS") instanceof JSONNull)) {
				JSONArray JsonDatas = jsonObject.getJSONArray("SQ_PHOTOS");
				if (JsonDatas.size() > 0) {
					Map<String, DrivingPhoto> photos = getDrivingPhotos(JsonDatas.getJSONObject(0));			
					String rq = getStringValue("SQRQ", JsonDatas.getJSONObject(0));
					if(rq!=null){
						SimpleDateFormat sd=new SimpleDateFormat("yyyy/MM/dd");
						apply.setSqrq( sd.parse(rq));
					}
					imgs.putAll(photos);
				}
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
			dataLog.setClzt(200);
			dataLog.setCjsj(new Date(System.currentTimeMillis()));
			dataScanJobManager.saveAll(dribase, apply, examination, imgs,tonGanDatas);
		} catch (Exception e) {
			dataLog.setClzt(500);
			dataLog.setCwxx( ".data数据入库失败:" + e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			String strData = DataScanJobUtil.mapper.writeValueAsString(dataLog);
			createStrFile(strData, ".data");
			scanDataLogManager.save(dataLog);
		}
	}

	public List<Object> getTonGanDatas(String data) throws Exception {
		if (data == null) {
			return null;
		}
		List<Object> datas = new ArrayList<Object>();
		JSONObject jsonObject = JSONObject.fromObject(new String(data));
		if (jsonObject.containsKey("DRV_TEMP_MID")) {
			JSONObject base = jsonObject.getJSONObject("DRV_TEMP_MID");
			DrvTempMid mid = new DrvTempMid();
			DrvTempMidId id = new DrvTempMidId();
			id.setJxdm(getStringValue("JXDM", base));
			id.setQh(getStringValue("QH", base));
			id.setSfzmhm(getStringValue("SFZMHM", base));
			mid.setId(id);
			mid.setBsl(getStringValue("BSL", base));
			mid.setCsrq(getDateValue("CSRQ", base));
			mid.setDjzsxxdz(getStringValue("DJZSXXDZ", base));
			mid.setDjzsxzqh(getStringValue("DJZSXZQH", base));
			mid.setDyslze(getStringValue("DYSLZE", base));
			mid.setDzxx(getStringValue("DZXX", base));
			mid.setGj(getStringValue("GJ", base));
			mid.setHmcd(getStringValue("HMCD", base));
			mid.setJxmc(getStringValue("JXMC", base));
			mid.setJyjbqk(getStringValue("JYJBQK", base));
			mid.setLxdh(getStringValue("LXDH", base));
			mid.setLxzsxxdz(getStringValue("LXZSXXDZ", base));
			mid.setLxzsxzqh(getStringValue("LXZSXZQH", base));
			mid.setLxzsyzbm(getStringValue("LXZSYZBM", base));
			mid.setLy(getStringValue("LY", base));
			mid.setQbqg(getStringValue("QBQG", base));
			mid.setQgjb(getStringValue("QGJB", base));
			mid.setSfjyjb(getStringValue("SFJYJB", base));
			mid.setSfngzzzl(getStringValue("SFNGZZZL", base));
			mid.setSfzmmc(getStringValue("SFZMMC", base));
			mid.setSg(getIntegerValue("SG", base));
			mid.setSjbj(getStringValue("SJBJ", base));
			mid.setTjrq(getDateValue("TJRQ", base));
			mid.setTjyymc(getStringValue("TJYYMC", base));
			mid.setTlsfjz(getStringValue("TLSFJZ", base));
			mid.setXb(getStringValue("XB", base));
			mid.setXm(getStringValue("XM", base));
			mid.setYddh(getStringValue("YDDH", base));
			mid.setYetl(getStringValue("YETL", base));
			mid.setYsl(getIntegerValue("YSL", base));
			mid.setYsz(getStringValue("YSZ", base));
			mid.setYwlx(getStringValue("YWLX", base));
			mid.setYxz(getStringValue("YXZ", base));
			mid.setYysfjz(getStringValue("YYSFJZ", base));
			mid.setYyspsy(getIntegerValue("YYSPSY", base));
			mid.setZetl(getStringValue("ZETL", base));
			mid.setZkcx(getStringValue("ZKCX", base));
			mid.setZsl(getIntegerValue("ZSL", base));
			mid.setZsz(getStringValue("ZSZ", base));
			mid.setZxz(getStringValue("ZXZ", base));
			mid.setZysfjz(getStringValue("ZYSFJZ", base));
			mid.setZzsb(getStringValue("ZZSB", base));
			mid.setZzzl(getStringValue("ZZZL", base));
			mid.setZzzm(getStringValue("ZZZM", base));
			datas.add(mid);

		}
		if (jsonObject.containsKey("TJ_PHOTOS")) {
			JSONArray imgsJson = jsonObject.getJSONArray("TJ_PHOTOS");
			int count = imgsJson.size();
			for (int i = 0; i < count; i++) {
				JSONObject imgJson = imgsJson.getJSONObject(i);
				TjPhotos tj = new TjPhotos();
				tj.setIdno(getStringValue("IDNO", imgJson));
				tj.setPhotoSfz(getBytesValue("PHOTO_SFZ", imgJson));
				tj.setPhotoXy(getBytesValue("PHOTO_XY", imgJson));
				tj.setPhotoXyqm(getBytesValue("PHOTO_XYQM", imgJson));
				tj.setPhotoYsqm(getBytesValue("PHOTO_YSQM", imgJson));
				tj.setTjrq(getDateValue("TJRQ", imgJson));
				datas.add(tj);
			}
		}
		if (jsonObject.containsKey("ZW_ENROLL_TEMP")) {
			JSONArray JsonDatas = jsonObject.getJSONArray("ZW_ENROLL_TEMP");
			int count = JsonDatas.size();
			for (int i = 0; i < count; i++) {
				JSONObject JsonData = JsonDatas.getJSONObject(i);
				ZwEnrollTemp zwEnrollTemp = new ZwEnrollTemp();
				ZwEnrollTempId id = new ZwEnrollTempId();
				id.setAuthenInfo(getStringValue("AUTHEN_INFO", JsonData));
				id.setUserId(getStringValue("USER_ID", JsonData));
				zwEnrollTemp.setCreateTime(getStringValue("CREATE_TIME", JsonData));
				zwEnrollTemp.setId(id);
				zwEnrollTemp.setModifyTime(getStringValue("MODIFY_TIME", JsonData));
				zwEnrollTemp.setRevokeTime(getStringValue("REVOKE_TIME", JsonData));
				zwEnrollTemp.setServiceCode(getIntegerValue("SERVICE_CODE", JsonData));
				zwEnrollTemp.setServiceType(getIntegerValue("SERVICE_TYPE", JsonData));
				zwEnrollTemp.setTemplate(getBytesValue("TEMPLATE", JsonData));
				zwEnrollTemp.setTempSize(getIntegerValue("TEMP_SIZE", JsonData));
				zwEnrollTemp.setTempType(getIntegerValue("TEMP_TYPE", JsonData));
				zwEnrollTemp.setVersion(getIntegerValue("VERSION", JsonData));
				datas.add(zwEnrollTemp);
			}
		}
		if (jsonObject.containsKey("ZW_USER_INFO")) {
			JSONArray JsonDatas = jsonObject.getJSONArray("ZW_USER_INFO");
			int count = JsonDatas.size();
			for (int i = 0; i < count; i++) {
				JSONObject JsonData = JsonDatas.getJSONObject(i);
				ZwUserInfo zwUserInfo = new ZwUserInfo();
				zwUserInfo.setCreateTime(getStringValue("CREATE_TIME", JsonData));
				zwUserInfo.setId(getStringValue("USER_ID", JsonData));
				zwUserInfo.setModifyTime(getStringValue("MODIFY_TIME", JsonData));
				datas.add(zwUserInfo);
			}
		}
		if (jsonObject.containsKey("SQ_PHOTOS") && !(jsonObject.get("SQ_PHOTOS") instanceof JSONNull)) {
			JSONArray JsonDatas = jsonObject.getJSONArray("SQ_PHOTOS");
			int count = JsonDatas.size();
			if (count > 0) {
				JSONObject JsonData = JsonDatas.getJSONObject(0);
				SqPhotos sqPhotos = new SqPhotos();
				SqPhotosId id = new SqPhotosId();
				id.setJxdm(getStringValue("JXDM", JsonData));
				id.setQh(getStringValue("QH", JsonData));
				id.setSfzmhm(getStringValue("SFZMHM", JsonData));
				sqPhotos.setCzrq(getStringValue("CZRQ", JsonData));
				sqPhotos.setId(id);
				sqPhotos.setPhotoDlrqm(getBytesValue("PHOTO_DLRQM", JsonData));
				sqPhotos.setPhotoDlrsfzfm(getBytesValue("PHOTO_DLRSFZFM", JsonData));
				sqPhotos.setPhotoDlrsfzzm(getBytesValue("PHOTO_DLRSFZZM", JsonData));
				sqPhotos.setPhotoJzzfm(getBytesValue("PHOTO_JZZFM", JsonData));
				sqPhotos.setPhotoJzzzm(getBytesValue("PHOTO_JZZZM", JsonData));
				sqPhotos.setPhotoMgzp(getBytesValue("PHOTO_MGZP", JsonData));
				sqPhotos.setPhotoSqrqm(getBytesValue("PHOTO_SQRQM", JsonData));
				sqPhotos.setPhotoSfzfm(getBytesValue("PHOTO_SFZFM", JsonData));
				sqPhotos.setPhotoSfzzm(getBytesValue("PHOTO_SFZZM", JsonData));
				sqPhotos.setPhotoSqb(getBytesValue("PHOTO_SQB", JsonData));
				sqPhotos.setPhotoTjb(getBytesValue("PHOTO_TJB", JsonData));
				sqPhotos.setSjbj(getCharValue("BSL", JsonData));
				sqPhotos.setSqrq(getStringValue("SQRQ", JsonData));
				datas.add(sqPhotos);
			}

		}
		if (jsonObject.containsKey("ZW_INFO_DATA")) {
			JSONArray imgsJson = jsonObject.getJSONArray("ZW_INFO_DATA");
			int count = imgsJson.size();
			for (int i = 0; i < count; i++) {
				JSONObject imgJson = imgsJson.getJSONObject(i);
				ZwInfoData zwInfoData = new ZwInfoData();
				ZwInfoDataId id = new ZwInfoDataId();
				id.setId(getStringValue("USER_ID", imgJson));
				id.setSubKey(getStringValue("SUB_KEY", imgJson));
				zwInfoData.setId(id);
				zwInfoData.setCreateTime(getStringValue("CREATE_TIME", imgJson));
				zwInfoData.setInfo(getBytesValue("INFO", imgJson));
				zwInfoData.setInfoSize(getIntegerValue("INFO_SIZE", imgJson));
				zwInfoData.setInfoType(getIntegerValue("INFO_TYPE", imgJson));
				zwInfoData.setMainKey(getStringValue("MAIN_KEY", imgJson));
				zwInfoData.setModifyTime(getStringValue("MODIFY_TIME", imgJson));
				zwInfoData.setRevokeTime(getStringValue("REVOKE_TIME", imgJson));
				zwInfoData.setSecurity(getIntegerValue("SECURITY", imgJson));
				datas.add(zwInfoData);
			}
		}
		return datas;
	}

	private byte[] getBytesValue(String key, JSONObject obj) throws IOException {
		if (obj.containsKey(key) && !(obj.get(key) instanceof JSONNull)) {
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
		if (obj.containsKey(key) && !(obj.get(key) instanceof JSONNull)) {
			return new BigDecimal(obj.getString(key));
		}
		return null;
	}

	private Integer getIntegerValue(String key, JSONObject obj) throws IOException {
		if (obj.containsKey(key) && !(obj.get(key) instanceof JSONNull)) {
			return obj.getInt(key);
		}
		return null;
	}

	private Date getDateValue(String key, JSONObject obj) throws IOException {
		if (obj.containsKey(key) &&!(obj.get(key) instanceof JSONNull)) {
			return new Date(obj.getLong(key));
		}
		return null;
	}

	private void createStrFile(String str, String hz) throws IOException {
		FileOutputStream fos = null;
		try {
			String fileName = UUID.randomUUID() + hz;
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
