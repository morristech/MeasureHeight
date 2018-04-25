package com.zzy.smartweight.action;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.zzy.smartweight.R;

public class Rollover
{	
	private Animation visToInvisRight;
	private Animation visToInvisLeft;
	
	private Animation invisToInvisRight;
	private Animation invisToInvisLeft;
	
	private ViewGroup viewCurr;
	
	public Rollover(Context context)
	{
		visToInvisRight = AnimationUtils.loadAnimation(context, R.anim.pocket_filp_right);
		visToInvisLeft = AnimationUtils.loadAnimation(context, R.anim.pocket_filp_left);
		
		invisToInvisRight = AnimationUtils.loadAnimation(context, R.anim.pocket_rotate_alpha_left);
		invisToInvisLeft = AnimationUtils.loadAnimation(context, R.anim.pocket_rotate_alpha_left);
	}
	
	private void reset(Animation anim)
	{
		if( !anim.hasEnded())
		{
			anim.cancel();
		}
		anim.reset();
	}
	
	public void RealPageStart(ViewGroup vCurr, ViewGroup vNext, boolean bLeft)
	{
		vCurr.setVisibility(View.VISIBLE);
		vNext.setVisibility(View.VISIBLE);
		
		reset(visToInvisRight);
		reset(invisToInvisLeft);
		
		reset(invisToInvisRight);
		reset(visToInvisLeft);
		
		vCurr.bringToFront();
		
		if (bLeft)
		{
			vCurr.setAnimation(visToInvisLeft);
			visToInvisLeft.setAnimationListener(animationCurr);
			visToInvisLeft.start();
			
			vNext.setAnimation(invisToInvisLeft);
			invisToInvisLeft.start();
		}
		else
		{
			vCurr.setAnimation(visToInvisRight);
			visToInvisRight.setAnimationListener(animationCurr);
			visToInvisRight.start();
			
			vNext.setAnimation(invisToInvisRight);
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
		}
	};
	
	public void onDestroy()
	{
		visToInvisRight.cancel();
		visToInvisLeft.cancel();	
		invisToInvisRight.cancel();
		invisToInvisLeft.cancel();
	}
		
	
}
