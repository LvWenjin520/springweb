package org.lwj.utils.MyStringUtils;

import java.io.UnsupportedEncodingException;

public class StringEncodingUtil {
	
	public static String stringEnCoding(String text) throws UnsupportedEncodingException {
		//将编码全部过滤成utf-8
		String resultText = new String(text.getBytes("iso-8859-1"),"UTF-8");
		return resultText;
	}
}
