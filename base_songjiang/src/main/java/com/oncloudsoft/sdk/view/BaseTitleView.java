package com.oncloudsoft.sdk.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;

/**
 * 统一的标题View
 */
public class BaseTitleView extends RelativeLayout implements View.OnClickListener {
    private TextView mTvTitle;
    private TextView mTvLeft;
    private TextView mTvRight;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private RelativeLayout mRlParent;
    private int top = 0;
//    private View mLineView;

    public BaseTitleView(Context context) {
        this(context, null);
    }

    public BaseTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attributeSet) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_title_layout, this);

        mTvTitle = view.findViewById(R.id.tv_title);
        mTvLeft = view.findViewById(R.id.tv_left);
        mTvRight = view.findViewById(R.id.tv_right);
        mIvLeft = view.findViewById(R.id.iv_left);
        mIvRight = view.findViewById(R.id.iv_right);
        mRlParent = view.findViewById(R.id.rl_parent);

//        mLineView = view.findViewById(R2.id.ic_line01);

        mTvLeft.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mRlParent.setOnClickListener(this);

        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.BaseTitleView);

        /*init text and image widget*/
        initLeftTextView(typeArray);
        initLeftImageView(typeArray);
        initRightTextView(typeArray);
        initRightImageView(typeArray);
        initType(typeArray);
        initTitleView(typeArray);
        changePos();
        typeArray.recycle();

    }


    public void changePos(){
        changePosition();

    }


    public void setTitleType(@TitleType int type) {
        if (type == 1) {
            mIvLeft.setImageResource(R.drawable.back);
            setTitleColor(getResources().getColor(R.color.white));

            mRlParent.setBackgroundColor(getResources().getColor(R.color.maincolor_01));
        } else if (type == 2) {
            mTvRight.setTextColor(getResources().getColor(R.color.black));
            mIvLeft.setImageResource(R.drawable.back_black);
            setTitleColor(getResources().getColor(R.color.textcolor_01));
            mRlParent.setBackgroundColor(getResources().getColor(R.color.white));
        }
        else if (type == 3) {
            mIvLeft.setImageResource(R.drawable.back);
            setTitleColor(getResources().getColor(R.color.white));
            mRlParent.setBackgroundColor(getResources().getColor(R.color.message_bc));

        }
    }


    private void initType(TypedArray typeArray) {
        int type = typeArray.getInt(R.styleable.BaseTitleView_title_type, 1);//默认蓝色背景
        setTitleType(type);
    }

//    private void initLine(TypedArray typeArray) {
//
//
////        int line = typeArray.getInt(R.styleable.BaseTitleView_lineVisibility, 0);//默认显示
//
////        mLineView.setVisibility(line == 0 ? VISIBLE : GONE);
//
//    }

    private void initRightImageView(TypedArray typeArray) {

        int rightImgAttr = typeArray.getResourceId(R.styleable.BaseTitleView_rightImage, 0);

        if (rightImgAttr != 0) {
            mIvRight.setImageResource(rightImgAttr);
            setRightImageViewVis(VISIBLE);
        } else {
            setRightImageViewVis(GONE);
        }


    }

    private void initRightTextView(TypedArray typeArray) {
        int rightText = typeArray.getResourceId(R.styleable.BaseTitleView_rightText, 0);


        CharSequence charSequence = rightText > 0 ? typeArray.getResources().getText(rightText) : typeArray.getString(R.styleable.BaseTitleView_rightText);

        mTvRight.setVisibility(charSequence != null && charSequence.length() > 0 ? VISIBLE : GONE);
        setRightText(charSequence);

        if (typeArray.getInt(R.styleable.BaseTitleView_right_tv_color, 0) == 1) {
            mTvRight.setTextColor(getResources().getColor(R.color.white));
        }
        ;
    }

    private void initTitleView(TypedArray typeArray) {

//        int titleColor = typeArray.getResourceId(R.styleable.BaseTitleView_titleColor, 0);
//
//        if (titleColor != 0) {
//            setTitleColor(getResources().getColor(titleColor));
//        }else {
//            setTitleColor(getResources().getColor(R.color.white));
//        }


        int titleText = typeArray.getResourceId(R.styleable.BaseTitleView_titleText, 0);
        CharSequence charSequence = titleText > 0 ? typeArray.getResources().getText(titleText) : typeArray.getString(R.styleable.BaseTitleView_titleText);
        setTitleText(charSequence);

    }

    public void setTitleColor(@ColorInt int color) {
        mTvTitle.setTextColor(color);
    }


    private void initLeftImageView(TypedArray typeArray) {

        int leftImgAttr = typeArray.getResourceId(R.styleable.BaseTitleView_leftImage, 0);
        mIvLeft.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_white_back));//设置默认图标
        if (leftImgAttr != 0) {
            mIvLeft.setImageResource(leftImgAttr);
        }
        int leftVis = typeArray.getInt(R.styleable.BaseTitleView_backIconVisibility, 0);//默认显示


        mIvLeft.setVisibility(leftVis == 0 ? VISIBLE : GONE);


    }

    private void initLeftTextView(TypedArray typeArray) {
        int leftText = typeArray.getResourceId(R.styleable.BaseTitleView_leftText, 0);
        CharSequence charSequence = leftText > 0 ? typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable.BaseTitleView_leftText);

        mTvLeft.setVisibility(charSequence != null && charSequence.length() > 0 ? VISIBLE : GONE);

        mTvLeft.setText(charSequence);
    }

    private void changePosition() {
        if (mTvLeft.getVisibility() == VISIBLE) {
            if (mIvLeft.getVisibility() == VISIBLE) {


                RelativeLayout.LayoutParams leftTvLp = new RelativeLayout.LayoutParams(mTvLeft.getLayoutParams());
                leftTvLp.setMargins(10, 0, 0, 0);
                mTvLeft.setLayoutParams(leftTvLp);

                RelativeLayout.LayoutParams leftIvLp = new RelativeLayout.LayoutParams(mIvLeft.getLayoutParams());
                leftIvLp.setMargins(40, top, 0, 0);
                leftIvLp.addRule(CENTER_VERTICAL);

                mIvLeft.setLayoutParams(leftIvLp);

            } else {
                RelativeLayout.LayoutParams leftTvLp = new RelativeLayout.LayoutParams(mTvLeft.getLayoutParams());
                leftTvLp.setMargins(40, 0, 0, 0);
                mTvLeft.setLayoutParams(leftTvLp);

                RelativeLayout.LayoutParams leftIvLp = new RelativeLayout.LayoutParams(mIvLeft.getLayoutParams());
                leftIvLp.setMargins(0, top, 0, 0);
                leftIvLp.addRule(CENTER_VERTICAL);

                mIvLeft.setLayoutParams(leftIvLp);


            }
        } else {
            if (mIvLeft.getVisibility() == VISIBLE) {


                RelativeLayout.LayoutParams leftTvLp = new RelativeLayout.LayoutParams(mTvLeft.getLayoutParams());
                leftTvLp.setMargins(0, 0, 0, 0);
                mTvLeft.setLayoutParams(leftTvLp);

                RelativeLayout.LayoutParams leftIvLp = new RelativeLayout.LayoutParams(mIvLeft.getLayoutParams());
                leftIvLp.setMargins(40, top, 0, 0);
                leftIvLp.addRule(CENTER_VERTICAL);
                mIvLeft.setLayoutParams(leftIvLp);


            } else {
                RelativeLayout.LayoutParams leftTvLp = new RelativeLayout.LayoutParams(mTvLeft.getLayoutParams());
                leftTvLp.setMargins(0, 0, 0, 0);
                mTvLeft.setLayoutParams(leftTvLp);

                RelativeLayout.LayoutParams leftIvLp = new RelativeLayout.LayoutParams(mIvLeft.getLayoutParams());
                leftIvLp.setMargins(0, top, 0, 0);
                leftIvLp.addRule(CENTER_VERTICAL);
                mIvLeft.setLayoutParams(leftIvLp);

                RelativeLayout.LayoutParams titleLp = new RelativeLayout.LayoutParams(mTvTitle.getLayoutParams());
                titleLp.setMargins(40, 0, 0, 0);
                mTvTitle.setLayoutParams(titleLp);
            }
        }


        if (mTvRight.getVisibility() == VISIBLE) {
            if (mIvRight.getVisibility() == VISIBLE) {


                RelativeLayout.LayoutParams rightTvLp = new RelativeLayout.LayoutParams(mTvRight.getLayoutParams());
                rightTvLp.setMargins(0, 0, 10, 0);
                mTvRight.setLayoutParams(rightTvLp);

                RelativeLayout.LayoutParams rightIvLp = new RelativeLayout.LayoutParams(mIvRight.getLayoutParams());
                rightIvLp.setMargins(0, 0, 40, 0);
                rightIvLp.addRule(CENTER_VERTICAL);
                mIvRight.setLayoutParams(rightIvLp);

            } else {
                RelativeLayout.LayoutParams rightTvLp = new RelativeLayout.LayoutParams(mTvRight.getLayoutParams());
                rightTvLp.setMargins(0, 0, 40, 0);


                //需要将mTVRight 放在布局右边
                rightTvLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mTvRight.setLayoutParams(rightTvLp);


                RelativeLayout.LayoutParams rightIvLp = new RelativeLayout.LayoutParams(mIvRight.getLayoutParams());
                rightIvLp.setMargins(0, 0, 0, 0);
                rightIvLp.addRule(CENTER_VERTICAL);

                mIvRight.setLayoutParams(rightIvLp);


            }
        } else {
            if (mIvRight.getVisibility() == VISIBLE) {
                Log.i("info", "...........right");
                RelativeLayout.LayoutParams rightTvLp = new RelativeLayout.LayoutParams(mTvRight.getLayoutParams());
                rightTvLp.setMargins(0, 0, 40, 0);
                mTvRight.setLayoutParams(rightTvLp);

                RelativeLayout.LayoutParams rightIvLp = new RelativeLayout.LayoutParams(mIvRight.getLayoutParams());
                rightIvLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                rightIvLp.addRule(CENTER_VERTICAL);
                rightIvLp.setMargins(0, top, 40, 0);
                mIvRight.setLayoutParams(rightIvLp);
            } else {
                RelativeLayout.LayoutParams rightTvLp = new RelativeLayout.LayoutParams(mTvRight.getLayoutParams());
                rightTvLp.setMargins(0, 0, 0, 0);
                mTvRight.setLayoutParams(rightTvLp);

                RelativeLayout.LayoutParams rightIvLp = new RelativeLayout.LayoutParams(mIvRight.getLayoutParams());
                rightIvLp.setMargins(0, 0, 0, 0);
                rightIvLp.addRule(CENTER_VERTICAL);

                mIvRight.setLayoutParams(rightIvLp);
            }
        }


//        List<View> viewList = new ArrayList<>();
//        viewList.add(mTvTitle);
//        viewList.add(mIvLeft);
//        viewList.add(mTvLeft);
//        viewList.add(mTvRight);
//        viewList.add(mIvRight);
//        for (View item : viewList) {
//            if (item.getVisibility() == VISIBLE) {
//
//
//                ViewGroup.LayoutParams layoutParams = mRlParent.getLayoutParams();
//
//                layoutParams.ad
//                mRlParent.getLayoutParams().addRule(RelativeLayout.BELOW, R2.id.igame_common_index_fragment_find_film_item_iv_icon);
//
//            }
//        }


    }

    @IntDef({VISIBLE, INVISIBLE, GONE})
    @interface Visibility {

    }

    public static final int WHITE = 2;
    public static final int BLUE = 1;
    public static final int  BULE2 = 3;

    @IntDef({WHITE, BLUE,BULE2})
    @interface TitleType {
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_left) {
            if (mOnLeftTextClickListener != null) {
                mOnLeftTextClickListener.onClick();
            }


        }
        else if (i == R.id.tv_right) {
            if (mOnRightTextClickListener != null) {
                mOnRightTextClickListener.onClick();
            }

        }
        else if (i == R.id.iv_left) {
            if (mOnLeftImageClickListener != null) {
                mOnLeftImageClickListener.onClick();
            }

        }
        else if (i == R.id.iv_right) {
            if (mOnRightImageClickListener != null) {
                mOnRightImageClickListener.onClick();
            }

        }
        else if (i == R.id.rl_parent) {
            if (mOnLabClickListener != null) {
                mOnLabClickListener.onClick();
            }

        }
    }

    public void setOnRightTextClickListener(onRightTextClickListener onRightTextClickListener) {
        mOnRightTextClickListener = onRightTextClickListener;
    }

    private onRightTextClickListener mOnRightTextClickListener;


    /**
     * 设置标题文字
     *
     * @param text
     */
    public void setTitleText(CharSequence text) {
        mTvTitle.setText(text);
    }

    /**
     * 设置右边icon是否可见
     */
    public void setRightImageViewVis(@Visibility int visibility) {
        mIvRight.setVisibility(visibility);
    }

    /**
     * 设置右边文本内容
     */
    private void setRightText(CharSequence charSequence) {
        mTvRight.setText(charSequence);

    }

    /**
     * 设置右边文本内容
     */
    public void setRightTexts(CharSequence charSequence) {
        setRightText(charSequence);
        setRightTextVisibility(VISIBLE);

    }
    public TextView getmTvRight(){
        return mTvRight;
    }
    /**
     * 设置右边文本内容
     */
    public void setRightImg(Drawable drawable) {
        mIvRight.setImageDrawable(drawable);
        mIvRight.setVisibility(VISIBLE);

    }
    /**
     * 设置右边文本颜色
     */
    public void setRightTextColor(int color) {
        mTvRight.setTextColor(color);
    }



    /**
     * 设置右边图片高度
     */
    public void setRightWH(int wh) {
        RelativeLayout.LayoutParams rightIvLp = new RelativeLayout.LayoutParams(wh, wh);
        rightIvLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightIvLp.addRule(CENTER_VERTICAL);
        rightIvLp.setMargins(0, top, 20, 0);
        mIvRight.setLayoutParams(rightIvLp);
    }

    /**
     * 设置右边图片高度
     */
    public ImageView getRightImgView() {
        return mIvRight;
    }

    /**
     * 设置右边图片高度
     */
    public void setLeftImg(Drawable drawable) {
        mIvLeft.setImageDrawable(drawable);
    }

    /**
     * 设置右边文本显示状态
     */
    public void setRightTextVisibility(@Visibility int resid) {
        mTvRight.setVisibility(resid);

        changePos();
//        if (resid == VISIBLE){
//            RelativeLayout.LayoutParams rightTvLp = new RelativeLayout.LayoutParams(mTvRight.getLayoutParams());
//            rightTvLp.setMargins(0, 0, 40, 0);
//            //需要将mTVRight 放在布局右边
//            rightTvLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            mTvRight.setLayoutParams(rightTvLp);
//        }
    }


    public interface onRightTextClickListener {
        void onClick();
    }


    public void setOnLeftTextClickListener(onLeftTextClickListener onLeftTextClickListener) {
        mOnLeftTextClickListener = onLeftTextClickListener;
    }

    private onLeftTextClickListener mOnLeftTextClickListener;

    public interface onLeftTextClickListener {
        void onClick();
    }


    public void setOnRightImageClickListener(onRightImageClickListener onRightImageClickListener) {
        mOnRightImageClickListener = onRightImageClickListener;
    }

    public void setBg(int colorId) {
        mRlParent.setBackgroundColor(colorId);
    }

    private onRightImageClickListener mOnRightImageClickListener;

    public interface onRightImageClickListener {
        void onClick();
    }


    public void setOnLeftImageClickListener(onLeftImageClickListener onLeftImageClickListener) {
        mOnLeftImageClickListener = onLeftImageClickListener;
    }

    private onLeftImageClickListener mOnLeftImageClickListener;

    private OnLabClickListener mOnLabClickListener;

    public interface onLeftImageClickListener {
        void onClick();
    }

    public interface OnLabClickListener {
        void onClick();
    }
}
