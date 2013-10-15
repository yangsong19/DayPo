package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HumpToUnderlineUtil {
	
	/**
	 * @param str
	 * @return converted str from hump format to underline 
	 */
	public static String humpToUnderline(String str) {
		Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
		StringBuilder builder = new StringBuilder(str);
		for (int i = 0; matcher.find(); i++) {
//			For a matcher m with input sequence s, the expressions m.group() and s.substring(m.start(), m.end()) are equivalent. 
			System.out.println(matcher.start() + ";" + matcher.end() + ";" + matcher.group() + " -> " + i);
//			why should "+i"? str has been alongate every replace operation with "_", the detail is very clever! 
			builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
		}
		if (builder.charAt(0) == '_') {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
	
	public static String underlineToHump(String str) {
		Matcher matcher = Pattern.compile("_[a-z]").matcher(str);
		StringBuilder builder = new StringBuilder(str);
		for(int i = 0; matcher.find(); i ++) {
			System.out.println(matcher.start() + ";" + matcher.end() + ";" + matcher.group() + " -> " + i);
//			why should "-i"? str has been narrowed every replace operation 
			builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
		}
		if(Character.isUpperCase(builder.charAt(0)))
			builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
		return builder.toString();
	}
	
	public static void main(String[] args) {
		String camel = "HumpToUnderline";
		String under = "Hump_to_underline";
		System.out.println(humpToUnderline(camel));
		System.out.println(underlineToHump(under));
	}
	
/*	
 *  4;5;T -> 0
	6;7;U -> 1
	hump_to_underline
	4;6;_t -> 0
	7;9;_u -> 1
	humpToUnderline
 *	
 */
}
