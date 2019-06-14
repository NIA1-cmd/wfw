package com.oncloudsoft.sdk.view;

import android.content.Context;
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
 * 首页table页的View
 */
public class BottomLayout extends RelativeLayout implements View.OnClickListener {
    private onItemClick mOnItemClick = null;
    private LinearLayout caseLinearLayout;
    private LinearLayout toolLinearLayout;
    private LinearLayout mineLinearLayout;

    private ImageView caseImageView;
    private ImageView toolImageView;
    private ImageView mineImageView;

    private TextView caseTextView;
    private TextView toolTextView;
    private TextView mineTextView;

    public BottomLayout(Context context) {
        super(context);
        init(context);
    }

    public BottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public BottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.bottom_view, this);


        caseLinearLayout = inflateView.findViewById(R.id.ll_case);
        toolLinearLayout = inflateView.findViewById(R.id.ll_tool);
        mineLinearLayout = inflateView.findViewById(R.id.ll_mine);


        caseImageView = inflateView.findViewById(R.id.iv_case);
        toolImageView = inflateView.findViewById(R.id.iv_tool);
        mineImageView = inflateView.findViewById(R.id.iv_mine);

        caseTextView = inflateView.findViewById(R.id.tv_case);
        toolTextView = inflateView.findViewById(R.id.tv_tool);
        mineTextView = inflateView.findViewById(R.id.tv_mine);

        caseLinearLayout.setOnClickListener(this);
        toolLinearLayout.setOnClickListener(this);
        mineLinearLayout.setOnClickListener(this);
        initres(context);
        //默认加载首页
        setSelectTextColor(caseTextView);
        setImageViewRes(caseImageView, R.drawable.case_selected);
    }

    public void setOnItemClickListener(onItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    @Override
    public void onClick(View v) {
        initres(v.getContext());
        int i = v.getId();
        if (i == R.id.ll_case) {
            mOnItemClick.click(1);
            setSelectTextColor(caseTextView);
            setImageViewRes(caseImageView, R.drawable.case_selected);

        }
        else if (i == R.id.ll_tool) {
            mOnItemClick.click(2);
            setSelectTextColor(toolTextView);
            setImageViewRes(toolImageView, R.drawable.tool_selected);


        }
        else if (i == R.id.ll_mine) {
            mOnItemClick.click(3);
            setSelectTextColor(mineTextView);
            setImageViewRes(mineImageView, R.drawable.me_selected);


        }
    }


    public interface onItemClick {
        void click(int position);
    }

    /**
     * 将文字和图片资源置为初始状态
     *
     * @param context
     */
    public void initres(Context context) {
        caseImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.case_normal));
        toolImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tool_normal));
        mineImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.me_normal));
        caseTextView.setTextColor(ContextCompat.getColor(context, R.color.gary));
        toolTextView.setTextColor(ContextCompat.getColor(context, R.color.gary));
        mineTextView.setTextColor(ContextCompat.getColor(context, R.color.gary));

    }


    /**
     * 设置选中之后的文字颜色
     *
     * @param textView
     */
    public void setSelectTextColor(TextView textView) {
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.maincolor_01));

    }

    public void setImageViewRes(ImageView imageView, int res) {
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), res));


    }
}
