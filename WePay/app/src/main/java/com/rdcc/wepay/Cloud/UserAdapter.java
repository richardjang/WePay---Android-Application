package com.rdcc.wepay.Cloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.R;

import java.util.ArrayList;

/**
 * Created by User on 12/6/2015.
 */
public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, ArrayList<User> data){
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_list_item, parent, false);
        }

        User data = getItem(position);
        ImageView icon = (ImageView) convertView.findViewById(R.id.group_list_item_icon);
        icon.setImageResource(data.getDrawable());

        TextView text = (TextView) convertView.findViewById(R.id.group_list_item_name);
        text.setText(data.getName());

        return convertView;
    }
}
