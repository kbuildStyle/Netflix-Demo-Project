package com.example.netflix.MainScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netflix.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MovieDetails extends AppCompatActivity {
    ImageView movieimage;
    TextView moviename;
    Button Play;
    String name, image, fileurl, movieid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().hide();
        movieimage = findViewById(R.id.imagedetails);
        moviename = findViewById(R.id.moviename);
        Play = findViewById(R.id.playbutton);
        movieid=getIntent().getStringExtra("movieId");
        name=getIntent().getStringExtra("movieName");
        image=getIntent().getStringExtra("movieImageUrl");
        fileurl=getIntent().getStringExtra("movieFile");

        Glide.with(this).load(image).into(movieimage);
        moviename.setText(name);
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieDetails.this,VideoPlayer.class);
                i.putExtra("url",fileurl);
                startActivity(i);
            }
        });


    }
}