package com.toqa.githubrepos.utils;

import java.util.List;

public class NullUtils {

    /*This function check if (list == null || list.isEmpty)*/
    public static boolean isListNullOrEmpty(List<? extends Object> objectList) {
        if (objectList != null && !objectList.isEmpty())
            return false;

        return true;
    }
}
