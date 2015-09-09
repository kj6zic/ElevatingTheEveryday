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
public class FacebookFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "FacebookFragment";
    private WebView facebookWebview;
    private SwipeRefreshLayout refreshfeed;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.facebook, container, false);

        refreshfeed = (SwipeRefreshLayout) rootView.findViewById(R.id.refreshfeed);
        refreshfeed.setOnRefreshListener(this);
        facebookWebview = (WebView) rootView.findViewById(R.id.webView1);
        WebSettings webSettings = facebookWebview.getSettings();
        facebookWebview.setWebViewClient(new SWWebViewClient());
        webSettings.setJavaScriptEnabled(true);
        facebookWebview.loadUrl(getResources().getString(R.string.facebookUrl));
        facebookWebview.setOnKeyListener(new View.OnKeyListener() {
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

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshfeed.setRefreshing(false);
                facebookWebview.loadUrl(getResources().getString(R.string.facebookUrl));
            }
        }, 2000);
    }

    private class SWWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}
