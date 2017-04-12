package com.example.win_8.cardigram;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth auth;
    private FirebaseApp app;
    public static String USERNAME;
    public static String IMAGE;
    byte[] decodedString;
    Bitmap decodedByte;

    TextView drawerUsername;
    ProgressDialog progressDialog;
    CircleImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


















        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        app = FirebaseApp.getInstance();
        auth = FirebaseAuth.getInstance(app);

        //Toast.makeText(getApplication(),"yo"+IMAGE,Toast.LENGTH_SHORT).show();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid());


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        View headerView = navigationView.getHeaderView(0);

        drawerUsername = (TextView) headerView.findViewById(R.id.textView1);




//
//        CircleImageView pic = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.profile_image);
//        byte[] decodedString = Base64.decode(encode, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        pic.setImageBitmap(decodedByte);








        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               pic = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.profile_image);
                Log.d("SEE", pic + "");
                Chalja Chalja = dataSnapshot.getValue(Chalja.class);
                String encodedImage = Chalja.getAmankiphoto();
                IMAGE = encodedImage;
                //Toast.makeText(getApplicationContext(),""+pic,Toast.LENGTH_SHORT).show();

                USERNAME = Chalja.getAman();
                decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                if(pic==null)
                {
                    //pic.setImageDrawable(getResources().getDrawable(R.drawable.start));
                }
               else
               pic.setImageBitmap(decodedByte);
                drawerUsername.setText(USERNAME);
                //drawerUsername.setText("Suvimal");



                // Hide the loading screen
                //progressDialog.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


















        // Create Navigation drawer and inflate layout



// Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        //TextView navhead = (TextView)findViewById(R.id.textView1);
        //navhead.setText("Aman Chopra");
// Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        //TextView navhead = (TextView)findViewById(R.id.textView1);
                        //navhead.setText("Aman Chopra");
                        menuItem.setChecked(true);

                        // TODO: handle navigation
                        String selected = menuItem.toString();
                        if(selected.equals("Home"))
                        {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else if(selected.equals("Appointments"))
                        {
                            Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                            startActivity(intent);
                        }
                        else if(selected.equals("Storage"))
                        {
                            Intent intent = new Intent(MainActivity.this, Storage.class);
                            startActivity(intent);
                        }

                        else if(selected.equals("Settings"))
                        {
                            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                            startActivity(intent);
                        }
                        else if(selected.equals("Logout"))
                        {

                            FirebaseAuth.getInstance().signOut();//End user session.
                            startActivity(new Intent(MainActivity.this, Login.class)); //Go back to home page
                            finish();
                            /*Intent intent = new Intent(MainActivity.this, Charts.class);
                            startActivity(intent);*/
                        }
                        else if(selected.equals("Bookings"))
                        {

                            Intent intent = new Intent(MainActivity.this, EventActivity.class);
                            startActivity(intent);
                            /*Intent intent = new Intent(MainActivity.this, Charts.class);
                            startActivity(intent);*/
                        }
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hello Snackbar!",
                        Snackbar.LENGTH_LONG).show();
            }
        });*/



    }









    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListContentFragment(), "Home");
        adapter.addFragment(new ItemContentFragment(), "Charts");
        adapter.addFragment(new CardContentFragment(), "Features");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Toast.makeText(getApplicationContext(),"Successful"+id,Toast.LENGTH_LONG).show();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {

            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();}




}
