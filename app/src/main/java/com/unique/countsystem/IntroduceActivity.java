package com.unique.countsystem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class IntroduceActivity extends ActionBarActivity {


    private ViewPager mViewPager;//声明ViewPager对象
    private ImageView mPageImg;// 动画图片
    private int currIndex = 0;
    private ImageView mPage0, mPage1, mPage2, mPage3, mPage4, mPage5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        init();

    }

    private void init() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        mPage0 = (ImageView) findViewById(R.id.page0);
        mPage1 = (ImageView) findViewById(R.id.page1);
        mPage2 = (ImageView) findViewById(R.id.page2);
        mPage3 = (ImageView) findViewById(R.id.page3);
        mPage4 = (ImageView) findViewById(R.id.page4);
        mPage5 = (ImageView) findViewById(R.id.page5);


        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.about_us_layout, null);
        View view2 = mLi.inflate(R.layout.about_us_layout, null);
        View view3 = mLi.inflate(R.layout.about_us_layout, null);
        View view4 = mLi.inflate(R.layout.about_us_layout, null);
        View view5 = mLi.inflate(R.layout.about_us_layout, null);
        View view6 = mLi.inflate(R.layout.about_us_layout, null);

        //每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);

        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }



            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);//与ListView用法相同，设置重写的Adapter。这样就实现了ViewPager的滑动效果。
    }
    public class MyOnPageChangeListener implements OnPageChangeListener {

        public void onPageSelected(int arg0) {//参数arg0为选中的View

            Animation animation = null;//声明动画对象
            switch (arg0) {
                case 0: //页面一
                    mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。
                    mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
                    if (currIndex == arg0 + 1) {
                        animation = new TranslateAnimation(arg0 + 1, arg0, 0, 0);//圆点移动效果动画，从当前View移动到下一个View
                    }
                    break;
                case 1: //页面二
                    mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));//当前View
                    mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));//上一个View
                    mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));//下一个View
                    if (currIndex == arg0 - 1) {//如果滑动到上一个View
                        animation = new TranslateAnimation(arg0 - 1, arg0, 0, 0); //圆点移动效果动画，从当前View移动到下一个View


                    } else if (currIndex == arg0 + 1) {//圆点移动效果动画，从当前View移动到下一个View，下同。

                        animation = new TranslateAnimation(arg0 + 1, arg0, 0, 0);
                    }
                    break;
                case 2: //页面三
                    mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                    mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
                    mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
                    if (currIndex == arg0 - 1) {
                        animation = new TranslateAnimation(arg0 - 1, arg0, 0, 0);
                    } else if (currIndex == arg0 + 1) {
                        animation = new TranslateAnimation(arg0 + 1, arg0, 0, 0);
                    }
                    break;
                case 3:
                    mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                    mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
                    mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
                    if (currIndex == arg0 - 1) {
                        animation = new TranslateAnimation(arg0 - 1, arg0, 0, 0);

                    } else if (currIndex == arg0 + 1) {
                        animation = new TranslateAnimation(arg0 + 1, arg0, 0, 0);
                    }
                    break;
                case 4:
                    mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                    mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
                    mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
                    if (currIndex == arg0 - 1) {
                        animation = new TranslateAnimation(arg0 - 1, arg0, 0, 0);
                    } else if (currIndex == arg0 + 1) {
                        animation = new TranslateAnimation(arg0 + 1, arg0, 0, 0);
                    }
                    break;
                case 5:
                    mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                    mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
//                    mPage6.setImageDrawable(getResources().getDrawable(R.drawable.page));
                    if (currIndex == arg0 - 1) {
                        animation = new TranslateAnimation(arg0 - 1, arg0, 0, 0);
//                    } else if (currIndex == arg0 + 1) {
//                        animation = new TranslateAnimation(arg0 + 1, arg0, 0, 0);//当最后一个导航说明时不需要
                    }
                    break;
            }
            currIndex = arg0;//设置当前View
            animation.setFillAfter(true);// True:设置图片停在动画结束位置
            animation.setDuration(300);//设置动画持续时间
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }



}
