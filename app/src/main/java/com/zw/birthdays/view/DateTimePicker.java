package com.zw.birthdays.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;


import com.zw.birthdays.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimePicker extends FrameLayout {

    private static final String TAG = DateTimePicker.class.getSimpleName();
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private final NumberPicker mNumPickerYear;
    private final NumberPicker mNumPickerMonth;
    private final NumberPicker mNumPickerDay;
    private Calendar mDate; // 当前日期
    private Integer year;
    private Integer month;
    private Integer day;
    private int[] maxDayOfMonths = new int[]{31, 28, 31, 30, 31, 30, 31, 31,
            30, 31, 30, 31};
    private int maxYear;
    private int minYear;
    private ValueChangeListener listener;

    private TextView mCustomTitleView;

    public void setOnValueChangeListener(ValueChangeListener listener) {
        this.listener = listener;
    }

    public interface ValueChangeListener {
        void onValueChangeListener();

        void onCancelClick();

        void onConfirmClick(long time);
    }

    public DateTimePicker(Context context, String title) {
        super(context);
        mDate = Calendar.getInstance();

        inflate(context, R.layout.data_time_picker, this);

        mCustomTitleView = (TextView) findViewById(R.id.tv_custom_dialog_title);
        mCustomTitleView.setText(title);

        // 年份
        mNumPickerYear = (NumberPicker) this
                .findViewById(R.id.numberPicker_year);
        getMaxYear();
        getMinYear();
        mNumPickerYear.setMaxValue(maxYear);
        mNumPickerYear.setMinValue(minYear);
        year = mDate.get(Calendar.YEAR);
        setYearDisplayName();
        mNumPickerYear.setValue(year);
        mNumPickerYear.getChildAt(0).setFocusable(false);
        mNumPickerYear.setOnValueChangedListener(mOnYearChangeListener);
        // 月份
        mNumPickerMonth = (NumberPicker) this
                .findViewById(R.id.numberPicker_month);
        mNumPickerMonth.setMinValue(0);
        setMonthDisplayName();
        month = mDate.get(Calendar.MONTH);
        mNumPickerMonth.setValue(month);
        mNumPickerMonth.getChildAt(0).setFocusable(false);
        mNumPickerMonth.setOnValueChangedListener(mOnMonthChangeListener);
        // 天
        mNumPickerDay = (NumberPicker) this.findViewById(R.id.numberPicker_day);
        mNumPickerDay.setMaxValue(30);
        mNumPickerDay.setMinValue(0);
        day = mDate.get(Calendar.DAY_OF_MONTH);
        setDayDisplayName();
        mNumPickerDay.setValue(day - 1);
        mNumPickerDay.getChildAt(0).setFocusable(false);
        mNumPickerDay.setOnValueChangedListener(mOnDayChangeListener);

        // 取消事件
        findViewById(R.id.iv_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancelClick();
                }
            }
        });

        findViewById(R.id.iv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onConfirmClick(getResultTime());
                }
            }
        });
    }

    /**
     * 设置日期选择器的起始显示时间
     * @param time 时间 ms
     */
    public void setTime(long time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mNumPickerYear.setValue(year);
        setMonthDisplayName();
        mNumPickerMonth.setValue(month);
        setDayDisplayName();
        mNumPickerDay.setValue(day - 1);
    }

    /**
     * 设置日期
     * @param date
     */
    public void setSettingDate(String date) {
        if (TextUtils.isEmpty(date))
            return;
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(mDateFormat.parse(date));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            mNumPickerYear.setValue(year);
            setMonthDisplayName();
            mNumPickerMonth.setValue(month);
            setDayDisplayName();
            mNumPickerDay.setValue(day - 1);
        } catch (ParseException e) {
            Log.d(TAG,e.toString());
        }
    }

    private void getMaxYear() {
        if (mDate == null) {
            mDate = Calendar.getInstance();
        }
        maxYear = mDate.get(Calendar.YEAR);
    }

    private void getMinYear() {
        if (mDate == null) {
            mDate = Calendar.getInstance();
        }
        //15岁已经很大了吧
        minYear = mDate.get(Calendar.YEAR) - 100;
    }

    private void setYearDisplayName() {
        String[] yearDisplayName = new String[maxYear - minYear + 1];
        for (int t = minYear, i = 0; t <= maxYear; t++, i++) {
            yearDisplayName[i] = t + " 年";
        }
        mNumPickerYear.setDisplayedValues(yearDisplayName);
    }

    private void setMonthDisplayName() {
        int value = 12;
        mNumPickerMonth.setDisplayedValues(null);
        if (year == mDate.get(Calendar.YEAR)) {
            value = mDate.get(Calendar.MONTH) + 1;
            // mNumPickerMonth.setValue(value - 1);
        }
        String[] monthDisplayName = new String[value];
        mNumPickerMonth.setMaxValue(value - 1);
        for (int i = 0; i < monthDisplayName.length; i++) {
            monthDisplayName[i] = (i + 1) + " 月";
        }
        mNumPickerMonth.setDisplayedValues(monthDisplayName);
    }

    private void setDayDisplayName() {
        month = mNumPickerMonth.getValue();
        mNumPickerDay.setDisplayedValues(null);
        int value = maxDayOfMonths[month];
        if (month == 1) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                value = 29;
            }
        }
        if (year == mDate.get(Calendar.YEAR)
                && month >= mDate.get(Calendar.MONTH)) {
            value = mDate.get(Calendar.DAY_OF_MONTH);
            // mNumPickerDay.setValue(value - 1);
        }
        mNumPickerDay.setMaxValue(value - 1);
        String[] dayDisplayName = new String[value];

        for (int i = 0; i < dayDisplayName.length; i++) {
            dayDisplayName[i] = (i + 1) + " 日";
        }
        mNumPickerDay.setDisplayedValues(dayDisplayName);
    }

    private OnValueChangeListener mOnYearChangeListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            year = picker.getValue();
            setMonthDisplayName();
            setDayDisplayName();
            if (listener != null) {
                listener.onValueChangeListener();
            }
        }
    };

    private OnValueChangeListener mOnMonthChangeListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            setDayDisplayName();
            if (listener != null) {
                listener.onValueChangeListener();
            }
        }
    };

    private OnValueChangeListener mOnDayChangeListener = new OnValueChangeListener() {
        @SuppressWarnings("unused")
        private OnValueChangeListener mOnDayChangeListener = new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                day = picker.getValue() + 1;
                if (listener != null) {
                    listener.onValueChangeListener();
                }
            }
        };

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            day = picker.getValue() + 1;
            if (listener != null) {
                listener.onValueChangeListener();
            }
        }
    };

    public Date getResult() {
        String brith = mNumPickerYear.getValue() + "-"
                + (mNumPickerMonth.getValue() + 1) + "-"
                + (mNumPickerDay.getValue() + 1);
        Date result = null;
        try {
            result = mDateFormat.parse(brith);
        } catch (ParseException e) {
            e.toString();
        }
        return result;
    }

    public long getResultTime() {
        Date date = getResult();
        if (date != null) {
            return date.getTime();
        }
        return -1;
    }
}
