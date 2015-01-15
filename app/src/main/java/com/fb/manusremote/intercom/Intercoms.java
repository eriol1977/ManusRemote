package com.fb.manusremote.intercom;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.fb.manusremote.PersistenceManager;
import com.fb.manusremote.R;
import com.fb.manusremote.model.Intercom;

import java.util.List;


public class Intercoms extends ActionBarActivity {

    final static String INTERCOMS = "intercoms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercoms);

        final ListView listIntercoms = (ListView) findViewById(R.id.listIntercoms);
        final List<Intercom> intercoms = PersistenceManager.loadIntercoms(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        listIntercoms.setAdapter(new IntercomListArrayAdapterItem(this, R.layout.intercoms_list_row, intercoms));
        listIntercoms.setOnItemClickListener(new IntercomListItemListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_intercoms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_intercom) {
            final Intent intent = new Intent();
            intent.setClass(this, IntercomCreate.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
