package projects.morrow.gastracker2;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class GraphActivity extends Activity {

    private ArrayList<Entry> mEntryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mEntryList = EntryList.get(this).getEntries();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(series());
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graph.getViewport().setMinX(dates()[0].getTime());
        graph.getViewport().setMaxX(dates()[mEntryList.size()-1].getTime());
        graph.getViewport().setXAxisBoundsManual(true);
    }


    private double[] milesPerGallon() {
        int size = mEntryList.size();
        double[] mpg = new double[size];
        if (mpg.length > 1) {
            for (int i = 1; i < size; i++) {
                double miles = (double) (mEntryList.get(i).getMiles() - mEntryList.get(i-1).getMiles());
                double gallons = (double) (mEntryList.get(i).getGas() - mEntryList.get(i-1).getGas());
                double milespergallon = miles / gallons;
                mpg[i] = milespergallon;
            }
            mpg[0] = mpg[1];
        }
        return mpg;
    }

    private Date[] dates() {
        int size = mEntryList.size();
        Date[] dates = new Date[size];
        for (int i = 0; i < size; i++) {
            Date date = mEntryList.get(i).getDate();
            dates[i] = date;
        }
        return dates;
    }

    private DataPoint[] series() {
        int size = mEntryList.size();
        DataPoint[] points = new DataPoint[size];
        double[] mpg = milesPerGallon();
        Date[] days = dates();
        for (int i = 0; i < size; i++) {
            points[i] = new DataPoint(days[i], mpg[i]);
        }
        return points;
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
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
}
