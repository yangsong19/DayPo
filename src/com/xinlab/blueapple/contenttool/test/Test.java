package com.xinlab.blueapple.contenttool.test;

    import static java.lang.Math.PI;  
    import static java.lang.Math.round;  
    import static java.lang.Math.sin;  
    import static java.lang.Math.cos;  
      
    import java.awt.Component;  
    import java.awt.Container;  
    import java.awt.Dimension;  
    import java.awt.LayoutManager;  
    import java.awt.Point;  
    import java.awt.Rectangle;  
      
    public class Test {
    	public static void main(String[] args) {
			byte[] by = fromHexString(new byte[]{1,2,3});
			for (byte b : by) {
				System.out.println(b);
			}
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
    }