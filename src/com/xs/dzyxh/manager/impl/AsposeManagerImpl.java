package com.xs.dzyxh.manager.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.Node;
import com.aspose.words.NodeCollection;
import com.aspose.words.NodeType;
import com.aspose.words.SaveFormat;
import com.aspose.words.Shape;
import com.aspose.words.ShapeType;
import com.xs.dzyxh.entity.aspose.ImageData;
import com.xs.dzyxh.manager.IAsposeManager;

@Service("asposeManager")
public class AsposeManagerImpl implements IAsposeManager {

	public OutputStream convertImg(InputStream word, OutputStream out, ImageSaveOptions iso,
			Map<String, String> stringDatas, List<ImageData> imageDatas) throws Exception {
		Document doc = createDocument(word, stringDatas, imageDatas);
		if (out == null) {
			out = new ByteArrayOutputStream();
		}
		doc.save(out, iso);
		return out;
	}

	public OutputStream convertPng(InputStream word, OutputStream out, Map<String, String> stringDatas,
			List<ImageData> imageDatas) throws Exception {
		ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.PNG);
		iso.setPrettyFormat(true);
		iso.setUseAntiAliasing(true);
		iso.setJpegQuality(80);
		return convertImg(word, out, iso, stringDatas, imageDatas);
	}

	public OutputStream convertJpeg(InputStream word, OutputStream out, Map<String, String> stringDatas,
			List<ImageData> imageDatas) throws Exception {
		ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
		iso.setPrettyFormat(true);
		iso.setUseAntiAliasing(true);
		iso.setJpegQuality(80);
		return convertImg(word, out, iso, stringDatas, imageDatas);
	}

	public void convertImg(InputStream word, String saveFilePath, ImageSaveOptions iso, Map<String, String> stringDatas,
			List<ImageData> imageDatas) throws Exception {
		Document doc = createDocument(word, stringDatas, imageDatas);
		doc.save(saveFilePath, iso);
	}

	public void convertPng(InputStream word, String saveFilePath, Map<String, String> stringDatas,
			List<ImageData> imageDatas) throws Exception {
		ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.PNG);
		iso.setPrettyFormat(true);
		iso.setUseAntiAliasing(true);
		iso.setJpegQuality(80);
		convertImg(word, saveFilePath, iso, stringDatas, imageDatas);
	}

	public void convertJpeg(InputStream word, String saveFilePath, Map<String, String> stringDatas,
			List<ImageData> imageDatas) throws Exception {
		ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
		iso.setPrettyFormat(true);
		iso.setUseAntiAliasing(true);
		iso.setJpegQuality(80);
		convertImg(word, saveFilePath, iso, stringDatas, imageDatas);
	}

	public Document createDocument(InputStream word, Map<String, String> stringDatas, List<ImageData> imageDatas)
			throws Exception {

		Document doc = new Document(word);
		// 填充图片
		if (imageDatas != null) {
			DocumentBuilder builder = new DocumentBuilder(doc);
			NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE, true);// 查询文档中所有wmf图片
			Node[] shapes = shapeCollection.toArray();// 序列化
			for (ImageData image : imageDatas) {
				//插入到指定标签位置
				if (image.getInsertType() == 0) {
					Shape shape = new Shape(doc, ShapeType.IMAGE);
					shape.getImageData().setImage((InputStream) image.getValue());
					// 设置宽度
					if (image.getWidth() != null) {
						shape.setWidth(image.getWidth());
					}
					// 设置高度
					if (image.getHeight() != null) {
						shape.setHeight(image.getHeight());
					}
					// 对齐方式
					shape.setHorizontalAlignment(image.getAlign()); // 居中对齐
					if (doc.getRange().getBookmarks().get(image.getName()) != null) {
						builder.moveToBookmark(image.getName());
						builder.insertNode(shape);
					}
				} else {
					//替换到指定图片
					if (shapes.length > 0) {
						for (Node node : shapes) {
							Shape shape = (Shape) node;
							com.aspose.words.ImageData i = shape.getImageData();// 获得图片数据
							if (shape.getAlternativeText().equals(image.getName())) {// 如果shape类型是ole类型
								i.setImage((InputStream)image.getValue());
								break;
							}

						}
					}
				}
			}
		}
		// 填充文字
		if (stringDatas != null) {
			String[] fieldNames = new String[stringDatas.size()];
			Object[] fieldValues = new Object[stringDatas.size()];
			int i = 0;
			for (String key : stringDatas.keySet()) {
				fieldNames[i] = key;
				fieldValues[i] = stringDatas.get(key);
				i++;
			}
			// 合并模版，相当于页面的渲染
			doc.getMailMerge().execute(fieldNames, fieldValues);
		}
		return doc;
	}

}
