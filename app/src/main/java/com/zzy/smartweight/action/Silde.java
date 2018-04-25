package com.zzy.smartweight.action;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.zzy.smartweight.R;

public class Silde
{
	private Animation visToInvisRight;
	private Animation invisToVisRight;
	
	private Animation visToInvisLeft;
	private Animation invisToVisLeft;
	
	private ViewGroup viewCurr;
	
	public Silde(Context context)
	{
		visToInvisRight = AnimationUtils.loadAnimation(context, R.anim.pocket_slide_right_out);
		invisToVisRight = AnimationUtils.loadAnimation(context, R.anim.pocket_slide_right_into);
		
		visToInvisLeft = AnimationUtils.loadAnimation(context, R.anim.pocket_slide_left_out);
		invisToVisLeft = AnimationUtils.loadAnimation(context, R.anim.pocket_slide_left_into);
	}
	
	private void reset(Animation anim)
	{
		if( !anim.hasEnded())
		{
			anim.cancel();
		}
		anim.reset();
	}
	
	public void SildeStart(ViewGroup vCurr, ViewGroup vNext, boolean bLeft)
	{	
		reset(visToInvisRight);
		reset(invisToVisRight);
		reset(visToInvisLeft);
		reset(invisToVisLeft);
		
		setVisible(vCurr,vNext);
		
		if(bLeft)
		{
			vCurr.setAnimation(visToInvisLeft);
			vNext.setAnimation(invisToVisLeft);
			visToInvisLeft.setAnimationListener(animationCurr);
			
			visToInvisLeft.start();
			invisToVisLeft.start();
		}
		else 
		{
			vCurr.setAnimation(visToInvisRight);
			vNext.setAnimation(invisToVisRight);
			visToInvisRight.setAnimationListener(animationCurr);
			
			visToInvisRight.start();
			invisToVisRight.start();
		}
		
		viewCurr = vCurr;
	}
	
	private void setVisible(ViewGroup vCurr, ViewGroup vNext)
	{
		vCurr.setVisibility(View.VISIBLE);
		vNext.setVisibility(View.VISIBLE);
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
		invisToVisRight.cancel();	
		visToInvisLeft.cancel();
		invisToVisLeft.cancel();
	}
}
