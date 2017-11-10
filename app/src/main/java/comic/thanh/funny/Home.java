package comic.thanh.funny;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import comic.thanh.funny.layout.AnimalsFragment;
import comic.thanh.funny.layout.GeneralFragment;
import comic.thanh.funny.layout.KidsFragment;
import comic.thanh.funny.layout.KidsSongFragment;
import comic.thanh.funny.layout.CartoonsFragment;
import comic.thanh.funny.layout.SportFragment;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                SportFragment.OnFragmentInteractionListener,
                GeneralFragment.OnFragmentInteractionListener,
                CartoonsFragment.OnFragmentInteractionListener,
                AnimalsFragment.OnFragmentInteractionListener,
                KidsFragment.OnFragmentInteractionListener,
                KidsSongFragment.OnFragmentInteractionListener{

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
        // TODO: replace app unit id
        MobileAds.initialize(this, "ca-app-pub-4439595704793521~4858151305");

        // loading ads
        // TODO: remove addTestDevice when publish app
        mAdView = (AdView) findViewById(R.id.adView_home);
        AdRequest adRequest = new AdRequest.Builder().build();
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
            case R.id.nav_kids:
                fragment = KidsFragment.newInstance("test1", "test2");
                toolbar.setTitle("Funny Kids");
                break;
            case R.id.nav_animals:
                fragment = AnimalsFragment.newInstance("test1", "test2");
                toolbar.setTitle("Funny Animals");
                break;
            case R.id.nav_sport:
                fragment = SportFragment.newInstance("test1", "test2");
                toolbar.setTitle("Funny Sport");
                break;
            case R.id.nav_cartoons:
                fragment = CartoonsFragment.newInstance("test1", "test2");
                toolbar.setTitle("Cartoons");
                break;
            case R.id.nav_kids_songs:
                fragment = KidsSongFragment.newInstance("test1", "test2");
                toolbar.setTitle("Kids Songs");
                break;
            case R.id.nav_rate_us:
                String packageName = getPackageName();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
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
