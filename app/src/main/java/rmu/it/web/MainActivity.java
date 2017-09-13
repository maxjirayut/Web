package rmu.it.web;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String url = "http://202.29.22.43/webservice/json.php" ;
    String url_delete = "http://202.29.22.43/webservice/deletedata.php" ;
    // "http://10.145.30.244/webservice/json.php" "http://foodtrai.pe.hu/json.php" "http://mtlabrmu.890m.com/json.txt"

    List<StudentModel> listStd;
    ListView lvWebservice;
    SwipeRefreshLayout swipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvWebservice = (ListView) findViewById(R.id.lvWebservice);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        // สั่งให้คลาส connect
        ConnectWebservice syn = new ConnectWebservice();
        syn.execute();

        // สูด swipe
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // เทื่อสูดแล้วสั่งให้มันเชื่อมต่อ webservist ใหม่
                ConnectWebservice syn = new ConnectWebservice();
                syn.execute();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(add);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        ConnectWebservice syn = new ConnectWebservice();
        syn.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar it em clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    private class ConnectWebservice extends AsyncTask<Voice, Voice, String> {

        @Override
        protected String doInBackground(Voice... voices) {

            String result = "";

            OkHttpClient ok = new OkHttpClient();
            Request.Builder builder = new Request.Builder();

            //ร้องขอ

            Request request = builder.url(url).build();

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

            // Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_LONG).show();

            Type type = new TypeToken<ArrayList<StudentModel>>() {
            }.getType();

            listStd = new Gson().fromJson(s, type);

            // นำค่าใน list มาใช้

//            String name = listStd.get(0).getStd_name();
//            Toast.makeText(getApplicationContext(),""+name,Toast.LENGTH_LONG).show();

            MyAdapter adapter = new MyAdapter(listStd, getApplicationContext());
            lvWebservice.setAdapter(adapter);

            // สั่งให้ wsipe หยุด
            swipe.setRefreshing(false);

            lvWebservice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String id = listStd.get(i).getAct_id();
                    String name = listStd.get(i).getName();
                    String date_start = listStd.get(i).getDate_start();
                    String date_end = listStd.get(i).getDate_end();
                    String location = listStd.get(i).getLocation();
                    String detail = listStd.get(i).getDetail();

                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("I", id);
                    intent.putExtra("N", name);
                    intent.putExtra("S", date_start);
                    intent.putExtra("E", date_end);
                    intent.putExtra("L", location);
                    intent.putExtra("D", detail);
                    startActivity(intent);

                }
            });

            // คริก แช่เพื่อแก่ไข ลบ
            lvWebservice.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
               @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                   String menu[] = {"แก้ไข", "ลบ"};

                  AlertDialog.Builder builder =
                           new AlertDialog.Builder(MainActivity.this);
                   builder.setTitle("เลือกรายการ");
                    builder.setItems(menu, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {

                            if (item == 0) {

                                //แก้ไข
                                intentEdit(i);

                            } else {
                                //ลบ
                                String id = listStd.get(i).getAct_id();

                                new DeleteDatatoServer().execute(id);

                                // เรียกใช้ ดคง json ใฟม่
                                new ConnectWebservice().execute();
                            }

                        }
                    });

                    builder.create();
                    builder.show();

                    return true;
                }
            });


        }
    }

    private void intentEdit(int i){

        String id = listStd.get(i).getAct_id();
        String name = listStd.get(i).getName();
        String date_start = listStd.get(i).getDate_start();
        String date_end = listStd.get(i).getDate_end();
        String location = listStd.get(i).getLocation();
        String detail = listStd.get(i).getDetail();

        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("date_start", date_start);
        intent.putExtra("date_end", date_end);
        intent.putExtra("location", location);
        intent.putExtra("detail", detail);
        startActivity(intent);
    }

    private class DeleteDatatoServer extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            OkHttpClient ok = new OkHttpClient();
            Request.Builder builder = new Request.Builder();

            //  ส่งข้อมูลไป
            RequestBody body = new FormBody.Builder()
                    .add("id", strings[0])
                    .build();

            //ร้องขอ

            Request request = builder.url(url_delete).post(body).build();

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
