package com.example.joy.customlinearlayout.model;


import com.example.joy.customlinearlayout.CustomButton;
import com.example.joy.customlinearlayout.CustomLinearLayout;
import com.example.joy.customlinearlayout.data.Coordinates;
import java.util.List;


/**
 * Created by joy on 2017/5/11.
 */

public class NormalModel {

    public static void actionDown(int x, int y, List<CustomButton> listView, CustomLinearLayout
            customLinearLayout) {

        //点击图标时才触发点击事件
        for (CustomButton customButton : listView) {
            Coordinates coordinates = customButton.getCoordinates();
            if (x > coordinates.left && x < coordinates.right && y > coordinates.top && y < coordinates.bottom) {
                customLinearLayout.isClick();
            }
        }

    }


    public static void actionUp(int downX, int downY, int upX, int upY, int count, List<CustomButton> listView,
                                    CustomLinearLayout customLinearLayout) {
        for (int i = 0; i < count; i++) {
            Coordinates coordinates = listView.get(i).getCoordinates();

            //分别判断手指按下以及手指抬起的坐标是否坐落在同一个区域，只有同时相同才认为是单击
            if (downX > coordinates.left && downX < coordinates.right && downY > coordinates.top && downY <
                    coordinates.bottom && upX > coordinates.left && upX < coordinates.right && upY > coordinates.top
                    && upY < coordinates.bottom) {

                customLinearLayout.isSingleClick(i);


            }
        }

    }
}
