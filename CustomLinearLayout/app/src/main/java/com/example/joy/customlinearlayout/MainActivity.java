package com.example.joy.customlinearlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.example.joy.customlinearlayout.CustomLinearLayout.OnCustomClickListener;


public class MainActivity extends AppCompatActivity {
public  static  MainActivity mMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainActivity=this;

        CustomButton customButton= (CustomButton) findViewById(R.id.weChat);
        customButton.setText("微信2.0");

        CustomLinearLayout customLinearLayout= (CustomLinearLayout) findViewById(R.id.custom_ll);
        customLinearLayout.setOnCustomListener(new OnCustomClickListener() {
            @Override
            public void onCustomSingleClick(int i) {
                Toast.makeText(getApplicationContext(), "单击位置："+i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCustomEnterDeleteModel() {
                Toast.makeText(getApplicationContext(), "长按", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCustomCancelDeleteModel() {
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCustomDeleteSucceed(int i) {
                Toast.makeText(getApplicationContext(), "删除成功，位置："+i, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.bt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
    }
}
