package com.lyd.utils;

public class RandomUtil {

    public static int getRandomRoll(int max,int min) {
        return (int) (min+Math.random()*max-min+1);
    }

    public static int[] getAppraisal(int appraisal) {
        int bigSuccess = 1;
        int ultimateSuccess = appraisal/5;
        int hardSuccess = appraisal/2;
        int success = appraisal;
        int failture = 99;
        int bigFailture = 100;
        return new int[]{bigSuccess, ultimateSuccess, hardSuccess, success, failture, bigFailture};
    }

}
