package com.example.duskagk.memotest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class MemoAdapter extends BaseAdapter {

    private List conte;
    private Context context;

    public MemoAdapter(List conte,Context context){
        this.conte=conte;
        this.context=context;
    }

    @Override
    public int getCount() {
        return this.conte.size();
    }

    @Override
    public Object getItem(int position) {
        return this.conte.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder=null;
        if(convertView==null) {
            convertView = new LinearLayout(context);
            TextView tvname = new TextView(context);
            tvname.setPadding(10, 0, 20, 0);
            tvname.setTextColor(Color.BLACK);

            TextView tvcon = new TextView(context);
            tvcon.setPadding(20, 0, 20, 0);
            tvcon.setTextColor(Color.BLACK);

            ((LinearLayout) convertView).addView(tvname);
            ((LinearLayout) convertView).addView(tvcon);

            holder = new Holder();
            holder.tvName = tvname;
            holder.tvCon = tvcon;
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();

        }

        Memo memo=(Memo)getItem(position);
        holder.tvId.setText(memo.get_id());
        holder.tvName.setText(memo.getMname()+"");
        holder.tvCon.setText(memo.getCon()+"");


        return convertView;
    }
    private class Holder{
        public TextView tvId;
        public TextView tvName;
        public TextView tvCon;
    }

}
