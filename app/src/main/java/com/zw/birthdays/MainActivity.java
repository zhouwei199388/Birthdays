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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/2/24.
 */

public class MainActivity extends BaseTitleActivity implements BottomNavigationBar.onSelectedChangeListener {
    private static final int ADD_BIRTHDAY_REQUEST_CODE = 0001;
    @BindView(R.id.bottomNavigationBar)
    public BottomNavigationBar mNavigationBar;
    @BindView(R.id.customViewPager)
    public CustomViewPager mCustomViewPager;
    private String[] mBottomNavigationStrings;


    private BirthdayFragment birthdayFragment;
    private SettingFragment settingFragment;

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
        startActivityForResult(new Intent(this, AddBirthdayActivity.class), ADD_BIRTHDAY_REQUEST_CODE);
    }

    /**
     * navigation选择回调接口
     *
     * @param position
     */
    @Override
    public void onSelect(int position) {
        setTitleText(mBottomNavigationStrings[position]);
        mCustomViewPager.setCurrentItem(position);
        showMenu(position == 0);
    }

    /**
     * 控制menu控件显示
     *
     * @param isShow
     */
    private void showMenu(boolean isShow) {
        if (isShow) {
            setMenu1Image(R.drawable.icon_add_selector);
        } else {
            hideMenuView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ADD_BIRTHDAY_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {

            if (birthdayFragment != null) {
                birthdayFragment.getData();
            }
        }
    }

    class MainAdapter extends FragmentPagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    birthdayFragment = new BirthdayFragment();
                    return birthdayFragment;
                case 1:
                    settingFragment = new SettingFragment();
                    return settingFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
