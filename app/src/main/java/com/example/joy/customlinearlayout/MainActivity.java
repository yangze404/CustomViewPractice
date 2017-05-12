package com.example.joy.customlinearlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
public  static  MainActivity mMainActivity;
    private CustomLinearLayout mCustomLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainActivity=this;
        mCustomLinearLayout= (CustomLinearLayout) findViewById(R.id.custom_ll);

        findViewById(R.id.bt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
    }
}
