package com.rosssveback.elevatingtheeveryday.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.declangao.wordpressreader.R;

/**
 * Created by a1121661 on 9/4/15.
 */
public class TwitterFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "FacebookFragment";

    private SwipeRefreshLayout refreshtwitterfeed;
    private WebView twitterWebView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.twitter, container, false);

        refreshtwitterfeed = (SwipeRefreshLayout) rootView.findViewById(R.id.refreshtwitterfeed);
        refreshtwitterfeed.setOnRefreshListener(this);
        twitterWebView = (WebView) rootView.findViewById(R.id.webView2);
        WebSettings webSettings = twitterWebView.getSettings();
        twitterWebView.setWebViewClient(new SWWebViewClient());
        webSettings.setJavaScriptEnabled(true);
        twitterWebView.loadUrl(getResources().getString(R.string.twitterUrl));

        twitterWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });
        return rootView;
    }
    private class SWWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshtwitterfeed.setRefreshing(false);
                twitterWebView.loadUrl((getResources().getString(R.string.twitterUrl)));
            }
        }, 2000);
    }
}
