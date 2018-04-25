package com.zzy.smartweight.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CostomCamera extends SurfaceView implements SurfaceHolder.Callback
{

	private SurfaceHolder mHolder;
	private Camera mCameraDevices;
	private CameraInterface cameraInterface;
	
	public CostomCamera(Context context)
	{
		super(context);
		init();
	}

	public CostomCamera(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}
	
	private void init()
	{
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	public void setCallBack(CameraInterface cameraInterface)
	{
		this.cameraInterface = cameraInterface;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		if(cameraInterface!=null)
		{
			cameraInterface.camera_ready(true);
		}
		
		try
        {
			InitPara(getContext());
			//AutoFocus();
	        StartPreview();
        }
        catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        catch (InterruptedException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		try
		{
			mCameraDevices = Camera.open();
			mCameraDevices.setPreviewDisplay(mHolder);
		}
		catch (Exception e)
		{
			if (mCameraDevices != null)
			{
				mCameraDevices.release();
			}
			mCameraDevices = null;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		if (mCameraDevices == null)
		{
			return;
		}
		mCameraDevices.stopPreview();
		mCameraDevices.release();
		mCameraDevices = null;
	}
	
	private int GetCameraId()
	{
		CameraInfo cameraInfo = new CameraInfo();
		int iCount = Camera.getNumberOfCameras();
		int cameraValue = 0;

		for (int i = 0; i < iCount; i++)
		{
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == cameraValue)
			{
				return i;
			}
		}
		return -1;
	}
	
	private CameraInfo getCameraInfo()
	{
		CameraInfo cameraInfo = new CameraInfo();
		int iCount = Camera.getNumberOfCameras();
		int cameraValue = 0;

		for (int i = 0; i < iCount; i++)
		{
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == cameraValue)
			{
				return cameraInfo;
			}
		}
		return null;
	}

	private String getFlashType()
	{
		SharedPreferences sp = getContext().getSharedPreferences("camera_param", Context.MODE_PRIVATE);
		if(sp.contains("flash_type")) {
			return sp.getString("flash_type",Camera.Parameters.FLASH_MODE_OFF);

		}else{
			return Camera.Parameters.FLASH_MODE_OFF;
		}
	}


	public void InitPara(Context context)
	{
		if(mCameraDevices==null)
		{
			return;
		}
	    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    int degrees = display.getRotation()*90;
	    
	    Camera.Parameters parameters = mCameraDevices.getParameters();
	    CameraInfo cameraInfo = getCameraInfo();
	    if(cameraInfo!=null)
	    {
	    	int orientation= cameraInfo.orientation;
	    	int previewRotation  = (orientation - degrees + 360) % 360;
	    	mCameraDevices.setDisplayOrientation(previewRotation);
			parameters.setRotation(previewRotation);
	    }

		parameters.setFlashMode(getFlashType());
	    parameters.setPictureFormat(ImageFormat.JPEG);
	    parameters.set("jpeg-quality", 100);
	    parameters.setJpegQuality(90);
		mCameraDevices.setParameters(parameters);
	}
	
	
	public void SetFlashMode(String sMode)
	{
		if(mCameraDevices==null)
		{
			return;
		}
		Camera.Parameters parameters = mCameraDevices.getParameters();
		parameters.setFlashMode(sMode);
		mCameraDevices.setParameters(parameters);
	}
	
	public void SetJpegQuality(int Quality)
	{
		Camera.Parameters parameters = mCameraDevices.getParameters();
		parameters.setJpegQuality(Quality);
	}

	public void StartPreview() throws IOException, InterruptedException
	{
		Thread.sleep(50L);
		this.mCameraDevices.setPreviewDisplay(this.getHolder());
		this.mCameraDevices.startPreview();
	}

	public void StopPreview()
	{
		if (this.mCameraDevices != null)
		{
			this.mCameraDevices.stopPreview();
		}
	}
	
	public void AutoFocus()
	{
		try
        {
			this.mCameraDevices.autoFocus(new Camera.AutoFocusCallback()
			{
				public void onAutoFocus(boolean success, Camera camera)
				{
					if(cameraInterface!=null)
					{
						cameraInterface.camera_focusdone(success);
					}
				}
			});
        }
        catch (Exception e)
        {
	        e.printStackTrace();
        }

	}

	public void CancelAutoFocus() throws SecurityException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
	{
		try
		{
			Method m = Camera.class.getMethod("cancelAutoFocus", new Class[0]);
			m.invoke(this.mCameraDevices, new Object[0]);
		} 
		catch (NoSuchMethodException localNoSuchMethodException)
		{
			localNoSuchMethodException.printStackTrace();
		}
	}
	
	public void TakePicture()
	{
		if (mCameraDevices == null)
		{
			//Log.v("B4A", "TakePicture camera==null");
			return;
		}
		
		try
        {
			mCameraDevices.takePicture(null, null, new Camera.PictureCallback()
			{
				public void onPictureTaken(byte[] data, Camera camera)
				{
					cameraInterface.camera_picturetaken(data);
				}
			});
        }
        catch (Exception e)
        {
	       e.printStackTrace();
        }

	}
	
	public interface CameraInterface
	{
		public void camera_ready(boolean bReady);
		public void camera_picturetaken(byte[] data);
		public void camera_focusdone(boolean bSuccess);
	}
}

