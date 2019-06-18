package com.xs.common;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.Node;
import com.aspose.words.NodeCollection;
import com.aspose.words.NodeType;
import com.aspose.words.SaveFormat;
import com.aspose.words.Shape;
import com.xs.dzyxh.controller.PhotoComposeController;
import com.xs.dzyxh.entity.system.BaseParams;

import oracle.sql.BLOB;

public class Sql2WordUtil {
	
	static Logger logger = Logger.getLogger(Sql2WordUtil.class);
	
	public static InputStream license ;
	static {
		
		license = Sql2WordUtil.class.getClassLoader().getResourceAsStream("\\asposelicense.xml"); // license路径	
		//is = DrivingApplyUitl.class.getClassLoader().getResourceAsStream("\\jdcjszsqb-2016.doc"); // 原始word路径
		License aposeLic = new License();
		try {
			aposeLic.setLicense(Sql2WordUtil.license);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Document sql2WordUtil(final String template,final String sql, HibernateTemplate hibernateTemplate) throws Exception{
		
		Map<String,Object> data = hibernateTemplate.execute(new HibernateCallback<Map<String,Object>>() {
			@Override
			 public Map<String, Object> doInHibernate(Session session) throws HibernateException {
				List<Map<String,Object>> datas =(List<Map<String,Object>>)session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if(!CollectionUtils.isEmpty(datas)) {
					return datas.get(0);
				}
				return null;
			}
		});
		Document doc=null;
		if(data!=null) {
			 doc = createTemplate(template,data);
		}
		return doc;
	}
	
	public static Map<String,Object> sql2MapUtil(final String sql, HibernateTemplate hibernateTemplate) throws Exception{
		
		Map<String,Object> data = hibernateTemplate.execute(new HibernateCallback<Map<String,Object>>() {
			@Override
			 public Map<String, Object> doInHibernate(Session session) throws HibernateException {
				List<Map<String,Object>> datas =(List<Map<String,Object>>)session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if(!CollectionUtils.isEmpty(datas)) {
					return datas.get(0);
				}
				return null;
			}
		});
		
		return data;
	}
	
	public static Document map2WordUtil(final String template,Map<String,Object> data) throws Exception{
		
		Document doc=null;
		if(data!=null) {
			 doc = createTemplate(template,data);
		}
		return doc;
	}
	
	public static Document map2WordUtil2(final String template,Map<String,Object> data) throws Exception{
		
		Document doc=null;
		if(data!=null) {
			 doc = createTemplate2(template,data);
		}
		return doc;
	}
	
	
	
	public static Document createTemplate(String template,Map<String, Object> data) throws Exception {
		
		InputStream wordTemplate = Sql2WordUtil.class.getClassLoader().getResourceAsStream(template);
		Document doc = new Document(wordTemplate);
		NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE, true);// 查询文档中所有wmf图片
		Node[] shapes = shapeCollection.toArray();// 序列化
		
		for(Node node:shapes) {
			Shape shape = (Shape) node;
			com.aspose.words.ImageData i = shape.getImageData();// 获得图片数据
			Object imgObj=data.get(shape.getAlternativeText()); 
			if(imgObj==null) {
				continue;
			}
			SerializableBlobProxy proxy = (SerializableBlobProxy )Proxy.getInvocationHandler(imgObj);
			BLOB blob =(BLOB)proxy.getWrappedBlob();
			if (blob!=null) {// 如果shape类型是ole类型
				InputStream inStream = blob.getBinaryStream();
				i.setImage(inStream);
			}
		}
		
		// 填充文字
		if (data != null) {
			
			String[] fieldNames =  doc.getMailMerge().getFieldNames();
			Object[] fieldValues = new Object[fieldNames.length];
			int i=0;
			for(String fieldName:fieldNames) {
				fieldName=fieldName.toUpperCase();
				if(fieldName.indexOf("CK")==0) {
					String[] temp = fieldName.split("##");
					String value=temp[1];
					if(data.get(temp[2]) instanceof Character){
						fieldValues[i]=((Character)data.get(temp[2])).toString();
					}else{
						fieldValues[i]=(String)data.get(temp[2]);
					}
					if(value.equals(fieldValues[i])) {
						fieldValues[i]="☑";
					}else {
						fieldValues[i]="□";
					}
				}else {
					fieldValues[i] = translateMapValue(data, fieldName);
				}
				i++;
			}
			
			// 合并模版，相当于页面的渲染
			doc.getMailMerge().execute(fieldNames, fieldValues);
			
			
		}
		return doc;
		
	}
	
	public static Document createTemplate2(String template,Map<String, Object> data) throws Exception {
		
		InputStream wordTemplate = Sql2WordUtil.class.getClassLoader().getResourceAsStream(template);
		Document doc = new Document(wordTemplate);
		NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE, true);// 查询文档中所有wmf图片
		Node[] shapes = shapeCollection.toArray();// 序列化
		
		for(Node node:shapes) {
			Shape shape = (Shape) node;
			com.aspose.words.ImageData i = shape.getImageData();// 获得图片数据
			Object imgObj=data.get(shape.getAlternativeText()); 
			if(imgObj==null) {
				continue;
			}
			if(imgObj instanceof Proxy) {
				SerializableBlobProxy proxy = (SerializableBlobProxy) Proxy.getInvocationHandler(imgObj);
				Blob blob =proxy.getWrappedBlob();
				if (blob!=null) {// 如果shape类型是ole类型
					InputStream inStream = blob.getBinaryStream();
					i.setImage(inStream);
				}
			}
			
			if(imgObj instanceof InputStream) {
				InputStream inStream =(InputStream)imgObj;
				i.setImage(inStream);
			}
			
		}
		
		// 填充文字
		if (data != null) {
			
			String[] fieldNames =  doc.getMailMerge().getFieldNames();
			Object[] fieldValues = new Object[fieldNames.length];
			int i=0;
			for(String fieldName:fieldNames) {
				if(fieldName.indexOf("CK##")==0) {
					String[] temp = fieldName.split("##");
					String value=temp[1];
					String key =temp[2].toLowerCase();
					fieldValues[i]=(String)data.get(key);
					if(value.equals(fieldValues[i])) {
						fieldValues[i]="☑";
					}else {
						fieldValues[i]="□";
					}
				}
//				else if(bpsMap!=null&&bpsMap.containsKey(fieldName.toLowerCase())){
//					
//					fieldValues[i] = translateParamVlaue(data.get(fieldName),bpsMap.get(fieldName.toLowerCase()));
//					
//				}
				else {
					fieldValues[i] = translateMapValue(data, fieldName.toLowerCase());
				}
				i++;
			}
			
			// 合并模版，相当于页面的渲染
			doc.getMailMerge().execute(fieldNames, fieldValues);
			
			
		}
		return doc;
		
	}
	
	
	public static String sql2WordUtilCase(String template, String sql, HibernateTemplate hibernateTemplate,String fileName) throws Exception {
		Document doc = sql2WordUtil(template, sql, hibernateTemplate);
		
		if(doc!=null) {
			ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
			iso.setPrettyFormat(true);
			iso.setUseAntiAliasing(true);
			iso.setJpegQuality(80);
			doc.save(getCacheDir()+fileName,iso);
			return fileName;
		}else {
			return null;
		}
		
		
	}
	
	public static String getCacheDir() {
		String path = Sql2WordUtil.class.getClassLoader().getResource("").getPath().toString();
		String temp = path.split("WEB-INF")[0];
		
		return temp+"images/cache/";
		
	}
	
	public static String getCacheDir2() {
		String path = Sql2WordUtil.class.getClassLoader().getResource("").getPath().toString();
		String temp = path.split("WEB-INF")[0];
		
		return temp+"images/cache/qmtp/";
		
	}

	public static String getStringDate(Date date,int type){
		return type==0?new SimpleDateFormat("yyyy年MM月dd日").format(date):new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static String translateMapValue(Map<String,Object> map,String key){
		
		if(map.get(key) instanceof BigDecimal ){
			BigDecimal bg = (BigDecimal) map.get(key);
			return bg.toString();
		}
		if(map.get(key) instanceof Date ){
			if(key.indexOf("PSSJ")==0){
				return getStringDate((Date) map.get(key),1);
			}
			return getStringDate((Date) map.get(key),0);
		}
		if(map.get(key) instanceof Character){
			return ((Character)map.get(key)).toString();
		}
		if(key.equals("XB")){
			return (map.get(key)).equals("1")?"男":"女";
		}
		if(key.equals("GJ")){
			return (map.get(key)).equals("156")?"中国":"外国";
		}
		if("ZETL".equals(key)||"YETL".equals(key)||"ZSZ".equals(key)||"ZXZ".equals(key)||"YSZ".equals(key)||"YXZ".equals(key)){
			return (map.get(key)).equals("1")?"合格":"不合格";
		}
		return (String) map.get(key);
	}
	
	private static Object translateParamVlaue(Object object, List<BaseParams> list) {
		
		if(object!=null) {
			for(BaseParams param : list) {
				if(param.getParamValue().equals(object.toString())) {
					return param.getParamName();
				}
			}
		}
		
		return object;
	}
	
	public static String toCase(Document doc,String paht,String fileName) throws Exception{
		return toCase(doc,paht,fileName,null);
	}
	
	
	public static String toCase(Document doc,String paht,String fileName,Float resolution) throws Exception{
		
		if(doc!=null) {
			ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
			iso.setPrettyFormat(true);
			iso.setUseAntiAliasing(true);
			iso.setJpegQuality(100);
			if(resolution!=null) {
				iso.setResolution(resolution);
			}
			doc.save(getCacheDir2()+fileName,iso);
			//doc.save(paht+fileName,iso);
			
			doc.save(getCacheDir2()+fileName+"_"+".doc");
			return fileName;
		}else {
			return null;
		}
	}
}
