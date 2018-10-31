package com.xs.dzyxh.manager;

import java.io.InputStream;

public interface IBarCodeManager {
	public InputStream createBarCode(String msg) throws Exception;
}
