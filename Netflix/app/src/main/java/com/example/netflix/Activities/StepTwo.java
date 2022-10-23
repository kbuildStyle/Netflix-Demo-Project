package com.example.netflix.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Timer;
import java.util.TimerTask;

public class StepTwo extends AppCompatActivity {
    String PlanName,PlanCost,PlanCostFormat,UserEmailId,UserPassword;
    TextView Signin,textView;
    Button continuebutton;
    EditText emailiduser,userpassword;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    int counter = 0;
    FirebaseAuth firebaseAuth;
    Boolean x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_step_two);
        Intent i = getIntent();
        PlanName=i.getStringExtra("PlanName");
        PlanCost=i.getStringExtra("PlanCost");
        PlanCostFormat=i.getStringExtra("PlanCostFormat");
        Signin=findViewById(R.id.signinstepone);
        emailiduser=findViewById(R.id.emailedittextsteptwo);
        userpassword=findViewById(R.id.passwordedittextsteptwo);
        progressBar=findViewById(R.id.progressbarsteptwo);
        progressBar.setVisibility(View.GONE);
        continuebutton=findViewById(R.id.continuesteptwo);
        textView=findViewById(R.id.step2of3);
        firebaseAuth=FirebaseAuth.getInstance();
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StepTwo.this,SigninActivity.class);
                startActivity(i);
            }
        });
        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEmailId=emailiduser.getText().toString();
                UserPassword=userpassword.getText().toString();
                x=true;
                if(UserEmailId.length()<10||!UserEmailId.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
                    emailiduser.setError("Enter a valid email Id");
                    x=false;
                }
                if(UserPassword.length()<8){
                    userpassword.setError("Password to short");
                    x=false;
                }
                if(x){
                    progressDialog = new ProgressDialog(StepTwo.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    firebaseAuth.signInWithEmailAndPassword(UserEmailId,UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                x=false;

                                Toast.makeText(getApplicationContext(), "Please enter via the main login screen", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(StepTwo.this,SigninActivity.class);
                                startActivity(i);


                            }
                            else {
                                if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                    emailiduser.setError("Email Id already exist");
                                    x=false;
                                    progressDialog.cancel();
                                    if(task.getException() instanceof FirebaseNetworkException){
                                        Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                                        x = false;
                                        progressDialog.cancel();
                                    }
                                }
                            }
                            if(UserEmailId.length()>9 && UserPassword.length()>7 && x){
                                Intent i =new Intent(StepTwo.this,StepThree.class);
                                i.putExtra("PlanName",PlanName);
                                i.putExtra("PlanCost",PlanCost);
                                i.putExtra("PlanCostFormat",PlanCostFormat);
                                i.putExtra("EmailId",UserEmailId);
                                i.putExtra("Password",UserPassword);
                                startActivity(i);
                                progressDialog.cancel();
                            }
                        }
                    });
                }

            }
        });
        SpannableString st= new SpannableString("Step 2 of 3");
        StyleSpan boldspan= new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1 = new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(st);



    }
    public void progress() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                progressBar.setProgress(counter);

                if(counter==1000) {
                    timer.cancel();
                }

            }
        };
        timer.schedule(timerTask,0,100);
    }
}