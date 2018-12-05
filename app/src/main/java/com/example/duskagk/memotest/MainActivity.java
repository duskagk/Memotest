package com.example.duskagk.memotest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DBhelper dbHelper;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    int flag;

    String dataBaseName = "memo";

    Memo memo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//데이터베이스 생성
        dbHelper=new DBhelper(
                getApplicationContext(), dataBaseName,null,1);
        dbHelper.testDB();
        ////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Button w_memo=(Button)findViewById(R.id.w_memo);   //
//        w_memo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder mBulid = new AlertDialog.Builder(v.getContext());
//                LayoutInflater inf = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
//                final View mv = inf.inflate(R.layout.memo_d, null);
//
//                Button btcat=(Button)mv.findViewById(R.id.cat);
//                Button btcan=(Button)findViewById(R.id.can);
//
//                btcat.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        EditText ed=(EditText)mv.findViewById(R.id.category);
//                        if(ed.getAccessibilityClassName().toString().length()>0){
//                            dbHelper=new DBhelper(
//                                    getApplicationContext(), dataBaseName,null,1);
//                            dbHelper.testDB();
//                        }
//                    }
//                });
//
//                mBulid.setView(mv);
//                AlertDialog dialog = mBulid.create();
//                dialog.show();
//            }
//        });

        Button inbtn=(Button)findViewById(R.id.insert);
        inbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBulid = new AlertDialog.Builder(v.getContext());
                LayoutInflater inf = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
                final View mv = inf.inflate(R.layout.add_data, null);
                Button savbtn=(Button)mv.findViewById(R.id.save);
                savbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edname=(EditText)mv.findViewById(R.id.edname);
                        EditText edcon=(EditText)mv.findViewById(R.id.edcon);
                        String name=edname.getText().toString();
                        String con=edcon.getText().toString();
                        if(dbHelper==null){
                            dbHelper=new DBhelper(getApplicationContext(),dataBaseName,null,1);
                        }
                        Memo memo=new Memo();
                        memo.setMname(name);
                        memo.setMcon(con);
                        dbHelper.addMemo(memo);
                        prepareListData();
                    }
                });
                mBulid.setView(mv);
                AlertDialog dialog = mBulid.create();
                dialog.show();
            }
        });

//        final ListView memolist=(ListView)findViewById(R.id.memolist);
//        Button loadmemo=(Button)findViewById(R.id.load);
//        loadmemo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                memolist.setVisibility(View.VISIBLE);
//
//                if(dbHelper==null){
//                    dbHelper=new DBhelper(getApplicationContext(),"TEST",null,1);
//                }
//
//                List conte=dbHelper.getMemo();
//                memolist.setAdapter(new MemoAdapter(conte,getApplicationContext()));
//            }
//        });







        expListView = (ExpandableListView)findViewById(R.id.lvExp);
        prepareListData();
        listAdapter = new ExListAdapter(getApplicationContext(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(v.getContext(),
                        "Group Clicked " + listDataHeader.get(groupPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                // Collapse the expanded group
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition)
                        + "Exapand", Toast.LENGTH_SHORT).show();

            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        v.getContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        DBhelper dbg=new DBhelper(getApplicationContext(),dataBaseName,null,1);
        List memoList=dbg.getMemo();
        Memo memo;
        for(int i=0;i<memoList.size();i++){
            memo = (Memo)memoList.get(i);
            listDataHeader.add(memo.getMname());

            List<String> child=new ArrayList<String>();
            child.add(memo.getCon());
            listDataChild.put(listDataHeader.get(i),child);
        }

//        for (int i = 0; i <= 10; i++) {
//            listDataHeader.add("head" + i);
//
//            // Adding child data
//            List<String> child = new ArrayList<String>();
//            child.add("Child" + i);
//            listDataChild.put(listDataHeader.get(i), child); // Header, Child data
//
//        }

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
