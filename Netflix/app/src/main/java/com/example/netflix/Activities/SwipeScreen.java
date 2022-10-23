package com.example.netflix.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.netflix.Adapters.ViewPagerAdapter;
import com.example.netflix.MainScreens.Mainscreen;
import com.example.netflix.R;

public class SwipeScreen extends AppCompatActivity {
    TextView signin, help, privacy;
    Button getStarted;
    LinearLayout sliderdots;
    ViewPager viewPagerswipe;
    private int dotscount;
    private ImageView [] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_swipe_screen);

        help=findViewById(R.id.helptextview);
        signin=findViewById(R.id.signintextview);
        privacy=findViewById(R.id.privacytextview);
        getStarted=findViewById(R.id.getstarted);
        viewPagerswipe=findViewById(R.id.viewpagerswipescreen);
        sliderdots=findViewById(R.id.sliderdots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerswipe.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i =0; i<dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactivedots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            sliderdots.addView(dots[i]);}

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.activedots));




        viewPagerswipe.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for(int i=0; i<dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactivedots));

                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.activedots));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(SwipeScreen.this, SigninActivity.class);
                    startActivity(i);
                }
            });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/en/node/100628")));
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/en")));
            }
        });
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SwipeScreen.this, StepOne.class);
                startActivity(i);
            }
        });



    }
}