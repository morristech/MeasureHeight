package com.zzy.smartweight;

import android.content.Context;
import android.database.DefaultDatabaseErrorHandler;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Random;


public class Common
{
	private final static String DIR_NAEM = "SmartMeasure";
	private final static String DIR_PHOTO = "photo";
	
	public static void LogEx(String sLog)
	{
		Log.v("SmartWeight",sLog);
	}

	private static String sRootDir;
	
	public static int Rnd(int Min, int Max)
	{
		Random random =null;
		random = new Random();
		return Min + random.nextInt(Max - Min);
	}

	public static int strToInt(String sDig,int def)
	{
		try{
			return Integer.parseInt(sDig);
		}catch (NumberFormatException e) {
			return def;
		}
	}

	public static String generateUUID()
	{
		long iNow = (new DateTime()).getNow();
		long iRnd = Rnd(1, 2147483647);

		return String.valueOf(iNow)+String.valueOf(iRnd);
	}


	/**
	 * dpתpx
	 *
	 */
	public static int dip2px(Context ctx, float dpValue) {
		final float scale = ctx.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}


	/**
	 *	pxתdp
	 */
	public static int px2dip(Context ctx,float pxValue) {
		final float scale = ctx.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public static File getRootDir()
	{
		if(sRootDir ==null)
		{
			StringBuilder sbTmp = new StringBuilder();
			sbTmp.append(Environment.getExternalStorageDirectory().toString()).append("/").append(DIR_NAEM);
			sRootDir = sbTmp.toString();
		}

		File file = new File(sRootDir);
		if(!file.exists())
		{
			file.mkdirs();
		}

		return file;
	}

	public static File getPhotoPath()
	{
		File file = getRootDir();
		file = new File(file,DIR_PHOTO);
		if(!file.exists())
		{
			file.mkdirs();
		}

		return file;
	}


}
