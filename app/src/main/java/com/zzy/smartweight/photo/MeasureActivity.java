package com.zzy.smartweight.photo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.zzy.smartweight.BaseActivity;
import com.zzy.smartweight.Common;
import com.zzy.smartweight.GlideApp;
import com.zzy.smartweight.R;
import com.zzy.smartweight.ReferChildInfo;
import com.zzy.smartweight.SmartWeightApp;
import com.zzy.smartweight.camera.CameraActivity;
import com.zzy.smartweight.materialrangebar.RangeBar;

import java.io.File;
import java.security.MessageDigest;

public class MeasureActivity extends BaseActivity {

    private TextView tvResult;
    private TextView tvEntity;
    private TextView tvReference;
    private TextView tvAngle;
    private ReferChildInfo referChildInfo;
    private float refH = 0;
    private float entityH = 0;

    private float x,y,z;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_layout);

        Intent intent = getIntent();
        if(intent== null || !intent.hasExtra(CameraActivity.RESULT_PATH))
        {
            this.finish();
            return;
        }

        referChildInfo = SmartWeightApp.getInstance().getCurrRefer();
        if(referChildInfo==null)
        {
            showToast(R.string.STR_SELECT_REFERENCE);
            this.finish();
            return;
        }

        String path = intent.getStringExtra(CameraActivity.RESULT_PATH);
        File file = new File(path);

        x = intent.getFloatExtra(CameraActivity.RESULT_X,0.0f);
        y = intent.getFloatExtra(CameraActivity.RESULT_Y,0.0f);
        z = intent.getFloatExtra(CameraActivity.RESULT_Z,0.0f);

        Common.LogEx("MeasureActivity path:"+path);

        ImageView imgvPhoto = findViewById(R.id.imgvPhoto);
        RotateTransformation rotateTransformation = new RotateTransformation(x,y,z);
        GlideApp.with(MeasureActivity.this)
        .load(file)
        .centerInside()//.transform(rotateTransformation)
        .into(imgvPhoto);

        tvResult = findViewById(R.id.tvResult);
        tvEntity = findViewById(R.id.tvEntity);
        tvReference = findViewById(R.id.tvReference);
        tvAngle = findViewById(R.id.tvAngle);

        String tmp = getString(R.string.STR_ANGLE);
        tmp = String.format(tmp, x,y,z);
        tvAngle.setText(tmp);

        final RangeBar rangebar1 = findViewById(R.id.rangebar1);

        rangebar1.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                String tmp = getString(R.string.STR_REFERENCE_HEIGHT);
                refH = rangebar1.getTickPixel();
                tvReference.setText(tmp+String.valueOf(refH));

                result(refH,entityH);
            }

        });

        final RangeBar rangebar2 = findViewById(R.id.rangebar2);
        rangebar2.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                String tmp = getString(R.string.STR_ENTITY_HEIGHT);
                entityH = rangebar2.getTickPixel();
                tvEntity.setText(tmp+String.valueOf(entityH));

                result(refH,entityH);
            }

        });
    }

    private void result(float refH,float entityH)
    {
        if(refH<1 || entityH<1)
        {
            return;
        }
        int height = referChildInfo.getHeight();


        float res = entityH*height/refH;

        String tmp = getString(R.string.STR_RESULT);
        tvResult.setText(tmp+ String.valueOf(res));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private static class RotateTransformation extends BitmapTransformation {

        private float rotateRotationAngle = 0f;
        private Camera camera;

        public RotateTransformation(float x, float y,float z)
        {
            camera = new Camera();
            camera.save();
            camera.rotateX(0);
            camera.rotateY(0);
            camera.rotateZ(0);
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Matrix matrix = new Matrix();
            camera.getMatrix(matrix);
            camera.restore();
            return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }
}
