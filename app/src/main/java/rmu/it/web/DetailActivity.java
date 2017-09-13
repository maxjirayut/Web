package rmu.it.web;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

//        TextView tvName,tvStart,tvEnd,tvLocation, tvDetail;

    TextView tvname,tvdate_start,tvdate_end,tvlocation, tvdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        tvname = (TextView) findViewById(R.id.tvname);
        tvdate_start = (TextView) findViewById(R.id.tvdate_start);
        tvdate_end = (TextView) findViewById(R.id.tvdate_end);
        tvlocation = (TextView) findViewById(R.id.tvlocation);
        tvdetail = (TextView) findViewById(R.id.tvdetail);


//        tvName = (TextView) findViewById(R.id.tvName);
//        tvStart = (TextView) findViewById(R.id.tvStart);
//        tvEnd = (TextView) findViewById(R.id.tvEnd);
//        tvLocation = (TextView) findViewById(R.id.tvLocation);
//        tvDetail = (TextView) findViewById(R.id.tvDetail);


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

//        Toast.makeText(getApplication(),""+name,Toast.LENGTH_LONG).show();
//
        String id = getIntent().getExtras().getString("I");
        String name = getIntent().getExtras().getString("N");
        tvname.setText(name);
        String date_start = getIntent().getExtras().getString("S");
        tvdate_start.setText(date_start);
        String date_end = getIntent().getExtras().getString("E");
        tvdate_end.setText(date_end);
        String location = getIntent().getExtras().getString("L");
        tvlocation.setText(location);
        String detail = getIntent().getExtras().getString("D");
        tvdetail.setText(detail);

//        Toast.makeText(getApplication(),""+name,Toast.LENGTH_LONG).show();
    }
}
