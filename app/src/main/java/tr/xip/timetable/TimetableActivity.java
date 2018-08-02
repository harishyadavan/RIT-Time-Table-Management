package tr.xip.timetable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.xip.timetable.Timetable_API.RetroApi;
import tr.xip.timetable.Timetable_API.fragmentAPI;
import tr.xip.timetable.model.Class;
import tr.xip.timetable.model.Timetableclass;

public class TimetableActivity extends AppCompatActivity {
    String tablename;
    fragmentAPI api;
    List<Timetableclass> timetablejson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity);

        //tablename= getIntent().getExtras().get("tablename").toString();
        //Toast.makeText(this, tablename, Toast.LENGTH_SHORT).show();
        api= RetroApi.geturl().create(fragmentAPI.class);
        Call<ArrayList<Class>> sc = api.RetriveTimeTable("eee1");
        sc.enqueue(new Callback<ArrayList<Class>>() {
            @Override
            public void onResponse(Call<ArrayList<Class>> call, Response<ArrayList<Class>> response) {
                if (response.body() != null ) {
                    if(response.body().get(0).getStatus()!=null){
                        Toast.makeText(TimetableActivity.this, response.body().get(0).getStatus(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TimetableActivity.this, response.body().get(0).getName(), Toast.LENGTH_SHORT).show();
                           // timetablejson = response.body();
                        }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Class>> call, Throwable t) {
                Toast.makeText(TimetableActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    }
