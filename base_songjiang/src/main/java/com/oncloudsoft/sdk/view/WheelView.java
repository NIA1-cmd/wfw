package com.oncloudsoft.sdk.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.oncloudsoft.sdk.entity.StageData;
import com.oncloudsoft.sdk.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于加载滚轮的选择节点View
 */
public class WheelView extends ScrollView {

    public static class OnWheelViewListener {
        public void onSelected(int selectedIndex, StageData item) {
        }
    }


    private Context context;
//    private ScrollView scrollView;

    private LinearLayout views;

    public WheelView(Context context) {
        super(context);
        init(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    //    String[] items;
    List<StageData> items;

    private List<StageData> getItems() {
        return items;
    }

    public void setItems(List<StageData> list) {
        if (null == items) {
            items = new ArrayList<StageData>();
        }
        items.clear();
        items.addAll(list);

        // 前面和后面补全
        StageData stageData = new StageData();
        stageData.setJdmc("");
        for (int i = 0; i < offset; i++) {
            //TODO  尾部添加空 后期优化
            items.add(0, stageData);
            items.add(stageData);
            items.add(stageData);
            items.add(stageData);
        }
        initData();
    }


    public static final int OFF_SET_DEFAULT = 1;
    int offset = OFF_SET_DEFAULT; // 偏移量（需要在最前面和最后面补全）

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    int displayItemCount; // 每页显示的数量

    int selectedIndex = 1;


    private void init(Context context) {
        this.context = context;

//        scrollView = ((ScrollView)this.getParent());
//        MyLog.d( "scrollview: " + scrollView);
        MyLog.d("parent: " + this.getParent());
//        this.setOrientation(VERTICAL);
        this.setVerticalScrollBarEnabled(false);

        views = new LinearLayout(context);
        views.setOrientation(LinearLayout.VERTICAL);
        this.addView(views);

        scrollerTask = new Runnable() {

            public void run() {

                int newY = getScrollY();
                if (initialY - newY == 0) { // stopped
                    final int remainder = initialY % itemHeight;
                    final int divided = initialY / itemHeight;
//                    MyLog.d( "initialY: " + initialY);
//                    MyLog.d( "remainder: " + remainder + ", divided: " + divided);
                    if (remainder == 0) {
                        selectedIndex = divided + offset;

                        onSeletedCallBack();
                    } else {
                        if (remainder > itemHeight / 2) {
                            WheelView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY - remainder + itemHeight);
                                    selectedIndex = divided + offset + 1;
                                    onSeletedCallBack();
                                }
                            });
                        } else {
                            WheelView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY - remainder);
                                    selectedIndex = divided + offset;
                                    onSeletedCallBack();
                                }
                            });
                        }


                    }


                } else {
                    initialY = getScrollY();
                    WheelView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };


    }

    int initialY;

    Runnable scrollerTask;
    int newCheck = 50;

    public void startScrollerTask() {

        initialY = getScrollY();
        this.postDelayed(scrollerTask, newCheck);
    }

    private void initData() {
        displayItemCount = offset * 2 + 1;


        for (int i=0;i<items.size();i++){
            StageData stageData = items.get(i);

            TextView view = createView(stageData.getJdmc());



//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            if (i==0){
//                lp.setMargins(0, 20, 0, 0);
//
//            }else if (i==items.size()-1){
//                lp.setMargins(0, 0, 0, 20);
//
//            }else {
//                lp.setMargins(0, 0, 0, 0);
//
//            }
//
//            view.setLayoutParams(lp);
            views.addView(view);
        }




        refreshItemView(0);
    }

    int itemHeight = 0;

    private TextView createView(String item) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setSingleLine(true);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER);
        int padding = dip2px(10);
        tv.setPadding(padding, padding, padding, padding);
        if (0 == itemHeight) {
            itemHeight = getViewMeasuredHeight(tv);
            MyLog.d("itemHeight: " + itemHeight);
            views.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * displayItemCount));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
            this.setLayoutParams(new LinearLayout.LayoutParams(lp.width, itemHeight * displayItemCount+200));
        }
        return tv;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

//        MyLog.d( "l: " + l + ", t: " + t + ", oldl: " + oldl + ", oldt: " + oldt);

//        try {
//            Field field = ScrollView.class.getDeclaredField("mScroller");
//            field.setAccessible(true);
//            OverScroller mScroller = (OverScroller) field.get(this);
//
//
//            if(mScroller.isFinished()){
//                MyLog.d( "isFinished...");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        refreshItemView(t);

        if (t > oldt) {
//            MyLog.d( "向下滚动");
            scrollDirection = SCROLL_DIRECTION_DOWN;
        } else {
//            MyLog.d( "向上滚动");
            scrollDirection = SCROLL_DIRECTION_UP;

        }


    }

    private void refreshItemView(int y) {
        int position = y / itemHeight + offset;
        int remainder = y % itemHeight;
        int divided = y / itemHeight;

        if (remainder == 0) {
            position = divided + offset;
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1;
            }

//            if(remainder > itemHeight / 2){
//                if(scrollDirection == SCROLL_DIRECTION_DOWN){
//                    position = divided + offset;
//                    MyLog.d( ">down...position: " + position);
//                }else if(scrollDirection == SCROLL_DIRECTION_UP){
//                    position = divided + offset + 1;
//                    MyLog.d( ">up...position: " + position);
//                }
//            }else{
////                position = y / itemHeight + offset;
//                if(scrollDirection == SCROLL_DIRECTION_DOWN){
//                    position = divided + offset;
//                    MyLog.d( "<down...position: " + position);
//                }else if(scrollDirection == SCROLL_DIRECTION_UP){
//                    position = divided + offset + 1;
//                    MyLog.d( "<up...position: " + position);
//                }
//            }
//        }

//        if(scrollDirection == SCROLL_DIRECTION_DOWN){
//            position = divided + offset;
//        }else if(scrollDirection == SCROLL_DIRECTION_UP){
//            position = divided + offset + 1;
        }

        int childSize = views.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) views.getChildAt(i);
            if (null == itemView) {
                return;
            }
            if (position == i) {
                itemView.setTextColor(Color.parseColor("#757575"));
            } else {
                itemView.setTextColor(Color.parseColor("#bbbbbb"));
            }
        }
    }

    /**
     * 获取选中区域的边界
     */
    int[] selectedAreaBorder;

    private int[] obtainSelectedAreaBorder() {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = new int[2];
            selectedAreaBorder[0] = itemHeight * offset;
            selectedAreaBorder[1] = itemHeight * (offset + 1);
        }
        return selectedAreaBorder;
    }


    private int scrollDirection = -1;
    private static final int SCROLL_DIRECTION_UP = 0;
    private static final int SCROLL_DIRECTION_DOWN = 1;

    Paint paint;
    int viewWidth;

    @Override
    public void setBackgroundDrawable(Drawable background) {

        if (viewWidth == 0) {
            viewWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
            MyLog.d("viewWidth: " + viewWidth);
        }

        if (null == paint) {
            paint = new Paint();
            paint.setColor(Color.parseColor("#83cde6"));
            paint.setStrokeWidth(dip2px(1f));
        }

        LinearGradient mLinearGradient = new LinearGradient(viewWidth * 2 / 6, 0, viewWidth * 4 / 6, 0, new int[]{0xFFFFFFFF, 0xE8E8E8E8, 0xFFFFFFFF}, null, Shader.TileMode.REPEAT);
        paint.setShader(mLinearGradient);
        background = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawLine(viewWidth * 2 / 6, obtainSelectedAreaBorder()[0], viewWidth * 4 / 6, obtainSelectedAreaBorder()[0], paint);
                canvas.drawLine(viewWidth * 2 / 6, obtainSelectedAreaBorder()[1], viewWidth * 4 / 6, obtainSelectedAreaBorder()[1], paint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };


        super.setBackgroundDrawable(background);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        MyLog.d("w: " + w + ", h: " + h + ", oldw: " + oldw + ", oldh: " + oldh);
        viewWidth = w;
        setBackgroundDrawable(null);
    }

    /**
     * 选中回调
     */
    private void onSeletedCallBack() {
        if (null != onWheelViewListener) {
            onWheelViewListener.onSelected(selectedIndex, items.get(selectedIndex));
        }

    }

    public void setSeletion(int position) {
        final int p = position;
        selectedIndex = p + offset;
        this.post(new Runnable() {
            @Override
            public void run() {
                WheelView.this.smoothScrollTo(0, p * itemHeight);
            }
        });

    }

    public StageData getSeletedItem() {
        return items.get(selectedIndex);
    }

    public int getSeletedIndex() {
        return selectedIndex - offset;
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {

            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    private OnWheelViewListener onWheelViewListener;

    public OnWheelViewListener getOnWheelViewListener() {
        return onWheelViewListener;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener) {
        this.onWheelViewListener = onWheelViewListener;
    }

    private int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getViewMeasuredHeight(View view) {
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
        return view.getMeasuredHeight();
    }

}