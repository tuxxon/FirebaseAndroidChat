package com.touchizen.firebaseandroidchat.view;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;

import com.bumptech.glide.request.transition.Transition;
import com.touchizen.firebaseandroidchat.R;
import com.touchizen.firebaseandroidchat.adapter.CircleTransform;

public class FullScreenImageActivity extends AppCompatActivity {

    private TouchImageView mImageView;
    private ImageView ivUser;
    private TextView tvUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.gc();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void bindViews(){
        progressDialog = new ProgressDialog(this);
        mImageView = (TouchImageView) findViewById(R.id.imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivUser = (ImageView)toolbar.findViewById(R.id.avatar);
        tvUser = (TextView)toolbar.findViewById(R.id.title);
    }

    private void setValues(){
        String nameUser,urlPhotoUser,urlPhotoClick;
        nameUser = getIntent().getStringExtra("nameUser");
        urlPhotoUser = getIntent().getStringExtra("urlPhotoUser");
        urlPhotoClick = getIntent().getStringExtra("urlPhotoClick");
        Log.i("TAG","imagem recebida "+urlPhotoClick);
        tvUser.setText(nameUser); // Name
        Glide.with(this).load(urlPhotoUser).centerCrop().transform(new CircleTransform(this)).override(40,40).into(ivUser);

        Glide.with(this).asBitmap().load(urlPhotoClick).override(640,640).fitCenter().into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                progressDialog.dismiss();
                mImageView.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                progressDialog.setMessage("Carregando Imagem...");
                progressDialog.show();
            }
        });


//        Glide.with(this).load( urlPhotoClick).asBitmap().override(640,640).fitCenter().into(new SimpleTarget<Bitmap>() {
//
//            @Override
//            public void onLoadStarted(Drawable placeholder) {
//                progressDialog.setMessage("Carregando Imagem...");
//                progressDialog.show();
//            }
//
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                progressDialog.dismiss();
//                mImageView.setImageBitmap(resource);
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                Toast.makeText(FullScreenImageActivity.this,"Erro, tente novamente",Toast.LENGTH_LONG).show();
//                progressDialog.dismiss();
//            }
//        });
    }

}
