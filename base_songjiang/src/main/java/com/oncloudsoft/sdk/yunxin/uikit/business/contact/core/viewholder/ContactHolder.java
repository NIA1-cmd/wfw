package com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.app.MyApplication;
import com.oncloudsoft.sdk.db.DBContext;
import com.oncloudsoft.sdk.db.DBData;
import com.oncloudsoft.sdk.utils.DateUtil;
import com.oncloudsoft.sdk.yunxin.uikit.api.NimUIKit;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.ContactItem;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.model.ContactDataAdapter;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.model.IContact;
import com.oncloudsoft.sdk.yunxin.uikit.business.custom.TeamExpansionInfo;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.drop.DropFake;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.drop.DropManager;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.imageview.HeadImageView;

import java.util.Date;
import java.util.List;

public class ContactHolder extends AbsContactViewHolder<ContactItem> {

    protected HeadImageView head;

    protected TextView name;

    protected TextView desc;

    protected RelativeLayout headLayout;

    private TextView mTvApply;
    private TextView mTvExecuted;
    private TextView tvName;
    private DropFake mDropFake;
    private TextView mData;
    private String contactId;
    private View vLIne;
    private LinearLayout llParent;
    private DBContext dbContext;
    private String unreadCount;
    private ImageView ivNew;

    @Override
    public void refresh(ContactDataAdapter adapter, int position, final ContactItem item) {

        if (position == 0) {
            vLIne.setVisibility(View.VISIBLE);
        } else {
            vLIne.setVisibility(View.GONE);
        }
        // contact info
        final IContact contact = item.getContact();
        contactId = contact.getContactId();

        this.dbContext =  MyApplication.dbContext;

        List<DBData> dbDatas = dbContext.whereQuery("*", "lastMessage","id", contactId);
        if (dbDatas.size() != 0) {
            try {
                desc.setVisibility(View.VISIBLE);
                tvName.setVisibility(View.VISIBLE);
                String lastName = dbDatas.get(0).getLastName();
                if (lastName.equals("")){
                    lastName = "系统";
                }
                tvName.setText(lastName+":");
                desc.setText(dbDatas.get(0).getLastMessage());
                mData.setText(DateUtil.showDate(dbDatas.get(0).getLastTime()));
                int count = dbDatas.get(0).getUnreadCount();
                int excludeNumber = dbDatas.get(0).getExcludeNumber();
                count = count - excludeNumber;
                if (count != 0) {
                    unreadCount = count+"";
                    if (count > 99) {
                        unreadCount = "99+";
                    }
                    mDropFake.setVisibility(View.VISIBLE);
                    mDropFake.setText(unreadCount);
                } else {
                    mDropFake.setText("");
                    mDropFake.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mDropFake.setText("");
                mDropFake.setVisibility(View.INVISIBLE);
                desc.setVisibility(View.GONE);
                tvName.setVisibility(View.GONE);
                mData.setText("");
            }
        } else {
            mDropFake.setText("");
            mDropFake.setVisibility(View.INVISIBLE);
            desc.setVisibility(View.GONE);
            tvName.setVisibility(View.GONE);
            mData.setText("");
        }
        /*if (contact.getContactType() == IContact.Type.Friend) {
            head.loadBuddyAvatar(contactId);
        } else {
            Team team = NimUIKit.getTeamProvider().getTeamById(contactId);
            head.loadTeamIconByTeam(team);
        }*/
        name.setText(contact.getDisplayName());

        Team t = NimUIKit.getTeamProvider().getTeamById(contactId);
        long time = t.getCreateTime();
        Date date = new Date();
        long newTime = date.getTime();
        long time2 = newTime-time;
        ivNew.setVisibility(time2 >84600000?View.GONE:View.VISIBLE);
        //TODO 从后台切换到前台  NullPoint
        TeamExpansionInfo.TeamExpansionData expansionInfo = TeamExpansionInfo.getExpansionInfo(t.getExtServer());
        mTvApply.setText(context.getResources().getString(R.string.apply_people_) + expansionInfo.getSqr());
        mTvExecuted.setText(context.getResources().getString(R.string.executed_people_) + expansionInfo.getBzxr());
        /*headLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact.getContactType() == IContact.Type.Friend) {
                    if (NimUIKitImpl.getContactEventListener() != null) {
                        NimUIKitImpl.getContactEventListener().onAvatarClick(context, contactId);
                    }
                }
            }
        });*/


       /* Random random = new Random();

        int i = random.nextInt(3);

        if (i==2){//需要显红点

            int num = random.nextInt(10);
            if (num==0){
                num = random.nextInt(10);
                mDropFake.setText(num + "");

            }
        }*/


        this.mDropFake.setTouchListener(new DropFake.ITouchListener() {
            @Override
            public void onDown() {
//                DropManager.getInstance().setCurrentId(recent);
                DropManager.getInstance().down(mDropFake, mDropFake.getText());
            }

            @Override
            public void onMove(float curX, float curY) {
                DropManager.getInstance().move(curX, curY);
            }

            @Override
            public void onUp() {
                DropManager.getInstance().up();
            }
        });

       /* desc.setText("李法官您好，我的案子进行的怎么样了?");
        mData.setText("12:00");*/
        // query result
//        desc.setVisibility(View.GONE);
        /*
        TextQuery query = adapter.getQuery();
        HitInfo hitInfo = query != null ? ContactSearch.hitInfo(contact, query) : null;
        if (hitInfo != null && !hitInfo.text.equals(contact.getDisplayName())) {
            desc.setVisibility(View.VISIBLE);
        } else {
            desc.setVisibility(View.GONE);
        }
        */
    }

    @Override
    public View inflate(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.nim_contacts_item, null);
        //headLayout = (RelativeLayout) view.findViewById(R.id.head_layout);
        ivNew = (ImageView) view.findViewById(R.id.iv_new);

        //head = (HeadImageView) view.findViewById(R.id.contacts_item_head);
        name = (TextView) view.findViewById(R.id.contacts_item_name);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        desc = (TextView) view.findViewById(R.id.contacts_item_desc);
        mTvApply = (TextView) view.findViewById(R.id.tv_apply);
        mTvExecuted = (TextView) view.findViewById(R.id.tv_executed);
        mDropFake = view.findViewById(R.id.unread_number_tip);
        mData = view.findViewById(R.id.contacts_item_time);
        vLIne = view.findViewById(R.id.v_lien);
        llParent = view.findViewById(R.id.contacts_item_name_layout);
        //ShadowDrawable.setPublicShadow(llParent);

        return view;
    }
}
