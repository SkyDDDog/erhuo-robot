package com.lyd.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
public class RollUtil {

    // 鉴定数组下标
    private static final int bigSuccess = 0,ultimateSuccess = 1,hardSuccess = 2,success = 3,failure = 4,bigFailure = 5;
    // 骰子结果上界
    private static final int topRange = 200;
    // 骰子次数上界
    private static final int topTimes = 20;
    // 复读概率
    private static final int repeatChance = 10;

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

    public static int getArrayMax(int[] arr) {
        int max=arr[0];
        for (int j : arr) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }

    public static int getArrayMin(int[] arr) {
        int min=arr[0];
        for (int j : arr) {
            if (j < min) {
                min = j;
            }
        }
        return min;
    }

    public static String appraisalResult(int roll,int app) {
        int[] res = getAppraisal(app);
        StringBuilder builder = new StringBuilder();
        builder.append(roll).append('/').append(app);
        if (roll<=res[bigSuccess]) {
            builder.append("[大成功]");
        } else if (roll <= res[ultimateSuccess]) {
            builder.append("[极难成功]");
        } else if (roll <= res[hardSuccess]) {
            builder.append("[困难成功]");
        } else if (roll <= res[success]) {
            builder.append("[成功]");
        } else if (roll <= res[failure]) {
            builder.append("[失败]");
        } else {
            builder.append("[大失败]");
        }
        return builder.toString();
    }

    public static String r(int roll) {
        String reply;
        if (roll>topRange) {
            reply = null;
        } else {
            int randomRoll = getRandomRoll(roll, 1);
            reply = "1d"+roll+"="+randomRoll;
        }
        return reply;
    }

    public static String r (int roll,int times) {
        String reply = null;
        int total = 0;
        if (times>topTimes || roll > topRange) {
            reply = null;
        } else {
            int[] rollResults = new int[times];
            for (int i = 0; i < times; i++) {
                rollResults[i] = getRandomRoll(roll,1);
                total += rollResults[i];
            }
            String resultsMsg = Arrays.toString(rollResults)+"("+total+")";
            reply = "1d"+roll+resultsMsg;
        }
        return reply;
    }

    public static String ra(int app) {
        int randomRoll = getRandomRoll(100, 1);
        String reply;
        if (app>topRange) {
            reply = null;
        } else {
            reply = "1d100="+appraisalResult(randomRoll,app);
        }
        return reply;
    }

    public static String ra(int app,int times) {
        String reply;
        if (app>topRange || times>topTimes) {
            reply = null;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < times; i++) {
                builder.append(ra(app)).append('\n');
            }
            reply = builder.toString();
        }
        return reply;
    }

    public static String rb(int num) {
        int times,a=0;
        if (num<10) {
            times = num;
        } else {
            times = 1;
            a = num;
        }

        int randomRoll = RollUtil.getRandomRoll(100, 1);

        StringBuilder rollBuilder = new StringBuilder("b={奖励d100=");
        rollBuilder.append(randomRoll).append("|");
        int[] bonusResult = new int[times];
        for (int i = 0; i < bonusResult.length; i++) {
            bonusResult[i] = RollUtil.getRandomRoll(9,0);
            rollBuilder.append(i).append(' ');
        }
        rollBuilder.append('}');
        int bonusRoll = RollUtil.getArrayMin(bonusResult);

        int bonus = Math.min(randomRoll / 10, bonusRoll);
        int b = randomRoll%10;
        int finalRoll = bonus*10+b;
        rollBuilder.append("(").append(finalRoll).append(')').append('=').append(finalRoll).append("}");
        if (a!=0) {
            rollBuilder.append(RollUtil.appraisalResult(randomRoll,a));
        }

        return rollBuilder.toString();
    }


    public static String rp(int num) {
        int times,a=0;
        if (num<10) {
            times = num;
        } else {
            times = 1;
            a = num;
        }

        int randomRoll = RollUtil.getRandomRoll(100, 1);

        StringBuilder rollBuilder = new StringBuilder("p={惩罚d100=");
        rollBuilder.append(randomRoll).append("|");
        int[] bonusResult = new int[times];
        for (int i : bonusResult) {
            i = RollUtil.getRandomRoll(9,0);
            rollBuilder.append(i).append(' ');
        }
        rollBuilder.append('}');
        int bonusRoll = RollUtil.getArrayMax(bonusResult);
        int bonus = Math.max(randomRoll / 10, bonusRoll);
        int b = randomRoll%10;
        int finalRoll = bonus*10+b;
        rollBuilder.append("(").append(finalRoll).append(')').append('=').append(finalRoll).append("}");
        if (finalRoll==0) {
            rollBuilder.append(100).append('/').append(a).append("[大失败]");
        } else if (a!=0) {
            rollBuilder.append(RollUtil.appraisalResult(randomRoll,a));
        }

        return rollBuilder.toString();
    }


}
