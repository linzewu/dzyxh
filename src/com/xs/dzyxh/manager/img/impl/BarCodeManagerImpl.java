package com.xs.dzyxh.manager.img.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.manager.img.IBarCodeManager;
@Service("barCodeManager")
public class BarCodeManagerImpl implements IBarCodeManager {
	public InputStream createBarCode(String msg) throws Exception {
		
		JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(),
				EAN13TextPainter.getInstance());
		BufferedImage localBufferedImage = localJBarcode.createBarcode(msg);
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
		ImageIO.write(localBufferedImage, "gif", os);  
		InputStream is = new ByteArrayInputStream(os.toByteArray());  
		return is;
	}
}
