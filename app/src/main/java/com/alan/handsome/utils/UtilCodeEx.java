package com.alan.handsome.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 类说明：UtilCode的某些方法的完善
 * 作者：qiujialiu
 * 时间：2018/8/1
 */

public class UtilCodeEx {
    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
     * <p>联通：130、131、132、145、155、156、171、175、176、185、186</p>
     * <p>电信：133、153、173、177、180、181、189</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     */
//    public static final String MY_REGEX_MOBILE_EXACT  = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,1,3,5-8])|(18[0-9])|(147)|(166)|(19[89]))\\d{8}$";
    //后面改的需求：是1开头加上号码位数是十一位
    public static final String MY_REGEX_MOBILE_EXACT = "^(1)\\d{10}$";
    /**
     * 验证手机号（精确）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(final CharSequence input) {
        return isMatch(MY_REGEX_MOBILE_EXACT, input);
    }

    /**     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    public static String format(long time,String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }

    /**
     * 检验字符串是否可以转换成颜色值
     * @param color
     * @return
     */
    public static boolean isValidColor(String color) {
        Pattern pattern = Pattern.compile("(#[0-9A-Fa-f]{6})||(#[0-9A-Fa-f]{3})");
        return pattern.matcher(color).matches();
    }

}
