package com.oncloudsoft.sdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.entity.GroupDetailData;

import java.util.List;

/**
 * Created by Administrator on 2018/12/19.
 */

public class GroupAdapter extends BaseAdapter{
    private List<GroupDetailData> list;
    private Context mContext;
    private int bitmap;
    private ChangeState c;

    public GroupAdapter(List<GroupDetailData> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final GroupDetailData groupDetailData = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.social_item, null);

            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.touxiang = (ImageView) view.findViewById(R.id.touxiang);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            viewHolder.line = (View) view.findViewById(R.id.line);
            viewHolder.line1 = (View) view.findViewById(R.id.line1);

            viewHolder.layout = view.findViewById(R.id.social_item_ll);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvLetter.setOnClickListener(null);
        viewHolder.checkbox.setVisibility(View.VISIBLE);
        viewHolder.layout.setClickable(true);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = list.get(position).isCheck();
                checkSetting(position, !isChecked);
            }
        });

        if (position == list.size() - 1) {
            viewHolder.line.setVisibility(View.GONE);
            viewHolder.line1.setVisibility(View.VISIBLE);
        }
        viewHolder.tvTitle.setText(groupDetailData.getFgmc());
        viewHolder.touxiang.setImageResource(R.drawable.icon_team_fg);
        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkSetting(position, isChecked);
            }
        });
        viewHolder.checkbox.setChecked(groupDetailData.isCheck());
        return view;

    }

    public void setListener(ChangeState c) {
        this.c = c;
    }
    private void checkSetting(int position, boolean isChecked) {
        int count = 0;
        String str = "";
        list.get(position).setCheck(isChecked);

        for (GroupDetailData b : list) {
            if (b.isCheck()) {
                count++;
                str += "," + b.getFgbs();
            }
        }
        c.getMember(str);
        notifyDataSetChanged();
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<GroupDetailData> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    public interface ChangeState {
        void getMember(String member);
    }

    private class ViewHolder {
        private TextView tvLetter;
        private TextView tvTitle;
        private ImageView touxiang;
        private View line;
        private View line1;
        private CheckBox checkbox;
        private View layout;
    }
}
