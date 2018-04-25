package com.zzy.smartweight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.zzy.smartweight.action.AniScalCenter;
import com.zzy.smartweight.action.AniScale;
import com.zzy.smartweight.action.Rollover;
import com.zzy.smartweight.action.Silde;
import com.zzy.smartweight.action.TurnHori;
import com.zzy.smartweight.action.TurnVer;
import com.zzy.smartweight.action.UpDown;
import com.zzy.smartweight.dot.ColorPointHintView;

import java.util.ArrayList;

public class SlipLineLayout extends LinearLayout
{
	
	private final int MOVE_LEFT=0;
	private final int MOVE_RIGHT=1;
	private final int MOVE_NONE=2;

	public static final int ANIM_SILDE =0;
	public static final int ANIM_FILP_HORI =1;
	public static final int ANIM_FILP_VERT =2;
	public static final int ANIM_REAL_PAGE =3;
	public static final int ANIM_CYCLE =4;
	public static final int ANIM_SCALE =5;
	public static final int ANIM_SCALE_CENTER=6;
	public static final int ANIM_MAX =7;
	
	private ArrayList<ViewGroup> lsPanel;
	private int iCurr=0;
	private int iStartX;
	private int iStartY;
	private boolean bFirst;
	private int iHeight;
	private int iWidth;
	private int iMoveState=MOVE_NONE;
	
	private TurnHori filpHori;
	private TurnVer filpVer;
	private Silde silde;
	private Rollover realPage;
	private UpDown cycleAnim;
	private AniScale aniScale;
	private AniScalCenter aniScalCenter;

	private int iAnimWay = ANIM_SILDE;
	private ColorPointHintView mHintView;

	private Handler handler= new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message message) {
			return false;
		}
	});

	public SlipLineLayout(Context context)
    {
	    super(context);
	    // TODO Auto-generated constructor stub
	    Init(context);
    }
	
	public SlipLineLayout(Context context, AttributeSet attrs)
    {
	    super(context,attrs);
	    // TODO Auto-generated constructor stub
	    Init(context);
    }
	
	public SlipLineLayout(Context context, AttributeSet attrs, int defStyle)
    {
	    super(context, attrs, defStyle);
	    // TODO Auto-generated constructor stub
	    Init(context);
    }
	
	public void Init(Context context)
	{
		lsPanel = new ArrayList<ViewGroup>();
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		iHeight= dm.heightPixels;
		iWidth = dm.widthPixels;
	}

	 public void initHintView(ColorPointHintView mHintView,int count,int colSel,int colUnsel)
	{
		this.mHintView = mHintView;
		mHintView.setColor(getResources().getColor(colSel),getResources().getColor(colUnsel));
		mHintView.initView(count,1);
	}

	public void setAniWay(int way)
	{
		this.iAnimWay = way;
	}

	public static void setLayout(View view, int x, int y)
	{ 
		MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
		margin.setMargins(x,y, x+margin.width, y+margin.height); 
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(margin);
		view.setLayoutParams(layoutParams); 
		view.invalidate();
	} 
	
	public void AddItem(ViewGroup view)
	{
		lsPanel.add(view);
	}

	@SuppressLint("ClickableViewAccessibility")
    @Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(iMoveState == MOVE_NONE)
		{
			int x, y;
			x = (int) event.getX();
			y = (int) event.getY();
			int iEvent = event.getAction();
			switch (iEvent)
			{
				case MotionEvent.ACTION_DOWN:
					iStartX = x;
					iStartY = y;
					bFirst = false;
					break;
				case MotionEvent.ACTION_MOVE:
					if (bFirst)
					{
						iStartX = x;
						iStartY = y;
						bFirst = false;
					}
					break;
				case MotionEvent.ACTION_UP:
					y = Math.abs(y - iStartY);
					if (y > iHeight * 20 / 100)
					{
						return true;
					}
					else if (x - iStartX > iWidth * 20 / 100)
					{
						ChangPanel(false);
						return true;
					}
					else if (iStartX - x > iWidth * 20 / 100)
					{
						ChangPanel(true);
						return true;
					}
					break;
				default:
					break;
			}
			return true;
		}
		else 
		{
			if(event.getAction()== MotionEvent.ACTION_UP)
			{
				switch (iMoveState)
				{
					case MOVE_LEFT:
						ChangPanel(true);
						break;
					case MOVE_RIGHT:
						ChangPanel(false);
					default:
						break;
				}
				iMoveState = MOVE_NONE;
			}
			return true;
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event)
	{
		int x, y;
		x = (int) event.getX();
		y = (int) event.getY();
		int iEvent = event.getAction();
		//Log.i("B4A","event:"+iEvent);
		switch (iEvent)
		{
			case MotionEvent.ACTION_DOWN:
				iStartX = x;
				iStartY = y;
				bFirst = false;
				iMoveState = MOVE_NONE;
				break;
			case MotionEvent.ACTION_MOVE:
				if (bFirst)
				{
					iStartX = x;
					iStartY = y;
					bFirst = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				
				//Log.i("B4A","ACTION:"+super.onInterceptTouchEvent(event));
				y = Math.abs(y - iStartY);
				if (y > iHeight * 20 / 100)
				{
					return false;
				}
				else if (x - iStartX > iWidth * 20 / 100)
				{
					iMoveState = MOVE_RIGHT;
					handler.postDelayed(rbChange, 100);
					//ChangPanel(false);
				}
				else if (iStartX - x > iWidth * 20 / 100)
				{
					iMoveState = MOVE_LEFT;
					handler.postDelayed(rbChange, 10);
					//ChangPanel(true);
				}
				break;
			default:
				break;
		}

		return false;
	}

	private Runnable rbCircle = new Runnable()
	{

		@Override
		public void run()
		{
			handler.removeCallbacks(rbCircle);
			iMoveState = MOVE_RIGHT;
			ChangPanel(true);
			iMoveState = MOVE_NONE;

			handler.postDelayed(rbCircle,5000);
		}
	};
	
	
	private Runnable rbChange = new Runnable()
	{
		
		@Override
		public void run()
		{
			switch (iMoveState)
			{
				case MOVE_LEFT:
					ChangPanel(true);
					break;
				case MOVE_RIGHT:
					ChangPanel(false);
				default:
					break;
			}
			iMoveState = MOVE_NONE;
		}
	};
	
	public void RemoveAction()
	{
		handler.removeCallbacks(rbChange);
		iMoveState = MOVE_NONE;
	}

	private void updateVisible(ViewGroup vCurr, ViewGroup vNext)
	{
		ViewGroup view;
		for(int i=0;i<lsPanel.size();i++)
		{
			view =lsPanel.get(i);
			if(view !=vCurr && view !=vNext)
			{
				if(view.getVisibility()== View.VISIBLE)
				{
					view.setVisibility(View.GONE);
				}
			}
		}
	}
	
	private void ChangPanel(boolean bLeft)
	{
		int iNext=0;
		if(bLeft)
		{

			if(iCurr==lsPanel.size()-1)
			{
				iNext=0;
			}
			else 
			{
				iNext=iCurr+1; 
			}
		}
		else 
		{
			if(iCurr==0)
			{
				iNext = lsPanel.size()-1;
			}
			else 
			{
				iNext =iCurr-1; 
			}
		}
		
		ViewGroup vNext= lsPanel.get(iNext);
		ViewGroup vCurr = lsPanel.get(iCurr);

		updateVisible(vCurr,vNext);
		
		switch (iAnimWay)
        {
		case ANIM_SILDE:
			if(silde ==null)
			{
				silde = new Silde(getContext());
			}
			silde.SildeStart(vCurr, vNext, bLeft);
			break;
		case ANIM_FILP_HORI:
			if(filpHori==null)
			{
				filpHori = new TurnHori();
			}
			filpHori.HorizontalFlip(vCurr,vNext,bLeft);
			break;
		case ANIM_FILP_VERT:
			if(filpVer==null)
			{
				filpVer = new TurnVer();
			}
			filpVer.HorizontalFlip(vCurr,vNext,bLeft);
			break;
		case ANIM_REAL_PAGE:
			if(realPage==null)
			{
				realPage = new Rollover(getContext());
			}
			realPage.RealPageStart(vCurr, vNext, bLeft);
			break;
		case ANIM_CYCLE:
			if(cycleAnim==null)
			{
				cycleAnim = new UpDown(getContext());
			}
			cycleAnim.CycleStart(vCurr, vNext, bLeft);
			break;
		case ANIM_SCALE:
			if(aniScale==null)
			{
				aniScale = new AniScale(getContext());
			}
			aniScale.AniScaleStart(vCurr, vNext, bLeft);
			break;
		case ANIM_SCALE_CENTER:
			if(aniScalCenter==null)
			{
				aniScalCenter = new AniScalCenter(getContext());
			}
			aniScalCenter.AniScalCenterStart(vCurr, vNext, bLeft);
			
		default:
			break;
		}
		
		iCurr = iNext;
		mHintView.setCurrent(iCurr);
	}

	public void play()
	{
		handler.postDelayed(rbCircle,5000);
	}
	public void stop()
	{
		handler.removeCallbacks(rbCircle);
	}
	
	public void onDestroy()
	{
		stop();

		if(filpHori!=null)
		{
			filpHori.onDestroy();
		}
		filpHori =null;
		
		if(filpVer!=null)
		{
			filpVer.onDestroy();
		}
		filpVer = null;
		
		if(silde!=null)
		{
			silde.onDestroy();
		}
		silde = null;
		
		if(realPage!=null)
		{
			realPage.onDestroy();
		}
		realPage = null;
		
		if(cycleAnim!=null)
		{
			cycleAnim.onDestroy();
		}
		cycleAnim = null;
		
		if(aniScale !=null)
		{
			aniScale.onDestroy();
		}
		aniScale =null;
		
		if(aniScalCenter !=null)
		{
			aniScalCenter.onDestroy();
		}
		aniScalCenter =null;
		
	}


}
