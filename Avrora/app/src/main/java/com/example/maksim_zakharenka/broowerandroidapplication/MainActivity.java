package com.example.maksim_zakharenka.broowerandroidapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    public static final String URL_HOST = "https://avroramarket.by/";
    public static final int FADE_OUT_MILLIS = 750;

    private View mSplashBackgroundView;
    private View mSplashView;
    private WebView mWebView;
    private boolean mIsError;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initWebView();
    }

    private void initViews() {
        mSplashView = findViewById(R.id.splash_view);
        mSplashBackgroundView = findViewById(R.id.splash_background_view);
        mWebView = findViewById(R.id.web_view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                if (url.startsWith(URL_HOST)) {
                    view.loadUrl(url);

                    return true;
                } else {
                    super.shouldOverrideUrlLoading(view, url);
                    return false;
                }
            }

            @Override
            public void onReceivedError(final WebView view, final WebResourceRequest request, final WebResourceError error) {
                super.onReceivedError(view, request, error);

                mIsError = true;

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Ошибка сети")
                        .setMessage("Проверьте подключение к интернету и повторите снова.")
                        .setCancelable(false)
                        .setPositiveButton("Повторить", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                initWebView();
                            }
                        })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(final DialogInterface dialog, final int id) {
                                        finish();
                                    }
                                });

                final AlertDialog alert = builder.create();
                alert.show();
            }

            public void onPageFinished(final WebView view, final String url) {
                if (mIsError) {

                } else {
                    fadeOutLogos();
                }

                mIsError = false;
            }
        });

        mWebView.loadUrl(URL_HOST);
    }

    private void fadeOutLogos() {
        final Animation fadeOut = getFadeOutAnimation();
        fadeOut.setAnimationListener(getLogosAnimationListener());

        mSplashView.startAnimation(fadeOut);
    }

    private void fadeOutBackground() {
        final Animation fadeOut = getFadeOutAnimation();

        fadeOut.setAnimationListener(getBackgroundAnimationListener());

        mSplashBackgroundView.startAnimation(fadeOut);
    }

    private Animation getFadeOutAnimation() {
        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(FADE_OUT_MILLIS);

        return fadeOut;
    }

    private Animation.AnimationListener getBackgroundAnimationListener() {
        return new SimpleAnimationListener() {

            public void onAnimationEnd(final Animation animation) {
                mSplashBackgroundView.setVisibility(View.GONE);
            }
        };
    }

    private Animation.AnimationListener getLogosAnimationListener() {
        return new SimpleAnimationListener() {

            public void onAnimationEnd(final Animation animation) {
                mSplashView.setVisibility(View.GONE);

                fadeOutBackground();
            }
        };
    }
}
