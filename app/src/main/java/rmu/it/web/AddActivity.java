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

public class AddActivity extends AppCompatActivity {

    String url = "http://202.29.22.43/webservice/adddata.php" ;
    EditText edtName,edtDate_strat,edtDate_end,edtLocastion, edtDetail;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtName = (EditText) findViewById(R.id.edtName);
        edtDate_strat = (EditText) findViewById(R.id.edtDate_strat);
        edtDate_end = (EditText) findViewById(R.id.edtDate_end);
        edtLocastion = (EditText) findViewById(R.id.edtLocastion);
        edtDetail = (EditText) findViewById(R.id.edtDatail);
        btnOk = (Button) findViewById(R.id.btdOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String date_start = edtDate_strat.getText().toString();
                String date_end = edtDate_end.getText().toString();
                String location = edtLocastion.getText().toString();
                String detail = edtDetail.getText().toString();

                new AddDatatoServer().execute(name,date_start,date_end,location,detail);
                finish();
            }
        });

    }

    private class AddDatatoServer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            OkHttpClient ok = new OkHttpClient();
            Request.Builder builder = new Request.Builder();

            //  ส่งข้อมูลไป
            RequestBody body = new FormBody.Builder()
                    .add("name", strings[0])
                    .add("date_start", strings[1])
                    .add("date_end", strings[2])
                    .add("location", strings[3])
                    .add("detail", strings[4])
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
