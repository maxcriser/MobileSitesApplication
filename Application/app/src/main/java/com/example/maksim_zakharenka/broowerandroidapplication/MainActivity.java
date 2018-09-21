package com.example.maksim_zakharenka.broowerandroidapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

import static com.example.maksim_zakharenka.broowerandroidapplication.BrandConstants.AVAILABLE_HOSTS;
import static com.example.maksim_zakharenka.broowerandroidapplication.BrandConstants.CHROME_TAB_HOSTS;

public class MainActivity extends AppCompatActivity {

    private View mProgressBar;
    private View mProgressBarClickableFrame;
    private View mSplashBackgroundView;
    private View mWhiteSplashBackgroundView;
    private View mLogoSplashView;
    private WebView mWebView;
    private boolean mIsErrorDialogShown;
    private CustomTabsIntent mCustomTabsIntent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCustomTabsIntent();
        initViews();
        initWebView();
    }

    private void initCustomTabsIntent() {
        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(this.getResources().getColor(R.color.brand_color))
                .setShowTitle(true)
                .enableUrlBarHiding()
                .build();

        CustomTabsHelper.addKeepAliveExtra(this, mCustomTabsIntent.intent);
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBarClickableFrame = findViewById(R.id.progressBarClickableFrame);
        mLogoSplashView = findViewById(R.id.logo_splash_view);
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
                if (isHostStartsWithUrl(AVAILABLE_HOSTS, pUrl)) {
                    showProgressBar();

                    view.loadUrl(pUrl);

                    return false;
                } else if (isHostStartsWithUrl(CHROME_TAB_HOSTS, pUrl)) {
                    CustomTabsHelper.openCustomTab(MainActivity.this, mCustomTabsIntent, Uri.parse(pUrl), new WebViewFallback());

                    return true;
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

                if (!mIsErrorDialogShown && (mSplashBackgroundView.getVisibility() == View.VISIBLE || mWhiteSplashBackgroundView.getVisibility() == View.VISIBLE || mLogoSplashView.getVisibility() == View.VISIBLE)) {
                    fadeOutLogos();
                }
            }

            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
                if (error.getPrimaryError() == 1001) {
                    handler.cancel();
                } else {
                    handler.proceed();
                }
            }
        });

        mWebView.loadUrl(BrandConstants.URL.URL_HOST);
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
        mLogoSplashView.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBarClickableFrame.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mProgressBarClickableFrame.setVisibility(View.GONE);
    }

    private boolean isHostStartsWithUrl(final Iterable<String> pHostList, final String pUrl) {
        for (final String url : pHostList) {
            if (pUrl.startsWith(url)) {
                return true;
            }
        }

        return false;
    }

    private void fadeOutLogos() {
        final Animation fadeOut = getFadeOutAnimation();
        fadeOut.setAnimationListener(getLogosAnimationListener());

        mLogoSplashView.startAnimation(fadeOut);
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
        fadeOut.setDuration(Constants.ANIMATION.FADE_OUT_MILLIS);

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

                mLogoSplashView.setVisibility(View.GONE);

                fadeOutBackground();
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
