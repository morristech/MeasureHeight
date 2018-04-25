package com.zzy.smartweight.action;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class Rotate3d
{
	private AniRotate3d visToInvisRight;
	private AniRotate3d visToInvisLeft;
	
	private AniRotate3d invisToInvisRight;
	private AniRotate3d invisToInvisLeft;
	
	private ViewGroup viewCurr;
	
	public Rotate3d(Context context)
	{
        DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		float mCenterX = dm.widthPixels / 2;
		float mCenterY = dm.heightPixels / 2;
		
		
		visToInvisLeft = new AniRotate3d(0 , -90 , 0, mCenterX, mCenterY);
		visToInvisLeft.setFillAfter(true);
		visToInvisLeft.setDuration(500);
		
		invisToInvisLeft = new AniRotate3d(90 , 0 , 0, mCenterX, mCenterY);
		invisToInvisLeft.setDuration(500);
		invisToInvisLeft.setFillAfter(true);
		
		visToInvisRight = new AniRotate3d(-90 , 0 , 0, mCenterX, mCenterY);
		visToInvisRight.setFillAfter(true);
		visToInvisRight.setDuration(500);
		
		invisToInvisRight = new AniRotate3d(0,90, 0, mCenterX, mCenterY);
		invisToInvisRight.setFillAfter(true);
		invisToInvisRight.setDuration(500);

	}
	
	private void reset(Animation anim)
	{
		if( !anim.hasEnded())
		{
			anim.cancel();
		}
		anim.reset();
	}
	
	public void Rotate3DStart(ViewGroup vCurr, ViewGroup vNext, boolean bLeft)
	{
		vCurr.setVisibility(View.VISIBLE);
		vNext.setVisibility(View.VISIBLE);
		
		reset(visToInvisRight);
		reset(invisToInvisLeft);
		
		reset(invisToInvisRight);
		reset(visToInvisLeft);
		
		//vCurr.bringToFront();
		
		if (bLeft)
		{
			vCurr.setAnimation(visToInvisLeft);
			visToInvisLeft.setAnimationListener(animationCurr);
			visToInvisLeft.start();
			
			vNext.setAnimation(invisToInvisLeft);
			visToInvisRight.setAnimationListener(animationNext);
			invisToInvisLeft.start();
		}
		else
		{
			vCurr.setAnimation(visToInvisRight);
			visToInvisRight.setAnimationListener(animationCurr);
			visToInvisRight.start();
			
			vNext.setAnimation(invisToInvisRight);
			visToInvisRight.setAnimationListener(animationNext);
			invisToInvisRight.start();
		}
		
		viewCurr = vCurr;
	}
	
	AnimationListener animationCurr = new AnimationListener()
	{
		@Override
		public void onAnimationStart(Animation animation)
		{
	
		}
		
		@Override
		public void onAnimationRepeat(Animation animation)
		{
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation)
		{
			viewCurr.setVisibility(View.GONE);
			visToInvisLeft.cancel();
			visToInvisRight.cancel();
		}
	};
	
	AnimationListener animationNext = new AnimationListener()
	{
		@Override
		public void onAnimationStart(Animation animation)
		{
	
		}
		
		@Override
		public void onAnimationRepeat(Animation animation)
		{
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation)
		{
			invisToInvisLeft.cancel();
			invisToInvisRight.cancel();
		}
	};
		
}
