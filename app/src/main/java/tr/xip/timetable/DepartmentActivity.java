package tr.xip.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DepartmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        TextView deptname = (TextView) findViewById(R.id.deptname);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            String j = (String) b.get("deptname");
            deptname.setText(j);
        }
        Button fs=(Button)findViewById(R.id.fs);
        fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentActivity.this,MainActivity.class));
            }
        });
    }
}
