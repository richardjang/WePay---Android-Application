package com.rdcc.wepay.Cloud.Group;

import com.rdcc.wepay.R;

public class Bitmaps{
    public int conversion(int id){
        switch(id){
            case 1:
                return myBitmaps.ICON1.getDrawable();
            case 2:
                return myBitmaps.ICON2.getDrawable();
            case 3:
                return myBitmaps.ICON3.getDrawable();
            case 4:
                return myBitmaps.PROF.getDrawable();
            default:
                return myBitmaps.DEFAULT.getDrawable();
        }
    }

    private enum myBitmaps {
        DEFAULT(0, R.drawable.person),
        ICON1(1, R.drawable.group1),
        ICON2(2, R.drawable.group2),
        ICON3(3, R.drawable.group3),
        PROF(4, R.drawable.iconprof)
        ;

        private myBitmaps(int id, int draw){
            bitID = id;
            drawable = draw;
        }
        private int bitID;
        private int drawable;

        public int getBitID() {
            return bitID;
        }

        public int getDrawable() {
            return drawable;
        }
    }
}
