package com.zzy.smartweight.action;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.zzy.smartweight.R;

public class UpDown
{
	private Animation visToInvisLeft;
	private Animation invisTovisLeft;
	
	private Animation visToInvisRight;
	private Animation invisTovisRight;
	
	private ViewGroup viewCurr;
	
	public UpDown(Context context)
	{
		visToInvisLeft = AnimationUtils.loadAnimation(context, R.anim.pocket_up_left_out);
		invisTovisLeft = AnimationUtils.loadAnimation(context, R.anim.pocket_up_left_into);
		
		visToInvisRight = AnimationUtils.loadAnimation(context, R.anim.pocket_up_right_out);
		invisTovisRight = AnimationUtils.loadAnimation(context, R.anim.pocket_up_right_into);
	}
	
	private void reset(Animation anim)
	{
		if( !anim.hasEnded())
		{
			anim.cancel();
		}
		anim.reset();
	}
	
	public void CycleStart(ViewGroup vCurr, ViewGroup vNext, boolean bLeft)
	{
		vCurr.setVisibility(View.VISIBLE);
		vNext.setVisibility(View.VISIBLE);
		
		reset(visToInvisLeft);
		reset(invisTovisLeft);
		
		reset(visToInvisRight);
		reset(invisTovisRight);
		
		if(bLeft)
		{
			vCurr.setAnimation(visToInvisLeft);
			visToInvisLeft.setAnimationListener(animationCurr);
			visToInvisLeft.start();
			
			vNext.setAnimation(invisTovisLeft);
			invisTovisLeft.start();
		}
		else 
		{
			vCurr.setAnimation(visToInvisRight);
			visToInvisRight.setAnimationListener(animationCurr);
			visToInvisRight.start();
			
			vNext.setAnimation(invisTovisRight);
			invisTovisRight.start();
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
		visToInvisLeft.cancel();
		invisTovisLeft.cancel();	
		visToInvisRight.cancel();
		invisTovisRight.cancel();
	}
		
}
