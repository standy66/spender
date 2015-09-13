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
    private static String FILE_NAME = "count.json";

    private CountIntentJSONSerializer mSerializer;

    private static CountLab countLab;
    private Context mContext;

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
            Log.e(TAG, "Error loading counts: ", e);
        }
    }

    public ArrayList<Count> getCrimes() {
        return mCounts;
    }

    public Count getCrime(UUID id) {
        for (Count count : mCounts) {
            if (count.getId().equals(id)) {
                return count;
            }
        }
        return null;
    }

    public void addCrime(Count count) {
        mCounts.add(count);
    }

    public void deleteCrime(Count count) {
        mCounts.remove(count);
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCounts);
            Log.d(TAG, "success saved crimes");
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error saving crimes:", e);
            return false;
        }
    }
}
