package com.fb.manusremote.intercom.view;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.fb.manusremote.R;
import com.fb.manusremote.infra.PersistenceManager;
import com.fb.manusremote.model.VOIPAdapter;
import com.fb.manusremote.view.VOIPListArrayAdapterItem;

import java.util.List;


public class IntercomsActivity extends ActionBarActivity {

    final static String INTERCOMS = "intercoms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0,0);
        setContentView(R.layout.activity_intercoms);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        final ListView listIntercoms = (ListView) findViewById(R.id.listIntercoms);
        final List<VOIPAdapter> intercoms =
                PersistenceManager.loadIntercoms(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        listIntercoms.setAdapter(new VOIPListArrayAdapterItem(this, R.layout.voip_list_row, intercoms));
        listIntercoms.setOnItemClickListener(new IntercomListItemListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voip_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_voip) {
            final Intent intent = new Intent();
            intent.setClass(this, IntercomCreateActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
