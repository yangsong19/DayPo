<%@ page import="com.xinlab.blueapple.contenttool.util.ContentToolUtil" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.xinlab.blueapple.contenttool.common.XinDBUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<% 
    String videoTitle = request.getParameter("videoTitle");
    String videoUrl = request.getParameter("videoUrl");
    String keywords = request.getParameter("keywords");
    String pcc = request.getParameter("pcc");
	String channel = request.getParameter("channel");

    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setHeaderEncoding("UTF-8");

    List<FileItem> items = upload.parseRequest(request);
    if (items != null) {
        Iterator<FileItem> itr = items.iterator();
        while (itr.hasNext()) {
            FileItem item = (FileItem) itr.next();
            if (item.isFormField() && "videoTitle".equals(item.getFieldName())) {
                videoTitle = new String(item.get(), "utf-8");
            }else if(item.isFormField() && "videoUrl".equals(item.getFieldName())){
                videoUrl = StringUtils.trimToNull(new String(item.get(), "utf-8")); 
            }else if(item.isFormField() && "keywords".equals(item.getFieldName())){
            	keywords = StringUtils.trimToNull(new String(item.get(), "utf-8"));
            }else if(item.isFormField() && "pcc".equals(item.getFieldName())){
            	pcc = StringUtils.trimToNull(new String(item.get(), "utf-8"));
            }else if(item.isFormField() && "channel".equals(item.getFieldName())){
            	channel = StringUtils.trimToNull(new String(item.get(), "utf-8"));
            }
        }
    }
    String watchUrl = ContentToolUtil.getWatchUrl(videoUrl, videoTitle, keywords,pcc,channel);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String date = df.format(new Date());
    int boo = XinDBUtil.instertFeeditem(pcc,videoTitle,watchUrl,videoTitle,date,watchUrl,videoTitle,null,keywords);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upload Video Result Page - vuclip.com</title>
    <link href="css/default.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="header">
    <h1 id="logo"></h1>

    <div id="version">v1.0</div>
</div>

<div id="content">
    <h2>Video Upload Result Page</h2>
    <% if (StringUtils.trimToNull(videoTitle) == null || StringUtils.trimToNull(videoUrl) == null || boo == -1) { %>
    <p>Your video was not received.</p>
    <% } else {%>
    <table>
        <tr>
            <td>Video Title:</td>
            <td><%=videoTitle%>
            </td>
        </tr>
        <tr>
            <td>Video Keywords:</td>
            <td><%=keywords%>
            </td>
        </tr>
        <tr>
            <td>Channel:</td>
            <td><%=channel%>
            </td>
        </tr>
        <tr>
            <td>Video Url:</td>
            <td><%=videoUrl%>
            </td>
        </tr>
        <tr>
            <td>Video Watch URL:</td>
            <td><%=watchUrl%>
            </td>
        </tr>
    </table>
    <hr width="90%"/>
    <p> Thank you for your submission. </p>
    <% } %>
    <p><a href="index.jsp">Submit another Video</a></p>
</div>
</body>
</html>
