package com.mzw.newversiondemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mzw.image.base.ImageLoader;
import com.mzw.image.glide.config.GlideApp;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //使用方式
        ImageView imageView = findViewById(R.id.imageview);
        String url = "https://ps.ssl.qhmsg.com/bdr/1728__/t013d7a21145c708288.jpg";
//        String url = "https://upload-images.jianshu.io/upload_images/3257965-0ef84c1f26b327a6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/480/format/webp";
        ImageLoader.getInstance()
                .load(url)
//                .angle(80)
                .override(800)
                .centerCrop()
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
//                .skipLocalCache(true)
//                .rotate(0)
                .into(imageView);

//        GlideApp.with(this)
//                .load(url)
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
            }
        });
    }
}
