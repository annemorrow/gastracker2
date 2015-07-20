package projects.morrow.gastracker2;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class EntryActivity extends FragmentActivity {
    private TextView mEnterGas;
    private TextView mEnterMiles;
    private TextView btnSelectDate;
    private Button mSubmitButton;
    private Button mDeleteButton;


    private Entry mEntry;

    static final int DATE_DIALOG_ID = 0;
    public  int year,month,day;
    private int mYear, mMonth, mDay;

    public static final String EXTRA_ENTRY_ID = "projects.morrow.gastracker2.entry_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID id = (UUID) getIntent().getSerializableExtra(EXTRA_ENTRY_ID);
        if (id != null) {
            mEntry = EntryList.get(this).getEntry(id);
        }

        setContentView(R.layout.entry);

        mEnterGas = (TextView) findViewById(R.id.enter_gas);
        mEnterMiles = (TextView) findViewById(R.id.enter_miles);
        mSubmitButton = (Button) findViewById(R.id.enter_button);
        mDeleteButton = (Button) findViewById(R.id.delete_button);
        btnSelectDate = (Button) findViewById(R.id.buttonSelectDate);

        final Calendar c = Calendar.getInstance();
        if (mEntry != null) {
            c.setTime(mEntry.getDate());
        } else {
            mEntry = new Entry(0, 0, c.getTime());
        }
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mEnterGas.setText(Integer.toString(mEntry.getGas()), TextView.BufferType.EDITABLE);
        mEnterMiles.setText(Integer.toString(mEntry.getMiles()), TextView.BufferType.EDITABLE);


        btnSelectDate.setText(mMonth + 1 + "/" + mDay + "/" + mYear);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Show the DatePickerDialog
                showDialog(DATE_DIALOG_ID);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gas = Integer.parseInt(mEnterGas.getText().toString());
                int miles = Integer.parseInt(mEnterMiles.getText().toString());
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                Date date = null;
                try {
                    date = dateFormat.parse(btnSelectDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                mEntry.setGas(gas);
                mEntry.setMiles(miles);
                mEntry.setDate(date);


                EntryList list = EntryList.get(getApplicationContext());
                if (!list.containsEntry(mEntry.getID())) {
                    list.addEntry(mEntry);
                }

                /*
                Entry entry = null;
                if (date != null) {
                    entry = new Entry(miles, gas, date);
                } else {
                    entry = new Entry(miles, gas, Calendar.getInstance().getTime());
                }
                EntryList list = EntryList.get(getApplicationContext());
                list.addEntry(entry);
                */

                Intent i = new Intent(EntryActivity.this, EntryListActivity.class);
                startActivity(i);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntryList list = EntryList.get(getApplicationContext());
                list.deleteEntry(mEntry.getID());
                Intent i = new Intent(EntryActivity.this, EntryListActivity.class);
                startActivity(i);
            }
        });

    }



    // Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {
                    year = yearSelected;
                    month = monthOfYear;
                    day = dayOfMonth;
                    // Set the Selected Date in Select date Button
                    btnSelectDate.setText(month + 1 + "/" + day + "/" + year);
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // create a new DatePickerDialog with values you want to show
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            // create a new TimePickerDialog with values you want to show
            /*case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);
*/
        }
        return null;
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
