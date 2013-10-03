<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ page import="com.xinlab.blueapple.contenttool.bean.FeedMsg" %><%@ page import="java.util.List" %><%@ page import="java.util.ArrayList" %><%@ page import="com.xinlab.blueapple.contenttool.common.XinDBUtil" %>
<%
  	String[][] str = XinDBUtil.getFeedtitle();
	List<FeedMsg> list = new ArrayList<FeedMsg>();
	for (int i = 0; i < str.length; i++) {
		String[] strings = str[i];
		FeedMsg f = new FeedMsg();
		for (int j = 0; j < strings.length; j++) {
			if(j == 0)
				f.setFeed_id(strings[j]);
			else
				f.setFeed_title(strings[j]);
		}
		list.add(f);
	}
%>
<!DOCTYPE html>
<html>
<head>
    <title>Video Upload - vuclip.com</title>
    <link href="css/default.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="swfupload/swfupload.js"></script>
    <script type="text/javascript" src="swfupload/swfupload.queue.js"></script>
    <script type="text/javascript" src="js/fileprogress.js"></script>
    <script type="text/javascript" src="js/handlers.js"></script>
    <script type="text/javascript">
        var swfu;

        window.onload = function () {
            swfu = new SWFUpload({
                // Backend settings
                upload_url: "upload.jsp",
                file_post_name: "resume_file",
                // Flash file settings
                file_size_limit : "100 MB",
                file_types : "*.*",            // or you could use something like: "*.doc;*.wpd;*.pdf",
                file_types_description : "All Files",
                file_upload_limit : 0,
                file_queue_limit : 1,

                // Event handler settings
                swfupload_loaded_handler : swfUploadLoaded,

                file_dialog_start_handler: fileDialogStart,
                file_queued_handler : fileQueued,
                file_queue_error_handler : fileQueueError,
                file_dialog_complete_handler : fileDialogComplete,

                //upload_start_handler : uploadStart,	// I could do some client/JavaScript validation here, but I don't need to.
                swfupload_preload_handler : preLoad,
                swfupload_load_failed_handler : loadFailed,
                upload_progress_handler : uploadProgress,
                upload_error_handler : uploadError,
                upload_success_handler : uploadSuccess,
                upload_complete_handler : uploadComplete,

                // Button Settings
                button_image_url : "XPButtonUploadText_61x22.png",
                button_placeholder_id : "spanButtonPlaceholder",
                button_width: 61,
                button_height: 22,

                // Flash Settings
                flash_url : "swfupload/swfupload.swf",
                flash9_url : "swfupload/swfupload_fp9.swf",

                custom_settings : {
                    progress_target : "fsUploadProgress",
                    upload_successful : false
                },

                // Debug settings
                debug: false
            });

        };
    </script>
</head>
<body>
<div id="header">
    <h1 id="logo"></h1>

    <div id="version">v1.0</div>
</div>

<div id="content">

    <h2>Video Upload</h2>

    <form id="form1" action="result.jsp" enctype="multipart/form-data" method="post">
        <p></p>

        <div class="fieldset">
            <span class="legend">Submit your Video</span>
            <table style="vertical-align:top;">
                <tr>
                    <td><label for="videoTitle">Video Title:</label></td>
                    <td><input name="videoTitle" id="videoTitle" type="text" style="width: 200px"/></td>
                </tr>
                <tr>
                	<td><label for="keywords">Video Keywords:</label></td>
                	<td><input name="keywords" id="keywords" type="text" style="width:200px" /></td>
                </tr>
                <tr>
                	<td><label for="pcc">Channel:</label></td>
                	<td>
                		<select id="pcc" name="pcc">
	                		<option value="0">Please Choose</option>
	                		<%for(int i = 0;i < list.size();i ++){%>
	                			<option value="<%=list.get(i).getFeed_id() %>"><%=list.get(i).getFeed_title() %></option>
	                		<%} %>
                		</select>
                	</td>
                </tr>
                <tr>
                    <td><label for="txtFileName">Video:</label></td>
                    <td>
                        <div>
                            <div>
                                <input type="text" id="txtFileName" disabled="true"
                                       style="border: solid 1px; background-color: #FFFFFF;"/>
                                <span id="spanButtonPlaceholder"></span>
                                (100 MB max)
                            </div>
                            <div class="flash" id="fsUploadProgress">
                                <!-- This is where the file progress gets shown.  SWFUpload doesn't update the UI directly.
                                                    The Handlers (in handlers.js) process the upload events and make the UI updates -->
                            </div>
                            <input type="hidden" name="videoUrl" id="videoUrl" value=""/>
                            <input type="hidden" name="channel" id="channel" value="" />
                            <!-- This is where the file ID is stored after SWFUpload uploads the file and gets the ID back from upload.php -->
                        </div>
                    </td>
                </tr>                
            </table>
            <br/>
            <input type="submit" value="Submit Video" id="btnSubmit"/>
        </div>
    </form>
</div>
</body>
</html>
