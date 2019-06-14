package com.oncloudsoft.sdk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.entity.bank.BankData;
import com.oncloudsoft.sdk.entity.bank.Xxxq;
import com.oncloudsoft.sdk.view.BaseItemTextView;
import com.oncloudsoft.sdk.view.ShadowDrawable;
import com.oncloudsoft.sdk.widget.MyListView;

import java.util.List;

/**
 * Created by Administrator on 2018/12/22.
 */

public class BankDetaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Xxxq> list;
    private BankData bankData;
    private static final int TYPE_COMPLETE = 0;
    private static final int TYPE_ONE_PEOPLE = 1;
    public BankDetaleAdapter(Context context, BankData bankData) {
        this.context = context;
        this.bankData = bankData;
        this.list = bankData.getXxxq();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_bank, parent, false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_bank_head, parent, false);
            return new HeadViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tv_title.setText(list.get(position-1).getYhmc());
            viewHolder.listView.setAdapter(new BankAdapter(context,list.get(position-1).getZhList()));
            ShadowDrawable.setPublicShadow(viewHolder.linearLayout);
        }else{
            HeadViewHolder viewHolder = (HeadViewHolder) holder;
            viewHolder.tv_bzxr.setEndText(bankData.getJbxx().getBzxr());
            viewHolder.tv_number.setEndText(bankData.getJbxx().getAh());
            viewHolder.tv_zjhm.setEndText(bankData.getJbxx().getDsrsfz());
            ShadowDrawable.setPublicShadow(viewHolder.linearLayout);
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_COMPLETE;
        }else{
            return TYPE_ONE_PEOPLE;
        }
    }
    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R2.id.listView)
        MyListView listView;
        //@BindView(R2.id.tv_title)
        TextView tv_title;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(itemView);
            listView = itemView.findViewById(R.id.listView);
            tv_title = itemView.findViewById(R.id.tv_title);
            linearLayout = itemView.findViewById(R.id.linear);
        }
    }
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R2.id.listView)
        BaseItemTextView tv_number;
        //@BindView(R2.id.tv_title)
        BaseItemTextView tv_bzxr;
        BaseItemTextView tv_zjhm;
        LinearLayout linearLayout;
        public HeadViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(itemView);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_bzxr = itemView.findViewById(R.id.tv_bzxr);
            tv_zjhm = itemView.findViewById(R.id.tv_zjhm);
            linearLayout = itemView.findViewById(R.id.linear);
        }
    }
}
