package com.wangziqing.goubige.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WZQ_PC on 2016/3/13 0013.
 */
public class RegexUtils {
    public static boolean isPhone(String phone){
        Pattern pattern=Pattern.compile(RegexType.PHONE.toString());
        Matcher matcher=pattern.matcher(phone);
        return matcher.matches();
    }
    public static boolean isEmail(String email){
        Pattern pattern=Pattern.compile(RegexType.EMAIL.toString());
        Matcher matcher=pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isPassword(String passWord){
        if(passWord.length()>8&&passWord.length()<16){
            return true;
        }
        return false;
    }
//   public static void main(String[] str){
//        System.out.println(
//                isEmail("wangziqingde@gmail.com")+""+isPhone("13570240744")
//        );
//   }
}
