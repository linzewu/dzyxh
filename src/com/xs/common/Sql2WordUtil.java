package com.xs.common;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.Node;
import com.aspose.words.NodeCollection;
import com.aspose.words.NodeType;
import com.aspose.words.SaveFormat;
import com.aspose.words.Shape;
import com.aspose.words.net.System.Data.DataTable;

import oracle.sql.BLOB;

public class Sql2WordUtil {
	
	static Logger logger = Logger.getLogger(Sql2WordUtil.class);
	
	public static Document sql2WordUtil(final String template,final String sql, HibernateTemplate hibernateTemplate) throws Exception{
		
		Map<String,Object> data = hibernateTemplate.execute(new HibernateCallback<Map<String,Object>>() {
			@Override
			 public Map<String, Object> doInHibernate(Session session) throws HibernateException {
				 Map<String,Object> data =(Map<String,Object>)session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
				return data;
			}
		});
		Document doc = createTemplate(template,data);
		
		//ByteArrayOutputStream out = new ByteArrayOutputStream();
		//doc.save(out, iso);
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
			SerializableBlobProxy proxy = (SerializableBlobProxy )Proxy.getInvocationHandler(imgObj);
			BLOB blob =(BLOB)proxy.getWrappedBlob();
			if (blob!=null) {// 如果shape类型是ole类型
				InputStream inStream = blob.getBinaryStream();
				i.setImage(inStream);
				break;
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
					fieldValues[i]=(String)data.get(temp[2]);
					if(value.equals(fieldValues[i])) {
						fieldValues[i]="☑";
					}else {
						fieldValues[i]="□";
					}
				}else {
					fieldValues[i]=(String)data.get(fieldName);
				}
				i++;
			}
			
			// 合并模版，相当于页面的渲染
			doc.getMailMerge().execute(fieldNames, fieldValues);
			
			
		}
		return doc;
		
	}
	
	
	public static void sql2WordUtilCase(String template, String sql, HibernateTemplate hibernateTemplate,String fileName) throws Exception {
		Document doc = sql2WordUtil(template, sql, hibernateTemplate);
		
		ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
		iso.setPrettyFormat(true);
		iso.setUseAntiAliasing(true);
		iso.setJpegQuality(80);
		doc.save(getCacheDir()+fileName,iso);
	}
	
	public static String getCacheDir() {
		String path = Sql2WordUtil.class.getClassLoader().getResource("").getPath().toString();
		String temp = path.split("WEB-INF")[0];
		
		return temp+"cache/";
		
	}

}
