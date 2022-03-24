package com.oculus.vsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SponsorsActivity extends AppCompatActivity {
private ViewPager2 viewPager2, viewPager22;
private Handler slideHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        viewPager2=findViewById(R.id.viewPagerImageSlider);
        viewPager22=findViewById(R.id.viewPagerImageSlider2);
        List<SliderItem> sliderItems2 = new ArrayList<>();
        List<SliderItem> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.imag_sp16));
        sliderItems.add(new SliderItem(R.drawable.imag_sp15));
        sliderItems.add(new SliderItem(R.drawable.imag_sp14));
        sliderItems.add(new SliderItem(R.drawable.imag_sp13));
        sliderItems.add(new SliderItem(R.drawable.imag_sp12));
        sliderItems.add(new SliderItem(R.drawable.imag_sp11));

        sliderItems2.add(new SliderItem(R.drawable.image_spnils1));
        sliderItems2.add(new SliderItem(R.drawable.image_spnils2));
        sliderItems2.add(new SliderItem(R.drawable.image_spnils3));
        sliderItems2.add(new SliderItem(R.drawable.image_spnils4));
        sliderItems2.add(new SliderItem(R.drawable.image_spnils5));
        sliderItems2.add(new SliderItem(R.drawable.image_spnils6));

        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        viewPager22.setAdapter(new SliderAdapter(sliderItems2,viewPager22));
        viewPager22.setClipToPadding(false);
        viewPager22.setClipChildren(false);
        viewPager22.setOffscreenPageLimit(3);
        viewPager22.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
               float r=1-Math.abs(position);
               page.setScaleY(0.85f+r*0.15f);

            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager22.setPageTransformer(compositePageTransformer);
    }

}