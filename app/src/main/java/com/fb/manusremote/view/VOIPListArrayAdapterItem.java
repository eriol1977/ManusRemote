package com.fb.manusremote.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fb.manusremote.R;
import com.fb.manusremote.model.VOIPAdapter;

import java.util.List;

/**
 * Created by Francesco on 14/01/2015.
 */
public class VOIPListArrayAdapterItem extends ArrayAdapter<VOIPAdapter> {

    final Context mContext;

    final int layoutResourceId;

    final List<VOIPAdapter> data;

    public VOIPListArrayAdapterItem(Context mContext, int layoutResourceId, List<VOIPAdapter> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * The convertView argument is essentially a "ScrapView" as described is Lucas post
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout.
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        VOIPAdapter voipAdapter = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewName = (TextView) convertView.findViewById(R.id.voipListName);
        textViewName.setText(voipAdapter.getName());

        TextView textViewIp = (TextView) convertView.findViewById(R.id.voipListIp);
        textViewIp.setText(voipAdapter.getIp() + ":" + voipAdapter.getPort());

        return convertView;

    }

}