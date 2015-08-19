package com.rosssveback.elevatingtheeveryday.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rosssveback.elevatingtheeveryday.model.Post;

import me.declangao.wordpressreader.R;

public class MainActivity extends AppCompatActivity implements
        RecyclerViewFragment.PostListListener, PostFragment.PostListener,
        TabLayoutFragment.TabLayoutListener, SearchResultFragment.SearchResultListener,
        CommentFragment.CommentListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TAB_LAYOUT_FRAGMENT_TAG = "TabLayoutFragment";
    public static final String POST_FRAGMENT_TAG = "PostFragment";
    public static final String COMMENT_FRAGMENT_TAG = "CommentFragment";

    private FragmentManager fm = null;
    private TabLayoutFragment tlf;
    private PostFragment pf;
    private CommentFragment cf;
    private SearchResultFragment srf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        }

        // Setup fragments
        tlf = new TabLayoutFragment();
        pf = new PostFragment();
        cf = new CommentFragment();
        srf = new SearchResultFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, pf, POST_FRAGMENT_TAG);
        ft.add(android.R.id.content, cf, COMMENT_FRAGMENT_TAG);
        ft.add(android.R.id.content, tlf, TAB_LAYOUT_FRAGMENT_TAG);

        ft.hide(pf);
        ft.hide(cf);
        ft.commit();
    }

    /**
     * Invoked when a post in the list is selected
     *
     * @param post Selected Post object
     */
    @Override
    public void onPostSelected(Post post, boolean isSearch) {
        // Find the fragment in order to set it up later
        pf = (PostFragment) getSupportFragmentManager().findFragmentByTag(POST_FRAGMENT_TAG);

        // Set necessary arguments
        Bundle args = new Bundle();
        args.putInt("id", post.getId());
        args.putString("title", post.getTitle());
        args.putString("date", post.getDate());
        args.putString("author", post.getAuthor());
        args.putString("content", post.getContent());
        args.putString("url", post.getUrl());
        //args.putString("thumbnailUrl", post.getThumbnailUrl());
        args.putString("featuredImage", post.getFeaturedImageUrl());

        // Configure PostFragment to display the right post
        pf.setUIArguments(args);

        // Show the fragment
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (!isSearch) { // Hide TabLayoutFragment if this is not search result
            ft.hide(tlf);
        } else { // Otherwise, hide the search result, ie. SearchResultFragment.
            ft.hide(srf);
        }
        ft.show(pf);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Invoked when a search query is submitted
     *
     * @param query Selected Post object
     */
    @Override
    public void onSearchSubmitted(String query) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        // Send query to fragment using factory method
        srf = SearchResultFragment.newInstance(query);
        ft.add(android.R.id.content, srf);
        ft.hide(tlf);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Invoked when comment menu is selected
     *
     * @param id ID of the article, assigned by WordPress
     */
    @Override
    public void onCommentSelected(int id) {
        cf = (CommentFragment) getSupportFragmentManager().findFragmentByTag(COMMENT_FRAGMENT_TAG);
        Bundle args = new Bundle();
        args.putInt("id", id);
        // Setup CommentFragment to display the right comments page
        cf.setUIArguments(args);

        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.hide(pf);
        ft.show(cf);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Intercept back button event, reset ActionBar if necessary
     */
    @Override
    public void onBackPressed() {
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment currentFragment = fm.findFragmentById(R.id.content_frame);
//        if(currentFragment instanceof RecyclerViewFragment){
//            RecyclerViewFragment recyclerViewFragment = ((RecyclerViewFragment) currentFragment);
//            boolean isPopFragment = false;

           // if (!isPopFragment) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setCancelable(true);
//                builder.setMessage(getResources().getString(R.string.exitapplication));
//                builder.setPositiveButton(getResources().getString(R.string.exityes), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//                builder.setNegativeButton(getResources().getString(R.string.exitno), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog alert=builder.create();
//                alert.show();
//                TextView messageText = (TextView)alert.findViewById(android.R.id.message);
//                messageText.setGravity(Gravity.CENTER);
//                resetActionBarIfApplicable();
         //   }
       // }


    }

    /**
     * Simulate a back button press when home is selected
     */
    @Override
    public void onHomePressed() {
        resetActionBarIfApplicable();
        fm.popBackStack();
    }

    /**
     * Reset TabLayoutFragment's ActionBar if necessary
     */
    private void resetActionBarIfApplicable() {
        Log.d(TAG, "SearchResultFragment is visible: " + srf.isHidden());
        if (srf.isVisible()) {
            tlf.resetActionBar();
        }
    }

    // Commented out coz we will let fragments handle their own Options Menus
    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_main, menu);
    //    return true;
    //}
}
