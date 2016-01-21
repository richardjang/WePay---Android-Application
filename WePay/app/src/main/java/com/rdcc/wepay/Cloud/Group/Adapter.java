package com.rdcc.wepay.Cloud.Group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdcc.wepay.R;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Data> {
    public Adapter(Context context, ArrayList<Data> data){
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_list_item, parent, false);
        }

        Data data = getItem(position);
        ImageView icon = (ImageView) convertView.findViewById(R.id.group_list_item_icon);
        icon.setImageResource(data.getDrawable());

        TextView text = (TextView) convertView.findViewById(R.id.group_list_item_name);
        text.setText(data.getName());

        return convertView;
    }
}
