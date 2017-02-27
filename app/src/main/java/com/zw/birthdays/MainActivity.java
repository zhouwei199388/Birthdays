package com.zw.birthdays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zw.birthdays.base.BaseTitleActivity;
import com.zw.birthdays.birthday.AddBirthdayActivity;
import com.zw.birthdays.birthday.BirthdayFragment;
import com.zw.birthdays.setting.SettingFragment;
import com.zw.birthdays.view.BottomNavigationBar;
import com.zw.birthdays.view.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/2/24.
 */

public class MainActivity extends BaseTitleActivity implements BottomNavigationBar.onSelectedChangeListener {
    @BindView(R.id.bottomNavigationBar)
    public BottomNavigationBar mNavigationBar;
    @BindView(R.id.customViewPager)
    public CustomViewPager mCustomViewPager;
    private String[] mBottomNavigationStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBottomNavigationStrings = getResources().getStringArray(R.array.BottomNavigationStrings);
        initView();
    }

    /**
     * 初始化title和控件
     */
    private void initView() {
        setTitleText(mBottomNavigationStrings[0]);
        setNoBack();
        showMenu(true);
        mNavigationBar.addItem(R.drawable.icon_birthday_selector, mBottomNavigationStrings[0])
                .addItem(R.drawable.icon_setting_selector, mBottomNavigationStrings[1])
                .initTab();
        mNavigationBar.setOnChangeListener(this);
        mCustomViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        mCustomViewPager.setScrollEnable(false);
    }


    @Override
    protected void onMenuClick() {
        super.onMenuClick();
        startActivity(new Intent(this, AddBirthdayActivity.class));
    }

    @Override
    public void onSelect(int position) {
        setTitleText(mBottomNavigationStrings[position]);
        mCustomViewPager.setCurrentItem(position);
        showMenu(position == 0);
    }

    /**
     *  控制menu控件显示
     * @param isShow
     */
    private void showMenu(boolean isShow){
        if(isShow){
            setMenu1Image(R.drawable.icon_add_selector);
        }else{
            hideMenuView();
        }
    }


    static class MainAdapter extends FragmentPagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new BirthdayFragment();
                    break;
                case 1:
                    fragment = new SettingFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
