package com.tongtong.tiny.loopviewpagergallary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    //测试数据个数
    public static final int LIST_SIZE = 10;

    FrameLayout flVpParent;
    LoopViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flVpParent = findViewById(R.id.fl_vp_parent);
        mViewPager = findViewById(R.id.id_viewpager);

        List<TextView> viewList = getViewLists();
        PageTransformerAdapter mAdapter = new PageTransformerAdapter(MainActivity.this, viewList);

        //设置Page间间距
        mViewPager.setPageMargin(20);
        //设置缓存的页面数量
        //这个如果设置为1，或者使用默认值1时，在向左滑动时，右面新加载出的view第一次加载时的缩放动画不显示。
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageTransformer(true, new ScaleDownPageTransformer());
        mViewPager.setAdapter(mAdapter);

        //这儿的1000，可以随便改，只要用户滑不出边界即可
        int startPos = 1000 * viewList.size() + 1;
        mViewPager.setCurrentItem(startPos);

        mViewPager.start();

        //扩大ViewPager的滑动区域
        flVpParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.onTouchEvent(event);
            }
        });
    }

    /**
     * 生成List<TextView>数据
     *
     * @return
     */
    private List<TextView> getViewLists() {
        List<TextView> list = new ArrayList<>();
        for (int j = 0; j < LIST_SIZE; j++) {
            TextView tv = generateTextView(j);
            list.add(tv);
        }
        return list;
    }

    /**
     * 生成单个的TextView对象
     *
     * @param j
     * @return
     */
    private TextView generateTextView(final int j) {
        TextView tv = new TextView(this);
        tv.setBackgroundColor(Color.parseColor("#2371e9"));
        String str = "这个是第" + (j + 1) + "个View";
        tv.setTag(str);
        tv.setText(str);
        tv.setTextColor(Color.parseColor("#f0523c"));
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "第" + (j + 1) + "个View被点击了");
            }
        });
        return tv;
    }
}
