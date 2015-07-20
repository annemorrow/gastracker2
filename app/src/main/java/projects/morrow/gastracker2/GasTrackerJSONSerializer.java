package projects.morrow.gastracker2;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by anne on 7/20/15.
 */
public class GasTrackerJSONSerializer {

    private Context mContext;
    private String mFilename;


    public GasTrackerJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Entry> loadEntries() throws IOException, JSONException {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        BufferedReader reader = null;
        try {
            // Open and read the file into the stringbuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
                Log.d("loadJSON", line);
            }
            // parse the JSON using the jsontokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of entries from JSON objects
            Log.d("array length", array.length() + "exists");
            for (int i = 0; i < array.length(); i++) {
                Entry entry = new Entry(array.getJSONObject(i));
                Log.d("formed entry", "GAS " + entry.getGas());
                Log.d("formed entry", "MILES " + entry.getMiles());
                entries.add(entry);

            }
        } catch(FileNotFoundException e) {
            // Ignore, this happens when starting fresh
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return entries;
    }

    public void saveEntries(ArrayList<Entry> entries) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (Entry e : entries) {
            Log.d("JSON", e.toJSON().toString());
            array.put(e.toJSON());
        }
        Writer writer = null;
        try {
            OutputStream out = mContext
                    .openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
