package com.project.taha.dicoveregypt.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.taha.dicoveregypt.R;
import com.project.taha.dicoveregypt.ui.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fmain,new MainFragment())
                    .commit();
        }
    }
}
