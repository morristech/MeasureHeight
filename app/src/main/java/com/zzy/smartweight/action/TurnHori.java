package com.zzy.smartweight.action;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class TurnHori
{
	private ObjectAnimator visToInvisRight;
	private ObjectAnimator invisToVisRight;
	
	private ObjectAnimator visToInvisLeft;
	private ObjectAnimator invisToVisLeft;

	public TurnHori()
	{
		Interpolator accelerator = new AccelerateInterpolator();
		Interpolator decelerator = new DecelerateInterpolator();
		
		visToInvisRight = new ObjectAnimator();
		visToInvisRight.setDuration(300);
		visToInvisRight.setPropertyName("rotationY");
		visToInvisRight.setFloatValues(0f, -90f);
		visToInvisRight.setInterpolator(accelerator);
		
		invisToVisRight = new ObjectAnimator();
		invisToVisRight.setDuration(300);
		invisToVisRight.setPropertyName("rotationY");
		invisToVisRight.setFloatValues(90f, 0f);
		invisToVisRight.setInterpolator(decelerator);
		
		visToInvisLeft = new ObjectAnimator();
		visToInvisLeft.setDuration(300);
		visToInvisLeft.setPropertyName("rotationY");
		visToInvisLeft.setFloatValues(0f, 90f);
		visToInvisLeft.setInterpolator(accelerator);
		
		invisToVisLeft = new ObjectAnimator();
		invisToVisLeft.setDuration(300);
		invisToVisLeft.setPropertyName("rotationY");
		invisToVisLeft.setFloatValues(-90f, 0f);
		invisToVisLeft.setInterpolator(decelerator);
	}

	public void HorizontalFlip(ViewGroup vCurr, ViewGroup vNext, boolean bLeft)
	{
		ViewGroup visibleView = vCurr;
		ViewGroup invisibleView = vNext;
		if (bLeft)
		{
			invisToVisLeft.setTarget(invisibleView);
			visToInvisLeft.setTarget(visibleView);
			visToInvisLeft.addListener(adapterLeft);
			visToInvisLeft.start();
		}
		else
		{
			invisToVisRight.setTarget(invisibleView);
			visToInvisRight.setTarget(visibleView);
			visToInvisRight.addListener(adapterRight);
			visToInvisRight.start();
		}
		

	}
	
	private AnimatorListenerAdapter adapterLeft = new AnimatorListenerAdapter()
	{
		@Override
		public void onAnimationEnd(Animator anim)
		{
			View view = (View)visToInvisLeft.getTarget();
			view.setVisibility(View.GONE);
			invisToVisLeft.start();
			view = (View)invisToVisLeft.getTarget();
			view.setVisibility(View.VISIBLE);
		}
	};
	
	private AnimatorListenerAdapter adapterRight = new AnimatorListenerAdapter()
	{
		@Override
		public void onAnimationEnd(Animator anim)
		{
			View view = (View)visToInvisRight.getTarget();
			view.setVisibility(View.GONE);
			invisToVisRight.start();
			view = (View)invisToVisRight.getTarget();
			view.setVisibility(View.VISIBLE);
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
