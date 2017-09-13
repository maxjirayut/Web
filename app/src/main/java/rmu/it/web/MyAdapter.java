package rmu.it.web;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ASUS on 10/9/2560.
 */

public class MyAdapter extends BaseAdapter {

    List<StudentModel> listStd;
    Context ct;

    public MyAdapter(List<StudentModel> list,Context ct) {
        this.listStd = list;
        this.ct = ct;
    }

    @Override
    public int getCount() {
        return listStd.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.itam_layout, viewGroup,false);

        TextView tvItem = (TextView) view.findViewById(R.id.tvItam);

        //imvPic.setImageResource(img[i]);
        tvItem.setText(listStd.get(i).getName());



        return view;
    }
}
