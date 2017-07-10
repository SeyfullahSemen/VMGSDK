package com.example.user.newapp;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AboutVMG extends AppCompatActivity {
    private Toolbar toolbar_aboutVMG;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigation_view;
    private ImageView VMG_pic_about;
    private NestedScrollView scroll_about_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vmg);
        toolbar_aboutVMG = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar_aboutVMG);// dit zorgt ervoor dat de toolbar tevoorschijn komt


        makeDrawerMenuClickable();
        seePicture();

    }

    /**
     * dit zorgt voor de event wanneer een menu item is geklikt hierdoor kunnen we naar de
     * volgende paginas gaan
     * wat wij hier meegeven is het volgende
     * - als parameter komt er een item in te staan
     * - daarna vragen we aan de computer om de id op te halen van de gekozen item
     * - hierna gaat de computer aan de hand van de id bepalen naar welke pagina hij moet gaan
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent myIntent = new Intent(AboutVMG.this, MainActivity.class);
                startActivity(myIntent);
                break;
            case R.id.listView:
                Intent ListViewIntent = new Intent (this, ListView_page.class);
                startActivity(ListViewIntent);
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

    /**
     * deze methode zorgt ervoor dat de menu wordt gevuld met de items die wij erin hebben gezet
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }// end OnCreateOptionsMenu()


    private void makeDrawerMenuClickable() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_aboutVMG, R.string.drawer_open, R.string.drawer_close);
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
                        Intent myIntent = new Intent(AboutVMG.this, MainActivity.class);
                        startActivity(myIntent);
                        break;
                    case R.id.listView:
                        Intent ListViewIntent = new Intent (AboutVMG.this, ListView_page.class);
                        startActivity(ListViewIntent);
                        break;

                }
                return false;
            }
        });

    } // end makeDrawerMenuClickable()

    private void seePicture(){
        int dimensionInPixel = 104;
        VMG_pic_about = (ImageView) findViewById(R.id.VMG_pic_about);
        scroll_about_page = (NestedScrollView) findViewById(R.id.scroll_about_page);
       final int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());

        scroll_about_page.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v , MotionEvent event){
                if (event.getAction() == MotionEvent.ACTION_UP){
                    VMG_pic_about.setVisibility(View.VISIBLE);
                    VMG_pic_about.getLayoutParams().height = dimensionInDp;
                    VMG_pic_about.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    VMG_pic_about.requestLayout();


                }

                return false;
            }

        });


    }


}
