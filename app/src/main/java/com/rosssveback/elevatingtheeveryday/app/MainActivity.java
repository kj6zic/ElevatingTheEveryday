package com.rosssveback.elevatingtheeveryday.app;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rosssveback.elevatingtheeveryday.adaptor.NavigationDrawerAdaptor;
import com.rosssveback.elevatingtheeveryday.model.NavDrawerItemModel;
import com.rosssveback.elevatingtheeveryday.model.Post;

import java.util.ArrayList;

import me.declangao.wordpressreader.R;

public class MainActivity extends AppCompatActivity implements
        RecyclerViewFragment.PostListListener, PostFragment.PostListener,
        TabLayoutFragment.TabLayoutListener, SearchResultFragment.SearchResultListener,
        CommentFragment.CommentsListListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TAB_LAYOUT_FRAGMENT_TAG = "TabLayoutFragment";
    public static final String POST_FRAGMENT_TAG = "PostFragment";
    public static final String COMMENT_FRAGMENT_TAG = "CommentFragment";
    public static final String FB = "FacebookFragment";
    public static final String TF = "TwitterFragment";
    public static final String EF = "WhatsHotFragment";

    private FragmentManager fm = null;
    private TabLayoutFragment tlf;
    private PostFragment pf;
    private CommentFragment cf;
    private SearchResultFragment srf;
    private FacebookFragment fbf;
    private TwitterFragment tf;
    private WhatsHotFragment etm;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String [] navMenuTitles;
    private TypedArray navMenuIcons;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationDrawerAdaptor adaptor;
    private ArrayList<NavDrawerItemModel> navDrawerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        /*mTitle = mDrawerTitle = getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItemModel(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navMenuIcons.recycle();

        adaptor = new NavigationDrawerAdaptor(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adaptor);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.twitterlogo, R.string.app_name, R.string.app_name
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
*/
        fm = getSupportFragmentManager();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        }

        // Setup fragments
        tlf = new TabLayoutFragment();
        pf = new PostFragment();
        cf = new CommentFragment();
        srf = new SearchResultFragment();
        fbf = new FacebookFragment();
        tf = new TwitterFragment();
        etm = new WhatsHotFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, pf, POST_FRAGMENT_TAG);
        ft.add(android.R.id.content, cf, COMMENT_FRAGMENT_TAG);
        ft.add(android.R.id.content, tlf, TAB_LAYOUT_FRAGMENT_TAG);
        ft.add(android.R.id.content, fbf, FB);
        ft.add(android.R.id.content, tf, TF);
        ft.add(android.R.id.content, etm, EF);

        ft.hide(etm);
        ft.hide(tf);
        ft.hide(fbf);
        ft.hide(pf);
        ft.hide(cf);
        ft.commit();
    }

    /*private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
              //  fragment = new RecyclerViewFragment();
                break;
            case 1:
                fragment = new TwitterFragment();
                break;
            case 2:
                fragment = new FacebookFragment();
                break;
            case 3:
                fragment = new Fav();
                break;
            case 4:
                fragment = new EventsFragment();
                break;
            case 5:
                fragment = new WhatsHotFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
         //   FragmentManager fragmentManager = getFragmentManager();
           // fragmentManager.beginTransaction()
             //       .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
*/
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
        //cf.setUIArguments(args);

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
        super.onBackPressed();
    }

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
/*
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.twitter) {
            tf = (TwitterFragment) getSupportFragmentManager().findFragmentByTag(TF);
            FragmentTransaction ft = fm.beginTransaction();
            ft.show(tf);
            ft.addToBackStack(null);
            ft.commit();
            return true;
        }
        else if(item.getItemId() == R.id.facebook) {
            //Toast msg = Toast.makeText(MainActivity.this, "Menu 999", Toast.LENGTH_LONG);
            //msg.show();
            fbf = (FacebookFragment) getSupportFragmentManager().findFragmentByTag(FB);
            FragmentTransaction ft = fm.beginTransaction();
            ft.show(fbf);
            ft.addToBackStack(null);
            ft.commit();
            return true;
        }
        else if(item.getItemId() == R.id.whatshot) {
            etm = (WhatsHotFragment) getSupportFragmentManager().findFragmentByTag(EF);
            FragmentTransaction ft = fm.beginTransaction();
            ft.show(etm);
            ft.addToBackStack(null);
            ft.commit();
            return true;
        }
        else if(item.getItemId() == R.id.about) {
            alertScrollView();
        }
        return (super.onOptionsItemSelected(item));
    }

    public void alertScrollView(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myScrollView = inflater.inflate(R.layout.scroll_text, null, false);
        TextView tv = (TextView) myScrollView
                .findViewById(R.id.textViewWithScroll);
        tv.setText(getResources().getString(R.string.aboutme));
        new AlertDialog.Builder(MainActivity.this).setView(myScrollView)
                .setTitle(getResources().getString(R.string.app_name_ross))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
