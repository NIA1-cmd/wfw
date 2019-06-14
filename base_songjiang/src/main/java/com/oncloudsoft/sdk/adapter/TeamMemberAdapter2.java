package com.oncloudsoft.sdk.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.app.MyApplication;
import com.oncloudsoft.sdk.db.DBContext;
import com.oncloudsoft.sdk.db.DBData;
import com.oncloudsoft.sdk.entity.MemberData;

import java.util.List;

/**
 * Created by Administrator on 2018/12/19.
 */

public class TeamMemberAdapter2 extends BaseAdapter{
    private List<MemberData> list;
    private Context mContext;
    private String name;
    private DBContext dbContext;

    public TeamMemberAdapter2(List<MemberData> list, Context context) {
        this.list = list;
        mContext = context;
        this.dbContext = MyApplication.dbContext;
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
        ViewHolder viewHolder;
        final MemberData groupDetailData = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_team_member, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.touxiang = (ImageView) view.findViewById(R.id.touxiang);
            viewHolder.line = (View) view.findViewById(R.id.line);
            viewHolder.tvJs = (TextView) view.findViewById(R.id.tv_js);
            viewHolder.red_view = (View) view.findViewById(R.id.red_view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (position == list.size() - 1) {
            viewHolder.line.setVisibility(View.GONE);
            //viewHolder.line1.setVisibility(View.VISIBLE);
        }else{
            viewHolder.line.setVisibility(View.VISIBLE);
        }

        int bitmap = 0;
        Drawable drawable = null;
        switch (groupDetailData.getCyjs()) {//成员角色 0 法官 1 申请人 2 被执行人 3 代理人(申) 4 代理人(被)
            case "0":
                name = "法官";
                bitmap = R.drawable.icon_team_fg;
                drawable = mContext.getResources().getDrawable(R.drawable.team_fg_radius);
                break;
            case "1":
                name = "申请人";
                bitmap = R.drawable.icon_team_sqr;
                drawable = mContext.getResources().getDrawable(R.drawable.team_sqr_radius);
                break;
            case "2":
                name = "被执行人";
                bitmap = R.drawable.icon_team_bzxr;
                drawable = mContext.getResources().getDrawable(R.drawable.team_bzxr_radius);
                break;
            case "3":
                name = "代理人(申)";
                bitmap = R.drawable.icon_team_sqr;
                drawable = mContext.getResources().getDrawable(R.drawable.team_sqr_radius);
                break;
            case "4":
                name = "代理人(被)";
                bitmap = R.drawable.icon_team_bzxr;
                drawable = mContext.getResources().getDrawable(R.drawable.team_bzxr_radius);
                break;
        }
        try {
            List<DBData> b = dbContext.whereQuery("*", "lastMessage","id", groupDetailData.getAccid());
            if (b!=null&&b.size()>0) {
                int i = b.get(0).getUnreadCount();
                if (i > 0) {
                    viewHolder.red_view.setVisibility(View.VISIBLE);
                }
                else {
                    viewHolder.red_view.setVisibility(View.GONE);
                }
            }else{
                viewHolder.red_view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewHolder.red_view.setVisibility(View.GONE);
        }
        viewHolder.tvTitle.setText(/*name + */groupDetailData.getCymc());
        viewHolder.touxiang.setImageResource(bitmap);
        viewHolder.tvJs.setText(groupDetailData.getJsmc());
        viewHolder.tvJs.setBackground(drawable);
        return view;

    }



    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<MemberData> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        private TextView tvTitle;
        private TextView tvJs;
        private ImageView touxiang;
        private View line;
        private View red_view;
    }
}
