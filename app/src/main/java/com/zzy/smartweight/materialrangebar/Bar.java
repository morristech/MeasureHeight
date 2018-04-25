/*
 * Copyright 2013, Edmodo, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" 
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */

package com.zzy.smartweight.materialrangebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

import com.zzy.smartweight.R;

import java.util.Arrays;

/**
 * This class represents the underlying gray bar in the RangeBar (without the
 * thumbs).
 */
public class Bar {

    // Member Variables ////////////////////////////////////////////////////////

    private final Paint mBarPaint;

    private final Paint mTickPaint;

    // Left-coordinate of the horizontal bar.
    private final float mLeftX;

    private final float mRightX;

    private final float mY;

    private int mNumSegments;

    private float mTickDistance;

    private final float mTickHeight;
    private int barPoint;

    /**
     * 短线的长度
     */
    private float mShortLineLength;
    /**
     * 长线的长度
     */
    private float mLongLineLength;
    /**
     * 中间线的长度
     */
    private float mMiddleLineLength;

    /**
     * 绘制文本的画笔
     */
    private Paint mTextPaint;

    /**
     * 保存线位置的数组
     */
    private float[] mLinesArr = new float[4];

    // Constructor /////////////////////////////////////////////////////////////

    /**
     * Bar constructor
     *
     * @param ctx          the context
     * @param x            the start x co-ordinate
     * @param y            the y co-ordinate
     * @param length       the length of the bar in px
     * @param tickCount    the number of ticks on the bar
     * @param tickHeight the height of each tick
     * @param tickColor    the color of each tick
     * @param barWeight    the weight of the bar
     * @param barColor     the color of the bar
     */
    public Bar(Context ctx,
            float x,
            float y,
            float length,
            int tickCount,
            float tickHeight,
            int tickColor,
            float barWeight,
            int barColor,
            int barPoint) {

        mLeftX = x;
        mRightX = x + length;
        mY = y;
        this.barPoint = barPoint;

        mNumSegments = tickCount - 1;
        mTickDistance = length / mNumSegments;
        mTickHeight = tickHeight;
        // Initialize the paint.

        mBarPaint = new Paint();
        mBarPaint.setColor(barColor);
        mBarPaint.setStrokeWidth(barWeight);
        mBarPaint.setAntiAlias(true);

        float tickWeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, ctx.getResources().getDisplayMetrics());
        mTickPaint = new Paint();
        mTickPaint.setColor(tickColor);
        mTickPaint.setStrokeWidth(tickWeight);
        mTickPaint.setAntiAlias(true);

        mShortLineLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, ctx.getResources().getDisplayMetrics());
        mMiddleLineLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, ctx.getResources().getDisplayMetrics());
        mLongLineLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, ctx.getResources().getDisplayMetrics());

        float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, ctx.getResources().getDisplayMetrics());

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(tickColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    // Package-Private Methods /////////////////////////////////////////////////

    /**
     * Draws the bar on the given Canvas.
     *
     * @param canvas Canvas to draw on; should be the Canvas passed into {#link
     *               View#onDraw()}
     */
    public void draw(Canvas canvas) {

        switch (barPoint)
        {
            case RangeBar.POINT_TO_LEFT:
            case RangeBar.POINT_TO_RIGHT:
                canvas.drawLine(mY,mLeftX, mY,mRightX, mBarPaint);
                break;
            default:
                canvas.drawLine(mLeftX, mY, mRightX, mY, mBarPaint);
                break;
        }

    }

    /**
     * Get the x-coordinate of the left edge of the bar.
     *
     * @return x-coordinate of the left edge of the bar
     */
    public float getLeftX() {
        return mLeftX;
    }

    /**
     * Get the x-coordinate of the right edge of the bar.
     *
     * @return x-coordinate of the right edge of the bar
     */
    public float getRightX() {
        return mRightX;
    }

    /**
     * Gets the x-coordinate of the nearest tick to the given x-coordinate.
     *
     * @param thumb the thumb to find the nearest tick for
     * @return the x-coordinate of the nearest tick
     */
    public float getNearestTickCoordinate(PinView thumb) {

        final int nearestTickIndex = getNearestTickIndex(thumb);

        return mLeftX + (nearestTickIndex * mTickDistance);
    }

    /**
     * Gets the zero-based index of the nearest tick to the given thumb.
     *
     * @param thumb the Thumb to find the nearest tick for
     * @return the zero-based index of the nearest tick
     */
    public int getNearestTickIndex(PinView thumb) {

        switch (barPoint)
        {
            case RangeBar.POINT_TO_LEFT:
            case RangeBar.POINT_TO_RIGHT:
                return (int) ((thumb.getY() - mLeftX + mTickDistance / 2f) / mTickDistance);
            default:
                return (int) ((thumb.getX() - mLeftX + mTickDistance / 2f) / mTickDistance);
        }

    }


    /**
     * Set the number of ticks that will appear in the RangeBar.
     *
     * @param tickCount the number of ticks
     */
    public void setTickCount(int tickCount) {

        final float barLength = mRightX - mLeftX;

        mNumSegments = tickCount - 1;
        mTickDistance = barLength / mNumSegments;
    }

    public float getTickDistance()
    {
        return mTickDistance;
    }

    /**
     * 重置线组
     */
    private void resetLinesArr() {
        if (mLinesArr.length < (mNumSegments + 1) * 4) {
            //需要重新创建数组
            mLinesArr = new float[(mNumSegments + 1) * 4];
        } else {
            Arrays.fill(mLinesArr, 0);
        }
    }

    // Private Methods /////////////////////////////////////////////////////////

    /**
     * Draws the tick marks on the bar.
     *
     * @param canvas Canvas to draw on; should be the Canvas passed into {#link
     *               View#onDraw()}
     */
    public void drawTicks(Canvas canvas) {

        float mx,my,x;
        float maxTextWidth = mTextPaint.measureText(String.valueOf("9999"));
        resetLinesArr();
        // Loop through and draw each tick (except final tick).
        float lineLength;
        float textX;
        float lineX2;
        for (int i = 0; i <= mNumSegments; i++) {

            switch (barPoint)
            {
                case RangeBar.POINT_TO_LEFT:
                    mx = mY;
                    my =i * mTickDistance + mLeftX;
                    textX = mx+mLongLineLength+maxTextWidth/2;
                    break;
                case RangeBar.POINT_TO_RIGHT:
                    mx = mY;
                    my =i * mTickDistance + mLeftX;
                    textX = mx-mLongLineLength-maxTextWidth/2;
                    break;
                default:
                    mx = i * mTickDistance + mLeftX;;
                    my = mY;
                    textX = mx+mLongLineLength+maxTextWidth/2;
                    break;
            }

            switch (i % 10) {
                case 0:
                    String text = String.valueOf(i);
                    canvas.drawText(text, textX, my, mTextPaint);
                    lineLength = mLongLineLength;
                    break;
                case 5:
                    lineLength = mMiddleLineLength;
                    break;
                default:
                    lineLength = mShortLineLength;
                    break;
            }


            switch (barPoint)
            {
                case RangeBar.POINT_TO_LEFT:
                    mLinesArr[i * 4] = mx /*+ maxTextWidth*/;
                    mLinesArr[i * 4 + 1] = my;
                    mLinesArr[i * 4 + 2] = mx + lineLength;
                    mLinesArr[i * 4 + 3] = my;
                    break;
                case RangeBar.POINT_TO_RIGHT:
                    mLinesArr[i * 4] = mx /*+ maxTextWidth*/;
                    mLinesArr[i * 4 + 1] = my;
                    mLinesArr[i * 4 + 2] = mx - lineLength;
                    mLinesArr[i * 4 + 3] = my;
                    break;
                default:
                    mLinesArr[i * 4] = mx /*+ maxTextWidth*/;
                    mLinesArr[i * 4 + 1] = my;
                    mLinesArr[i * 4 + 2] = mx + lineLength;
                    mLinesArr[i * 4 + 3] = my;
                    break;
            }

            //canvas.drawCircle(mx, my, mTickHeight, mTickPaint);
        }
        //绘制线
        canvas.drawLines(mLinesArr, mTickPaint);
    }
}
