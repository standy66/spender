package io.github.standy66.spender;

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
 * Created by kagudkov on 17.08.15.
 */
public class CountIntentJSONSerializer {
    private Context mContext;
    private String mFileName;
    private static String TAG = "CountIntentJSONSerializer";

    public CountIntentJSONSerializer(Context mContext, String mFileName) {
        this.mContext = mContext;
        this.mFileName = mFileName;
    }

    public void saveCount(ArrayList<Count> counts) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Count count : counts) {
            try {
                jsonArray.put(count.toJSON());
            } catch (JSONException e) {
                Log.e(TAG, "problem with JSON", e);
            }
        }
        Writer writer = null;
        try {
            OutputStream outputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            try {
                writer.write(jsonArray.toString());
            } catch (IOException e) {
                Log.e(TAG, "problem with writer", e);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "file didn't open", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ArrayList<Count> loadCounts() throws IOException, JSONException {
        ArrayList<Count> counts = new ArrayList<Count>();
        BufferedReader bufferedReader = null;

        InputStream inputStream = null;
        try {
            inputStream = mContext.openFileInput(mFileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < jsonArray.length(); ++i) {
                counts.add(new Count(jsonArray.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "not lucky");
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return counts;
    }
}
