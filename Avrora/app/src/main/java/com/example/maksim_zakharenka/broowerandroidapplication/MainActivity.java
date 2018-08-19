package com.example.maksim_zakharenka.broowerandroidapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

    static final List<String> AVAILABLE_HOSTS = new ArrayList<String>() {{
        add(Constants.URL.URL_HOST);
        add(Constants.URL.URL_HOST_COUNTRY);
    }};

    static final List<String> CHROME_TAB_HOSTS = new ArrayList<String>() {{
        add(Constants.URL.URL_HOST_PROFILE);
    }};

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

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
                Log.d("thecriser", "shouldOverrideUrlLoading");
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
                Log.d("thecriser", "onPageStarted");

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(final WebView view, final String url) {
                Log.d("thecriser", "onPageFinished");

                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(final WebView view, final String url) {
                Log.d("thecriser", "onLoadResource");

                super.onLoadResource(view, url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, final String url) {
                Log.d("thecriser", "shouldInterceptRequest");

                return super.shouldInterceptRequest(view, url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, final WebResourceRequest request) {
                Log.d("thecriser", "shouldInterceptRequest");

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onTooManyRedirects(final WebView view, final Message cancelMsg, final Message continueMsg) {
                Log.d("thecriser", "onTooManyRedirects");

                super.onTooManyRedirects(view, cancelMsg, continueMsg);
            }

            @Override
            public void onReceivedError(final WebView view, final int errorCode, final String description, final String failingUrl) {
                Log.d("thecriser", "onReceivedError");

                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedHttpError(final WebView view, final WebResourceRequest request, final WebResourceResponse errorResponse) {
                Log.d("thecriser", "onReceivedHttpError");

                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onFormResubmission(final WebView view, final Message dontResend, final Message resend) {
                Log.d("thecriser", "onFormResubmission");

                super.onFormResubmission(view, dontResend, resend);
            }

            @Override
            public void doUpdateVisitedHistory(final WebView view, final String url, final boolean isReload) {
                Log.d("thecriser", "doUpdateVisitedHistory");

                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onReceivedClientCertRequest(final WebView view, final ClientCertRequest request) {
                Log.d("thecriser", "onReceivedClientCertRequest");

                super.onReceivedClientCertRequest(view, request);
            }

            @Override
            public void onReceivedHttpAuthRequest(final WebView view, final HttpAuthHandler handler, final String host, final String realm) {
                Log.d("thecriser", "onReceivedHttpAuthRequest");

                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public boolean shouldOverrideKeyEvent(final WebView view, final KeyEvent event) {
                Log.d("thecriser", "shouldOverrideKeyEvent");

                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void onUnhandledKeyEvent(final WebView view, final KeyEvent event) {
                Log.d("thecriser", "onUnhandledKeyEvent");

                super.onUnhandledKeyEvent(view, event);
            }

            @Override
            public void onScaleChanged(final WebView view, final float oldScale, final float newScale) {
                Log.d("thecriser", "onScaleChanged");

                super.onScaleChanged(view, oldScale, newScale);
            }

            @Override
            public void onReceivedLoginRequest(final WebView view, final String realm, @Nullable final String account, final String args) {
                Log.d("thecriser", "onReceivedLoginRequest");

                super.onReceivedLoginRequest(view, realm, account, args);
            }

            @Override
            public boolean onRenderProcessGone(final WebView view, final RenderProcessGoneDetail detail) {
                Log.d("thecriser", "onRenderProcessGone");

                return super.onRenderProcessGone(view, detail);
            }

            @Override
            public void onSafeBrowsingHit(final WebView view, final WebResourceRequest request, final int threatType, final SafeBrowsingResponse callback) {
                Log.d("thecriser", "onSafeBrowsingHit");

                super.onSafeBrowsingHit(view, request, threatType, callback);
            }

            public boolean shouldOverrideUrlLoading(final WebView view, final String pUrl) {
                Log.d("thecriser", "shouldOverrideUrlLoading");

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
                        mLogoSplashView.getVisibility() == View.VISIBLE) {
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
