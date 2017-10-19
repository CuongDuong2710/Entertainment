package organization.tho.entertaiment;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import layout.AnimalsFragment;
import layout.ComicFragment;
import layout.GeneralFragment;
import layout.KidsFragment;
import layout.KidsSongFragment;
import layout.MusicForKidsFragment;
import layout.SportFragment;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                SportFragment.OnFragmentInteractionListener,
                ComicFragment.OnFragmentInteractionListener,
                GeneralFragment.OnFragmentInteractionListener,
                MusicForKidsFragment.OnFragmentInteractionListener,
                AnimalsFragment.OnFragmentInteractionListener,
                KidsFragment.OnFragmentInteractionListener,
                KidsSongFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager = null;
    Toolbar toolbar = null;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set default Fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, GeneralFragment.newInstance("test1", "test2"))
                        .commit();

        // set Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("General");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // init ads
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        // loading ads
        mAdView = (AdView) findViewById(R.id.adView_home);
        AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Replacing fragment layout
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // init Fragment manager
        fragmentManager = getSupportFragmentManager();
        // init fragment to switch
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_general:
                fragment = GeneralFragment.newInstance("test1", "test2");
                toolbar.setTitle("General");
                break;
            case R.id.nav_comic:
                fragment = ComicFragment.newInstance("test1", "test2");
                toolbar.setTitle("Comic");
                break;
            case R.id.nav_kids:
                fragment = KidsFragment.newInstance("test1", "test2");
                toolbar.setTitle("Kids");
                break;
            case R.id.nav_animals:
                fragment = AnimalsFragment.newInstance("test1", "test2");
                toolbar.setTitle("Animals");
                break;
            case R.id.nav_sport:
                fragment = SportFragment.newInstance("test1", "test2");
                toolbar.setTitle("Sport");
                break;
            case R.id.nav_music_for_baby:
                fragment = MusicForKidsFragment.newInstance("test1", "test2");
                toolbar.setTitle("Music For Baby");
                break;
            case R.id.nav_kids_songs:
                fragment = KidsSongFragment.newInstance("test1", "test2");
                toolbar.setTitle("Kids Songs");
                break;
            default:
                fragment = GeneralFragment.newInstance("test1", "test2");
                toolbar.setTitle("General");
        }

        // Replace fragment
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
