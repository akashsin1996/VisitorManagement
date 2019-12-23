package com.sisoft.vm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sisoft.vm.Fragments.DashboardFragment;
import com.sisoft.vm.MySharedPrefrenced;
import com.sisoft.vm.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Dashboard");



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            Intent intent = new Intent(this,VisitorRegActivity.class) ;
            startActivity(intent);



        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        String role = MySharedPrefrenced.getInstance(this).getRole() ;
   //     Toast.makeText(this, "role"+role, Toast.LENGTH_SHORT).show();

        Menu m1 = navigationView.getMenu();

        if(role.equalsIgnoreCase("operator")) {
            m1.removeItem(R.id.nav_manage_users);
            m1.removeItem(R.id.nav_visit);
            m1.removeItem(R.id.nav_rp);
            m1.removeItem(R.id.nav_manage_staff);
            m1.removeItem(R.id.nav_completed);
            m1.removeItem(R.id.nav_current);



        }else{

        }





        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
            toolbar.setTitle("Dashboard");
        }

        View header =navigationView.getHeaderView(0);
        TextView tv_username=header.findViewById(R.id.tv_username);
        tv_username.setText(MySharedPrefrenced.getInstance(this).getUser());
        TextView tv_role=header.findViewById(R.id.tv_role);
        tv_role.setText(MySharedPrefrenced.getInstance(this).getRole());


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(getDrawable(R.drawable.app_icon));
            builder.setTitle("logout");
            builder.setMessage("Confirm logout");
            builder.setPositiveButton("Logout",(dialog, which) -> {
                MySharedPrefrenced.getInstance(this).removeUser();
                MySharedPrefrenced.getInstance(this).removePassword();

                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                this.finish();

            });

            builder.setNegativeButton("cancel",(dialog, which) -> {
                dialog.dismiss();
            });

            AlertDialog alert=builder.create();
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_dashboard) {
           getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new DashboardFragment()).commit();
           toolbar.setTitle("Dashboard");

        } else if (id == R.id.nav_visit) {
            Intent intent = new Intent(this,VisitorRegActivity.class) ;
            startActivity(intent);

        }else if (id == R.id.nav_review_visitor) {
            Intent intent =new Intent(this, ReviewVisitorActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage_users) {
            Intent intent = new Intent(this, SystemUserActivity.class) ;
            startActivity(intent);

        } else if (id == R.id.nav_manage_staff) {
            Intent intent = new Intent(this, ManageStaffActivity.class) ;
            startActivity(intent);

        } else if (id == R.id.nav_profile) {

            Intent intent = new Intent(this,AdminProfileActivity.class) ;
            startActivity(intent);

        } else if (id == R.id.nav_completed) {
            Intent intent = new Intent(this,CompletedVisitorActivity.class) ;
            startActivity(intent);

        } else if (id == R.id.nav_current) {
            Intent intent = new Intent(this, CurrentVisitorActivity.class) ;
            startActivity(intent);

        }else if (id == R.id.nav_pdf) {


        }else if (id == R.id.nav_exit) {

        }else if (id == R.id.nav_contact_us){
            Intent intent = new Intent(this, ContactActivity.class) ;
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
