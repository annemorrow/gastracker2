package projects.morrow.gastracker2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;


public class EntryListActivity extends ListActivity {

    private ArrayList<Entry> mEntryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        // mListView = (ListView) findViewById(R.id.list);
/*
        Entry first = new Entry(15, 15, Calendar.getInstance().getTime());
        Entry second = new Entry (1, 14, Calendar.getInstance().getTime());
        Entry third = new Entry (30, 10, Calendar.getInstance().getTime());

        mEntryList = new ArrayList<Entry>();
        mEntryList.add(first);
        mEntryList.add(second);
        mEntryList.add(third);
*/
        mEntryList = EntryList.get(this).getEntries();
        EntryAdapter adapter = new EntryAdapter(mEntryList);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Entry e = ((EntryAdapter)getListAdapter()).getItem(position);
        Intent i = new Intent(this, EntryActivity.class);
        i.putExtra(EntryActivity.EXTRA_ENTRY_ID, e.getID());
        startActivity(i);
    }

    private class EntryAdapter extends ArrayAdapter<Entry> {
        public EntryAdapter(ArrayList<Entry> entries) {
            super(EntryListActivity.this, 0, entries);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.entry_list_item, null);
            }

            // configure the view for this entry
            Entry e = ((EntryAdapter) getListAdapter()).getItem(position);

            TextView gas = (TextView) convertView.findViewById(R.id.entry_list_item_gallons);
            gas.setText(Integer.toString(e.getGas()), TextView.BufferType.NORMAL);

            TextView miles = (TextView) convertView.findViewById(R.id.entry_list_item_miles);
            miles.setText(Integer.toString(e.getMiles()), TextView.BufferType.NORMAL);

            TextView date = (TextView) convertView.findViewById(R.id.entry_list_item_date);
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            date.setText(dateFormat.format(e.getDate()), TextView.BufferType.NORMAL);

            return convertView;
        }
    }
}
