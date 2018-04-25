package com.zzy.smartweight.action;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.zzy.smartweight.R;

public class AniScale
{
	private Animation visToInvisLeft;
	private Animation invisTovisLeft;
	
	private ViewGroup viewCurr;
	private ViewGroup viewNext;
	
	public AniScale(Context context)
	{
		visToInvisLeft = AnimationUtils.loadAnimation(context, R.anim.pocket_zoom_exit);
		invisTovisLeft = AnimationUtils.loadAnimation(context, R.anim.pocket_zoom_enter);
	}
	
	private void reset(Animation anim)
	{
		if( !anim.hasEnded())
		{
			anim.cancel();
		}
		anim.reset();
	}
	
	public void AniScaleStart(ViewGroup vCurr, ViewGroup vNext, boolean bLeft)
	{
		vCurr.setVisibility(View.VISIBLE);
		vNext.setVisibility(View.VISIBLE);
		
		reset(visToInvisLeft);
		reset(invisTovisLeft);

		vCurr.setAnimation(visToInvisLeft);
		visToInvisLeft.setAnimationListener(animationCurr);
		visToInvisLeft.start();

		vNext.setAnimation(invisTovisLeft);
		invisTovisLeft.setAnimationListener(animationNext);
		invisTovisLeft.start();

		viewCurr = vCurr;
		viewNext = vNext;
	}
	
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
			viewNext.setVisibility(View.VISIBLE);
		}
	};
	
	
	
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
		visToInvisLeft.cancel();
		invisTovisLeft.cancel();	
	}
}
