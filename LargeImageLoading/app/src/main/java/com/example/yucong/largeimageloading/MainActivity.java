package com.example.yucong.largeimageloading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //长图片缩放
//        SubsamplingScaleImageView image_big= (SubsamplingScaleImageView) findViewById(R.id.image_big);
//
//        image_big.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
//        image_big.setMaxScale(5f);
//        image_big.setMinScale(0.1f);
//        image_big.setImage(ImageSource.resource(R.mipmap.icon));



        SubsamplingScaleImageView image_big= (SubsamplingScaleImageView) findViewById(R.id.image_big1);

//        image_big.setMinimumScaleType(SubsamplingScaleImageView.ORIENTATION_USE_EXIF);

        image_big.setOrientation(SubsamplingScaleImageView.ORIENTATION_0);
        image_big.setOrientation(SubsamplingScaleImageView.ORIENTATION_90);

        image_big.setOrientation(SubsamplingScaleImageView.ORIENTATION_180);
        image_big.setOrientation(SubsamplingScaleImageView.ORIENTATION_270);
        image_big.setOrientation(SubsamplingScaleImageView.ORIENTATION_USE_EXIF);

        image_big.setImage(ImageSource.resource(R.mipmap.icon));






    }
}
