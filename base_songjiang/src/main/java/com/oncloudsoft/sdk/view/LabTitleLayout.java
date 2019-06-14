package com.oncloudsoft.sdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;


public class LabTitleLayout extends RelativeLayout {


   private TextView tvLabname;


    public LabTitleLayout(Context context) {
        this(context, null);
    }

    public LabTitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attributeSet) {
        View view = LayoutInflater.from(context).inflate(R.layout.lab_title_layout, this);
        tvLabname = view.findViewById(R.id.tv_labname);


        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.LabTitleLayout);

        initTitle(typeArray);
        typeArray.recycle();

    }

    private void initTitle(TypedArray typeArray) {
        int titleText = typeArray.getResourceId(R.styleable.LabTitleLayout_titleName, 0);
        CharSequence charSequence = titleText > 0 ? typeArray.getResources().getText(titleText) : typeArray.getString(R.styleable.LabTitleLayout_titleName);
        setTitleName(charSequence);


    }


    //----------------------为外部提供修改属性的方法-----------------


    public void setTitleName(CharSequence charSequence) {

        tvLabname.setText(charSequence);

    }


}
