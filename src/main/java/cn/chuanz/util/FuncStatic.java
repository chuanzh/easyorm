package cn.chuanz.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

/**
 * 公用方法
 * @author chuan.zhang
 *
 */
public class FuncStatic {

	/**
	 * 输出异常־
	 * @param e
	 * @return
	 */
	public static String errorTrace(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);  
		return e.toString();
 	}
	
	/**
	 * 匹配字符串中的占位符 如:{0},{1}
	 * @param content
	 * @param arguments
	 * @return
	 */
	public static String format(String content, Object... arguments) {
		if (content == null)
			return "";
		String to = null;
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == null)
				to = "";
			else
				to = arguments[i].toString();

			content = StringUtils.replace(content, "{" + i + "}", to);
		}

		return content;
	}
	
	public static boolean checkIsEmpty(Object var) {
		if (var == null)
			return true;
		if (var.toString().equals(""))
			return true;
		return false;
	}
	
	/**
	 * 去掉一个字符串两端的某个字符串
	 * 
	 * @param str
	 *            原字符串
	 * @param c
	 *            需要去掉的字符串
	 * @return
	 */
	public static String trim(String str, String c) {
		while (str.startsWith(c))
			str = str.substring(c.length(), str.length());
		while (str.endsWith(c))
			str = str.substring(0, str.length() - c.length());
		return str;
	}
	
}
