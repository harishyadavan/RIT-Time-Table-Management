package tr.xip.timetable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.xip.timetable.Timetable_API.RetroApi;
import tr.xip.timetable.Timetable_API.fragmentAPI;
import tr.xip.timetable.model.Class;
import tr.xip.timetable.model.Timetableclass;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textName, textEmail;
    FirebaseAuth mAuth;
    private Button signOut;
    private ImageButton dept1,dept2,dept3,dept4,dept5,dept6,dept7,dept8,dept9,dept10;
    private TextView tdept1,tdept2,tdept3,tdept4,tdept5,tdept6,tdept7,tdept8,tdept9,tdept10;
    private Spinner dept,sem;
    private Button retrive;
    private FirebaseAuth auth;
    String deptname;
    fragmentAPI api;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ll= findViewById(R.id.ll);
        dept = findViewById(R.id.dept);
        sem = findViewById(R.id.sem);
        mAuth = FirebaseAuth.getInstance();

        Snackbar snack = Snackbar.make(ll, "Welcome : "+mAuth.getCurrentUser().getEmail(), Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snack.show();

        retrive = findViewById(R.id.retrive);
        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deptname= dept.getSelectedItem().toString() + sem.getSelectedItem().toString();
                if(!dept.getSelectedItem().toString().equals("Department") && !sem.getSelectedItem().toString().equals("Semester")) {

                    api= RetroApi.geturl().create(fragmentAPI.class);

                    Call<ArrayList<Class>> sc=api.RetriveTimeTable(deptname);
                    sc.enqueue(new Callback<ArrayList<Class>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Class>> call, Response<ArrayList<Class>> response) {

                            if(response.body()!=null&&response.body().get(0).getStatus()==null) {
                               /* Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<Class>>() {}.getType();*/
                                //String json = gson.toJson(response, type);
                                //Toast.makeText(ProfileActivity.this,deptname , Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ProfileActivity.this, Main2Activity.class);
                                intent.putExtra("deptname",  deptname);
                                intent.putExtra("dept",  dept.getSelectedItem().toString());
                                intent.putExtra("year",  sem.getSelectedItem().toString());

                                intent.putExtra("data",new Gson().toJson(response));
                                //Toast.makeText(ProfileActivity.this,new Gson().toJson(response), Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else
                            {
                                Snackbar snack = Snackbar.make(ll, response.body().get(0).getStatus(), Snackbar.LENGTH_LONG);
                                View view = snack.getView();
                                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                                tv.setTextColor(Color.WHITE);
                                snack.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Class>> call, Throwable t) {

                        }
                    });
                }else
                {
                    Snackbar snack = Snackbar.make(ll, "Select Department and Semester", Snackbar.LENGTH_LONG);
                    View view = snack.getView();
                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.WHITE);
                    snack.show();
                }
            }
        });

        String[] deptarray = new String[] {"Department","MCA","MBA","ElectricalElectronics","ElectronicsCommunication","InformationScience","Architecture","ComputerScience","TeleCommunication","Civil","Chemical","Mechanical"};
        String[] semarray = new String[] {"Year","1", "2", "3", "4", "5"};

        ArrayAdapter<String> deptadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, deptarray);
        deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(deptadapter);

        ArrayAdapter<String> semadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, semarray);
        deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(semadapter);

        mAuth = FirebaseAuth.getInstance();

        imageView = findViewById(R.id.imageView);
        textName = findViewById(R.id.textViewName);
        textEmail = findViewById(R.id.textViewEmail);

        FirebaseUser user = mAuth.getCurrentUser();

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(imageView);

        textName.setText(user.getDisplayName());
        textEmail.setText(user.getEmail());

        signOut = (Button) findViewById(R.id.sign_out);
        auth = FirebaseAuth.getInstance();


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            }
        });
    }

    public void nextActivity(String deptname){
        Intent i = new Intent(ProfileActivity.this, DepartmentActivity.class);
        i.putExtra("deptname", deptname);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dept1:
                nextActivity(tdept1.getText().toString());
                break;

            case R.id.dept2:
                nextActivity(tdept2.getText().toString());
                break;

            case R.id.dept3:
                nextActivity(tdept3.getText().toString());
                break;

            case R.id.dept4:
                nextActivity(tdept4.getText().toString());
                break;
            case R.id.dept5:
                nextActivity(tdept5.getText().toString());
                break;
            case R.id.dept6:
                nextActivity(tdept6.getText().toString());
                break;
            case R.id.dept7:
                nextActivity(tdept7.getText().toString());
                break;
            case R.id.dept8:
                nextActivity(tdept8.getText().toString());
                break;
            case R.id.dept9:
                nextActivity(tdept9.getText().toString());
                break;
            case R.id.dept10:
                nextActivity(tdept10.getText().toString());
                break;
            default:
                break;
        }
*/


   /* }*/
}
