package projects.morrow.gastracker2;

import java.util.Date;
import java.util.UUID;

/**
 * Created by anne on 7/15/15.
 */
public class Entry {

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
}
