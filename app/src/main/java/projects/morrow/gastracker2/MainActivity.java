package projects.morrow.gastracker2;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    private TextView mEnterGas;
    private TextView mEnterMiles;
    private TextView mEnterDate;
    private Button mSubmitButton;
    private Button mListButton;

    private Entry mEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEnterGas = (TextView) findViewById(R.id.enter_gas);
        mEnterMiles = (TextView) findViewById(R.id.enter_miles);
        mEnterDate = (TextView) findViewById(R.id.enter_date);
        mSubmitButton = (Button) findViewById(R.id.enter_button);
        mListButton = (Button) findViewById(R.id.list_button);

        Calendar c = Calendar.getInstance();
        if (mEntry == null) {
            mEntry = new Entry(0, 0, c.getTime());
        }

        mEnterGas.setText(Integer.toString(mEntry.getGas()), TextView.BufferType.EDITABLE);
        mEnterMiles.setText(Integer.toString(mEntry.getMiles()), TextView.BufferType.EDITABLE);

        mEnterDate.setText(mEntry.getDate().toString(), TextView.BufferType.NORMAL);

        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EntryListActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void showDatePickerDialog(View v) {
        DialogFragment date = new DatePickerFragment();
        date.show(getSupportFragmentManager(), "datePicker");
    }
}
