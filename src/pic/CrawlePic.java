package pic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.protocol.HTTP;


public class CrawlePic {
	public static void main(String[] args) throws IOException {
//		writeFile("http://s920.vuclip.com/81/55/8155e844e20ca17a746e9114c57b461e/120x60/3.jpg", "d:/pic", "3.jpg");
//		System.out.println(existFile("d:/pic/1.jpg"));
//		listChildFile("G:\\Youku Files");
//		getCidDir("", "");

		/*String dir = "c:/home/admin/content/thumbnails";
		File f = new File(dir);
		if (!f.exists())
			f.mkdirs();
		System.out.println(getCidDir(dir, "123"));*/

		/*listChildFile("G:/Youku Files/download");
		try {
			System.out.println("IP:"+InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}*/
		
//		getThumbnails("c:/home/admin/content/thumbnails/", "123");
//		copyFile(new File("C:\\home\\admin\\content\\thumbnails\\123_1386971513840\\80x60.jpg"), new File("c:/80x60.jpg"));
		/*File file = new File("G:/temp/temp");
		deleteDirectory(file);
		if(file.isDirectory())
			file.delete();
		*/
		/*String local_path = "C:/home/admin/content/thumbnails/" + 123 + "_" + System.currentTimeMillis() +"/";
		File saveDir = new File(local_path);
		// Here comes the existence check
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}*/
		StringBuffer sb = new StringBuffer();
		System.out.println(sb.length());
	}
	private static void deleteDirectory(File sPath) {
		File[] files = sPath.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}
    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	private static void getThumbnails(String path, String cid) {
		//list all thumbnails in local directory
        HashMap<String, String> thumbnails = new HashMap<String, String>();
        String str = "", fullPath = "";
        int n1 = -1, n2 = -1;
        String dir = getCidDir(path, cid);
        System.out.println("dir:" + dir);
        String tempDir = path + dir;
		File file = new File(tempDir);
        if(file.exists()) {
        	File[] files = file.listFiles();
        	for(File f:files) {
        		if(f.isFile()) {
        			fullPath = f.getAbsoluteFile().toString();//123_1398745037816/120x60.jpg
        			System.out.println("fullPath:" + fullPath);
        			n1 = fullPath.lastIndexOf(File.separator);
        			n2 = fullPath.indexOf(".");
        			System.out.println("n:"+n1+";"+n2);
        			str = fullPath.substring(n1 + 1, n2);
        			thumbnails.put(str, fullPath);
        		}
        	}
        }
        Set<String> set = thumbnails.keySet();
        Iterator<String> it = set.iterator();
        while(it.hasNext()) {
        	String key = it.next();
        	System.out.println("key:" + key + ";value:" + thumbnails.get(key));
        }
	}
	// Method to get the IP Address of the Host.
	private static String getIP()
	{
	    // This try will give the Public IP Address of the Host.
	    try
	    {
	        URL url = new URL("http://s1530.vuclip.com/9e/97/9e97295c7875198560042caaaccf6cff/80x60/5.jpg");
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        String ipAddress = new String();
	        ipAddress = (in.readLine()).trim();
	        /* IF not connected to internet, then
	         * the above code will return one empty
	         * String, we can check it's length and
	         * if length is not greater than zero, 
	         * then we can go for LAN IP or Local IP
	         * or PRIVATE IP
	         */
	        if (!(ipAddress.length() > 0))
	        {
	            try
	            {
	                InetAddress ip = InetAddress.getLocalHost();
	                System.out.println((ip.getHostAddress()).trim());
	                return ((ip.getHostAddress()).trim());
	            }
	            catch(Exception ex)
	            {
	                return "ERROR";
	            }
	        }
	        System.out.println("IP Address is : " + new String(ipAddress.getBytes(HTTP.ISO_8859_1), HTTP.UTF_8));

	        return (ipAddress);
	    }
	    catch(Exception e)
	    {
	        // This try will give the Private IP of the Host.
	        try
	        {
	            InetAddress ip = InetAddress.getLocalHost();
	            System.out.println((ip.getHostAddress()).trim());
	            return ((ip.getHostAddress()).trim());
	        }
	        catch(Exception ex)
	        {
	            return "ERROR";
	        }
	    }
	}
	private static void writeFile(String sourceUrl, String fullPath, String picName) {
		URL url = null;
		try {
			url = new URL(sourceUrl);
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}
		InputStream is = null;
		try {
			is = new BufferedInputStream(url.openStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		OutputStream os = null;
		File f = new File(fullPath);
		if(!f.exists())
			f.mkdirs();
		try {
			os = new FileOutputStream(fullPath + "/" + picName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];

			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(os != null) 
					os.close();
				if(is != null) 
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	private static boolean existFile(String fileName) {
		File file = new File(fileName);
		if(file.exists())
			return true;
		return false;
	}
	
	private static void listChildFile(String parent) {
		File file = new File(parent);
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(int i = 0;i < files.length; i++)
//				if(files[i].isFile())
					System.out.println(files[i].getAbsoluteFile().toString());
		}
	}
	
    /**
     * return inner class
     *
     * @param 
     */
    static class FileFilter implements FilenameFilter{
    	private String filter;
    	
		public boolean accept(File dir, String name) {
			return name.endsWith(filter);
		}

		public FileFilter(String filter) {
			this.filter = filter;
		}
		
	}
    /**
     * return the cid's directory
     * @param path
     * @param cid
     * @return
     */
    public static String getCidDir(String path,String cid){
    	String cidDir = "";
    	File cidDirs = new File(path);
		if(!cidDirs.exists()){
			return cidDir;
		}
		for(File cidDirFile:cidDirs.listFiles()){
			if(!cidDirFile.isDirectory()){
				continue;
			}
			String cidDirName = cidDirFile.getName();
			String[] cidFromCidDirName = cidDirName.split("_");
			if(cidFromCidDirName[0].equals(cid)){
				cidDir = cidDirName;
				break;
			}
			
		}
		return cidDir;
	}
}
