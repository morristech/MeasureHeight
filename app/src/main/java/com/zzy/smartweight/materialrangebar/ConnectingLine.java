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
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Class representing the blue connecting line between the two thumbs.
 */
public class ConnectingLine {

    // Member Variables ////////////////////////////////////////////////////////

    private final Paint mPaint;

    private final float fixCo;

    private int barPoint;

    // Constructor /////////////////////////////////////////////////////////////

    /**
     * Constructor for connecting line
     *
     * @param ctx                  the context for the line
     * @param fixCo                the x or y co-ordinate for the line
     * @param connectingLineWeight the weight of the line
     * @param connectingLineColor  the color of the line
     */
    public ConnectingLine(Context ctx, float fixCo, float connectingLineWeight,
            int connectingLineColor,int barPoint) {

        final Resources res = ctx.getResources();

        // Initialize the paint, set values
        mPaint = new Paint();
        mPaint.setColor(connectingLineColor);
        mPaint.setStrokeWidth(connectingLineWeight);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        this.barPoint = barPoint;
        this.fixCo = fixCo;
    }

    // Package-Private Methods /////////////////////////////////////////////////

    /**
     * Draw the connecting line between the two thumbs in rangebar.
     *
     * @param canvas     the Canvas to draw to
     * @param leftThumb  the left thumb
     * @param rightThumb the right thumb
     */
    public void draw(Canvas canvas, PinView leftThumb, PinView rightThumb) {
        switch (barPoint)
        {
            case RangeBar.POINT_TO_LEFT:
            case RangeBar.POINT_TO_RIGHT:
                canvas.drawLine(fixCo,leftThumb.getY(), fixCo,rightThumb.getY(),mPaint);
                break;
            default:
                canvas.drawLine(leftThumb.getX(), fixCo, rightThumb.getX(), fixCo, mPaint);
                break;
        }

    }

    /**
     * Draw the connecting line between for single slider.
     *
     * @param canvas     the Canvas to draw to
     * @param rightThumb the right thumb
     * @param leftMargin the left margin
     */
    public void draw(Canvas canvas, float leftMargin, PinView rightThumb) {

        switch (barPoint)
        {
            case RangeBar.POINT_TO_LEFT:
            case RangeBar.POINT_TO_RIGHT:
                canvas.drawLine(fixCo,leftMargin, fixCo,rightThumb.getY(),mPaint);
                break;
            default:
                canvas.drawLine(leftMargin, fixCo, rightThumb.getX(), fixCo, mPaint);
                break;
        }
    }
}
