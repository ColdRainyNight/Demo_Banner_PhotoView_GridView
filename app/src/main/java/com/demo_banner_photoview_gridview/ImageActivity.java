package com.demo_banner_photoview_gridview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class ImageActivity extends AppCompatActivity {
    private PhotoView photoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageavtivity);

        Intent it = getIntent();
        String im = it.getStringExtra("image");

        photoview = (PhotoView) findViewById(R.id.photoview);
        Glide.with(this).load(im).into(photoview);
    }
}
