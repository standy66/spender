package io.github.standy66.spender;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by kagudkov on 14.08.15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CountFragment extends android.support.v4.app.Fragment {

    public static final String EXTRA_COUNT_ID = "spender.count_fragment";
    public static final String DIALOG_DATE = "date";
    public static final int REQUEST_BALANCE = 0;

    private Count mCount;
    private EditText mTitleField;
    private EditText mBalanceField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_COUNT_ID);
        mCount = CountLab.get(getActivity()).getCount(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_count, parent, false);
        mTitleField = (EditText) v.findViewById(R.id.count_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() != 0) {
                    mCount.setTitle(s.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTitleField.setText(mCount.getTitle());

        mBalanceField = (EditText) v.findViewById(R.id.count_balance_field);
        mBalanceField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    mCount.setBalance(Double.valueOf(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*if (NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        return v;
    }

    public static CountFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_COUNT_ID, id);
        CountFragment fragment = new CountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CountLab.get(getActivity()).saveCount();
    }
}
