package com.zzy.smartweight.camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.smartweight.BaseActivity;
import com.zzy.smartweight.Common;
import com.zzy.smartweight.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CameraActivity extends BaseActivity implements OnClickListener
{
	public final static String RESULT_PATH ="path";
	public final static String RESULT_X="X";
	public final static String RESULT_Y ="Y";
	public final static String RESULT_Z ="Z";

	private final static int CAMERA_ENTRY= 0x01;
	private final static int CAMERA_FOUCS=0x02;
	private final static int CAMERA_CLICK=0x04;
    private final static int CAMERA_READ=0x08;
	private int iState;

	private CostomCamera scCamera;
	private ImageView imgvCameraFlash;
	private RelativeLayout lyCameraBack;
	private String sFlashType;
	private TextView tvItem;

	private ScaleAnimation mAnimation;
	private float x=0;
	private float y=0;

	private SoundPool soundPool;
	private int soundId;

	private CustomSensor customSensor;
	private String cameraPath = null;
	private long startTick = 0;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setOverly(true);
		setContentView(R.layout.camera_layout);
		setToolBarBackgroundColor(R.color.colorCamera);

        tvItem = findViewById(R.id.tvItem);

		scCamera = findViewById(R.id.scCamera);
		scCamera.setCallBack(cameraInterface);
		scCamera.SetFlashMode(sFlashType);


		imgvCameraFlash = findViewById(R.id.imgvCameraFlash);
		imgvCameraFlash.setOnClickListener(this);

		ImageView imgvCameraTake = findViewById(R.id.imgvCameraTake);
		imgvCameraTake.setOnClickListener(this);


		mAnimation =new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
		mAnimation.setDuration(500);
		mAnimation.setRepeatMode(2);
		mAnimation.setRepeatCount(0xffff);

		lyCameraBack = findViewById(R.id.lyCameraBack);
		lyCameraBack.setOnTouchListener( new OnTouchListener()
		{

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if(event.getAction()== MotionEvent.ACTION_DOWN)
				{
					x= event.getX();
					y= event.getY();
				}
				else if(event.getAction()== MotionEvent.ACTION_UP)
				{
					scCamera.AutoFocus();
				}
				return true;
			}
		});

		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundId = soundPool.load(this, R.raw.camera_click, 11);

		iState = CAMERA_ENTRY;

		startTick = SystemClock.elapsedRealtime();
		customSensor = new CustomSensor(this);
		customSensor.setValueListen(new CustomSensor.ValueListen() {
            @Override
            public void getValues(float[] values) {

            	long endTick = SystemClock.elapsedRealtime();
            	if(Math.abs(endTick-startTick)>1000)
				{
					values[0] = ((int)(values[0]*100))/100;
					values[1] = ((int)(values[1]*100))/100;
					values[2] = ((int)(values[2]*100))/100;
					tvItem.setText("x:"+values[0]+" y:"+values[1]+" z:"+values[2]);
					startTick = endTick;
				}

            }
        });
		getFlashType();
	}


	@Override
	protected void onResume()
	{
		super.onResume();
		customSensor.start();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		customSensor.stop();
	}

	private void getFlashType()
	{
		SharedPreferences sp = getSharedPreferences("camera_param", Context.MODE_PRIVATE);
		if(sp.contains("flash_type")) {
			sFlashType = sp.getString("flash_type",Camera.Parameters.FLASH_MODE_OFF);

		}else{
			sFlashType = Camera.Parameters.FLASH_MODE_OFF;
		}
		LoadFlashBitmap(sFlashType);
	}

	private void saveFlashType()
	{
		SharedPreferences sp = getSharedPreferences("camera_param", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("flash_type", sFlashType);
		editor.commit();
	}

	private void LoadFlashBitmap(String sType)
	{
		if(sType.equals(Camera.Parameters.FLASH_MODE_OFF))
		{
			imgvCameraFlash.setImageResource(R.mipmap.pocket_camera_flash_off);
		}
		else if(sType.equals(Camera.Parameters.FLASH_MODE_ON))
		{
			imgvCameraFlash.setImageResource(R.mipmap.pocket_camera_flash_on);
		}
		else 
		{
			imgvCameraFlash.setImageResource(R.mipmap.pocket_camera_flash_auto);
		}

		imgvCameraFlash.setScaleType(ScaleType.FIT_XY);
	}
	
	private CostomCamera.CameraInterface cameraInterface = new CostomCamera.CameraInterface()
	{
		
		@Override
		public void camera_ready(boolean bReady)
		{
		}

		private File getPhotoPath()
		{
			File file = null;
			File filePhoto = Common.getPhotoPath();
			String sName = null;
			while (true) {
				sName = Common.generateUUID() + ".jpg";
				file = new File(filePhoto, sName);
				if (!file.exists()) {
					break;
				}
			}

			return file;
		}
		
		@Override
		public void camera_picturetaken(byte[] data)
		{
			iState = CAMERA_READ;
			scCamera.StopPreview();

			File filePhoto = getPhotoPath();
			cameraPath = filePhoto.toString();
			BufferedOutputStream output=null;
			try
			{
				output = new BufferedOutputStream(new FileOutputStream(filePhoto,false));
				output.write(data);
				output.close();
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					result();
					CameraActivity.this.finish();
				}
			});
			
//			try
//			{
//				scCamera.StartPreview();
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//			catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
		}
		
		@Override
		public void camera_focusdone(boolean bSuccess)
		{
			if(((iState & CAMERA_CLICK)>0) && ((iState & CAMERA_ENTRY)>0))
			{
				scCamera.TakePicture();
				iState =iState | CAMERA_ENTRY;
				playNotify();
			}
		}
	};
	
	private void playNotify()
	{
		soundPool.play(soundId, 1, 1, 0, 0, 1);
	}

	@Override
    public void onClick(View v)
    {
		switch (v.getId())
		{
			case R.id.imgvCameraTake:
				if ((iState & CAMERA_CLICK)>0)
				{
					return;
				}
				
				try
				{
					scCamera.CancelAutoFocus();
				}
				catch (SecurityException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (InvocationTargetException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iState= iState | CAMERA_CLICK;
				if ((iState & CAMERA_ENTRY)>0)
				{
//					setLayout(imgvAnimation,lyCameraBack.getWidth()/2,lyCameraBack.getHeight()/2);
//					SetFoucsBack(TYPE_AUTO_FOUCS,true);
//					imgvAnimation.startAnimation(mAnimation);
					scCamera.AutoFocus();
				}
				else 
				{
					playNotify();
					scCamera.TakePicture();
				}
				break;
			case R.id.imgvCameraFlash:
				if(sFlashType.equals(Camera.Parameters.FLASH_MODE_OFF))
				{
					sFlashType = Camera.Parameters.FLASH_MODE_AUTO;
				}
				else if(sFlashType.equals(Camera.Parameters.FLASH_MODE_ON))
				{
					sFlashType = Camera.Parameters.FLASH_MODE_OFF;
				}
				else 
				{
					sFlashType = Camera.Parameters.FLASH_MODE_ON;
				}
				LoadFlashBitmap(sFlashType);
				scCamera.SetFlashMode(sFlashType);
				saveFlashType();
				break;
			default:
				break;
		}
	    
    }

    private void result()
	{
		Intent intent = new Intent();
		float[] values = customSensor.getOrientation();
		intent.putExtra(RESULT_PATH,cameraPath);
		intent.putExtra(RESULT_X,values[0]);
		intent.putExtra(RESULT_Y,values[1]);
		intent.putExtra(RESULT_Z,values[2]);

		setResult(Activity.RESULT_OK,intent);
	}

}
