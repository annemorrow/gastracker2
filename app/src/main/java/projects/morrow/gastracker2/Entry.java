package projects.morrow.gastracker2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by anne on 7/15/15.
 */
public class Entry {

    private static final String JSON_ID = "id";
    private static final String JSON_GAS = "gas";
    private static final String JSON_MILES = "miles";
    private static final String JSON_DATE = "date";

    private UUID mID;
    private int mGas;
    private int mMiles;
    private Date mDate;

    public int getGas() {
        return mGas;
    }

    public void setGas(int gas) {
        mGas = gas;
    }

    public int getMiles() {
        return mMiles;
    }

    public void setMiles(int miles) {
        mMiles = miles;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public UUID getID() {
        return mID;
    }

    public Entry(int miles, int gas, Date date) {
        mID = UUID.randomUUID();
        mMiles = miles;
        mGas = gas;
        mDate = date;
    }

    public Entry(JSONObject json) throws JSONException {
        Log.d("ENTRY", "formed from json");
        mID = UUID.fromString(json.getString(JSON_ID));
        Log.d("ENTRY", "UUID is " + mID);
        mMiles = json.getInt(JSON_MILES);
        Log.d("ENTRY", "miles is " + mMiles);
        mGas = json.getInt(JSON_GAS);
        Log.d("ENTRY", "gas is " + mGas);
        mDate = new Date(json.getLong(JSON_DATE));
        Log.d("ENTRY", "date is " + mDate.toString());
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mID.toString());
        json.put(JSON_GAS, mGas);
        json.put(JSON_MILES, mMiles);
        json.put(JSON_DATE, mDate.getTime());
        return json;
    }
}
