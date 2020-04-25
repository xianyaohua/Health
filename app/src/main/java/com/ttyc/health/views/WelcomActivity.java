package com.ttyc.health.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.ttyc.health.R;
import com.ttyc.health.utils.SharedPreferencesUtils;

import java.util.ArrayList;

public class WelcomActivity extends Activity {
    private RadioGroup rg;
    private ViewPager vp;
    private ArrayList listViews;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        rg=(RadioGroup) findViewById(R.id.rg);
        rg.clearCheck();
        rg.check(R.id.rb1);
        vp=(ViewPager)findViewById(R.id.image_vp);
        listViews=new ArrayList();
        ImageView view0=new ImageView(this);
        view0.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        view0.setScaleType(ImageView.ScaleType.FIT_XY);
        view0.setImageDrawable(getResources().getDrawable(R.drawable.guid0));
        ImageView view1=new ImageView(this);
        view1.setScaleType(ImageView.ScaleType.FIT_XY);
        view1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        view1.setImageDrawable(getResources().getDrawable(R.drawable.guid1));
        ImageView view2=new ImageView(this);
        view2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        view2.setScaleType(ImageView.ScaleType.FIT_XY);
        view2.setImageDrawable(getResources().getDrawable(R.drawable.guid2));
        listViews.add(view0);
        listViews.add(view1);
        listViews.add(view2);
        vp.setAdapter(new MyImageAdapter(listViews));
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    rg.check(R.id.rb1);
                }else if(position==1){
                    rg.check(R.id.rb2);
                }else if(position==2){
                    rg.check(R.id.rb3);
                }
                if(position==2){
                    findViewById(R.id.tv).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.tv).setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    static class MyImageAdapter extends PagerAdapter {
        private ArrayList<View> listdata;
        @Override
        public int getCount() {
            return listdata.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        public MyImageAdapter(ArrayList<View> list) {
            super();
            listdata=list;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            container.addView((ImageView)listdata.get(position));
            return (ImageView)listdata.get(position);
        }
    }

    public void onClickText(View view){
        SharedPreferencesUtils.putGuidFlag(this,true);
        startActivity(new Intent(this,LoginActivity.class));
        this.finish();
    }
}
