package com.oncloudsoft.sdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;

/**
 * 统一的lab输入框样式View
 */
public class BaseItemEditext extends RelativeLayout {


    private TextView mTvLabName;
    private EditText mEtContent;
    private LinearLayout mLlLine;
    private ImageView mIvEndIcon;
    private View me;

    public BaseItemEditext(Context context) {
        this(context, null);
    }

    public BaseItemEditext(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseItemEditext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attributeSet) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_editext_layout, this);
        mTvLabName = view.findViewById(R.id.tv_name);
        mEtContent = view.findViewById(R.id.et_input);
        mLlLine = view.findViewById(R.id.ll_line);
        mIvEndIcon = view.findViewById(R.id.iv_endicon);
        me = view;
        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.BaseItemEditext);

        initLabName(typeArray);
        intiLine(typeArray);
        intiType(typeArray);
        initInput(typeArray);

        typeArray.recycle();

    }

    private void intiType(TypedArray typeArray) {

        int icon = typeArray.getInt(R.styleable.BaseItemEditext_be_textType, 2);//默认是输入框


        if (icon == 2) {//输入框

            mIvEndIcon.setVisibility(GONE);
            RelativeLayout.LayoutParams endEditextLp = new RelativeLayout.LayoutParams(mEtContent.getLayoutParams());
            endEditextLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            endEditextLp.setMargins(0, 0, 20, 0);
            mEtContent.setLayoutParams(endEditextLp);
            mEtContent.setFocusable(true);

            setEditHint(getResources().getString(R.string.please_input));


        } else {//请选择


            mIvEndIcon.setVisibility(VISIBLE);
            RelativeLayout.LayoutParams endIvLp = new RelativeLayout.LayoutParams(mIvEndIcon.getLayoutParams());
            endIvLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            endIvLp.addRule(RelativeLayout.CENTER_VERTICAL);
            endIvLp.setMargins(0, 0, 20, 0);
            mIvEndIcon.setLayoutParams(endIvLp);
            mIvEndIcon.setImageDrawable(getResources().getDrawable(R.drawable.skip));
            RelativeLayout.LayoutParams endEditextLp = new RelativeLayout.LayoutParams(mEtContent.getLayoutParams());
            endEditextLp.addRule(RelativeLayout.LEFT_OF, mIvEndIcon.getId());
            mEtContent.setLayoutParams(endEditextLp);
            mEtContent.setFocusable(false);
            mEtContent.setOnClickListener(listener);
            setEditHint(getResources().getString(R.string.please_choose));

        }

    }

    View.OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            me.callOnClick();
        }
    };


    private void intiLine(TypedArray typeArray) {
        int icon = typeArray.getInt(R.styleable.BaseItemEditext_be_lineVisibility, 0);
        mLlLine.setVisibility(icon == 0 ? VISIBLE : INVISIBLE);

    }

    private void initInput(TypedArray typeArray) {

        int name = typeArray.getResourceId(R.styleable.BaseItemEditext_be_endhint, 0);
        CharSequence hint = name > 0 ? typeArray.getResources().getText(name) : typeArray.getString(R.styleable.BaseItemEditext_be_endhint);
        if (!TextUtils.isEmpty(hint)) {
            setEditHint(hint);
        }


        int type = typeArray.getInt(R.styleable.BaseItemEditext_be_inputtype, 3);

        if (type == 2) {
            setInputType(8194);//只能输入小数
            com.oncloudsoft.sdk.utils.TextUtils.setEditext(mEtContent, -1, 4);


        }


    }

    private void initLabName(TypedArray typeArray) {
        int name = typeArray.getResourceId(R.styleable.BaseItemEditext_be_labName, 0);
        CharSequence charSequence = name > 0 ? typeArray.getResources().getText(name) : typeArray.getString(R.styleable.BaseItemEditext_be_labName);
        setLabName(charSequence);
    }


    //----------------------为外部提供修改属性的方法-----------------


    public void setLabName(CharSequence charSequence) {

        mTvLabName.setText(charSequence);

    }

    public void setEditHint(CharSequence charSequence) {
        mEtContent.setHint(charSequence);

    }

    public void setEditText(CharSequence charSequence) {
        mEtContent.setText(charSequence);

    }

    public String getInputContent() {
        return mEtContent.getText().toString().trim();
    }

    public void setInputType(int type) {
        mEtContent.setInputType(type);
    }
}
