
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class UserDao {
	/*
	insert into users(id,name,gender,email) values(1112,"yang","boy","973515702@qq.com");
	select name,gender,email from users where id=1111;
	delete from users where id = 1111;
	update users set name="bao" where id=1112;
	*/
	//append info
	public void insert(String sql){
		String reg = "^insert";
		Pattern pattern = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
		List<String> resultList = null;
		if(matcher.find()){
			reg = "(?<=values\\().*(?=\\))";//fetch the value with regular expression
			pattern = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(sql);
			if(matcher.find()){
				String result = matcher.group();
				String[] reStr = result.split(",");
				resultList = new ArrayList<String>();
				for(int i = 0;i < reStr.length;i ++){
					resultList.add(reStr[i].replaceAll("\"", ""));
				}
				
				
			/*System.out.println(resultList.get(0) + "  " + 
			  resultList.get(1) + "  " + resultList.get(2) + "  " + resultList.get(3));
				*/
				
			}
		}
		Document document = new Document();
		Element root = new Element("users");
		Element user = new Element("user");
		Attribute id = new Attribute("id",resultList.get(0));
		user.setAttribute(id);
		Element name = new Element("name");
		name.setText(resultList.get(1));
		Element gender = new Element("gender");
		gender.setText(resultList.get(2));
		Element email = new Element("email");
		email.setText(resultList.get(3));
		document.addContent(root);
		root.addContent(user);
		user.addContent(name);
		user.addContent(gender);
		user.addContent(email);
		Format format = Format.getPrettyFormat();
		format.setEncoding("utf-8");
		format.setIndent("    ");
		XMLOutputter outputer = new XMLOutputter(format);
		try {
			outputer.output(document, new FileOutputStream("E:\\bjsxtTourist\\DailyRecord\\MiddleRank\\TwelvethDay\\Mine\\SqlXml\\src\\user.xml"));
			JOptionPane.showMessageDialog(null, "Your data information has already been saved in the path:"
					+"\nE:\\bjsxtTourist\\DailyRecord\\MiddleRank\\TwelvethDay\\Mine\\SqlXml\\src\\user.xml");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Your data information has been saved failedly");
			e.printStackTrace();
		} 
	}
	@SuppressWarnings("unchecked")
	//query info
	public void select(String sql){
		String reg = "^select";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(sql);
		String strRes = null;//save the id
		if(m.find()){
			String regStr = "(?<=id(\\s{0,100}=\\s{0,100}))\\d*";
			p = Pattern.compile(regStr);
			m = p.matcher(sql);
			if(m.find()){
				strRes = m.group();
				//System.out.println(strRes);
			}
		}
		SAXBuilder sb = new SAXBuilder();
		Document d = null;
		Element root = null;
		Element ele = null;//get the target element
		List list = null;
		Iterator<Element> it = null;
		try {
			d = sb.build(new FileInputStream("E:\\bjsxtTourist\\DailyRecord\\MiddleRank\\TwelvethDay\\Mine\\SqlXml\\src\\user.xml"));
			root = d.getRootElement();
			list = root.getChildren();
			it = list.listIterator();
			while(it.hasNext()){
				Element e = it.next();
				if(strRes.equals(e.getAttributeValue("id"))){
					ele = e;
				}
			}
			list = ele.getChildren();
			it = list.listIterator();
			String info = "";
			while(it.hasNext()){
				Element e = it.next();
				info += e.getText() + "   ";
			}
			JOptionPane.showMessageDialog(null, "Your info :  \n" + info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void update(String sql){
		String reg = "^update";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(sql);
		String strRes = null;
		if(m.find()){
			String regStr = "(?<=id(\\s{0,100}=\\s{0,100}))\\d*";
			p = Pattern.compile(regStr);
			m = p.matcher(sql);
			if(m.find()){//if there is not this sentence ,it will be wrong
				//If the match succeeds then more information can be obtained via the start, end, and group methods. 
				strRes = m.group();
			}
		}
		SAXBuilder sb = new SAXBuilder();
		Document d = null;
		try {
			d = sb.build(new FileInputStream("E:\\bjsxtTourist\\DailyRecord\\MiddleRank\\TwelvethDay\\Mine\\SqlXml\\src\\user.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Element root = d.getRootElement();
		List list = root.getChildren();
		Element resultEle = null;
		for(int i = 0;i < list.size();i ++){//look for the id value of the specified element
			Element e = (Element)list.get(i);
			if(e.getAttributeValue("id").equals(strRes)){
				resultEle = e;
			}
		}
		//System.out.println(resultEle.getChildren());
		String lastReg = "(?<=set\\s).*(?=\\swhere)";//obtain the required modified value
		p = Pattern.compile(lastReg);
		m = p.matcher(sql);
		if(m.find()){
			String lastStr = m.group();//consider the space and enter between "="
			String lastResult = lastStr.replaceAll("\"|\\s|=", ",");
			String str = lastResult.replaceAll("[,]+", ",");
			String[] result = str.split(",");
			for(int i = 0;i < result.length - 1;i ++){
					if(((Element)resultEle.getChildren().get(0)).getName().equals(result[i])){
						//System.out.println(((Element)resultEle.getChildren().get(0)).getName() + "  " + result[i]);
						((Element)resultEle.getChildren().get(0)).setText(result[i+1]);
						//System.out.println(result[i+1]);
					}else if(((Element)resultEle.getChildren().get(1)).getName().equals(result[i])){
						((Element)resultEle.getChildren().get(1)).setText(result[i+1]);
						//System.out.println(result[i+1]);
					}else if(((Element)resultEle.getChildren().get(2)).getName().equals(result[i])){
						((Element)resultEle.getChildren().get(2)).setText(result[i+1]);
					}
			}
		}
		Format format = Format.getPrettyFormat();
		format.setEncoding("utf-8");
		format.setIndent("    ");
		XMLOutputter out = new XMLOutputter(format);
		try {
			out.output(d, new FileOutputStream("E:\\bjsxtTourist\\DailyRecord\\MiddleRank\\TwelvethDay\\Mine\\SqlXml\\src\\user.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//delete info
	@SuppressWarnings("unchecked")
	public void delete(String sql){
		String reg = "^delete from";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(sql);
		String strRes = null;
		if(m.find()){
			String regStr = "(?<=id(\\s{0,100}=\\s{0,100}))\\d*";
			p = Pattern.compile(regStr);
			m = p.matcher(sql);
			if(m.find()){//if there is not this sentence ,it will be wrong
				//If the match succeeds then more information can be obtained via the start, end, and group methods. 
				strRes = m.group();
				System.out.println(strRes);
			}
		}
		SAXBuilder sb = new SAXBuilder();
		Document d = null;
		try {
			d = sb.build(new FileInputStream("E:\\bjsxtTourist\\DailyRecord\\MiddleRank\\TwelvethDay\\Mine\\SqlXml\\src\\user.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Element root = d.getRootElement();
		List list = root.getChildren();
		Element resultEle = null;
		for(int i = 0;i < list.size();i ++){//look for the id value of the specified element
			Element e = (Element)list.get(i);
			if(e.getAttributeValue("id").equals(strRes)){
				resultEle = e;
			}
		}
		List list2 = resultEle.getChildren();
		System.out.println(list2);
		for(int i = 0;i < list2.size();i ++){
			((Element)resultEle.getChildren().get(i)).detach();
			System.out.println((Element)list2.get(i));
		}
		Format format = Format.getPrettyFormat();
		format.setEncoding("utf-8");
		format.setIndent("    ");
		XMLOutputter out = new XMLOutputter(format);
		try {
			out.output(d, new FileOutputStream("E:\\bjsxtTourist\\DailyRecord\\MiddleRank\\TwelvethDay\\Mine\\SqlXml\\src\\user.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		/*
		String sql = "insert into users(id,name,gender,email) values(1112,\"yang\",\"boy\",\"973515702@qq.com\")";
		new UserDao().insert(sql);
		
		String sql = "select name,gender,email from users where id=1113";
		new UserDao().select(sql);
		
		String sql = "update users set name=\"LiLy\" gender = \"girl\" email=\"9765898888@163.com\" where id=1112";
		new UserDao().update(sql);
		*/
		String sql = "delete from users where id = 1111";
		new UserDao().delete(sql);
	}
}
