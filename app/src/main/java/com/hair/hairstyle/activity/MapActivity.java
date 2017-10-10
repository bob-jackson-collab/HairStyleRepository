package com.hair.hairstyle.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.hair.hairstyle.R;


/**
 * Created by yunshan on 17/7/28.
 */

public class MapActivity extends AppCompatActivity{

    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

//        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//            }
//        });

        tv = (TextView) findViewById(R.id.textView);

        int location [] = new int [2];
        int location2 [] = new int[2];

        tv.getLocationInWindow(location);

        tv.getLocationOnScreen(location2);

        Log.e("111111111",location[0]+"0"+location[1]+"=========="+location2[0]+"0"+location2[1]);
    }
}
