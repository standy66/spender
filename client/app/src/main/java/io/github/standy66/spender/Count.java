package io.github.standy66.spender;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by kagudkov on 14.08.15.
 */
public class Count {
    private UUID mId;
    private String mTitle;
    private Double mBalance;
    private boolean mSolved;

    private static String JSON_ID = "id";
    private static String JSON_TITLE = "title";
    private static String JSON_BALANCE = "balance";
    private static String JSON_SOLVED = "solved";

    public Double getBalance() {
        return mBalance;
    }

    public void setBalance(Double mBalance) {
        this.mBalance = mBalance;
    }


    public Count() {
        mId = UUID.randomUUID();
        mBalance = new Double(0);
    }

    public Count(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mBalance = json.getDouble(JSON_BALANCE);
        mSolved = json.getBoolean(JSON_SOLVED);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_BALANCE, mBalance);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_TITLE, mTitle);
        return json;
    }
}
