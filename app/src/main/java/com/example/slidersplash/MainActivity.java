package com.example.slidersplash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button next,skip;
    private ViewPager viewPager;
    private Intromanager intromanager;
    private int layout[];
    private TextView[] dots;
    private LinearLayout dotsLayout;
    ViewPageAdapter viewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intromanager=new Intromanager(this);
        if(!intromanager.check())
        {
            intromanager.setFirst(false);
            Intent i=new Intent(MainActivity.this,Second.class);
            startActivity(i);
            finish();
        }
        if (Build.VERSION.SDK_INT>=21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_FULLSCREEN);

        }
        setContentView(R.layout.activity_main);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        dotsLayout=(LinearLayout)findViewById(R.id.layoutDots);
        skip=(Button)findViewById(R.id.btn_skip);
        next=(Button)findViewById(R.id.btn_next);

layout=new int[]{R.layout.screen1,R.layout.screen2,R.layout.screen3};
addBottomDots(0);
changestatusbarcolor();
viewPageAdapter=new ViewPageAdapter();
viewPager.setAdapter(viewPageAdapter);
viewPager.addOnPageChangeListener(viewListener);
skip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(MainActivity.this,Second.class);
        startActivity(i);
        finish();

    }
});
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int current =getItem(+1);
        if(current<layout.length)
        {
            viewPager.setCurrentItem(current);
        }
        else
        {
            Intent i=new Intent(MainActivity.this,Second.class);
            startActivity(i);
            finish();
        }

    }
});
    }
    private void addBottomDots(int position)
    {
        dots=new TextView[layout.length];
        int[] coloractive=getResources().getIntArray(R.array.dot_active);
        int[] colorinactive=getResources().getIntArray(R.array.dot_inactive);
        dotsLayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorinactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0)
        {
            dots[position].setTextColor(coloractive[position]);
        }

    }
    private int getItem(int i)
    {
        return viewPager.getCurrentItem()+1;
    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addBottomDots(i);
            if(i==layout.length-1)
            {
                next.setText("Proceed");
                skip.setVisibility(View.GONE);
            }
            else
            {
                next.setText("NEXT");
                skip.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    private void changestatusbarcolor()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPageAdapter extends PagerAdapter
    {
        private LayoutInflater layoutInflater;
        public Object instantiateItem(ViewGroup container,int position)
        {
            layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=layoutInflater.inflate(layout[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layout.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View v=(View)object;
            container.removeView(v);
        }
    }
}
