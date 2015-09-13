package io.github.standy66.spender;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kagudkov on 15.08.15.
 */
public class CountLab {
    private static CountLab sCountLab;
    private Context mAppContext;
    private ArrayList<Count> mCounts;
    private static String TAG = "CountLab";
    private static String FILE_NAME = "Counts.json";

    private CountIntentJSONSerializer mSerializer;


    public static CountLab get(Context context) {
        if (sCountLab == null) {
            sCountLab = new CountLab(context);
        }
        return sCountLab;
    }

    private CountLab(Context context) {
        mAppContext = context;
        mSerializer = new CountIntentJSONSerializer(mAppContext, FILE_NAME);
        try {
            mCounts = mSerializer.loadCounts();
        } catch (Exception e) {
            mCounts = new ArrayList<Count>();
            Log.e(TAG, "Error loading Counts: ", e);
        }
    }

    public ArrayList<Count> getCounts() {
        return mCounts;
    }

    public Count getCount(UUID id) {
        for (Count Count : mCounts) {
            if (Count.getId().equals(id)) {
                return Count;
            }
        }
        return null;
    }

    public void addCount(Count Count) {
        mCounts.add(Count);
    }

    public void deleteCount(Count Count) {
        mCounts.remove(Count);
    }

    public boolean saveCounts() {
        mSerializer.saveCounts(mCounts);
        Log.d(TAG, "success saved Counts");
        return true;
    }
}

