package com.xinlab.blueapple.contenttool.test;

import java.io.UnsupportedEncodingException;


public class THeader {
	//	text/html;charset=UTF-8
	private static String getValidContent(String content) throws UnsupportedEncodingException {

		if (content == null || "".equals(content.trim()))
			return "";
		
		String charset=null;
		String ct = "text/html;charset=UTF-8";//request.getHeader("content-type");
 		if (ct!=null){
 			int index = ct.indexOf(';');
 			System.out.println("@:"+index);//9
 			if (index>0){
 				index = ct.indexOf("charset=",index);
 				System.out.println("@@:"+index);//10
 				if (index>0){
 					charset = ct.substring(index+8);
 					System.out.println(charset);//UTF-8
 					charset = charset.replace('"', ' ').replace('\'', ' ').trim();
 					System.out.println(charset);//UTF-8
 				}
 			}
 		}
 		content= new String(content.getBytes(charset!=null?charset:"ISO-8859-1"),"UTF-8");
		return content;//hi,boy'"
	}
	public static void main(String[] args) {
		try {
			System.out.println(getValidContent("'hi,boy'\""));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
