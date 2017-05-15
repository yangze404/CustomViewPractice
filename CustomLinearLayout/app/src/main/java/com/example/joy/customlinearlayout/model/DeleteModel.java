package com.example.joy.customlinearlayout.model;


import com.example.joy.customlinearlayout.CustomButton;
import com.example.joy.customlinearlayout.CustomLinearLayout;
import com.example.joy.customlinearlayout.data.Coordinates;
import java.util.List;



/**
 * Created by joy on 2017/5/11.
 */

public class DeleteModel {
    public  static void  deleteModel(int x, int y, int count, List<CustomButton> viewList, CustomLinearLayout customLinearLayout) {
        for (int i = 0; i < count; i++) {
            Coordinates deleteCoordinates = viewList.get(i).getDeleteCoordinates();
            Coordinates coordinates = viewList.get(i).getCoordinates();
            if (x > coordinates.left && x < coordinates.left + deleteCoordinates.right && y > coordinates
                    .top && y < coordinates.top + deleteCoordinates.bottom) {

                customLinearLayout.deleteSucceed(i);
                customLinearLayout.removeViewAt(i);
                customLinearLayout.requestLayout();

            }
            //检测点击的坐标是否坐落在margin中间
            if ((x < coordinates.left && x > coordinates.left - coordinates.mParams.leftMargin) || (x >
                    coordinates.right && x< coordinates.right + coordinates.mParams.rightMargin) || (y <
                    coordinates.top && y > coordinates.top - coordinates.mParams.topMargin) || (y >
                    coordinates.bottom && y < coordinates.bottom + coordinates.mParams.bottomMargin)) {
                for (CustomButton customButton : viewList) {
                    customButton.shortPress();
                }
                customLinearLayout.cancelDeleteModel();


            }
        }
    }
}



