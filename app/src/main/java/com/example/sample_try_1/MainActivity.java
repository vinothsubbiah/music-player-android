package com.example.sample_try_1;


import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Instantiating the MediaPlayer class

    ListView listview;
    String[] items ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_songs);

        listview = (ListView)findViewById(R.id.listView);
        runtimePermission();
        // Adding the music file to our
        // newly created object music

    }


    public void runtimePermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                displaySongs();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findsong(File file){
        ArrayList<File> arraylist = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singlefile : files){
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                arraylist.addAll(findsong(singlefile));
            }
            else{
                if(singlefile.getName().endsWith(".mp3") || singlefile.getName().endsWith(".wav")){
                    arraylist.add(singlefile);
                }
            }
        }
        return arraylist;
    }

    public void displaySongs(){
        ArrayList<File> mysongs = findsong(Environment.getExternalStorageDirectory());
        items = new String[mysongs.size()];
        for(int i=0; i< mysongs.size(); i++){
            items[i] = mysongs.get(i).getName().replace(".mp3","").replace(".wav","");
        }
        customAdapter customAdapter = new customAdapter();
        listview.setAdapter(customAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName = (String) listview.getItemAtPosition(i);

                startActivity(new Intent(getApplicationContext(), PlayerActivity1.class)
                .putExtra("songs", mysongs)
                        .putExtra("songname",songName)
                        .putExtra("pos", i)
                );
            }
        });
    }
    class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.song_list, null);
            TextView txt_song = view.findViewById(R.id.txt_song);
            txt_song.setSelected(true);
            txt_song.setText(items[i]);
            return view;
        }
    }
}