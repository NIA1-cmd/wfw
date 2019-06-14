package com.oncloudsoft.sdk.fragment.approval;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.MyViewPageAdapter;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.BaseTabData;
import com.oncloudsoft.sdk.view.BaseTitleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者 黄继栋
 * 创建时间 2019/2/12 16:57
 * 描述  统一的tab页面
 */

public abstract class BaseTablayoutActivity extends BaseActivity {
    private List<BaseTabData> baseTabData;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> titleDatas = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basetablayout);
        TabLayout tabLayout = findViewById(R.id.tl_lab);

        ViewPager viewPager = findViewById(R.id.vp_coupon);
        BaseTitleView mBtTitle = findViewById(R.id.bt_title);
        mBtTitle.setTitleText(getTitleText());
        setStatusBarLightcolor(this, R.color.white);
        mBtTitle.setTitleType(BaseTitleView.WHITE);

        //是否需要下划线长度等于 文字宽度  默认 是需要
        boolean booleanExtra = getIntent().getBooleanExtra(Global.TYPE, true);

        mBtTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });


        ArrayList<BaseTabData> list=new ArrayList<>();
        list.clear();
        baseTabData = getBaseTabData(list);


        fragmentList.clear();
        titleDatas.clear();

        for (int i = 0; i < baseTabData.size(); i++) {
            BaseTabData baseTabData = this.baseTabData.get(i);
            fragmentList.add(baseTabData.getBaseFragment());
            titleDatas.add(baseTabData.getFragmentTitle());
        }


        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        viewPager.setAdapter(myViewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.setTabsFromPagerAdapter(myViewPageAdapter);

        if (booleanExtra){
            setTabLayoutIndicator(tabLayout);
        }

        //设置分割线
//        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
//        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        linearLayout.setDividerPadding(40);
//        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.layout_divider_vertical));

    }

    protected abstract String getTitleText();


    protected abstract List<BaseTabData> getBaseTabData(List<BaseTabData> list);

    /**
     * 修改tablayout下面的横线的长短为文字的长短
     *
     * @param tabLayout view
     */
    public static void setTabLayoutIndicator(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Field field = tabLayout.getClass().getDeclaredField("mTabStrip");
                    field.setAccessible(true);
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout tabStrip = (LinearLayout) field.get(tabLayout);
                    for (int i = 0, count = tabStrip.getChildCount(); i < count; i++) {
                        View tabView = tabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView textView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int textWidth = 0;
                        textWidth = textView.getWidth();
                        if (textWidth == 0) {
                            textView.measure(0, 0);
                            textWidth = textView.getMeasuredWidth();
                        }
                        int tabWidth = 0;
                        tabWidth = tabView.getWidth();
                        if (tabWidth == 0) {
                            tabView.measure(0, 0);
                            tabWidth = tabView.getMeasuredWidth();
                        }
                        LinearLayout.LayoutParams tabViewParams = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        int margin = (tabWidth - textWidth) / 2;
                        //LogUtils.d("textWidth=" + textWidth + ", tabWidth=" + tabWidth + ", margin=" + margin);
                        tabViewParams.leftMargin = margin;
                        tabViewParams.rightMargin = margin;
                        tabView.setLayoutParams(tabViewParams);
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
