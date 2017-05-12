package com.example.joy.customlinearlayout.model;

import android.util.Log;
import android.widget.Toast;
import com.example.joy.customlinearlayout.CustomButton;
import com.example.joy.customlinearlayout.CustomLinearLayout;
import com.example.joy.customlinearlayout.MainActivity;
import com.example.joy.customlinearlayout.data.Coordinates;
import java.util.List;


/**
 * Created by joy on 2017/5/11.
 */

public class NormalModel {

    public void normalModelActionDown(int X, int Y, List<CustomButton> listView , CustomLinearLayout customLinearLayout){

            //点击图标时才触发点击事件
            for (CustomButton customButton : listView) {
                Coordinates coordinates = customButton.getCoordinates();
                if (X > coordinates.left && X < coordinates.right && Y > coordinates.top && Y < coordinates
                        .bottom) {
                    customLinearLayout.isClick();
                }
            }

        }



    public void normalModelActionUp(int downX,int downY,int upX,int upY,int count , List<CustomButton> listView){
        for (int i = 0; i < count; i++) {
            Coordinates coordinates = listView.get(i).getCoordinates();

            //分别判断手指按下以及手指抬起的坐标是否坐落在同一个区域，只有同时相同才认为是单击
            if (downX > coordinates.left && downX < coordinates.right && downY > coordinates.top && downY < coordinates.bottom
                    && upX > coordinates.left && upX < coordinates.right && upY > coordinates.top && upY < coordinates.bottom
                    ) {
                Toast.makeText(MainActivity.mMainActivity, "单击：" + coordinates.count, Toast.LENGTH_SHORT).show();
                Log.e("yz", "点击：" + coordinates.count);
            }
        }

    }
}
