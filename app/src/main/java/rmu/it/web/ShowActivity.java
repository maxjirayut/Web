package rmu.it.web;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    TextView tvName,tvStart,tvEnd,tvLocation, tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

//        tvName = (TextView) findViewById(R.id.tvName);
//        tvStart = (TextView) findViewById(R.id.tvStart);
//        tvEnd = (TextView) findViewById(R.id.tvEnd);
//        tvLocation = (TextView) findViewById(R.id.tvLocation);
//        tvDetail = (TextView) findViewById(R.id.tvDetail);
//
//
//        String name = getIntent().getExtras().getString("name");
//        tvName.setText(name);
//        String date_start = getIntent().getExtras().getString("date_start");
//        tvStart.setText(date_start);
//        String date_end = getIntent().getExtras().getString("date_end");
//        tvEnd.setText(date_end);
//        String location = getIntent().getExtras().getString("location");
//        tvLocation.setText(location);
//        String detail = getIntent().getExtras().getString("detail");
//        tvDetail.setText(detail);

    }
}
