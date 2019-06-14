package com.oncloudsoft.sdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;

/**
 * 统一的lab样式View
 */
public class BaseItemTextView extends RelativeLayout implements View.OnClickListener{
    private RelativeLayout mRlParent;
    private TextView mTvLabName;
    private TextView mTvEndText;
    private ImageView mIvEndIcon;
    private ImageView mIvLabIcon;
    private LinearLayout mLlLine;

    private int spacing = 20;

    public BaseItemTextView(Context context) {
        this(context, null);
    }

    public BaseItemTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseItemTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attributeSet) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_text_layout, this);
        mTvLabName = view.findViewById(R.id.tv_name);
        mTvEndText = view.findViewById(R.id.tv_endtext);
        mIvEndIcon = view.findViewById(R.id.iv_endicon);
        mIvLabIcon = view.findViewById(R.id.iv_labicon);
        mRlParent = view.findViewById(R.id.rl_parent);
        mLlLine = view.findViewById(R.id.ll_line);

        mIvEndIcon.setOnClickListener(this);
        mTvLabName.setOnClickListener(this);
        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.BaseItemTextView);

        /*init text and image widget*/
        initNameText(typeArray);
        initEndTextView(typeArray);
        initEndIconImageView(typeArray);
        initLabIconImageView(typeArray);
        initLine(typeArray);
        chageView();
        initType(typeArray);
        typeArray.recycle();

    }

    private void initLine(TypedArray typeArray) {
        int icon = typeArray.getInt(R.styleable.BaseItemTextView_lineVisibility, -1);
        setLineVisibile(icon == 0?VISIBLE:INVISIBLE);
    }

    private void initLabIconImageView(TypedArray typeArray) {

        int icon = typeArray.getResourceId(R.styleable.BaseItemTextView_labicon, 0);
        if (icon != 0) {
            mIvLabIcon.setImageResource(icon);
            mIvLabIcon.setVisibility(VISIBLE);
        } else {
            mIvLabIcon.setVisibility(GONE);
        }

    }

    private void initEndIconImageView(TypedArray typeArray) {
        int icon = typeArray.getResourceId(R.styleable.BaseItemTextView_endicon, 0);
        mIvEndIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.skip));//设置默认图标
        if (icon != 0) {
            mIvEndIcon.setImageResource(icon);
        }
        int leftVis = typeArray.getInt(R.styleable.BaseItemTextView_endIconVisibility, 0);//默认显示


        setEndIvVisibilitys(leftVis == 0 ? VISIBLE : GONE);
    }

    private void chageView() {
        if (mTvLabName.getVisibility() == VISIBLE) {
            if (mIvLabIcon.getVisibility() == VISIBLE) {

                RelativeLayout.LayoutParams iconIvLp = new RelativeLayout.LayoutParams(mIvLabIcon.getLayoutParams());
                iconIvLp.setMargins(spacing, 0, 0, 0);
                iconIvLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                iconIvLp.addRule(RelativeLayout.CENTER_VERTICAL);
                mIvLabIcon.setLayoutParams(iconIvLp);

                RelativeLayout.LayoutParams iconTvLp = new RelativeLayout.LayoutParams(mTvLabName.getLayoutParams());
                iconTvLp.setMargins(spacing, 0, 0, 0);
                iconTvLp.addRule(RelativeLayout.RIGHT_OF, mIvLabIcon.getId());
                mTvLabName.setLayoutParams(iconTvLp);


            } else {
                RelativeLayout.LayoutParams iconTvLp = new RelativeLayout.LayoutParams(mTvLabName.getLayoutParams());
                iconTvLp.setMargins(spacing, 0, 0, 0);
                //需要将mTvEndText 放在布局右边
                iconTvLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                mTvLabName.setLayoutParams(iconTvLp);


                RelativeLayout.LayoutParams iconIvLp = new RelativeLayout.LayoutParams(mIvLabIcon.getLayoutParams());
                iconIvLp.setMargins(0, 0, 0, 0);
                iconIvLp.addRule(CENTER_VERTICAL);
                mIvLabIcon.setLayoutParams(iconIvLp);

            }
        } else {
            if (mIvLabIcon.getVisibility() == VISIBLE) {
                RelativeLayout.LayoutParams iconIvLp = new RelativeLayout.LayoutParams(mIvLabIcon.getLayoutParams());
                iconIvLp.setMargins(spacing, 0, 0, 0);
                iconIvLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                iconIvLp.addRule(RelativeLayout.CENTER_VERTICAL);
                mIvLabIcon.setLayoutParams(iconIvLp);

                RelativeLayout.LayoutParams endTvLp = new RelativeLayout.LayoutParams(mTvLabName.getLayoutParams());
                endTvLp.setMargins(0, 0, 0, 0);
                mTvLabName.setLayoutParams(endTvLp);
            } else {
                RelativeLayout.LayoutParams endIvLp = new RelativeLayout.LayoutParams(mTvLabName.getLayoutParams());
                endIvLp.setMargins(0, 0, 0, 0);
                mTvLabName.setLayoutParams(endIvLp);

                RelativeLayout.LayoutParams endTvLp = new RelativeLayout.LayoutParams(mIvLabIcon.getLayoutParams());
                endTvLp.setMargins(0, 0, 0, 0);
                mIvLabIcon.setLayoutParams(endTvLp);
            }
        }


        if (mTvEndText.getVisibility() == VISIBLE) {
            if (mIvEndIcon.getVisibility() == VISIBLE) {

                RelativeLayout.LayoutParams endIvLp = new RelativeLayout.LayoutParams(mIvEndIcon.getLayoutParams());
                endIvLp.setMargins(0, 0, 15, 0);
                endIvLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                endIvLp.addRule(RelativeLayout.CENTER_VERTICAL);
                mIvEndIcon.setLayoutParams(endIvLp);

                RelativeLayout.LayoutParams endTvLp = new RelativeLayout.LayoutParams(mTvEndText.getLayoutParams());
                endTvLp.setMargins(0, 0, 15, 0);
                endTvLp.addRule(RelativeLayout.LEFT_OF, mIvEndIcon.getId());
                mTvEndText.setLayoutParams(endTvLp);


            } else {
                RelativeLayout.LayoutParams endTvLp = new RelativeLayout.LayoutParams(mTvEndText.getLayoutParams());
                endTvLp.setMargins(0, 0, 15, 0);
                //需要将mTvEndText 放在布局右边
                endTvLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mTvEndText.setLayoutParams(endTvLp);


                RelativeLayout.LayoutParams endIvLp = new RelativeLayout.LayoutParams(mIvEndIcon.getLayoutParams());
                endIvLp.setMargins(0, 0, 0, 0);
                endIvLp.addRule(CENTER_VERTICAL);
                mIvEndIcon.setLayoutParams(endIvLp);


            }
        } else {
            if (mIvEndIcon.getVisibility() == VISIBLE) {
                RelativeLayout.LayoutParams endTvLp = new RelativeLayout.LayoutParams(mTvEndText.getLayoutParams());
                endTvLp.setMargins(0, 0, 15, 0);
                mTvEndText.setLayoutParams(endTvLp);

                RelativeLayout.LayoutParams endIvLp = new RelativeLayout.LayoutParams(mIvEndIcon.getLayoutParams());
                endIvLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                endIvLp.addRule(CENTER_VERTICAL);
                endIvLp.setMargins(0, 0, 15, 0);
                mIvEndIcon.setLayoutParams(endIvLp);
            } else {
                RelativeLayout.LayoutParams endTvLp = new RelativeLayout.LayoutParams(mTvEndText.getLayoutParams());
                endTvLp.setMargins(0, 0, 0, 0);
                mTvEndText.setLayoutParams(endTvLp);

                RelativeLayout.LayoutParams endIvLp = new RelativeLayout.LayoutParams(mIvEndIcon.getLayoutParams());
                endIvLp.setMargins(0, 0, 0, 0);
                endIvLp.addRule(CENTER_VERTICAL);

                mIvEndIcon.setLayoutParams(endIvLp);
            }
        }


    }


    private void initEndTextView(TypedArray typeArray) {
        int name = typeArray.getResourceId(R.styleable.BaseItemTextView_endText, 0);

        CharSequence charSequence = name > 0 ? typeArray.getResources().getText(name) : typeArray.getString(R.styleable.BaseItemTextView_endText);

        if (charSequence != null && charSequence.length() > 0) {
            setEndText(charSequence.toString());

        }


    }

    private void initNameText(TypedArray typeArray) {
        int name = typeArray.getResourceId(R.styleable.BaseItemTextView_labName, 0);

        CharSequence charSequence = name > 0 ? typeArray.getResources().getText(name) : typeArray.getString(R.styleable.BaseItemTextView_labName);

//        mTvLabName.setVisibility(charSequence !=null && charSequence.length() > 0 ? VISIBLE : GONE);

        mTvLabName.setText(charSequence);

    }


    /**
     * 设置末尾icon 是否可见颜色状态
     */
    public void setEndIvVisibility(@BaseTitleView.Visibility int visibility) {
        setEndIvVisibilitys(visibility);
        chageView();
    }


    private void setEndIvVisibilitys(@BaseTitleView.Visibility int visibility) {
        mIvEndIcon.setVisibility(visibility);

    }




    /**
     * 设置末尾字体颜色
     *
     * @param color
     */
    public void setEndTextColor(int color) {

        mTvEndText.setTextColor(color);

    }

    /**
     * 设置末尾的字体内容
     *
     * @param text hh
     */
    public void setEndText(String text) {
        mTvEndText.setText(text);
    }

    public void setStartText(String text) {
        mTvLabName.setText(text);
    }

    private void initType(TypedArray typeArray) {
        int type = typeArray.getInt(R.styleable.BaseItemTextView_ent_color, 1);//默认灰色
        if (type == 1) {
            mTvLabName.setTextColor(ContextCompat.getColor(mTvLabName.getContext(), R.color.textcolor_02));
        } else if (type == 2) {
            mTvLabName.setTextColor(ContextCompat.getColor(mTvLabName.getContext(), R.color.textcolor_05));
        }
        int type1 = typeArray.getInt(R.styleable.BaseItemTextView_start_color, 2);//默认灰色
        if (type1 == 1) {
            mTvEndText.setTextColor(ContextCompat.getColor(mTvLabName.getContext(), R.color.textcolor_02));
        } else if (type1 == 2) {
            mTvEndText.setTextColor(ContextCompat.getColor(mTvLabName.getContext(), R.color.textcolor_05));
        }
    }

    public String getLabText() {
        return mTvLabName.getText().toString();
    }

    public String getEndText() {
        return mTvEndText.getText().toString();
    }
    public void setLineVisibile(int visibile) {
         mLlLine.setVisibility(visibile);
    }

    /*public void setmIvEndIconWH(int wh){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wh, wh);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.setMargins(20,0,0,0);
        mIvEndIcon.setLayoutParams(layoutParams);
    }*/

    public void setOnRightIvClickListener(onRightIvClickListener clickListener) {
        mOnRightIvClickListener = clickListener;
    }

    private onRightIvClickListener mOnRightIvClickListener;

    public interface onRightIvClickListener {
        void onClick();
    }
    private onLeftTvClickListener mOnLeftTvClickListener;

    public interface onLeftTvClickListener {
        void onClick();
    }
    public void setOnLeftTvClickListener(onLeftTvClickListener clickListener) {
        mOnLeftTvClickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_endicon) {
            if (mOnRightIvClickListener != null) {
                mOnRightIvClickListener.onClick();
            }

        }
        else if (i == R.id.tv_name) {
            if (mOnLeftTvClickListener != null) {
                mOnLeftTvClickListener.onClick();
            }

        }
    }
}
