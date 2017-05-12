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

public class DeleteModel {

    public void  deleteModel(int X, int Y, int count, List<CustomButton> viewList, CustomLinearLayout customLinearLayout) {
        for (int i = 0; i < count; i++) {
            Coordinates deleteCoordinates = viewList.get(i).getDeleteCoordinates();
            Coordinates coordinates = viewList.get(i).getCoordinates();
            if (X > coordinates.left && X < coordinates.left + deleteCoordinates.right && Y > coordinates
                    .top && Y < coordinates.top + deleteCoordinates.bottom) {
                Toast.makeText(MainActivity.mMainActivity, "删除：" + i, Toast.LENGTH_SHORT).show();
                Log.e("yz", "删除：" + i);

                customLinearLayout.removeViewAt(i);
                customLinearLayout.requestLayout();

            }
            //检测点击的坐标是否坐落在margin中间
            if ((X < coordinates.left && X > coordinates.left - coordinates.mParams.leftMargin) || (X >
                    coordinates.right && X < coordinates.right + coordinates.mParams.rightMargin) || (Y <
                    coordinates.top && Y > coordinates.top - coordinates.mParams.topMargin) || (Y >
                    coordinates.bottom && Y < coordinates.bottom + coordinates.mParams.bottomMargin)) {
                for (CustomButton customButton : viewList) {
                    customButton.shortPress();
                }
                customLinearLayout.cancelDeleteModel();
                Toast.makeText(MainActivity.mMainActivity, "取消删除模式", Toast.LENGTH_SHORT).show();

            }
        }
    }
}



