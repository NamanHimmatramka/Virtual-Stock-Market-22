package com.example.vsm22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager mSLideViewPager;

    Button backbtn, nextbtn, skipbtn;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        intial();
        SharedPreferences preferences=getSharedPreferences("preferences",MODE_PRIVATE);
        boolean firstSTart=preferences.getBoolean("firstStart",true);
        if(firstSTart){
            backbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getitem(0) > 0){

                        mSLideViewPager.setCurrentItem(getitem(-1),true);

                    }

                }
            });

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getitem(0) < 3)
                        mSLideViewPager.setCurrentItem(getitem(1),true);
                    else {

                        Intent i = new Intent(OnBoardingActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();

                    }

                }
            });

            skipbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent(OnBoardingActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();

                }
            });

            mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);

            viewPagerAdapter = new ViewPagerAdapter(this);

            mSLideViewPager.setAdapter(viewPagerAdapter);

            mSLideViewPager.addOnPageChangeListener(viewListener);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }
        else{
            startActivity(new Intent(OnBoardingActivity.this,LoginActivity.class));
            finish();
        }


    }

    private void intial() {
        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);
        skipbtn = findViewById(R.id.skipButton);
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {


            if (position > 0){

                backbtn.setVisibility(View.VISIBLE);

            }else {

                backbtn.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){

        return mSLideViewPager.getCurrentItem() + i;
    }

}