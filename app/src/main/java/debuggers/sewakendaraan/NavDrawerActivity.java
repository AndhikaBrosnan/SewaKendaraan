package debuggers.sewakendaraan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import debuggers.sewakendaraan.models.Users;
import debuggers.sewakendaraan.utils.Constants;

//import com.example.brad.accountdetailstutorial.models.Users;
//import com.example.brad.accountdetailstutorial.utils.Constants;

public class NavDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView mDisplayImageView;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    boolean doubleBackToExitPressedOnce = false;

    private DatabaseReference mDb;
    private RentalAdapter3P mAdapter;

    private SectionPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        rv.setLayoutManager(layoutManager);

        mSectionsPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view,"Menambah pesanan motor", Snackbar.LENGTH_LONG)
//                        .setAction("Action",null).show();
//            }
//        });

//

        //Buat deklarasi pas nambahin ke data ke RecyclerViewnya

//        mDb = FirebaseDatabase.getInstance().getReference().child("motor_list");
//        mAdapter = new RentalAdapter3P(this, mDb, new RentalAdapter3P.onClickHandler() {
//            @Override
//            public void onClick(Rental currentPet) {
//                Toast.makeText(NavDrawerActivity.this,"Data telah ditambah",Toast.LENGTH_LONG).show();
//            }
//        });
//        rv.setAdapter(mAdapter);


//        FloatingActionButton fab_ = (FloatingActionButton) findViewById(R.id.fab);
//        fab_.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //addMotor();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);

//        GridView gridview = (GridView) findViewById(R.id.gridview);
//        gridview.setAdapter(new ImageAdapter(this));

        mDisplayImageView = (ImageView) navHeaderView.findViewById(R.id.imageView_display);
        mNameTextView = (TextView) navHeaderView.findViewById(R.id.textView_name);
        mEmailTextView = (TextView) navHeaderView.findViewById(R.id.textView_email);

        FirebaseDatabase.getInstance().getReference(Constants.USER_KEY).child(mFirebaseUser.getEmail().replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            Users users = dataSnapshot.getValue(Users.class);
                            Glide.with(NavDrawerActivity.this)
                                    .load(users.getPhotUrl())
                                    .into(mDisplayImageView);

                            mNameTextView.setText(users.getUser());
                            mEmailTextView.setText(users.getEmail());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void addMotor() {
        //TODO (10) insert dummy data to our real-time database
        String key = mDb.push().getKey();
        mDb.child(key).setValue(new Rental("Motor Bu Suk","Tahun 2013","Gambar Coming Soon"));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;

//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_HOME);
//                    startActivity(intent);

//                    Process.killProcess(Process.myPid());

                    finish();
                    System.exit(0);

                }
            }, 2000);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_category) {
            Intent intent = new Intent (getApplicationContext(),CategoryActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_revokeaccess) {
            revokeAccess();
            Intent intent = new Intent(NavDrawerActivity.this,RegisterActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_signout) {
            //syntax signOut terus ga usah pilih account
            signOut();
            finish();
            System.exit(0);
        }
       else if(id == R.id.home){
            Intent intent = new Intent(getApplicationContext(),NavDrawerActivity.class);
            startActivity(intent);

        }else if(id == R.id.buka_rental){
            Intent intent1 = new Intent(getApplicationContext(),BukaRental.class);
            startActivity(intent1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //buat fragment data dummy yang ditambah
    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MotorFragment();
                case 1:
                    return new MobilFragment();
                case 2:
                    return new SepedaFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Motor";
                case 1:
                    return "Mobil";
                case 2:
                    return "Sepeda";
            }
            return null;
        }
    }

}
