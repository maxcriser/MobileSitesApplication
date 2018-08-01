package com.example.maksim_zakharenka.broowerandroidapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

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

    private CustomTabsIntent customTabsIntent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(this.getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true)
                .enableUrlBarHiding()
                .build();

        CustomTabsHelper.addKeepAliveExtra(MainActivity.this, customTabsIntent.intent);

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

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("thecriser", "shouldOverrideUrlLoading");
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("thecriser", "onPageStarted");

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("thecriser", "onPageFinished");

                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d("thecriser", "onLoadResource");

                super.onLoadResource(view, url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("thecriser", "shouldInterceptRequest");

                return super.shouldInterceptRequest(view, url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d("thecriser", "shouldInterceptRequest");

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
                Log.d("thecriser", "onTooManyRedirects");

                super.onTooManyRedirects(view, cancelMsg, continueMsg);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.d("thecriser", "onReceivedError");

                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Log.d("thecriser", "onReceivedHttpError");

                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                Log.d("thecriser", "onFormResubmission");

                super.onFormResubmission(view, dontResend, resend);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                Log.d("thecriser", "doUpdateVisitedHistory");

                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                Log.d("thecriser", "onReceivedClientCertRequest");

                super.onReceivedClientCertRequest(view, request);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                Log.d("thecriser", "onReceivedHttpAuthRequest");

                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                Log.d("thecriser", "shouldOverrideKeyEvent");

                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                Log.d("thecriser", "onUnhandledKeyEvent");

                super.onUnhandledKeyEvent(view, event);
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                Log.d("thecriser", "onScaleChanged");

                super.onScaleChanged(view, oldScale, newScale);
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
                Log.d("thecriser", "onReceivedLoginRequest");

                super.onReceivedLoginRequest(view, realm, account, args);
            }

            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
                Log.d("thecriser", "onRenderProcessGone");

                return super.onRenderProcessGone(view, detail);
            }

            @Override
            public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
                Log.d("thecriser", "onSafeBrowsingHit");

                super.onSafeBrowsingHit(view, request, threatType, callback);
            }

            public boolean shouldOverrideUrlLoading(final WebView view, final String pUrl) {
                Log.d("thecriser", "shouldOverrideUrlLoading");

                if (isHostAvailable(pUrl)) {
                    showProgressBar();

                    view.loadUrl(pUrl);

                    return false;
                } else if (pUrl.startsWith(Constants.URL.URL_HOST_PROFILE)) {
                    CustomTabsHelper.openCustomTab(MainActivity.this, customTabsIntent, Uri.parse(pUrl), new WebViewFallback());

                    return true;
                } else {
                    sendUrlIntent(pUrl);

                    return true;
                }
            }

            @Override
            public void onReceivedError(final WebView view, final WebResourceRequest request, final WebResourceError error) {
                Log.d("thecriser", "onReceivedError");

                super.onReceivedError(view, request, error);

                showSplashView();

                if (!mIsErrorDialogShown) {
                    showAlertDialog();

                    mIsErrorDialogShown = true;
                }
            }

            @Override
            public void onPageCommitVisible(final WebView view, final String url) {
                Log.d("thecriser", "onPageCommitVisible");

                super.onPageCommitVisible(view, url);

                hideProgressBar();

                if (mSplashBackgroundView.getVisibility() == View.VISIBLE ||
                        mWhiteSplashBackgroundView.getVisibility() == View.VISIBLE ||
                        mSplashView.getVisibility() == View.VISIBLE) {
                    fadeOutLogos();
                }
            }

            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
                Log.d("thecriser", "onReceivedSslError");

                handler.proceed();
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
