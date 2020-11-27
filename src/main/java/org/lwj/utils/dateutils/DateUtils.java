package org.lwj.utils.dateutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//日期工具类
public class DateUtils {
	
	//格式化日期的工具类
	public static String formatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String chatDate = simpleDateFormat.format(date);
		return chatDate;
	}
	
	//前一天的事件节点
	public static String limitDate() throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Date date=new Date();		
		Calendar calendar = Calendar.getInstance();		
		calendar.setTime(date);		
		calendar.add(Calendar.DAY_OF_MONTH, -5);		
		date = calendar.getTime();		
		return sdf.format(date);
	}
}
