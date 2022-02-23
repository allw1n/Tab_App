package com.example.tabapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ExpandableListAdapter expandableListAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ArrayList<String> listMenuHeader = new ArrayList<>();
    HashMap<String, List<String>> listMenuChild = new HashMap<>();
    ExpandableListView expandableListMenu;
    Integer lastExpandedGroupPosition = -1;
    ImageView appIcon;
    LinearLayout navigationHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationHeader = (LinearLayout) navigationView.getHeaderView(0);
        appIcon = navigationHeader.findViewById(R.id.app_icon);

        ArrayList<Integer> tabTitles = new ArrayList<>();
        tabTitles.add(R.string.movies);
        tabTitles.add(R.string.tv_series);
        tabTitles.add(R.string.anime);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_menu_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //App Icon closes drawer
        appIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
        });

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            onBackPressed();
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.movies));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tv_series));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.anime));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager2 = findViewById(R.id.viewpager2);

        fragmentArrayList.add(new TabFragment1());
        fragmentArrayList.add(new TabFragment2());
        fragmentArrayList.add(new TabFragment3());

        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(viewPagerFragmentAdapter);
        viewPager2.setPageTransformer(new MarginPageTransformer(1500));

        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });*/

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles.get(position));
            }
        });
        tabLayoutMediator.attach();

        expandableListMenu = findViewById(R.id.expand_list_view);
        prepareMenuData(listMenuHeader, listMenuChild);
        expandableListAdapter = new ExpandableListAdapter(this, listMenuHeader, listMenuChild);
        expandableListMenu.setAdapter(expandableListAdapter);

        expandableListMenu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                return false;
            }
        });

        expandableListMenu.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedGroupPosition != -1 && lastExpandedGroupPosition != groupPosition) {
                    expandableListMenu.collapseGroup(lastExpandedGroupPosition);
                }
                lastExpandedGroupPosition = groupPosition;
            }
        });

        expandableListMenu.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //TODO
            }
        });

        expandableListMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition,
                                        int childPosition, long id) {
                switch (childPosition) {
                    case 0:
                        Toast.makeText(MainActivity.this, "Favourite Movies", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Favourite TV Series", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Favourite Anime", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void prepareMenuData (List<String> listMenuHeader,
                                  Map<String, List<String>> listMenuChild) {

        //Add Menu Header
        listMenuHeader.add(getResources().getString(R.string.favourites));
        listMenuHeader.add(getResources().getString(R.string.latest));

        //Add Menu Children
        List<String> favourites = new ArrayList<>();
        favourites.add(getResources().getString(R.string.movies));
        favourites.add(getResources().getString(R.string.tv_series));
        favourites.add(getResources().getString(R.string.anime));

        List<String> latest = new ArrayList<>();
        latest.add(getResources().getString(R.string.movies));
        latest.add(getResources().getString(R.string.tv_series));
        latest.add(getResources().getString(R.string.anime));

        listMenuChild.put(listMenuHeader.get(0), favourites);
        listMenuChild.put(listMenuHeader.get(1), latest);
    }
}