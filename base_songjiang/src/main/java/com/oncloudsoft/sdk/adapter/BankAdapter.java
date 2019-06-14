package com.oncloudsoft.sdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.entity.bank.ZhList;
import com.oncloudsoft.sdk.view.BaseItemTextView;

import java.util.List;

/**
 * Created by Administrator on 2018/12/22.
 */

class BankAdapter extends BaseAdapter {
    private List<ZhList> zhList;
    private Context mContext;

    public BankAdapter(Context context, List<ZhList> zhList) {
        this.mContext = context;
        this.zhList = zhList;
    }


    @Override
    public int getCount() {
        return this.zhList.size();
    }

    @Override
    public Object getItem(int position) {
        return zhList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        BankAdapter.ViewHolder viewHolder = null;
        final ZhList zh = zhList.get(position);
        if (view == null) {
            viewHolder = new BankAdapter.ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_bank2, null);

            viewHolder.tv_cartnumber = (BaseItemTextView) view.findViewById(R.id.tv_cartnumber);
            viewHolder.tv_action_type = (BaseItemTextView) view.findViewById(R.id.tv_action_type);
            viewHolder.tv_currency = (BaseItemTextView) view.findViewById(R.id.tv_currency);
            viewHolder.tv_ye = (BaseItemTextView) view.findViewById(R.id.tv_ye);
            viewHolder.tv_kyye = (BaseItemTextView) view.findViewById(R.id.tv_kyye);
            viewHolder.view_line = (View) view.findViewById(R.id.view_line);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (BankAdapter.ViewHolder) view.getTag();
        }
        if (position != 0){
            viewHolder.view_line.setVisibility(View.GONE);
        }else{
            viewHolder.view_line.setVisibility(View.VISIBLE);
        }
        viewHolder.tv_cartnumber.setEndText(zh.getZh());
        viewHolder.tv_action_type.setEndText(zh.getZhzt());
        viewHolder.tv_currency.setEndText(zh.getBz());
        viewHolder.tv_ye.setEndText(zh.getYe());
        viewHolder.tv_kyye.setEndText(zh.getKyye());
        return view;
    }
    private class ViewHolder {
        private BaseItemTextView tv_cartnumber;
        private BaseItemTextView tv_action_type;
        private BaseItemTextView tv_currency;
        private BaseItemTextView tv_ye;
        private BaseItemTextView tv_kyye;
        private View view_line;
    }
}
