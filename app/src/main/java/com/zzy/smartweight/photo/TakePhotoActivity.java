package com.zzy.smartweight.photo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import com.zzy.smartweight.BaseActivity;
import com.zzy.smartweight.Common;
import com.zzy.smartweight.R;
import com.zzy.smartweight.camera.CameraActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TakePhotoActivity extends BaseActivity {

    private File fileParent;
    private String imagePath;

    private int HANDLE_CAMERA = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);
    }

    public void btnCamera_click(View view)
    {
        Intent intent = new Intent(TakePhotoActivity.this,CameraActivity.class);
        startActivityForResult(intent,HANDLE_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == HANDLE_CAMERA)
        {
            if (resultCode == Activity.RESULT_OK && data!=null)
            {
                Intent intent = new Intent(TakePhotoActivity.this,MeasureActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        }
    }
}
