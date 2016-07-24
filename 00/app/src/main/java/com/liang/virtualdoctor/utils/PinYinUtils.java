package com.liang.virtualdoctor.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class PinYinUtils {

    public static String getPinYinHead(String str){

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = new String[0];
            try {
                pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word, new HanyuPinyinOutputFormat());
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert.substring(0,1).toUpperCase().trim();//返回首个汉字的首字母
    }
}
