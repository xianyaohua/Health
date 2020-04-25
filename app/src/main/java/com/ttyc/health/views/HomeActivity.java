package com.ttyc.health.views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ttyc.health.R;
import com.ttyc.health.utils.LogUtil;
import com.ttyc.health.views.fragment.HealthFragment;
import com.ttyc.health.views.fragment.IndexFragment;
import com.ttyc.health.views.fragment.ShopFragment;
import com.ttyc.health.views.fragment.UserInfoFragment;
import com.ttyc.health.views.v4.MyNoScrollViewPager;

import java.util.ArrayList;

//主页
public class HomeActivity extends AppCompatActivity {
    private RadioGroup rg;
    private RadioButton indexRb,healthRb,shopRb,userRb;
    private ArrayList<Fragment> fragments;
    private IndexFragment indexFragment;
    private ShopFragment shopFragment;
    private UserInfoFragment userInfoFragment;
    private HealthFragment healthFragment;
    private MyFragmentAdapter myFragmentAdapter;
    private FragmentManager fm;
    private MyNoScrollViewPager mViewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        rg=(RadioGroup) findViewById(R.id.menu_rg);

        indexRb=findViewById(R.id.index_btn);
        healthRb=findViewById(R.id.health_btn);
        shopRb=findViewById(R.id.shop_btn);
        userRb=findViewById(R.id.user_btn);
        rg.check(R.id.index_btn);
        changeTextColor(indexRb);
        mViewPager=(MyNoScrollViewPager) findViewById(R.id.fragment_vp);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setTextCheckColor(i);
            }
        });

        fragments=new ArrayList<>();
        indexFragment=new IndexFragment();
        shopFragment= new ShopFragment();
        userInfoFragment = new UserInfoFragment();
        healthFragment=new HealthFragment();
        fragments.add(indexFragment);
        fragments.add(healthFragment);
        fragments.add(shopFragment);
        fragments.add(userInfoFragment);
        myFragmentAdapter=new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(myFragmentAdapter);
        mViewPager.setCurrentItem(0);
    }



   /**********************设置字体颜色****************************/
    private void setTextColorGray(RadioButton rb){
        rb.setTextColor(getResources().getColor(R.color.Black_9));
    }
    private void setTextColorBlue(RadioButton rb){
        rb.setTextColor(getResources().getColor(R.color.MainBlue));
    }
    private void setAllMenuColorGray(){
        setTextColorGray(indexRb);
        setTextColorGray(healthRb);
        setTextColorGray(shopRb);
        setTextColorGray(userRb);
    }
    private void changeTextColor(RadioButton rb){
        setAllMenuColorGray();
        setTextColorBlue(rb);
    }
    private void setTextCheckColor(int i){
        switch (i){
            case R.id.index_btn:
                changeTextColor(indexRb);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.health_btn:
                changeTextColor(healthRb);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.shop_btn:
                changeTextColor(shopRb);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.user_btn:
                changeTextColor(userRb);
                mViewPager.setCurrentItem(3);
                break;
            default:break;
        }
    }
    /****************************************************/

    static class MyFragmentAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        public MyFragmentAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments=fragments;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
