package io.github.standy66.spender;

import android.app.Fragment;

/**
 * Created by kagudkov on 15.08.15.
 */
public class CountListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CountListFragment();
    }
}
