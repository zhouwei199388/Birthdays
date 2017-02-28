package com.zw.birthdays.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import com.zw.birthdays.R;

/**
 * 日期选择对话框
 * @author Yuri
 */
public class DatePickerDialog extends Dialog {

	public DatePickerDialog(Context context, long time, String title) {
        super(context, R.style.Custom_Dialog);
        init(context, time, title);
    }

    private void init(Context context, long time, String title) {
        if (time <= 0) {
            time = System.currentTimeMillis();
        }
        final DateTimePicker picker = new DateTimePicker(context, title);
        picker.setTime(time);
        picker.setOnValueChangeListener(new DateTimePicker.ValueChangeListener() {
            @Override
            public void onValueChangeListener() {
            }

            @Override
            public void onCancelClick() {
                dismiss();
            }

            @Override
            public void onConfirmClick(long time) {
                if (mSelectDateListener != null) {
                    mSelectDateListener.onSelectDate(time);
                }
                dismiss();
            }
        });
        setContentView(picker);
    }

    @Override
    public void show() {

        Window window = getWindow();
        window.setWindowAnimations(R.style.Dialog_Buttom_Anim);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        super.show();
    }

    public interface OnSelectDateListener{
        void onSelectDate(long time);
	}
	
	private OnSelectDateListener mSelectDateListener;
	public void setSelectDateListener(OnSelectDateListener selectDateListener) {
		this.mSelectDateListener = selectDateListener;
	}
	
}
