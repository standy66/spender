package io.github.standy66.spender;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kagudkov on 15.08.15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CountListFragment extends ListFragment {
    private static final String TAG = "CountListFragment";
    private ArrayList<Count> mCounts;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.counts_title);
        mCounts = CountLab.get(getActivity()).getCounts();
        ArrayAdapter<Count> adapter = new CountAdapter(mCounts);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Count count = ((CountAdapter) getListAdapter()).getItem(position);
        Log.d(TAG, count.getTitle() + "was clicked");
        Intent intent = new Intent(getActivity(), CountPagerActivity.class);
        intent.putExtra(CountFragment.EXTRA_COUNT_ID, count.getId());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CountAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private class CountAdapter extends ArrayAdapter<Count> {
        public CountAdapter(ArrayList<Count> counts) {
            super(getActivity(), 0, counts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_count, null);
            }
            Count count = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.list_item_crime_titleTextView);
            titleTextView.setText(count.getTitle());
            TextView textView = (TextView) convertView.findViewById(R.id.list_item_crime_dataTextView);
            textView.setText(count.getBalance().toString());
            return convertView;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_count_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_count: {
                Count count = new Count();
                CountLab.get(getActivity()).addCount(count);
                Intent intent = new Intent(getActivity(), CountPagerActivity.class);
                intent.putExtra(CountFragment.EXTRA_COUNT_ID, count.getId());
                startActivityForResult(intent, 0);
                return true;
            }
            case R.id.menu_item_show_subtitle: {
                if (getActivity().getActionBar() != null) {
                    if (getActivity().getActionBar().getSubtitle() == null) {
                        getActivity().getActionBar().setSubtitle(R.string.subtitle);
                        item.setTitle(R.string.hide_subtitle);
                        mSubtitleVisible = true;
                    } else {
                        mSubtitleVisible = false;
                        getActivity().getActionBar().setSubtitle(null);
                        item.setTitle(R.string.show_subtitle);
                    }
                }
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        CountAdapter adapter = (CountAdapter) getListAdapter();
        Count count = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_count: {
                CountLab.get(getActivity()).deleteCount(count);
                adapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        if (mSubtitleVisible) {
            getActivity().getActionBar().setSubtitle(R.string.subtitle);
        }
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.count_list_item_context, menu);
    }
}
