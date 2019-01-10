/**
 * Copyright (c) 2015 - 2016 eya Inc. All rights reserved.
 */
package cn.bocom.other.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 
 * 生成随机ID，用于图片
 * 
 * @create Jkf
 * @createDate 2016年3月21日 下午12:29:38
 * @update pengjunhao
 * @updateDate 2016-3-21 12:29:55
 */
public class RandomUtil {
    private static Random R = new Random();
    private static final String ZERO20 = "00000000000000000000";

    /**
     * 根据时间生成ID
     * 
     * @param length 时间长度
     * @return String ID
     * @Author : pengjunhao. create at 2016年3月21日 下午1:25:29
     */
    public static String getTimeRandomId(int length) {
        String val = "";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String pre = simpleDateFormat.format(date);
        if (length <= 14) {
            val = pre;
        } else {
            int i = length - 14;
            String aft = getRandomId(i);
            val = pre + aft;
        }
        return val;
    }

    /**
     * 根据时间生成序列：长度16位，10位日期（年份取2位，yyMMddHHmm）+6位随机数
     * 
     * @return 16位随机数
     * @Author : ll. create at 2016年3月21日 下午1:25:29
     */
    public static String get16TimeRandom() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = simpleDateFormat.format(date);
        dateStr = dateStr.substring(2, dateStr.length() - 2);
        return dateStr + getRandomId(6);
    }

    /**
     * 获取随机数
     * 
     * @param length 时间长度
     * @return String 随机数
     * @Author : pengjunhao. create at 2016年3月21日 下午1:26:43
     */
    public static String getRandomId(int length) {
        String val = "";
        if (length <= 10) {
            val = String.valueOf(Math.abs(R.nextInt()));
        } else if (length <= 19) {
            val = String.valueOf(Math.abs(R.nextLong()));
        } else if (length <= 29) {
            val = String.valueOf(Math.abs(R.nextLong())) + String.valueOf(Math.abs(R.nextInt()));
        } else {
            val = String.valueOf(Math.abs(R.nextLong())) + String.valueOf(Math.abs(R.nextLong()));
        }
        if (length < val.length()) {
            val = val.substring(0, length);
        } else {
            val = val + ZERO20;
            val = val.substring(0, length);
        }
        return val;
    }

    /***
     * 生成32位UUID
     * 
     * @return
     */
    public static String getUUID32() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;

    }
}
