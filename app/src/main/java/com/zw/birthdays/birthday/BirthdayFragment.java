package com.zw.birthdays.birthday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zw.birthdays.R;
import com.zw.birthdays.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/2/25.
 */

public class BirthdayFragment extends Fragment {

    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_birthday,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new MyAdapter());
    }


    class MyAdapter extends RecyclerView.Adapter<MyHolder>{
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_birthday,null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 12;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
         CircleImageView circleImageView;
        TextView tv_name;
        TextView tv_calendar;
        TextView tv_lunch_calendar;

        public MyHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.user_iv);
            tv_name = (TextView) itemView.findViewById(R.id.name);
            tv_calendar = (TextView) itemView.findViewById(R.id.calendar);
            tv_lunch_calendar = (TextView) itemView.findViewById(R.id.lunar_calendar);
        }
    }


}
