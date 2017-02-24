package com.zw.birthdays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.zw.birthdays.view.BottomNavigationBar;

/**
 * Created by lenovo on 2017/2/24.
 */

public class MainActivity extends Activity implements BottomNavigationBar.onSelectedChangeListener {
    private BottomNavigationBar mNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mNavigationBar = (BottomNavigationBar) findViewById(R.id.bottomNavigationBar);
        mNavigationBar.addItem(R.drawable.ic_launcher, "列表")
                .addItem(R.drawable.ic_launcher, "我的")
                .addItem(R.drawable.ic_launcher, "发现")
                .addItem(R.drawable.ic_launcher, "视频")
                .addItem(R.drawable.ic_launcher, "附近")
                .initTab();

        mNavigationBar.setOnChangeListener(this);
    }


    @Override
    public void onSelect(int position) {
        TextView textView = (TextView) findViewById(R.id.tv_test);
        textView.setText("Hello World" + position);
    }
}
