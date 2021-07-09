package com.lj.mpdemo.utils;

/**
 * @author luojing
 * @version 1.0
 * @date 2021/7/9 16:00
 * <p>
 * 正则工具
 */
public class RegexUtil {

    /**
     * 生成 0-from 数字的正则表达式（包括from）
     *
     * @param from 目标数字 例如255 -> 2(?:[0-4]\d{1}|5[0-5])|[01]?\d{1,2}
     * @return 返回的正则表达式
     */
    public static String genZeroToInt(Integer from) throws Exception {
        if (from < 0) {
            throw new Exception("from value can't be less than 0");
        }
        String s = from.toString();
        int length = s.length();
        char[] numChars = new char[length];
        s.getChars(0, length, numChars, 0);

        //拼接低级正则
        StringBuilder lowBuilder = new StringBuilder();
        lowBuilder.append("|[");
        // i1 最高位值
        int i1 = Integer.parseInt(numChars[0] + "");
        for (int i = 0; i < i1; i++) {
            lowBuilder.append(i);
        }
        lowBuilder.append("]?\\d{1,");
        lowBuilder.append((length - 1)).append("}");
        //拼接高级正则
        String base = numChars[length - 2] + "[0-" + numChars[length - 1] + "]";
        for (int i = numChars.length - 3, j = 1; i >= 0; i--, j++) {
            int upper = Integer.parseInt(numChars[i + 1] + "");
            base = numChars[i] + "(?:[0-" + (upper - 1) + "]\\d{" + j + "}|" + base + ")";
        }

        return base + lowBuilder.toString();
    }
}
