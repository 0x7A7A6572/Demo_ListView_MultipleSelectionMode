package com.noform.demo_listview_multipleselectionmode;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, Object>> balistItem;


    @Override
    public int getCount() {
        return balistItem.size();
    }

    @Override
    public Object getItem(int position) {
        return balistItem.get(position);

    }

    @Override
    public long getItemId(int p1) {
        return p1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(MainActivity.CONTEXT, R.layout.listview_item, null);
            viewHolder.title = convertView.findViewById(R.id.lv_text1);
            viewHolder.text = convertView.findViewById(R.id.lv_text2);
            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(balistItem.get(position).get("标题").toString());
        viewHolder.text.setText(balistItem.get(position).get("内容").toString());

        //判断position位置是否被选中，改变颜色
        if (MainActivity.list.isItemChecked(position) && MainActivity.isMultipleSelectionMode) {
            convertView.setBackgroundColor(0xffff521d);

        } else {
            convertView.setBackgroundColor(0xff1E90FF);

        }
        return convertView;
    }


    public MyAdapter(List<? extends Map<String, ?>> data) {
        this.balistItem = (ArrayList<HashMap<String, Object>>) data;
        Log.d("MyAdapter",this.balistItem.toString());
    }

    private static class ViewHolder {
        TextView title;
        TextView text;


        public static ViewHolder newsInstance(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();

            if (holder == null) {
                holder = new ViewHolder();
                holder.title = convertView.findViewById(R.id.lv_text1);
                holder.text = convertView.findViewById(R.id.lv_text2);
                convertView.setTag(holder);
            }

            return holder;
        }

    }
}