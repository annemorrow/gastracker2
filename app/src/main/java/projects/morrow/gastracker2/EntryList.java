package projects.morrow.gastracker2;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by anne on 7/15/15.
 */
public class EntryList {

    private ArrayList<Entry> mEntries;

    private static EntryList sList;
    private Context mAppContext;

    private EntryList(Context appContext) {
        mAppContext = appContext;
        mEntries = new ArrayList<Entry>();
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

}
