//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: XinEmailUtil.java,v 1.1.1.1 2010/07/21 23:37:11 tli Exp $
//
package com.xinlab.blueapple.contenttool.db;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class XinEmailUtil {

    private static final Logger  vulogger = Logger.getLogger(XinEmailUtil.class);
    
    static final private String host = "127.0.0.1";
    
    //import javax.mail.*;
    static public boolean sendEmail(String from, String to, String[] bcc, String subject, String content) {
        return sendEmail(from,new String[]{to},bcc,subject,content);
    }

    static public boolean sendEmail(String from, String[] to, String[] bcc, String subject, String content) {
        
        vulogger.info("Sending email from <" + from + "> to ");
        
        for (int i=0; i<to.length; i++)
            vulogger.info("<" + to[i] + "> ");
    
        if (bcc != null && bcc.length>0)
        {
            vulogger.info(" bcc to ");
            for (int i=0; i<bcc.length; i++)
                vulogger.info("<" + bcc[i] + "> ");
        }
        if (content.length()<1000)
        {
            vulogger.info("Subject: " + subject);
            vulogger.info("Content: ");
            vulogger.info(content);
        }    
        
        Properties properties = new Properties();
        
        // Setup mail server
        properties.put("mail.smtp.host", host);
        
        // Get session
        Session session = Session.getDefaultInstance(properties, null);
        
        // Define message
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            for (int i=0; i<to.length; i++)
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
            if (bcc != null)
                for (int i=0; i<bcc.length; i++)
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
            message.setSubject(subject);
            message.setText(content);
            
            // Send message
            Transport.send(message);
            vulogger.info("Message sent!");
            return true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }        
        
        return false;
    }

    static public boolean sendEmailAttachFile(String from, String to, String[] bcc, String subject, String content, String filename) {
        return sendEmailAttachFile(from, new String[]{to}, bcc, subject, content, filename);
    }

    static public boolean sendEmailAttachFile(String from, String[] to, String[] bcc, String subject, String content, String filename) {
        
        vulogger.info("Sending email from <" + from + "> to ");
    
        for (int i=0; i<to.length; i++)
            vulogger.info("<" + to[i] + "> ");
    
        if (bcc != null && bcc.length>0)
        {
            vulogger.info(" bcc to ");
            for (int i=0; i<bcc.length; i++)
                vulogger.info("<" + bcc[i] + "> ");
        }
        if (content.length()<1000)
        {
            vulogger.info("Subject: " + subject);
            vulogger.info("Content: ");
            vulogger.info(content);
        }
        
        Properties properties = new Properties();
        
        // Setup mail server
        properties.put("mail.smtp.host", host);
        
        // Get session
        Session session = Session.getDefaultInstance(properties, null);
        
        // Define message
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            for (int i=0; i<to.length; i++)
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
            if (bcc != null)
                for (int i=0; i<bcc.length; i++)
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
            message.setSubject(subject);
            
            Multipart multipart = new MimeMultipart();
    
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(content);
            multipart.addBodyPart(mimeBodyPart);
    
            mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setDataHandler(new DataHandler(new FileDataSource(filename)));
            mimeBodyPart.setFileName(filename);
            multipart.addBodyPart(mimeBodyPart);
    
            message.setContent(multipart);
            
            
            // Send message
            Transport.send(message);
            vulogger.info("Message sent!");
            return true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }        
        
        return false;
    }

    static public boolean sendEmailAttachString(String from, String to, String[] bcc, String subject, String content, String name, String attachment) {
        return sendEmailAttachString(from, new String[]{to}, bcc, subject, content, name, attachment);
    }

    static public boolean sendEmailAttachString(String from, String[] to, String[] bcc, String subject, String content, String name, String attachment) {
        
        vulogger.info("Sending email from <" + from + "> to ");
    
        for (int i=0; i<to.length; i++)
            vulogger.info("<" + to[i] + "> ");
    
        if (bcc != null && bcc.length>0)
        {
            vulogger.info(" bcc to ");
            for (int i=0; i<bcc.length; i++)
                vulogger.info("<" + bcc[i] + "> ");
        }
        if (content.length()<1000)
        {
            vulogger.info("Subject: " + subject);
            vulogger.info("Content: ");
            vulogger.info(content);
        }
        
        //String host = XinConfig.sendmailServer;
        
        Properties properties = new Properties();
        
        // Setup mail server
        properties.put("mail.smtp.host", host);
        
        // Get session
        Session session = Session.getDefaultInstance(properties, null);
        
        // Define message
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            for (int i=0; i<to.length; i++)
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
            if (bcc != null)
                for (int i=0; i<bcc.length; i++)
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
            message.setSubject(subject);
            
            Multipart multipart = new MimeMultipart();
    
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(content);
            multipart.addBodyPart(mimeBodyPart);
    
            mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setDataHandler(new DataHandler(attachment, "text/plain"));
            mimeBodyPart.setFileName(name);
            multipart.addBodyPart(mimeBodyPart);
    
            message.setContent(multipart);
            
            
            // Send message
            Transport.send(message);
            vulogger.info("Message sent!");
            return true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }        
        
        return false;
    }
//    static public void main(String[] args)
//    {
//        sendEmail(args[0], args[1], null, args[2], args[3]);
//    }

}
