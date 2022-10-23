package com.example.netflix.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.MainScreens.Mainscreen;
import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SigninActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView Signuptextview, forgotpasswordtextview;
    Button signinbutton;
    EditText email,password;
    String resetemail,authemail,authpassword,UserID,FireContact,Firefirstname,Firelastname;
    FirebaseAuth firebaseAuth;

    static int counter =0;
    FirebaseFirestore firebaseFirestore;
    Date validate, today;
    DocumentReference userRef;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signin);

        //where is set content view

        progressBar=findViewById(R.id.signinprogressbar);
        progressBar.setVisibility(View.GONE);
        Signuptextview=findViewById(R.id.signuptextview);
        forgotpasswordtextview=findViewById(R.id.forgotpasswordtextview);
        signinbutton=findViewById(R.id.signinbutton);
        email=findViewById(R.id.emailedittext);
        password=findViewById(R.id.passwordedittext);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Calendar c = Calendar.getInstance();
        today = c.getTime();

       signinbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               authemail=email.getText().toString();
               authpassword=password.getText().toString();
               progressBar.setVisibility(View.VISIBLE);
               getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

               if(email.getText().toString().length()>8 && email.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") && password.getText().toString().length()>7){



               firebaseAuth.signInWithEmailAndPassword(authemail,authpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           UserID=firebaseAuth.getCurrentUser().getUid();
                           userRef=firebaseFirestore.collection("Users").document(UserID);
                           userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                               @Override
                               public void onSuccess(DocumentSnapshot documentSnapshot) {

                                   validate = documentSnapshot.getDate("Valid_date");
                                   Firefirstname = documentSnapshot.getString("First_Name");
                                   Firelastname = documentSnapshot.getString("Last_name");
                                   FireContact = documentSnapshot.getString("Contact_number");
                                   if(validate.compareTo(today)>=0){
                                       Intent i=new Intent(SigninActivity.this,Mainscreen.class);
                                       startActivity(i);
                                       progressBar.setVisibility(View.GONE);
                                       getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                   }
                                   else {
                                      Intent i = new Intent(SigninActivity.this,PaymentOverdue.class);
                                      i.putExtra("firstname",Firefirstname);
                                      i.putExtra("lastname",Firelastname);
                                      i.putExtra("contact",FireContact);
                                      i.putExtra("email",authemail);
                                      i.putExtra("Uid",UserID);
                                      startActivity(i);
                                      progressBar.setVisibility(View.GONE);
                                      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                   }
                               }
                           });

                       }
                       else{
                           if(task.getException() instanceof FirebaseNetworkException)
                               Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                           if(task.getException() instanceof FirebaseAuthInvalidUserException){
                               Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_LONG).show();

                           }
                           if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                           Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.GONE);
                           getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                       }

                   }
               });
               }
               else {

                   if (email.getText().toString().length() <= 7 || !email.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") && password.getText().toString().length() > 7) {
                       email.setError("Enter a valid email id");
                       progressBar.setVisibility(View.GONE);
                       getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                   }
                   if (password.getText().toString().length() <= 7) {
                       password.setError("Wrong password");
                       if (email.getText().toString().length() == 0) {
                           email.setError("Field cannot be empty");
                           progressBar.setVisibility(View.GONE);
                           getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                       }
                       if (password.getText().toString().length() == 0) {
                           password.setError("Field cannot be empty");
                           progressBar.setVisibility(View.GONE);
                           getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                       }
                   } else {
                       email.setError("Enter a valid email id");
                       password.setError("Wrong password");


                   }
                   progressBar.setVisibility(View.GONE);
                   getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

               }
           }
       });
        Signuptextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SigninActivity.this,SwipeScreen.class);
                startActivity(i);


            }
        });
        forgotpasswordtextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().length() > 8 && email.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {


                    AlertDialog.Builder passwordreset = new AlertDialog.Builder(view.getContext());
                    passwordreset.setTitle("Reset Password");
                    passwordreset.setMessage("Press YES to recieve reset link");
                    passwordreset.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            resetemail = email.getText().toString();
                            firebaseAuth.sendPasswordResetEmail(resetemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Email reset link sent", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Email reset link not sent as no user exist by this email", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    });
                    passwordreset.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    passwordreset.create().show();
                }
                else {
                    email.setError("Enter a valid email");
                }
            }
        });

    }
    public void progress() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                progressBar.setProgress(counter);

                if(counter==5000) {
                    timer.cancel();
                }

            }
        };
        timer.schedule(timerTask,0,100);
    }
}