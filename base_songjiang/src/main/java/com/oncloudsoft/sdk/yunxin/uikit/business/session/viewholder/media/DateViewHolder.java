package com.oncloudsoft.sdk.yunxin.uikit.business.session.viewholder.media;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.oncloudsoft.sdk.R;

/**
 * Created by winnie on 2017/9/18.
 */

public class DateViewHolder extends RecyclerView.ViewHolder {

    public TextView dateText;

    public DateViewHolder(View itemView) {
        super(itemView);
        dateText = (TextView) itemView.findViewById(R.id.date_tip);
    }
}
