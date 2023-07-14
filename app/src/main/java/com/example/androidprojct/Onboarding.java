package com.example.androidprojct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Onboarding extends AppCompatActivity {
    ViewPager mSlideViewPAger;
    LinearLayout mDotlayout;
    Button backbtn,nextbtn,skipbtn;
    TextView[] dots;
    ViewPagerAdaptor viewPagerAdaptor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        backbtn=findViewById(R.id.btn_back);
        nextbtn=findViewById(R.id.btn_next);
        skipbtn=findViewById(R.id.skip_button);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getitem(0)>0){
                    mSlideViewPAger.setCurrentItem(getitem(-1),true);
                }
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getitem(0)<3){
                    mSlideViewPAger.setCurrentItem(getitem(1),true);
                }else{
                    Intent intent=new Intent(Onboarding.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Onboarding.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mSlideViewPAger=findViewById(R.id.slideViewPager);
        mDotlayout=(LinearLayout) findViewById(R.id.indicator_layout);
        viewPagerAdaptor=new ViewPagerAdaptor(this);
        mSlideViewPAger.setAdapter(viewPagerAdaptor);
        setUpIndicatior(0);
        mSlideViewPAger.addOnPageChangeListener(viewlistener);
    }
    protected void onStart() {
        super.onStart();

        int userId = SharedPreferenceManager.getInstance(this).getCurrentUserID(this);

        if (SharedPreferenceManager.getInstance(this).isLoggedIn(userId)) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    public void setUpIndicatior(int postion){
        dots=new TextView[4];
        mDotlayout.removeAllViews();
        for (int i=0;i< dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            }
            mDotlayout.addView(dots[i]);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dots[postion].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));
        }


    }
    ViewPager.OnPageChangeListener viewlistener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicatior(position);
            if(position>0){
                backbtn.setVisibility(View.VISIBLE);
            }else{
                backbtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private int getitem(int i){
        return mSlideViewPAger.getCurrentItem()+i;
    }


}