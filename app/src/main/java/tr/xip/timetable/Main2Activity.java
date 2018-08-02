package tr.xip.timetable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.xip.timetable.Timetable_API.RetroApi;
import tr.xip.timetable.Timetable_API.fragmentAPI;
import tr.xip.timetable.model.Class;

public class Main2Activity extends AppCompatActivity {
    fragmentAPI api;
    String deptname,dept,year;
    public TabLayout tabs;
    public ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        progressDialog();
        tabs= (TabLayout)findViewById(R.id.tabs);
        pager = (ViewPager)findViewById(R.id.pager);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        api= RetroApi.geturl().create(fragmentAPI.class);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent in= getIntent();
        Bundle b = in.getExtras();
        if(b!=null) {
            deptname = (String) b.get("deptname");
            dept = (String) b.get("dept");
            year = (String) b.get("year");

        }
        final Context mContext;

        this.setTitle(dept);
        //Toast.makeText(this, deptname, Toast.LENGTH_SHORT).show();
        mContext = getApplicationContext();
        Call<ArrayList<Class>> sc=api.RetriveTimeTable(deptname);
        sc.enqueue(new Callback<ArrayList<Class>>() {

            @Override
            public void onResponse(Call<ArrayList<Class>> call, Response<ArrayList<Class>> response) {

                if (response.body() != null && response.body().get(0).getStatus() == null) {
                    pager.setAdapter((PagerAdapter) (new DaysPagerAdapter(response.body(), getSupportFragmentManager())));
                    tabs.setupWithViewPager(pager);
                    int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;
                    pager.setCurrentItem(today);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Class>> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void progressDialog(){
        Handler handler = new Handler();
        final ProgressDialog pg = new ProgressDialog(Main2Activity.this);
        pg.setMessage("Loading...");
        pg.setCanceledOnTouchOutside(false);
        //pg.setCancelable(false);
        pg.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },1000);
    }
}