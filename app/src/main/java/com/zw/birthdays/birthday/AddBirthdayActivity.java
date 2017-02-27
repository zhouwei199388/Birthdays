package com.zw.birthdays.birthday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.zw.birthdays.R;
import com.zw.birthdays.base.BaseTitleActivity;

/**
 * Created by lenovo on 2017/2/27.
 */

public class AddBirthdayActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);


        setTitleText("添加生日");
    }

}
