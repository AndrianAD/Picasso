package com.example.picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;


public class MainActivity extends AppCompatActivity {
    private Button buttonPicasso, btLink, btPhoto, btVideo;
    private ImageView imageView;
    private static final String ACCESS_TOKEN = "xI7j8VloxgAAAAAAAAAAHk0kTDulVWjIkkiRsHN9QSe6QvGfbRsCh9RMGiSmnzd7";
    private static final String APP_KEY = "8ti87qvwglh8ja3";
    private static final String APP_SECRET = "uzrnug4f3p81ldb";
    private CallbackManager callbackManager;
    LoginButton loginButton;
    TextView textView;
    ShareDialog shareDialog;
    Target target;
    private int REQUEST_CODE=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPicasso = findViewById(R.id.button4);
        imageView = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView);
        btLink = findViewById(R.id.buttonLink);
        btPhoto = findViewById(R.id.buttonPhoto);
        btVideo = findViewById(R.id.buttonVideo);


        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email");
        shareDialog = new ShareDialog(this);

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(MainActivity.this, "Ошибка загрузки фото", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        btLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("Tecт")
                        .setContentUrl(Uri.parse("https://www.youtube.com/watch?v=fEA00WDqCfs"))
                        .build();
                if (shareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(linkContent);
                }
            }
        });


        btPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4r3SoRHZHX3PZ7d5ZJGVr2PYVAsoE_Ut-FBTCK_ndv_-FmLXwEw")
                        .into(target);
            }
        });


        btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Выберите видео"),REQUEST_CODE);
            }
        });

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                textView.setText("Успешно !  " + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                textView.setText("Ошибка !");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        buttonPicasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Callback callBack = new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "Картинка загрузилась", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, "Картинка  НЕ загрузилась", Toast.LENGTH_LONG).show();

                    }
                };


                Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSS6wnfu9372TIPwu3cOcY5-FbxNpSmTMPSfsl2fVWSBRnWxMZ2XQ")
                        .placeholder(R.drawable.ic_assignment_return_black_24dp)
                        .error(R.drawable.ic_assignment_return_black_24dp)
                        .rotate(33)
                        .into(imageView, callBack);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                Uri selectedVideo = data.getData();
                shareVideo(selectedVideo);
            }


}
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void shareVideo(Uri selectedVideo) {
        ShareVideo shareVideo= new ShareVideo.Builder()
                .setLocalUrl(selectedVideo)
                .build();
        ShareVideoContent videocontent = new ShareVideoContent.Builder()
                .setContentDescription("видео супер !")
                .setVideo(shareVideo)
                .build();
        if (shareDialog.canShow(ShareVideoContent.class)) {
            shareDialog.show(videocontent);
        }
    }


}
