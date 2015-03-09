package com.unique.countsystem.utils;

import java.util.regex.Pattern;

/**
 * Translate DATE to standard pattern
 * ************************
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: CountSystem
 * DATE: 2015/3/9
 */
public class DAYUtils {
    /**
     * parse DAY from String to Integer
     * @param time pattern should be "20130405"
     * @return int time
     */
    public static int fromString(String time) throws IllegalArgumentException{
        time = time.trim();
        Pattern pattern = Pattern.compile("^(20\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$");
        if (!pattern.matcher(time).matches()){
            throw new IllegalArgumentException("argument is illegal, it should be day like \"20130331\"");
        }
        return Integer.parseInt(time);
    }

    public static boolean ifMatchPattern(int i){

        return false;
    }
}
