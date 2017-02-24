package com.zw.birthdays.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zw.birthdays.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/2/24.
 */

public class BottomNavigationBar extends LinearLayout implements View.OnClickListener {
    //默认tab个数
    private static final int DEFAULT_TAB_NUM = 1;
    private Context mContext;
    private int mTabNum = DEFAULT_TAB_NUM;
    private List<BottomNavigationItem> mBottomNavigationItems;
    private List<TabItem> mTabItems;
    private onSelectedChangeListener mChangeListener;

    public BottomNavigationBar(Context context) {
        super(context);
        init(context);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化布局方向
     *
     * @param context
     */
    private void init(Context context) {
        this.mContext = context;
        mBottomNavigationItems = new ArrayList<>();
        setBaselineAligned(false);
        setOrientation(HORIZONTAL);
    }

    /**
     * 添加Item
     *
     * @param iconId item图标id
     * @param title  item Title
     * @return
     */
    public BottomNavigationBar addItem(@NonNull int iconId, @NonNull String title) {
        mBottomNavigationItems.add(new BottomNavigationItem(iconId, title));
        return this;
    }

    /**
     * 初始化Item布局
     */
    public void initTab() {
        mTabNum = mBottomNavigationItems.size();
        mTabItems = new ArrayList<>();
        TabItem tabItem;
        BottomNavigationItem navigationItem;
        for (int i = 0; i < mTabNum; i++) {
            navigationItem = mBottomNavigationItems.get(i);
            tabItem = new TabItem(mContext, i);
            tabItem.textView.setText(navigationItem.getTitle());
            tabItem.imageView.setImageResource(navigationItem.getIconResId());
            tabItem.setOnClickListener(this);
            addView(tabItem.itemView);
            mTabItems.add(tabItem);
        }
        setSelect(0);
    }

    /**
     * 选中item
     *
     * @param position
     */
    private void setSelect(int position) {
        for (int i = 0; i < mTabNum; i++) {
            mTabItems.get(i).setSelected(i == position);
        }
        if (mChangeListener != null) {
            mChangeListener.onSelect(position);
        }
    }

    @Override
    public void onClick(View v) {
        setSelect(v.getId());
    }

    public void setOnChangeListener(onSelectedChangeListener listener) {
        this.mChangeListener = listener;
    }

    public interface onSelectedChangeListener {
        void onSelect(int position);
    }


    /**
     * item Buttom 布局
     */
    private static class TabItem {
        View itemView;
        TextView textView;
        ImageView imageView;

        public TabItem(Context context, int position) {
            itemView = inflate(context, R.layout.layout_main_tab_item, null);
            textView = (TextView) itemView.findViewById(R.id.iv_main_tab_text);
            imageView = (ImageView) itemView.findViewById(R.id.iv_main_tab_icon);
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER;
            itemView.setLayoutParams(params);
            itemView.setId(position);
        }

        public void setOnClickListener(OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }

        public void setSelected(boolean select) {
            itemView.setSelected(select);
        }
    }


    /**
     * 内部类：底部导航栏Item属性
     */
    private static class BottomNavigationItem {
        int iconResId; //图标iconId
        String title; //item Title

        public BottomNavigationItem(int iconResId, String title) {
            this.iconResId = iconResId;
            this.title = title;
        }

        public int getIconResId() {
            return iconResId;
        }

        public String getTitle() {
            return title;
        }
    }
}
