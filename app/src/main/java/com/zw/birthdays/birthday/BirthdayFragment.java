package com.zw.birthdays.birthday;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.zw.birthdays.R;
import com.zw.birthdays.bean.UserBean;
import com.zw.birthdays.utils.LunarCalendar;
import com.zw.birthdays.utils.TimeUtil;
import com.zw.birthdays.utils.ToastUtil;
import com.zw.birthdays.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/2/25.
 */

public class BirthdayFragment extends Fragment {

    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    List<UserBean> mUserBeans;
    private MyAdapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_birthday, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);
        getData();
    }

    public void getData() {
        if (mUserBeans == null) {
            mUserBeans = new Select().from(UserBean.class).execute();
        } else {
            mUserBeans.clear();
            List<UserBean> beans = new Select().from(UserBean.class).execute();
            ToastUtil.showToast(getContext(), beans.size() + "");
            mUserBeans.addAll(beans);
        }
        myAdapter.notifyDataSetChanged();
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_birthday, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            UserBean userBean = mUserBeans.get(position);
            holder.tv_name.setText(userBean.userName);
            holder.tv_calendar.setText(TimeUtil.getDate(userBean.birthdayTime));
            if (mUserBeans.get(position).headImgUrl != null) {
                holder.circleImageView.setImageURI(Uri.parse(userBean.headImgUrl));
            }
            String age = TimeUtil.getBabyAge(userBean.birthdayTime, userBean.isLunar);
            holder.tv_age.setText(age);
            if (userBean.isLunar) {
                holder.tv_calendar.setText(LunarCalendar.getLunarData(userBean.birthdayTime));
            } else {
                holder.tv_calendar.setText(TimeUtil.getDate(userBean.birthdayTime));
            }
            holder.tv_differDay.setText(TimeUtil.getDifferDay(userBean.birthdayTime, userBean.isLunar)+"");
        }

        @Override
        public int getItemCount() {
            return mUserBeans == null ? 0 : mUserBeans.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView tv_name;
        TextView tv_calendar;
        TextView tv_age;
        TextView tv_differDay;

        public MyHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.user_iv);
            tv_name = (TextView) itemView.findViewById(R.id.name);
            tv_calendar = (TextView) itemView.findViewById(R.id.calendar);
            tv_age = (TextView) itemView.findViewById(R.id.tv_age);
            tv_differDay = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }


}
