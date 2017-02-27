package com.zw.birthdays.base;

import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zw.birthdays.R;

public class BaseTitleActivity extends AppCompatActivity {

    private View mTitleBarView;
    /**
     * 标题
     */
    private TextView mTitleView;

    /**
     * 正在加载动画控件
     */
    private View mLoadingView;

    /**
     * 网络加载失败View
     */
    private View mLoadFailView;

    /**
     * 空内容提示View（文本提示）
     */
    private TextView mEmptyViewTV;
    /**
     * 空内容提示View（图片提示）
     */
    private ImageView mEmptyViewIV;


    /**
     * 右上角的菜单View
     */
    private View mMenuView1;
    private TextView mTextMenuView1;
    private ImageView mImageMenuView1;

    /**
     * 允许添加第二个菜单
     */
    private TextView mMenuView2;

    private ImageView mBackView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);
        if (layoutResID > 0) {
            getLayoutInflater().inflate(layoutResID, (ViewGroup) findViewById(R.id
                    .fragmentContainer));
        }
        initView();
    }

    private void initView() {
        mTitleBarView = findViewById(R.id.rl_activity_base_titlebar);

        mBackView = (ImageView) findViewById(R.id.iv_title_back);
        assert mBackView != null;
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleView = (TextView) findViewById(R.id.tv_title);
        assert mTitleView != null;
        mTitleView.setVisibility(View.GONE);

        mMenuView1 = findViewById(R.id.fl_title_menu_1);
        mTextMenuView1 = (TextView) findViewById(R.id.tv_title_menu1);
        mImageMenuView1 = (ImageView) findViewById(R.id.iv_title_menu1);
        mMenuView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuClick();
            }
        });
        mMenuView1.setVisibility(View.GONE);

        mMenuView2 = (TextView) findViewById(R.id.tv_title_menu2);
        mMenuView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenu2Click();
            }
        });

        mLoadingView = findViewById(R.id.loading_view);
        mLoadFailView = findViewById(R.id.iv_page_load_fail);
        mLoadFailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReload();
            }
        });
        mEmptyViewTV = (TextView) findViewById(R.id.tv_empty);
        mEmptyViewIV = (ImageView) findViewById(R.id.iv_empty);
        //临时替换title颜色
        setTestTitleBg();
    }

    /**
     * 设置title标题文字
     *
     * @param title 文字
     */
    public void setTitleText(String title) {
        mTitleView.setVisibility(View.VISIBLE);

        mTitleView.setText(title);
    }

    public void setLeftBg(int resid) {
        mBackView.setBackgroundResource(resid);
    }

    public void seMenuBg(int resid) {
        mMenuView1.setBackgroundResource(resid);
    }


    public void setTestTitleBg() {
        mTitleBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }


    protected void setNoTitle() {
        mTitleBarView.setVisibility(View.GONE);
    }

    /**
     * 设置title标题文字
     *
     * @param titleResId resid
     */
    public void setTitleText(int titleResId) {
        setTitleText(getString(titleResId));
    }


    protected void setNoBack() {
        mBackView.setVisibility(View.GONE);
    }

    /**
     * 设置菜单文本
     *
     * @param menuText 文本
     */
    protected void setMenuText(String menuText) {
        mMenuView1.setVisibility(View.VISIBLE);
        mTextMenuView1.setText(menuText);
        mTextMenuView1.setVisibility(View.VISIBLE);
        mImageMenuView1.setVisibility(View.GONE);
    }

    protected void setMenu1Image(int resId) {
        mMenuView1.setVisibility(View.VISIBLE);
        mTextMenuView1.setVisibility(View.GONE);
        mImageMenuView1.setVisibility(View.VISIBLE);
        mImageMenuView1.setImageResource(resId);
    }

    protected void hideMenuView() {
        mMenuView1.setVisibility(View.GONE);
    }

    /**
     * 显示菜单是否可用
     *
     * @param enable true:enable;false:disable
     */
    protected void setMenuEnable(boolean enable) {
        mMenuView1.setEnabled(enable);
    }

    /**
     * 设置菜单文本
     *
     * @param menuText 文本
     */
    protected void setMenu2Text(String menuText) {
        mMenuView2.setText(menuText);
        mMenuView2.setVisibility(View.VISIBLE);
    }

    /**
     * 显示菜单是否可用
     *
     * @param enable true:enable;false:disable
     */
    protected void setMenu2Enable(boolean enable) {
        mMenuView2.setEnabled(enable);
    }

    /**
     * 显示正在加载动画
     */
    protected void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏正在加载动画
     */
    protected void dismissLoading() {
        mLoadingView.setVisibility(View.GONE);
    }

    /**
     * 显示或隐藏正在加载动画
     *
     * @param show 显示或隐藏
     */
    protected void showLoading(boolean show) {
        mLoadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示或隐藏内容为空视图
     *
     * @param show    显示或隐藏
     * @param message 要显示的内容（文字）
     */
    protected void showEmpty(boolean show, String message) {
        mEmptyViewTV.setVisibility(show ? View.VISIBLE : View.GONE);
        mEmptyViewTV.setText(message);
    }

    /**
     * 显示或隐藏内容为空视图
     *
     * @param show  显示或隐藏
     * @param resId 要显示的内容(图片id)
     */
    protected void showEmpty(boolean show, int resId) {
        if (show) {
            mEmptyViewIV.setVisibility(View.VISIBLE);
            mEmptyViewIV.setImageResource(resId);
        } else {
            mEmptyViewIV.setVisibility(View.GONE);
            mEmptyViewIV.setImageResource(0);
        }
    }

    /**
     * 显示或隐藏网络失败的视图
     *
     * @param show 显示或隐藏
     */
    protected void showLoadFail(boolean show) {
        mLoadFailView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 重新加载
     */
    protected void onReload() {

    }

    /**
     * title 菜单点击
     */
    protected void onMenuClick() {

    }

    protected void onMenu2Click() {

    }
}
