package com.alan.handsome.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/***
 * 功能描述:
 * 作者:qiujialiu
 * 时间:
 * 版本:1.0
 ***/

public class NumberUtil {
    public static String double2String(Double value){
//        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");//格式化设置
        String str;
        if(value!=null){
            str=decimalFormat.format(value);
        }else {
            str="0.00";
        }
        return str;
    }

    public static String double2StringAdaptive(Double value){
        if (value == null) {
            return "0";
        }
        if (value % 1 == 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#0");//格式化设置
            String result = decimalFormat.format(value);
            return result;
        }else {
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");//格式化设置
            String str;
            if(value!=null){
                str=decimalFormat.format(value);
            }else {
                str="0";
            }
            if("0.00".equals(str)){
                str="0";
            }
            if (str.matches("[\\d]+.00")) {
                str = str.substring(0,str.length() - 3);
            }
            return str;
        }
    }

    public static Double string2double(String str){
        Double value;
        try{
            if(str!=null&&str.length()>0){
                str=str.replaceAll(",","");
                value=  Double.parseDouble(str);
            }else {
                value=0d;
            }
        }
        catch (Exception e)
        {
            value=0d;
        }
        return value;
    }


    public static int string2int(String str){
        int value;
        try{
            if(str!=null&&str.length()>0){
                str=str.replaceAll(",","");
                value=  Integer.parseInt(str);
            }else {
                value=0;
            }
        }
        catch (Exception e)
        {
            value=0;
        }
        return value;
    }

    /**
     * 数字除以10的decimal次方返回
     * @param number
     * @param decimal
     * @return
     */
    public static String longDivideToDouble(long number, int decimal) {
        long divisor = Math.round(Math.pow(10,decimal));
        double d = ((double)number) / divisor;
        if (decimal == 0) {
            return String.valueOf(Math.round(d));
        }else {
            StringBuilder builder = new StringBuilder();
            builder.append("#0.");
            for (int i = 0; i < decimal; i++) {
                builder.append("#");
            }
            DecimalFormat decimalFormat = new DecimalFormat(builder.toString());//格式化设置
            String str = decimalFormat.format(d);
            return str;
        }
    }

    public static double getUGAValue(long uga, int ugaDecimal) {
        if (uga < 0)
            uga = 0;
        return uga / Math.pow(10, ugaDecimal);
    }

    /**
     * 获取整数的碳宝数
     * @param uga
     * @param ugaDecimal
     * @return
     */
    public static int getUGAValueInt(double uga, int ugaDecimal) {
        if (uga < 0)
            uga = 0;
        return (int) (uga / Math.pow(10, ugaDecimal));
    }

    public static InputFilter get2NumPoint(final int DECIMAL_DIGITS, final int MAX_LENGTH){
        InputFilter lengthfilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if (nullFilter(source)) return null;
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");//在点前后分开两段
                if(splitArray.length>0){
                    String intValue=splitArray[0];
                    int errorIndex=dValue.indexOf(".");
                    if(errorIndex==-1){
                        errorIndex=dValue.length();
                    }
                    if(intValue.length()>=MAX_LENGTH-DECIMAL_DIGITS-1&&dstart<=errorIndex){
                        if(".".equals(source.toString())){
                            return null;
                        }
                        return "";
                    }
                }
                //&&dstart==dValue.length()
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                    if (diff > 0) {
                        try {
                            return source.subSequence(start, end - diff);
                        } catch (IndexOutOfBoundsException e) {
                            return source;
                        }
                    }
                }
                if(dest.length()==MAX_LENGTH-1&&".".equals(source.toString())){
                    return "";
                }
                if(dest.length()>=MAX_LENGTH){
                    return "";
                }
                return null;
            }
        };
        return lengthfilter;
    }

    private static boolean nullFilter(CharSequence source) {
        return "".equals(source.toString());
    }

    public static String getDistanceShow(float meter) {
        if (meter < 1000) {
            return ((int)meter)+"m";
        }else {
            return NumberUtil.double2StringAdaptive(meter/1000D)+"km";
        }
    }

    public static String getDistanceShow(double meter) {
        if (meter < 1000) {
            return ((int)meter)+"m";
        }else {
            return NumberUtil.double2StringAdaptive(meter/1000D)+"km";
        }
    }

    /**
     * 向上取值 适用4位小数位数
     * 对double类型的数值保留指定位数的小数。<br>
     * 该方法舍入模式：向“远离0的”数字舍入
     * @param number    要保留小数的数字
     * @param precision 小数位数
     * @return double 如果数值较大，则使用科学计数法表示
     */
    public static String keepPrecisionToString(double number, int precision) {
        double _100number = number * Math.pow(10, precision);
        //取整
        int intger100Number = (int) (_100number+0.01);

        if (_100number - 0.001 >= intger100Number){
            //包含小数位数
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_CEILING).toPlainString();
        }else{
            //不包含小数位数，向下
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).toPlainString();
        }
    }

    /**
     * 向上取值 适用4位小数位数
     * 对double类型的数值保留指定位数的小数。<br>
     * 该方法舍入模式：向“远离0的”数字舍入
     * @param number    要保留小数的数字
     * @param precision 小数位数
     * @return double 如果数值较大，则使用科学计数法表示
     */
    public static double keepPrecisionToDouble(double number, int precision) {
        double _100number = number * Math.pow(10, precision);
        //取整
        int intger100Number = (int) (_100number+0.01);

        if (_100number - 0.001 >= intger100Number){
            //包含小数位数
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_CEILING).doubleValue();
        }else{
            //不包含小数位数，向下
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * 向上取值 适用4位小数位数
     * 对double类型的数值保留指定位数的小数。<br>
     * 该方法舍入模式：向“远离0的”数字舍入
     * @param number    要保留小数的数字
     * @param precision 小数位数
     * @return double 如果数值较大，则使用科学计数法表示
     */
    public static String keepPrecisionToString(float number, int precision) {
        double _100number = number * Math.pow(10, precision);
        //取整
        int intger100Number = (int) (_100number+0.01);

        if (_100number - 0.001 >= intger100Number){
            //包含小数位数
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_CEILING).toPlainString();
        }else{
            //不包含小数位数，向下
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).toPlainString();
        }
    }

    /**
     * 向上取值 适用4位小数位数
     * 对double类型的数值保留指定位数的小数。<br>
     * 该方法舍入模式：向“远离0的”数字舍入
     * @param number    要保留小数的数字
     * @param precision 小数位数
     * @return double 如果数值较大，则使用科学计数法表示
     */
    public static float keepPrecisionToFloat(double number, int precision) {
        double _100number = number * Math.pow(10, precision);
        //取整
        int intger100Number = (int) (_100number+0.01);

        if (_100number - 0.001 >= intger100Number){
            //包含小数位数
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_CEILING).floatValue();
        }else{
            //不包含小数位数，向下
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).floatValue();
        }
    }
}
