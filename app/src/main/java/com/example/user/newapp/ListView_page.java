package com.example.user.newapp;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class ListView_page extends AppCompatActivity {
    private Toolbar toolbar_listView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigation_view;
    private String[] titels = {"VMG", "Video Media Group", "Advertising", "Server", "Media player"};
    private ListView listview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        toolbar_listView = (Toolbar) findViewById(R.id.toolbar);
        listview = (ListView) findViewById(R.id.listview_VMG);

        setSupportActionBar(toolbar_listView);// dit zorgt ervoor dat de toolbar tevoorschijn komt


        makeDrawerMenuClickable();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent myIntent = new Intent(ListView_page.this, MainActivity.class);
                startActivity(myIntent);
                break;
            case R.id.about:
                Intent aboutIntent = new Intent(ListView_page.this, AboutVMG.class);
                startActivity(aboutIntent);
            default:
                return super.onOptionsItemSelected(item);


        }
        return false;

    } // end onOptionsItemSelected


    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        actionBarDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState, persistentState);

    }//end onPostCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }// end OnCreateOptionsMenu()


    private void makeDrawerMenuClickable() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_listView, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        /**
         * in grote lijnen zorgt deze methode er voor dat deze klik event kan uitgevoerd worden
         */
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent myIntent = new Intent(ListView_page.this, MainActivity.class);
                        startActivity(myIntent);
                        break;
                    case R.id.about:
                        Intent about_intent = new Intent(ListView_page.this, AboutVMG.class);
                        startActivity(about_intent);
                        break;

                }
                return false;
            }
        });

    } // end makeDrawerMenuClickable()


}
