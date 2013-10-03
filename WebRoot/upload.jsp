<%@ page import="com.xinlab.blueapple.contenttool.conf.Configure" %><%@ page import="org.apache.commons.fileupload.FileItem" %><%@ page import="org.apache.commons.fileupload.FileItemFactory" %><%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %><%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %><%@ page import="org.apache.commons.io.FilenameUtils" %><%@ page import="org.apache.commons.lang.StringUtils" %><%@ page import="java.io.File" %><%@ page import="java.util.Iterator" %><%@ page import="java.util.List" %><%@ page contentType="text/html;charset=UTF-8" language="java" %><%
    String realPath = request.getRealPath("/");
    String rootPath = realPath + Configure.uploadPath;
    String contentType = request.getContentType();
    if (contentType.indexOf("multipart/form-data") >= 0) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        try {
            List<FileItem> items = upload.parseRequest(request);
            if (items != null) {
                Iterator<FileItem> itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        continue;
                    } else {
                        long now = System.currentTimeMillis();
                        File fullFile = new File(item.getName());
                        String extension = StringUtils.trimToNull(FilenameUtils.getExtension(fullFile.getName()));
                        String newfilename = extension==null?(""+now):(now+"."+extension);
                        File savedFile = new File(rootPath, newfilename);
                        item.write(savedFile);
                        out.print("http://"+Configure.SERVERIP+":8080/upload/"+newfilename); 
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
