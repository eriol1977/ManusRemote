package com.fb.manusremote.intercom.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.fb.manusremote.intercom.model.Intercom;

/**
 * Created by Francesco on 14/01/2015.
 */
public class IntercomListItemListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Context context = view.getContext();

        final Intercom intercom = (Intercom) parent.getItemAtPosition(position);

        final Intent intent = new Intent();
        intent.setClass(context, IntercomConfig.class);
        intent.putExtra(IntercomConfig.INTERCOM, intercom);
        context.startActivity(intent);
    }

}
