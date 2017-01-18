package com.example.andre_000.mrservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Adminswipe extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminswipe);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab().setText("Inicio").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Reportes").setTabListener(this);
        actionBar.addTab(tab);

       /* tab = actionBar.newTab().setText("Gruas").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Choferes").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Servicios").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Empresas").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Facturas").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Pagos").setTabListener(this);
        actionBar.addTab(tab);*/

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    return new FragmentMain();
                case 1:
                    return new FragmentUsuarios();
                /*case 2:
                    return new FragmentGruas();
                case 3:
                    return new FragmentChoferes();
                case 4:
                    return new FragmentServicios();
                case 5:
                    return new FragmentEmpresas();
                case 6:
                    return new FragmentFacturas();
                case 7:
                    return new FragmentPagos();*/
                default:
                    return null;
            }
        }

        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        getSupportActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    //Implementacion de TabListener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adminswipe, menu);
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
}
