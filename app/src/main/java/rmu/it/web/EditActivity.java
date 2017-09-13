package rmu.it.web;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActivity extends AppCompatActivity {

    String url = "http://202.29.22.43/webservice/updatedata.php" ;

    EditText edtID,edtName,edtDate_strat,edtDate_end,edtLocastion, edtDetail;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtID = (EditText) findViewById(R.id.edtID);
        edtName = (EditText) findViewById(R.id.edtName);
        edtDate_strat = (EditText) findViewById(R.id.edtDate_strat);
        edtDate_end = (EditText) findViewById(R.id.edtDate_end);
        edtLocastion = (EditText) findViewById(R.id.edtLocastion);
        edtDetail = (EditText) findViewById(R.id.edtDatail);
        btnOk = (Button) findViewById(R.id.btdOk);

        edtID.setEnabled(false);

       String id = getIntent().getExtras().getString("id");
        String name = getIntent().getExtras().getString("name");
        String date_start = getIntent().getExtras().getString("date_start");
        String date_end = getIntent().getExtras().getString("date_end");
        String location = getIntent().getExtras().getString("location");
        String detail = getIntent().getExtras().getString("detail");

        edtID.setText(id);
        edtName.setText(name);
        edtDate_strat.setText(date_start);
        edtDate_end.setText(date_end);
        edtLocastion.setText(location);
        edtDetail.setText(detail);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtID.getText().toString();
                String name = edtName.getText().toString();
                String date_start = edtDate_strat.getText().toString();
                String date_end = edtDate_end.getText().toString();
                String location = edtLocastion.getText().toString();
                String detail = edtDetail.getText().toString();

                new UpdatDatatoServer().execute( id, name, date_start, date_end,location,detail);
                finish();
            }
        });


    }


    private class UpdatDatatoServer extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            OkHttpClient ok = new OkHttpClient();
            Request.Builder builder = new Request.Builder();

            //  ส่งข้อมูลไป
            RequestBody body = new FormBody.Builder()
                    .add("id", strings[0])
                    .add("name", strings[1])
                    .add("date_start", strings[2])
                    .add("date_end", strings[3])
                    .add("location", strings[4])
                    .add("detail", strings[5])
                    .build();

            //ร้องขอ

            Request request = builder.url(url).post(body).build();

            // server ตอบกลับ json มา

            try {
                Response response = ok.newCall(request).execute();

                // ส่งข้อมูลกลับมาสำเร็จหรือไม่
                if (response.isSuccessful()) {
                    result = response.body().string();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
