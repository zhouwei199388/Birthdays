package com.zw.birthdays.birthday;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yanzhenjie.album.Album;
import com.zw.birthdays.R;
import com.zw.birthdays.base.BaseTitleActivity;
import com.zw.birthdays.bean.UserBean;
import com.zw.birthdays.utils.LunarCalendar;
import com.zw.birthdays.utils.TimeUtil;
import com.zw.birthdays.utils.ToastUtil;
import com.zw.birthdays.view.CircleImageView;
import com.zw.birthdays.view.DatePickerDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/2/27.
 */

public class AddBirthdayActivity extends BaseTitleActivity {

    private static final int ACTIVITY_REQUEST_SELECT_PHOTO = 0;
    @BindView(R.id.tv_name)
    EditText mNameEt;
    @BindView(R.id.tv_birthday)
    TextView mBirthdayTv;
    @BindView(R.id.iv_head)
    CircleImageView mHeadIv;
    @BindView(R.id.iv_add_head)
    ImageView mAddHeadIv;
    @BindView(R.id.rg_sex)
    RadioGroup mSexRg;
    private Bitmap mHeadBitmap = null;
    private UserBean mUserBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);
        ButterKnife.bind(this);
        mUserBean = new UserBean();
        mUserBean.sex = 0;
        setTitleText("添加生日");

        mSexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ToastUtil.showToast(getApplicationContext(), "" + checkedId);
                if (checkedId == R.id.rb_man) {
                    mUserBean.sex = 0;
                } else if (checkedId == R.id.rb_woman) {
                    mUserBean.sex = 1;
                }
            }
        });
    }

    @OnClick({R.id.iv_add_head, R.id.iv_head})
    public void selectHeadImg() {
        Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO
                , 1                                                         // 指定选择数量。
                , ContextCompat.getColor(this, R.color.colorPrimary)        // 指定Toolbar的颜色。
                , ContextCompat.getColor(this, R.color.colorPrimaryDark));  // 指定状态栏的颜色。
    }

    @OnClick(R.id.tv_birthday)
    public void selectBirthday() {
        DatePickerDialog pickerDialog = new DatePickerDialog(this, System.currentTimeMillis(), "请选择出生日期");
        pickerDialog.setSelectDateListener(new DatePickerDialog.OnSelectDateListener() {
            @Override
            public void onSelectDate(long time, boolean isLunar) {
                mUserBean.birthdayTime = time;
                mUserBean.isLunar = isLunar;
                String date;
                if (isLunar) {
                    date = LunarCalendar.getLunarData(time);
                } else {
                    date = TimeUtil.getDate(time);
                }
                mBirthdayTv.setText(date);
            }
        });
        pickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO && resultCode == RESULT_OK) {
            List<String> pathList = Album.parseResult(data);
            if (pathList.size() == 0) {
                ToastUtil.showToast(this, "获取失败！");
            } else {
                mUserBean.headImgUrl = pathList.get(0);
                mHeadBitmap = BitmapFactory.decodeFile(pathList.get(0));
                mAddHeadIv.setVisibility(View.GONE);
                mHeadIv.setImageBitmap(mHeadBitmap);
                mHeadIv.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.btn_save)
    public void save() {
        String name = mNameEt.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            mUserBean.userName = name;
            mUserBean.save();
            setResult(RESULT_OK);
            finish();
            ToastUtil.showToast(this, R.string.toast_success);
        } else {
            ToastUtil.showToast(this, R.string.toast_warn);
        }

    }
}
