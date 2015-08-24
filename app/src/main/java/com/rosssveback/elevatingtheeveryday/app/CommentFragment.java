package com.rosssveback.elevatingtheeveryday.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rosssveback.elevatingtheeveryday.adaptor.CommentsViewAdaptor;
import com.rosssveback.elevatingtheeveryday.model.Comments;
import com.rosssveback.elevatingtheeveryday.util.Config;
import com.rosssveback.elevatingtheeveryday.util.JSONParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import me.declangao.wordpressreader.R;

/**
 * Fragment to display a Disqus comments page.
 * Activities that contain this fragment must implement the
 * {@link CommentFragment.CommentsListListener} interface
 * to handle interaction events.
 */
public class CommentFragment extends Fragment {
    private static final String TAG = "CommentFragment";
    protected static final String POST_ID = "id";
    protected static final String QUERY = "query";

    //private WebView webView;
    private Toolbar toolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mLoadingView;
    private CommentsViewAdaptor mCommentsViewAdaptor;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Comments> commentsList = new ArrayList<>();
    private boolean isLoading = false;
    private boolean isSearch = false;

    //this is to keep track of the items in the recycler view
    private int mPastVisibleItems;
    private int mVisibleItemCount;


    private int totalItemCount;
    private int mPage = 1; //Page number
    private int mpostId; //ID of the wordPress post
    private int mCommentNum; //Number of comments in the wordPress post
    private int mPreviousCommentNum = 0; // Number of comments in the Post


    private CommentsListListener mListener;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(int id) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putInt(POST_ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_comment, container, false);

        //pull to refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLoadingView = (TextView) rootView.findViewById(R.id.text_view_loading);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //getActivity().setTitle(getResources().getString(R.string.action_comments));
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        //pull to refresh listener
        //mSwipeRefreshLayout.setOnRefreshListener(this);

        // Setup WebView
        //webView = (WebView) rootView.findViewById(R.id.webView_comment);

        mCommentsViewAdaptor = new CommentsViewAdaptor(commentsList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCommentsViewAdaptor);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                mPastVisibleItems = mLayoutManager.getChildCount();
                mVisibleItemCount = mLayoutManager.findFirstVisibleItemPosition();

                int totalItemCount = mLayoutManager.getItemCount();

                if (mCommentNum > mPreviousCommentNum && !commentsList.isEmpty()
                        && mVisibleItemCount != 0 && totalItemCount > mVisibleItemCount && !isLoading
                        && (mPastVisibleItems + mVisibleItemCount) >= totalItemCount) {

                    //update the comment number
                    mPreviousCommentNum = mCommentNum;
                }
            }

        });

        return rootView;
    }

    /**
     * load comment page
     *
     * @param
     * @return
     */
    public void loadCommentPage() {
        mPage = 1; // Reset page number

        if (commentsList.isEmpty()) {
            showLoadingView();
            // Reset post number to 0
            mPreviousCommentNum = 0;
            loadComments(mPage, false);
        } else {
            hideLoadingView();
        }
    }

    /**
     * @param page
     * @param showLoadingMsg
     */
    private void loadComments(int page, final boolean showLoadingMsg) {

        Log.d(TAG, "----------------- Loading post id " + mpostId +
                ", page " + String.valueOf(page));

        isLoading = true;

        if (showLoadingMsg) {
            Toast.makeText(getActivity(), getString(R.string.loading_comments),
                    Toast.LENGTH_LONG).show();
        }

        // Construct the proper API Url
        //this is work in progress
        String url;
            url = Config.POST_URL + String.valueOf(mpostId);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mSwipeRefreshLayout.setRefreshing(false); // Stop when done

                        // Parse JSON data
                        commentsList.addAll(JSONParser.parseComments(jsonObject));

                        // A temporary workaround to avoid downloading duplicate posts in some
                        // rare circumstances by converting ArrayList to a LinkedHashSet without
                        // losing its order
                        Set<Comments> set = new LinkedHashSet<>(commentsList);
                        commentsList.clear();
                        commentsList.addAll(new ArrayList<>(set));

                        mCommentNum = commentsList.size(); // The newest post number
                        Log.d(TAG, "Number of comments: " + mCommentNum);
                        mCommentsViewAdaptor.notifyDataSetChanged(); // Display the list

                        // Set ListView position
                        if (CommentFragment.this.mPage != 1) {
                            // Move the article list up by one row
                            // We don't actually need to add 1 here since position starts at 0
                            mLayoutManager.scrollToPosition(mPastVisibleItems + mVisibleItemCount);
                        }

                        // Loading finished. Set flag to false
                        isLoading = false;

                        hideLoadingView();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        isLoading = false;
                        hideLoadingView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        volleyError.printStackTrace();
                        Log.d(TAG, "----- Error: " + volleyError.getMessage());

                        // Show a Snackbar with a retry button
                        Snackbar.make(mRecyclerView, R.string.error_load_posts,
                                Snackbar.LENGTH_LONG).setAction(R.string.action_retry,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //loadFirstPage();
                                        loadComments(mPage, true);
                                    }
                                }).show();
                    }
                });

        // Set timeout to 10 seconds instead of the default value 5 since my
        // crappy server is quite slow
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add request to request queue
        AppController.getInstance().addToRequestQueue(request, TAG);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mListener.onHomePressed();
        }
        return true;
    }

    /**
     * Since we can't call setArguments() on an existing fragment, we make our own!
     *
     * @param args Bundle containing information about the new comments page
     */
    /*protected void setUIArguments(final Bundle args) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                int id = args.getInt("id");

                // Create Disqus Thread ID
                String disqusThreadId = id + " " + Config.BASE_URL + "?p=" + id;
                // Create Disqus URL for this specific post
                String url = Config.BASE_URL + "showcomments.php?disqus_id=" + disqusThreadId;

                WebSettings webSettings = webView.getSettings();
                // Enable JavaScript
                webSettings.setJavaScriptEnabled(true);
                // Let the WebView handle links in stead of opening links in external web browsers
                webView.requestFocusFromTouch();
                // User a custom WebViewClient to solve Disqus login and logout issues on Android
                // See http://globeotter.com/blog/disqus-android-code/
                webView.setWebViewClient(new MyWebViewClient(url));
                webView.setWebChromeClient(new WebChromeClient());

                // Lollipop only code
                // Required on Lollipop in order to save login state
                if (Build.VERSION.SDK_INT >= 21) {
                    // Enable cookies
                    CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
                }

                // Load Disqus
                webView.loadUrl(url);

                Log.d(TAG, "Disqus Thread Id: " + disqusThreadId);

                // Reset Actionbar
                ((MainActivity) getActivity()).setSupportActionBar(toolbar);
                ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((MainActivity)getActivity()).getSupportActionBar()
                        .setTitle(getString(R.string.action_comments));
            }
        });
    }*/

    /**
     * Eliminate the chance of showing previous content by clearing the fragment on hidden.
     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        if (webView != null && hidden) {
//            webView.loadData("", "text/html; charset=UTF-8", null);
//        }
//
//        super.onHiddenChanged(hidden);
//    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CommentsListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CommentListener");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface CommentsListListener {
        void onHomePressed();
    }

    /**
     * Show the loading view and hide the list
     */
    private void showLoadingView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the loading view and show the list
     */
    private void hideLoadingView() {
        mLoadingView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

}
