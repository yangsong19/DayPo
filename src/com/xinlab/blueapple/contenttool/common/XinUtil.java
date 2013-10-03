//
// Copyright XinLab Inc (blueapple.mobi) 2006-2008
// Proprietary and confidential
// All rights reserved
//
// $Id: XinUtil.java,v 1.2 2010/09/23 17:28:17 sbho Exp $
//
package com.xinlab.blueapple.contenttool.common;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class XinUtil{
    
    static final public BufferedImage toBufferedImage(Image image)
    {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics grahpics = bufferedImage.getGraphics();
        grahpics.drawImage(image, 0, 0, null);
        grahpics.dispose();    
        return bufferedImage;
    }
    
    //
    // some stream/reader operation
    //
    /**
     *  read n byte and return a string
     */
    static final public String read(BufferedReader in, int len) throws IOException
    {
        char[] buffer = new char[len];
        int index = 0;
        int nread;
        
        while (index<len)
        {
            nread = in.read(buffer, index, (len-index));
            if (nread == -1)
            {
                // end of file
                break;
            }
            index += nread;
        }
        
        return new String(buffer);
    }
    static final public String read(BufferedInputStream in, int len) throws IOException
    {
        byte[] buffer = new byte[len];
        int index = 0;
        int nread;
        
        while (index<len)
        {
            nread = in.read(buffer, index, (len-index));
            if (nread == -1)
            {
                // end of file
                break;
            }
            index += nread;
        }
        
        return new String(buffer);
    }
    static final public String readLine(BufferedInputStream in) throws IOException {
/*        
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.readLine();
*/        
        if (!in.markSupported())
            throw new IOException();

        StringBuffer sb = new StringBuffer();

        int n;
        boolean removed = false;
        
        n = in.read();
        if (n==-1) return null;
        
        while (n!='\n' && n!='\r')
        {
            sb.append((char)n);
            n = in.read();
            if (n==-1) break;
        }
        
        //
        // remove remaining \n \r
        //
        while ((in.available()>0) && (n=='\n' || n=='\r'))
        {
            removed = true;
            in.mark(2);
            n = in.read();
            if (n==-1) break;
        }
        
        if (removed)
            in.reset();
        
        return sb.toString();
        
    }
    
    //
    // http post
    //
    static final public String postUrl(String urlString, String msg)
    {
        HttpURLConnection con = null;
        BufferedInputStream in = null;
        OutputStreamWriter out = null;
        try {
            ByteArrayOutputStream urlData = new ByteArrayOutputStream();
            URL url = new URL(urlString);
            con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(15*1000);
            con.setReadTimeout(15*1000);
            
            con.setDoOutput(true);
            out = new OutputStreamWriter(con.getOutputStream());
            out.write(msg);
            out.flush();
            in = new BufferedInputStream(con.getInputStream());
            byte[] buf2 = new byte[1024];
            int n;
            while ((n=in.read(buf2))>0)
            {
                urlData.write(buf2, 0, n);
            }
            in.close();
            return new String(urlData.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out!=null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            out=null;
            if (in!=null)
                try{
                    in.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            in=null;
            //(may) reuse connection in the future
//          if (con!=null){
//              con.disconnect();
//              con=null;
//          }
        }
    }
    
    //
    // simple http get
    //
    static final public byte[] getUrlE(String urlString) throws IOException
    {
        HttpURLConnection con = null;
        BufferedInputStream in = null;
        try {
            ByteArrayOutputStream urlData = new ByteArrayOutputStream();
            URL url = new URL(urlString);
            con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(15*1000);
            con.setReadTimeout(15*1000);
            in = new BufferedInputStream(con.getInputStream());
            byte[] buf2 = new byte[1024];
            int n;
            while ((n=in.read(buf2))>0)
            {
                urlData.write(buf2, 0, n);
            }
            in.close();
            return urlData.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (in!=null){
                try{
                    in.close();
                }catch (IOException e) {
                    throw e;
                }
                in=null;
            }
            //(may) reuse connection in the future
//          if (con!=null){
//              con.disconnect();
//              con=null;
//          }
        }
    }
    static final public byte[] getUrl(String urlString)
    {
        try {
            return getUrlE(urlString);
        }catch (IOException e){
            return null;
        }
    }
    static final public String getUrlStringE(String urlString) throws IOException
    {
        return new String(getUrlE(urlString));
    }
    static final public String getUrlString(String urlString)
    {
        try {
            return new String(getUrlE(urlString));
        }catch (IOException e){
            return null;
        }
    }
    
    //
    // get a file in one api call
    //
    static final public byte[] getFileE(String file) throws IOException
    {
        HttpURLConnection con = null;
        BufferedInputStream in = null;
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            
            in = new BufferedInputStream(new FileInputStream(file));
            byte[] buf2 = new byte[1024];
            int n;
            while ((n=in.read(buf2))>0)
            {
                buf.write(buf2, 0, n);
            }
            in.close();
            return buf.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (in!=null){
                try{
                    in.close();
                }catch (IOException e) {
                    throw e;
                }
                in=null;
            }
            //(may) reuse connection in the future
//          if (con!=null){
//              con.disconnect();
//              con=null;
//          }
        }
    }
    static final public byte[] getFile(String file)
    {
        try {
            return getFileE(file);
        }catch (IOException e){
            return null;
        }
    }
    static final public String getFileStringE(String urlString) throws IOException
    {
        return new String(getFileE(urlString));
    }
    static final public String getFileString(String urlString)
    {
        try {
            return new String(getFileE(urlString));
        }catch (IOException e){
            return null;
        }
    }
    
    //
    // string util
    //
    static final public String[] splitString(String str, String delim)
    {
        return splitString(str, delim, false);
    }
    /**
     * @param str - the string need to split
     * @param delim - the deliminator
     * @param trim - remove empty string if true, better for splitting sub string of the same type (e.g., a list of emails)
     * @return - the splitted strings
     */
    static final public String[] splitString(String str, String delim, boolean trim)
    {
        ArrayList list = new ArrayList();
        int index = 0, offset = 0;
        index = str.indexOf(delim, offset);
        
        while(index != -1)
        {
            String s = str.substring(offset, index).trim();
            if (trim)
            {
                if (s.length()>0)
                    list.add(s);
            }
            else
                list.add(s);
            
            offset = index + 1;
            index = str.indexOf(delim, offset);
        }
        
        String s = str.substring(offset, str.length()).trim();
        if (trim)
        {
            if (s.length()>0)
                list.add(s);
        }
        else
            list.add(s);

        //       StringTokenizer st = new StringTokenizer(str, delim);
        //       
        //       while (st.hasMoreTokens())
        //       {
        //          String s = st.nextToken().trim();
        //           //if (s.length()>0)
        //               list.add(s);
        //       }
        
        return (String[]) list.toArray(new String[0]);
    } 
    static final public String padString(String str, char pad, int tolen)
    {
        if (tolen>str.length())
        {
            // need padding
            char[] padding = new char[tolen-str.length()];
            Arrays.fill(padding, pad);
            return (str + new String(padding));
        }
        else
        {
            return str.substring(0, tolen);
        }
    }
    static final public String replaceString(String s, String target, String replacement)
    {
        // the same api exists in java 1.5, just for support in 1.4
        // however, this implementation is faster in most case
        StringBuffer sb = new StringBuffer();
        int targetLen = target.length();
        int sLen = s.length();
        
        int index=0;
        while(index<sLen)
        {
            int index2=s.indexOf(target,index);
            if (index2<0)break;
            sb.append(s.substring(index,index2));
            sb.append(replacement);
            index=index2+targetLen;
        }
        sb.append(s.substring(index));
        return sb.toString();
    }
    
    //
    // a very fast(just byte shift)/ very simple(one api call)  base64 encoder/decoder
    //
    static final public String base64decode(String s)
    {
        return new String(base64decode(s.getBytes()));
    }
    static final public byte[] base64decode(byte inByte[])
    {
        byte[] decodeTable = {
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,-1,-1,-1,63,  // +, /
                52,53,54,55,56,57,58,59,60,61,-1,-1,-1,-2,-1,-1,  // 0-9, =
                -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,  // A-O
                15,16,17,18,19,20,21,22,23,24,25,-1,-1,-1,-1,-1,  // P-Z
                -1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,  // a-o
                41,42,43,44,45,46,47,48,49,50,51,-1,-1,-1,-1,-1}; // p-z
        
        if(inByte.length == 0)
            return inByte;
        
        int length = (inByte.length / 4) * 3;
        if(inByte[inByte.length - 2] == '=')
        {
            length-= 2;
        }
        else if(inByte[inByte.length - 1] == '=')
        {
            length--;
        }
        byte outByte[] = new byte[length];
        
        int i = 0;
        int j = 0;
        byte byte1, byte2, byte3, byte4;
        int block = inByte.length / 4;
        for (int k=0; k<block-1; k++)
        {
            byte1 = decodeTable[inByte[i++] & 0xff];
            byte2 = decodeTable[inByte[i++] & 0xff];
            byte3 = decodeTable[inByte[i++] & 0xff];
            byte4 = decodeTable[inByte[i++] & 0xff];
            outByte[j++] = (byte)(byte1 << 2 & 0xfc | byte2 >> 4 & 0x03);
            outByte[j++] = (byte)(byte2 << 4 & 0xf0 | byte3 >> 2 & 0x0f);
            outByte[j++] = (byte)(byte3 << 6 & 0xc0 | byte4 & 0x3f);
        }
        
        byte1 = decodeTable[inByte[i++] & 0xff];
        byte2 = decodeTable[inByte[i++] & 0xff];
        byte3 = decodeTable[inByte[i++] & 0xff];
        byte4 = decodeTable[inByte[i++] & 0xff];
        
        if (byte3 == -2)
        {
            outByte[j++] = (byte)(byte1 << 2 & 0xfc | byte2 >> 4 & 0x03);
        }
        else if (byte4 == -2)
        {
            outByte[j++] = (byte)(byte1 << 2 & 0xfc | byte2 >> 4 & 0x03);
            outByte[j++] = (byte)(byte2 << 4 & 0xf0 | byte3 >> 2 & 0x0f);
        }
        else
        {
            outByte[j++] = (byte)(byte1 << 2 & 0xfc | byte2 >> 4 & 0x03);
            outByte[j++] = (byte)(byte2 << 4 & 0xf0 | byte3 >> 2 & 0x0f);
            outByte[j++] = (byte)(byte3 << 6 & 0xc0 | byte4 & 0x3f);
        }
        
        return outByte;
    }
    static final public String base64encode(String s)
    {
                try{
            return new String(base64encode(s.getBytes("UTF-8")));
                }catch(Exception e){
            return new String(base64encode(s.getBytes()));
                }
    }
    static final public byte[] base64encode(byte inByte[])
    {
        byte encodeTable[] = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
                'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
        };
        
        if(inByte.length == 0)
            return inByte;
        
        byte outByte[] = new byte[((inByte.length)/3) * 4 +((((inByte.length)%3)==0)?0:4)];
        int i = 0;
        int j = 0;
        
        byte byte1, byte2, byte3;
        int block = inByte.length / 3;
        for(int k = 0; k < block; k++)
        {
            byte1 = inByte[i++];
            byte2 = inByte[i++];
            byte3 = inByte[i++];
            outByte[j++] = (byte)encodeTable[byte1 >> 2 & 0x3f];
            outByte[j++] = (byte)encodeTable[(byte1 << 4 & 0x30) | (byte2 >> 4 & 0x0f)];
            outByte[j++] = (byte)encodeTable[(byte2 << 2 & 0x3c) | (byte3 >> 6 & 0x03)];
            outByte[j++] = (byte)encodeTable[byte3 & 0x3f];
        }
        
        if((inByte.length % 3) == 1)
        {
            byte1 = inByte[i++];
            outByte[j++] = (byte)encodeTable[byte1 >> 2 & 0x3f];
            outByte[j++] = (byte)encodeTable[(byte1 << 4 & 0x30)];
            outByte[j++] = '=';
            outByte[j++] = '=';
        }
        else if((inByte.length % 3) == 2)
        {
            byte1 = inByte[i++];
            byte2 = inByte[i++];
            outByte[j++] = (byte)encodeTable[byte1 >> 2 & 0x3f];
            outByte[j++] = (byte)encodeTable[(byte1 << 4 & 0x30) | (byte2 >> 4 & 0x0f)];
            outByte[j++] = (byte)encodeTable[(byte2 << 2 & 0x3c)];
            outByte[j++] = '=';
        }
        
        return outByte;
    }
    
    //
    // some number/byte convertion
    //
    static final public byte[] toBigEndianByteArray(int i)
    {
        byte b[] = new byte[4];
        
        b[0] = (byte)((i & 0xff000000) >> 24);
        b[1] = (byte)((i & 0x00ff0000) >> 16);
        b[2] = (byte)((i & 0x0000ff00) >> 8);
        b[3] = (byte)((i & 0x000000ff));
        
        return b;
    }
    static final public byte[] toLittleEndianByteArray(int i)
    {
        byte b[] = new byte[4];
        
        b[3] = (byte)((i & 0xff000000) >> 24);
        b[2] = (byte)((i & 0x00ff0000) >> 16);
        b[1] = (byte)((i & 0x0000ff00) >> 8);
        b[0] = (byte)((i & 0x000000ff));
        
        return b;
    }
    static final public byte[] toBigEndianByteArray(short i)
    {
        byte b[] = new byte[2];
        
        b[0] = (byte)((i & 0xff00) >> 8);
        b[1] = (byte)((i & 0x00ff));
        
        return b;
    }
    static final public byte[] toLittleEndianByteArray(short i)
    {
        byte b[] = new byte[2];
        
        b[1] = (byte)((i & 0xff00) >> 8);
        b[0] = (byte)((i & 0x00ff));
        
        return b;
    }    
    static final public int fromBigEndianByteArray(byte[] b)
    {
        return ((b[0]<<24)&0xff000000)|
               ((b[1]<<16)&0x00ff0000)|
               ((b[2]<< 8)&0x0000ff00)|
               ((b[3]    )&0x000000ff);
    }
    static final public String toHexString(byte[] in)
    {
/*        
        byte[] buf = new byte[in.length*2];
        int j=0;
        for (int i=0; i<in.length; i++)
        {
            if (in[i]<0x0f)
            {
                buf[j++]='0';
            }
            else if (in[i]<0xaf)
            {
                buf[j++]=(byte)(in[i]+'0');
            }
            else
            {
                buf[j++]=(byte)(in[i]+'a'); 
            }
            
        }
*/        
/*
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < in.length; i++) {
            String hex=Integer.toHexString(0xff & in[i]);
            if(hex.length()==1) 
                sb.append("0");
            sb.append(hex);
        }
        return sb.toString();
*/
        return toHexString(in,0,in.length);
    }
    static final public char[] hexmap = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; 
    static final public String toHexString(byte[] in, int offset, int length) {
        char[] buf = new char[length*2];
        int j=0;
        int end = offset+length;
        for (int i=offset; i<end; i++) {
            buf[j++] = hexmap[(in[i] >> 4) & 0x0f];
            buf[j++] = hexmap[in[i] & 0x0f];
        }
        return new String(buf);
    }
    static final public byte[] fromHexString(byte[] in)
    {
        int length = in.length/2;
        byte[] buf = new byte[length];
        int j=0;
        
        for (int i=0; i<length; i++)
        {
            byte b = in[j++];
            
            if (b>='0' && b<='9')
                buf[i] = (byte)(((b - '0')<<4)&0xf0);
            else if (b>='a' && b<='f')
                buf[i] = (byte)(((b - 'a'+10)<<4)&0xf0);
            else if (b>='A' && b<='F')
                buf[i] = (byte)(((b - 'A'+10)<<4)&0xf0);

            b = in[j++];
            if (b>='0' && b<='9')
                buf[i] |= (byte)(((b - '0')&0x0f));
            else if (b>='a' && b<='f')
                buf[i] |= (byte)(((b - 'a'+10)&0x0f));
            else if (b>='A' && b<='F')
                buf[i] |= (byte)(((b - 'A'+10)&0x0f));
            
        }
        return buf;
    }
    static final public String hexByteToString(byte[] in)
    {
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < in.length; i++) {
            String hex=Integer.toHexString(0xff & in[i]);
            if(hex.length()==1) 
                sb.append("0");
            sb.append(hex);
        }
        return sb.toString();
    }

    //
    // RSA/AES/MD5 stuff
    //
//    static final public byte[] magic(byte[] magic)
//    {
//        return new BigInteger(magic).modPow(new BigInteger(XinConfig.magic1), new BigInteger(XinConfig.magic2)).toByteArray();
//    }
    static final public String stripMsisdn(String msisdn)
    {
        if (msisdn.startsWith("+"))
        {
            return msisdn.substring(1);
        }
        else if (msisdn.startsWith("%2B")||msisdn.startsWith("%2b"))
        {
            return msisdn.substring(3);
        }
        return msisdn;
    }
    static private byte[] KEY = {1, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9, 2, 4, 0};
    static final public String getEncrpytedMsisdn(String plainMsisdn)
    {
        return new String(base64encode(AESCryptor(KEY, (padString(plainMsisdn, ' ', 31).getBytes()),Cipher.ENCRYPT_MODE)));
    }
    static final public String getEncrpytedMsisdn2(String plainMsisdn)
    {
        return new String(base64encode(AESCryptor(KEY, plainMsisdn.getBytes(),Cipher.ENCRYPT_MODE)));
    }
    static final public String getDecrpytedMsisdn(String encMsisdn)
    {
        byte[] desByte = AESCryptor(KEY, base64decode(encMsisdn.getBytes()),Cipher.DECRYPT_MODE);
        return new String(desByte).trim();
    }
    // direction = Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE
    static final public byte[] AESCryptor(byte[] key, byte[] inByte, int direction)
    {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(direction, skeySpec);
            return cipher.doFinal(inByte);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    static final public String toMD5(String plaintext) {
        StringBuffer sb=new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(plaintext.getBytes());
            byte digested[] = algorithm.digest();
            
            for (int i = 0; i < digested.length; i++) {
                String hex=Integer.toHexString(0xff & digested[i]);
                if(hex.length()==1) 
                    sb.append("0");
                sb.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    static final public byte[] toMD5(byte[] message) {
        
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(message);
            return algorithm.digest();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }    
    //
    // a simple encryptor/decryptor
    //
    static final public byte[] XinEncryptor(String plain, byte key)
    {
        byte[] bin = plain.getBytes();
        byte[] bout = new byte[bin.length];
        
        for (int i=0; i<bin.length; i++)
        {
            byte b = bin[i];
            int shift = (key^i)&7;
            byte temp = b = (byte)((b << shift) | ((b&0xff) >>> (8-shift)));
            bout[i] = (byte)(b^key);
            key = temp;
        }

        return bout;
    }
    static final public String XinDecryptor(byte[] crypt, byte key)
    {
        byte[] bin = crypt;
        byte[] bout = new byte[bin.length];
        
        for (int i=0; i<bin.length; i++)
        {
            byte b = bin[i];
            b = (byte)(b^key);
            int shift = (key^i)&7;
            bout[i] = (byte)(((b&0xff) >>> shift ) | (b << (8-shift)));
            key = b;
        }
        
        return new String(bout);
    }

    static final public void printByteArray(String str){

        byte[] b1 = str.getBytes();
        System.out.println("--- print bytes for string number of bytes:"+b1.length);
        for(int i=0;i<b1.length;i++){
             System.out.print(" "+Integer.toHexString(b1[i]&0xff));
        }
        System.out.println("--- done printing bytes");
    }
    //
    //escape seq like \\uxxxx
    //
    static final public String readEscape(String s) {
        char[] in = s.toCharArray();
        
        byte[] table = {
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,-1,-1,-1,-1,-1,-1,  // 0-9
                -1,10,11,12,13,14,15,-1,-1,-1,-1,-1,-1,-1,-1,-1,  // A-F
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,10,11,12,13,14,15,-1,-1,-1,-1,-1,-1,-1,-1,-1,  // a-f
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

        int inIdx=0, outIdx=0;
        char c;
        char[] buf = new char[in.length]; 

        while (inIdx < in.length) {
            c = in[inIdx++];
            if (c == '\\') {
                c = in[inIdx++];   
                if(c == 'u') {
                    buf[outIdx++] = (char)((table[in[inIdx++]]<<12) | (table[in[inIdx++]]<<8) | (table[in[inIdx++]]<<4) | table[in[inIdx++]]);
                }else if (c == 't'){
                    buf[outIdx++] = '\t'; 
                }else if (c == 'r'){ 
                    buf[outIdx++] = '\r';
                }else if (c == 'n') {
                    buf[outIdx++] = '\n';
                }else if (c == 'f') {
                    buf[outIdx++] = '\f';
                }
            } else {
                buf[outIdx++] = (char)c;
            }
        }

        return new String(buf,0,outIdx);
    }
    //
    // escape seq like &#xxxxx; or &#xx;
    //
    static final public String readEscape2(String s) {
        char[] in = s.toCharArray();
        
        int inIdx=0, outIdx=0;
        char c;
        char[] buf = new char[in.length]; 
        boolean escapeMode=false;
        int charvar = 0;
        
        while (inIdx < in.length) {
            c = in[inIdx++];
            if (c == '&') {
                c = in[inIdx++];
                if (c=='#'){
                    escapeMode=true;
                    charvar=0;
                }else{
                    buf[outIdx++] = '&';
                    buf[outIdx++] = c;
                }
            }else if (c == ';' && escapeMode) {
                escapeMode=false;
                buf[outIdx++] = (char)charvar;
            }else{
                if (escapeMode){
                    charvar = charvar*10 + c - '0';
                }else{
                    buf[outIdx++] = c;
                }
            }
        }

        return new String(buf,0,outIdx);
    }
    static final public boolean isNumber(String s){
        if (s == null || s.length() == 0)
            return false;
        for (int i=0; i<s.length();i++)
            if (s.charAt(i)<'0'||s.charAt(i)>'9')
                return false;
        return true;
    }
    static final public String xin64Encode(long l){
        char encodeTable[] = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
                'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
        };
        String s = "";
        while(l>0){
            s = encodeTable[(((int)l)&0x3f)] + s;
            l = l >> 6;
        }
        return s;
    }
    static final public String xin64Encode(int i){
        char encodeTable[] = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
                'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
        };
        String s = "";
        while(i>0){
            s = encodeTable[(i&0x3f)] + s;
            i = i >> 6;
        }
        return s;
    }
    static final public int xin64DecodeInt(String s){
        byte[] decodeTable = {
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,-1,-1,  // -
                52,53,54,55,56,57,58,59,60,61,-1,-1,-1,-1,-1,-1,  // 0-9
                -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,  // A-O
                15,16,17,18,19,20,21,22,23,24,25,-1,-1,-1,-1,63,  // P-Z,_
                -1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,  // a-o
                41,42,43,44,45,46,47,48,49,50,51,-1,-1,-1,-1,-1}; // p-z
        
        char[] c = s.toCharArray();
        int ret=0;
        for (int i=0; i<c.length; i++){
            if (c[i]<0||c[i]>'z')
                return 0;//or throws Exception
            int v = decodeTable[c[i]];
            ret = (ret<<6) | v;
        }
        return ret;
    }    
    static final public long xin64DecodeLong(String s){
        byte[] decodeTable = {
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,-1,-1,  // -
                52,53,54,55,56,57,58,59,60,61,-1,-1,-1,-1,-1,-1,  // 0-9
                -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,  // A-O
                15,16,17,18,19,20,21,22,23,24,25,-1,-1,-1,-1,63,  // P-Z,_
                -1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,  // a-o
                41,42,43,44,45,46,47,48,49,50,51,-1,-1,-1,-1,-1}; // p-z
        
        char[] c = s.toCharArray();
        long ret=0;
        for (int i=0; i<c.length; i++){
            if (c[i]<0||c[i]>'z')
                return 0;//or throws Exception
            int v = decodeTable[c[i]];
            ret = (ret<<6) | v;
        }
        return ret;
    }    
}
