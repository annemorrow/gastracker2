package projects.morrow.gastracker2;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by anne on 7/15/15.
 */
public class EntryList {

    private ArrayList<Entry> mEntries;

    private static final String FILENAME = "entries.json";
    private GasTrackerJSONSerializer mSerializer;

    private static final String TAG = "EntryList";

    private static EntryList sList;
    private Context mAppContext;

    private EntryList(Context appContext) {
        mAppContext = appContext;
        mSerializer = new GasTrackerJSONSerializer(mAppContext, FILENAME);
        Log.d(TAG, mSerializer.toString());
        try {
            mEntries = mSerializer.loadEntries();
            Log.d(TAG, "list loaded");
        } catch (Exception e) {
            mEntries = new ArrayList<Entry>();
            Log.e(TAG, "list not loaded");
        }
    }

    public static EntryList get(Context c) {
        if (sList == null) {
            sList = new EntryList(c.getApplicationContext());
        }
        return sList;
    }

    public void addEntry(Entry entry) {
        mEntries.add(entry);
    }

    public boolean containsEntry(UUID id) {
        for (Entry e : mEntries) {
            if (e.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Entry> getEntries() {
        return mEntries;
    }

    public Entry getEntry(UUID id) {
        for(Entry e : mEntries) {
            if (id.equals(e.getID())) {
                return e;
            }
        }
        return null;
    }

    public void deleteEntry(UUID id) {
        for (int index = 0; index < mEntries.size(); index++) {
            if (id.equals(mEntries.get(index).getID())) {
                mEntries.remove(index);
                return;
            }
        }
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveEntries(mEntries);
            Log.d(TAG, "entries saved");
            return true;
        } catch (Exception e) {
            Log.e("EntryList", "Error saving entries: ", e);
            return false;
        }
    }
}
