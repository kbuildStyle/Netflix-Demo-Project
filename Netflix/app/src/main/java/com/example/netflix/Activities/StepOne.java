package com.example.netflix.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.netflix.R;

public class StepOne extends AppCompatActivity {
    TextView Signin,textView;
    Button seeyourplanbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_step_one);

        Signin=findViewById(R.id.signinstepone);
        textView=findViewById(R.id.step1of3);
        seeyourplanbutton=findViewById(R.id.seeyourplansbutton);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StepOne.this,SigninActivity.class);
                startActivity(i);
            }
        });
        seeyourplanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StepOne.this,ChooseYourPlan.class);
                startActivity(i);
            }
        });
        SpannableString st= new SpannableString("Step 1 of 3");
        StyleSpan boldspan= new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1 = new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(st);
    }
}