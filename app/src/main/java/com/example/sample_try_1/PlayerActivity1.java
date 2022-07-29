package com.example.sample_try_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity1 extends AppCompatActivity {
    static MediaPlayer music;
    Button play, loop, stop, prev, next;
    TextView txtSongName;
    ImageView songImage;
    String songName;

    ArrayList<File> mySongs;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        loop = findViewById(R.id.loop);
        stop = findViewById(R.id.stop);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        txtSongName = findViewById(R.id.txtSongName);
        songImage = findViewById(R.id.songImage);
        Uri uri;

        if(music != null){
            music.start();
            music.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mySongs = (ArrayList)bundle.getParcelableArrayList("songs");
        String sName = intent.getStringExtra("songname");
        position = bundle.getInt("pos", 0);
        txtSongName.setSelected(true);
        uri = Uri.parse(mySongs.get(position).toString());
        songName = mySongs.get(position).getName();
        txtSongName.setText(songName);

        music = MediaPlayer.create(
                getApplicationContext(), uri);
        music.start();

        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                position = ((position+1)%mySongs.size());
                Uri uri = Uri.parse(mySongs.get(position).toString());
                music = MediaPlayer.create(getApplicationContext(), uri);
                songName = mySongs.get(position).getName();
                txtSongName.setText(songName);
                music.start();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music.isPlaying()){
                    play.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24);
                    music.pause();
                }
                else{
                    play.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    music.start();
                }
            }
        });

        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.pause();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music.isPlaying()){
                    play.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24);

                }
                music.stop();
                Uri uri = Uri.parse(mySongs.get(position).toString());
                music = MediaPlayer.create(
                        getApplicationContext(), uri);

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.stop();
                music.release();
                position = ((position-1)<0)?(mySongs.size()-1):position-1;
                Uri uri = Uri.parse(mySongs.get(position).toString());
                music = MediaPlayer.create(getApplicationContext(), uri);
                songName = mySongs.get(position).getName();
                txtSongName.setText(songName);
                music.start();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                music.stop();
                music.release();
                position = ((position+1)%mySongs.size());
                Uri uri = Uri.parse(mySongs.get(position).toString());
                music = MediaPlayer.create(getApplicationContext(), uri);
                songName = mySongs.get(position).getName();
                txtSongName.setText(songName);
                music.start();
            }
        });

        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music.isLooping()){
                    music.setLooping(false);
                    loop.setBackgroundResource(R.drawable.ic_baseline_repeat_24);
                }
                else{
                    music.setLooping(true);
                    loop.setBackgroundResource(R.drawable.ic_baseline_repeat_one_24);
                }
            }
        });

    }
}