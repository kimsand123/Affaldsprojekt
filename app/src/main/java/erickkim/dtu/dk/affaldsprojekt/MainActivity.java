package erickkim.dtu.dk.affaldsprojekt;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.Map;

import erickkim.dtu.dk.affaldsprojekt.fragments.screen0login;
import erickkim.dtu.dk.affaldsprojekt.fragments.screen1main;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;
import erickkim.dtu.dk.affaldsprojekt.utilities.DimensionHandling;
import erickkim.dtu.dk.affaldsprojekt.utilities.ScreenSize;
import io.fabric.sdk.android.Fabric;

// Denne app er lavet af Grp 20 som består af.
// Erick Lauridsen S175199
// Kim Sandberg S163290

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private ScreenSize screenSize = new ScreenSize();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Map<String,Integer> map = screenSize.deriveMetrics(this);
        int density = map.get("screenDensity");
        //Setting textsize på baggrund af screendensity
        if (density < 520){
            DimensionHandling.getInstance().setSmall();
        } else {
            DimensionHandling.getInstance().setLarge();
        }

        Fragment startscreen;
        if (Data_Controller.getInstance().performDefaultLogin(getApplicationContext())) {
            startscreen = new screen1main();
        } else {
            startscreen = new screen0login();
        }
        fragmentTransaction
                    .add(R.id.fragmentContent, startscreen)
                    .commit();
        //}



    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (fragmentManager.findFragmentById(R.id.fragmentContent) instanceof screen1main) {
            finish();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (Data_Controller.getInstance().performDefaultLogin(getApplicationContext())) {
            int id = item.getItemId();
            Intent intent;
            switch (id){
                case R.id.my_profile:
                    break;
                case R.id.notifications:
                    intent = new Intent(MainActivity.this, NotificationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.coinshop:
                    if (Data_Controller.getInstance().getUserType().equals("borger")) {
                        intent = new Intent(MainActivity.this, CoinShopActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Guldbutikken kan ikke bruges af virksomheder.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.feedback:
                    intent = new Intent(MainActivity.this, FeedbackActivity.class);
                    startActivity(intent);
                    break;
                case R.id.settings:
                    //Når ikke at blive implementeret.
                    /*intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);*/
                    break;
                case R.id.about:
                    intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.logout:
                    // TODO: Closes app entirely. We want this to simply go back to the screen0login fragment, but without messing up the activity stack.
                    Data_Controller.getInstance().removeDefaultLogin(getApplicationContext());
                    finish();
                    break;
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            Toast.makeText(getApplicationContext(),
                    "Du er ikke logget ind endnu.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


}
