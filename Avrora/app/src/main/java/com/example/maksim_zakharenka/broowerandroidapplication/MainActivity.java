package com.example.maksim_zakharenka.broowerandroidapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int FADE_OUT_MILLIS = 750;

    static final List<String> AVAILABLE_HOSTS = new ArrayList<String>() {{
        add(Constants.URL.URL_HOST);
        add(Constants.URL.URL_HOST_COUNTRY);
//        add(Constants.URL.URL_HOST_PROFILE);
    }};

    private View mProgressBar;
    private View mProgressBarClickableFrame;
    private View mSplashBackgroundView;
    private View mWhiteSplashBackgroundView;
    private View mSplashView;
    private WebView mWebView;
    private boolean mIsErrorDialogShown;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initWebView();
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBarClickableFrame = findViewById(R.id.progressBarClickableFrame);
        mSplashView = findViewById(R.id.splash_view);
        mSplashBackgroundView = findViewById(R.id.splash_background_view);
        mWhiteSplashBackgroundView = findViewById(R.id.white_splash_background_view);
        mWebView = findViewById(R.id.web_view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(final WebView view, final String pUrl) {
                if (isHostAvailable(pUrl)) {
                    showProgressBar();

                    view.loadUrl(pUrl);

                    return false;
                } else {
                    sendUrlIntent(pUrl);

                    return true;
                }
            }

            @Override
            public void onReceivedError(final WebView view, final WebResourceRequest request, final WebResourceError error) {
                super.onReceivedError(view, request, error);

                showSplashView();

                if (!mIsErrorDialogShown) {
                    showAlertDialog();

                    mIsErrorDialogShown = true;
                }
            }

            @Override
            public void onPageCommitVisible(final WebView view, final String url) {
                super.onPageCommitVisible(view, url);

                hideProgressBar();

                if (mSplashBackgroundView.getVisibility() == View.VISIBLE ||
                        mWhiteSplashBackgroundView.getVisibility() == View.VISIBLE ||
                        mSplashView.getVisibility() == View.VISIBLE) {
                    fadeOutLogos();
                }
            }
        });

        mWebView.loadUrl(Constants.URL.URL_HOST);
    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error_title)
                .setMessage(R.string.error_body)
                .setCancelable(false)
                .setPositiveButton(R.string.error_try_again, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        mIsErrorDialogShown = false;

                        showProgressBar();

                        mWebView.reload();
                    }
                })
                .setNegativeButton(R.string.error_cancel,
                        new DialogInterface.OnClickListener() {

                            public void onClick(final DialogInterface dialog, final int id) {
                                mIsErrorDialogShown = false;

                                finish();
                            }
                        });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void sendUrlIntent(final String pUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(pUrl));
        startActivity(intent);
    }

    private void showSplashView() {
        mSplashBackgroundView.setVisibility(View.VISIBLE);
        mWhiteSplashBackgroundView.setVisibility(View.VISIBLE);
        mSplashView.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBarClickableFrame.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mProgressBarClickableFrame.setVisibility(View.GONE);
    }

    private boolean isHostAvailable(final String pUrl) {
        for (final String url : AVAILABLE_HOSTS) {
            if (pUrl.startsWith(url)) {
                return true;
            }
        }

        return false;
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
        mWhiteSplashBackgroundView.startAnimation(fadeOut);
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
                mWhiteSplashBackgroundView.setVisibility(View.GONE);
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
