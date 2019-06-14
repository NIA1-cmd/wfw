package com.oncloudsoft.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.ClueVerificationOperatingActivity;
import com.oncloudsoft.sdk.utils.GlidelUtil;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.sys.ScreenUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class MultiImageAdapter extends CommonAdapter<String> {
    //    private ShowPopuWindow mShowPopuWindow;
    final List<String> list = new ArrayList<>();
    private static final int TAKE_PICTURE = 0x000001;
    private boolean needDel;
    private int margin,count;


    private Context context;

    public MultiImageAdapter(Context context, List<String> datas, boolean needDel, int margin, int count) {
        super(context, R.layout.layout_add_image, datas);

        this.context = context;
        this.margin=margin;
        list.add("拍摄");
        list.add("相册选择");
        this.needDel = needDel;
        this.count = count;

    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, final int position) {


        ImageView addImg = viewHolder.getView(R.id.add_image);
        ImageView delImg = viewHolder.getView(R.id.del_image);


        int gap = 4;//每张图片之间的间隙

        ViewGroup.LayoutParams layoutParams = addImg.getLayoutParams();

        ViewGroup.MarginLayoutParams marginParams;

        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
        } else {
            //不存在时创建一个新的参数
            //基于View本身原有的布局参数对象
            marginParams = new ViewGroup.MarginLayoutParams(layoutParams);
        }


        int clone = position % count;

        marginParams.setMargins(clone == 0 ? gap * 2 : gap, gap, clone == 3 ? gap * 2 : gap, gap);

//        marginParams.setMargins();

        int screenWidth = ScreenUtil.getDisplayWidth();


        int Margin = ScreenUtil.dip2px(margin);//布局中设置的外边距

        int allImageWidth = screenWidth - (((count * 2) + 2) * gap) - Margin;
        int oneImageWidth = allImageWidth / count;
        layoutParams.height = layoutParams.width = oneImageWidth;
        addImg.setLayoutParams(layoutParams);


        if (TextUtils.isEmpty(item)) {
            delImg.setVisibility(View.GONE);
            Glide.with(mContext).load(R.drawable.btn_add_img).into(addImg);
            addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加图片
                   if (mContext instanceof ClueVerificationOperatingActivity) {
                        ((ClueVerificationOperatingActivity) mContext).selectPictrue();

                    }
                   /* PopupWindowUtils.getInstance().showSelectPopuWindow((ImageCollectionActivity)mContext, new PopupWindowUtils.onSelectLinstener() {
                        @Override
                        public void OnItemClick(int whitch) {
                            Log.i("info","....whitch="+whitch);
                            switch (whitch){
                                case 0:
                                    photo();
                                    break;
                                case 1:
                                    ((ImageCollectionActivity) mContext).selectPictrue();
                                    break;
                            }
                        }
                    },list);*/


                    /*if (mContext instanceof ImageCollectionActivity) {
                        ((ImageCollectionActivity) mContext).selectPictrue();
                    }*/
                }
            });
        } else {
            delImg.setVisibility(View.VISIBLE);


            GlidelUtil.loadImag((Activity) context, "file://" + item, addImg);

//            Glide.with(mContext).load().apply(new RequestOptions().fitCenter().placeholder(R.drawable.nim_image_default).error(R.drawable.nim_image_default)).into();
            addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查看大图
                }
            });
            delImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatas.remove(position);
                    if (!"".equals(mDatas.get(mDatas.size() - 1))) {
                        mDatas.add("");
                    }
                    notifyDataSetChanged();
//
//
//                    if (mContext instanceof ImageCollectionActivity) {
//                        ((ImageCollectionActivity) mContext).setGridViewHeight(mDatas.size());
//                    }else if(mContext instanceof WenShuActivity){
//                        ((WenShuActivity) mContext).setGridViewHeight(mDatas.size());
//                    }


                }
            });
        }
        if (!needDel) {
            delImg.setVisibility(View.GONE);
        }
    }

//    public static void setMargins(View v, int l, int t, int r, int b) {
//
//
//        ViewGroup.LayoutParams
//        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            p.setMargins(l, t, r, b);
//            v.requestLayout();
//        }
//    }

}
