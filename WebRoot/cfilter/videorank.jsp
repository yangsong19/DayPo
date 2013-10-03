<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
    <link href="../cfilter/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../cfilter/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
    <div class="navbar">
        <div class="navbar-inner">
            <div style="width: auto;" class="container">
                <a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <a href="#" class="brand">Content Filter</a>

                <div class="nav-collapse">
                    <ul class="nav">
                        <li class="active"><a href="#">Video</a></li>
                        <li><a href="#">Channel</a></li>
                        <li><a href="#">Site</a></li>
                        <li><a href="#">Carrier</a></li>
                        <li><a href="#">Country</a></li>
                        <li><a href="#">Region</a></li>
                    </ul>
                    <ul class="nav pull-right">
                        <li><a href="#">Link</a></li>
                    </ul>
                </div>
                <!-- /.nav-collapse -->
            </div>
        </div>
        <!-- /navbar-inner -->
    </div>

    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Cid</th>
            <th>Video Title</th>
            <th>Rank</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>111</td>
            <td>test title</td>
            <td>X</td>
        </tr>
        <tr>
            <td>222</td>
            <td>test title2</td>
            <td>G</td>
        </tr>
        </tbody>
    </table>

    <form class="form-horizontal well" action="../cfilter/videorank" method="get">
    <div class="row">
        <div class="span6">
            <textarea rows="10" id="textarea" class="input-xxlarge" name="textarea"></textarea>
        </div>
        <div class="span4 offset1">
            <select id="rank" name="rank">
                <option>G</option>
                <option>R</option>
                <option>X</option>
                <option>N</option>
            </select>
        </div>
    </div>
    <div class="form-actions">
        <button class="btn btn-primary" type="submit">Set Rank</button>
    </div>
    </form>
</div>
</body>
</html>