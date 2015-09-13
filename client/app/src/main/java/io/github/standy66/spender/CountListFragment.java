package io.github.standy66.spender;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by kagudkov on 13.09.15.
 */
public class CountListFragment extends ListFragment {
    private ArrayList<Count> counts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        counts = CountLab.get(getActivity()).getCounts();
        ArrayAdapter<Count> adapter = new CountAdapter(counts);
        setListAdapter(adapter);
        setRetainInstance(true);
    }

    private class CountAdapter extends ArrayAdapter<Count> {
        public CountAdapter(ArrayList<Count> Counts) {
            super(getActivity(), 0, Counts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_count, null);
            }
            Count Count = getItem(position);
            return convertView;
        }
    }
}
