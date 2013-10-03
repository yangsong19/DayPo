package com.xinlab.blueapple.contenttool.servlet;

import org.apache.commons.io.FilenameUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-23
 * Time: 16:03:16
 * To change this template use File | Settings | File Templates.
 */
public class VideoUpload extends XinBaseServlet{
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("test & debug/","utf-8"));
        String filename = "c:\\file";
        System.out.println(FilenameUtils.getExtension(filename));
    }
}
