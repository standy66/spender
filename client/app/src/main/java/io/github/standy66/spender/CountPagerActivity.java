package io.github.standy66.spender;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kagudkov on 16.08.15.
 */
public class CountPagerActivity extends ActionBarActivity {

    private ViewPager mViewPager;
    private ArrayList<Count> mCounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mCounts = CountLab.get(this).getCrimes();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Count count = mCounts.get(position);
                return CountFragment.newInstance(count.getId());
            }

            @Override
            public int getCount() {
                return mCounts.size();
            }
        });

        UUID mCrimeId = (UUID) getIntent().getSerializableExtra(CountFragment.EXTRA_COUNT_ID);
        for (int i = 0; i < mCounts.size(); ++i) {
            if (mCounts.get(i).getId().equals(mCrimeId)) {
                mViewPager.setCurrentItem(i);
            }
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 Count count = mCounts.get(position);
                 if(count.getTitle() != null){
                     setTitle(count.getTitle());
                 }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}