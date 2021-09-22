package com.example.imageslider;

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

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPagerImageSlider;
    private List<SliderItem> sliderItemList;
    private SliderAdapter sliderAdapter;

    //Implementing Auto Slide Facility
    private Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPagerImageSlider = findViewById(R.id.viewPagerImageSlider);
        sliderItemList = new ArrayList<>();
        sliderItemList.add(new SliderItem(R.drawable.eating));
        sliderItemList.add(new SliderItem(R.drawable.fast_food));
        sliderItemList.add(new SliderItem(R.drawable.food_blogger));
        sliderItemList.add(new SliderItem(R.drawable.hamburger));
        sliderItemList.add(new SliderItem(R.drawable.healthy_eating));
        sliderItemList.add(new SliderItem(R.drawable.pizza));
        sliderAdapter = new SliderAdapter(sliderItemList, viewPagerImageSlider);
        viewPagerImageSlider.setAdapter(sliderAdapter);

        viewPagerImageSlider.setClipToPadding(false);
        viewPagerImageSlider.setClipChildren(false);
        viewPagerImageSlider.setOffscreenPageLimit(3);
        viewPagerImageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPagerImageSlider.setPageTransformer(compositePageTransformer);
        viewPagerImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,3000);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPagerImageSlider.setCurrentItem(viewPagerImageSlider.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,3000);
    }
}