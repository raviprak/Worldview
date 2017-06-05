package data;

import java.io.*;

public class util
{
	static void writeInt(FileOutputStream fout, int d) throws IOException
	{
		long filter=255, towrite;		
		for(int i=0; i<4; i++)
		{
			towrite=d&filter;
			towrite=towrite>>>(i*8);
			fout.write((int)towrite);
			filter=filter<<8;
		}
	}
	
	static int readInt(FileInputStream fin) throws IOException
	{
		int toret=0, temp;
		for(int i=0; i<4; i++)
		{
			temp=fin.read();
			temp=temp<<(i*8);
			toret=toret|temp;
		}
		return toret;					
	}
	
	public static String getProperty(String from, String propname)
	{
		int pos=from.indexOf(propname);
		if(pos<0) return null;
		
		StringBuffer toret=new StringBuffer( from.substring(pos+propname.length()+1) );
		
		pos=toret.indexOf("\ua432");
		while(pos+2<toret.length() && toret.charAt(pos+1)=='\ua432') pos=toret.indexOf("\ua432", pos+2);
		if(pos<0) return toret.toString();
		toret=new StringBuffer(toret.substring(0,pos));
		for(pos=0;pos<toret.length()-1; pos++)	if(toret.charAt(pos)=='\ua432'&&toret.charAt(pos+1)=='\ua432') toret.deleteCharAt(pos+1);
		return toret.toString();	
	}

}