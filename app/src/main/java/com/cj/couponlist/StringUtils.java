package com.cj.couponlist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class StringUtils {
    public static String safelyTrim(String str) {
        return str == null ? "" : str.trim();
    }

    public static boolean isNull(String str) {
        return isEmpty(str) || str.trim().equalsIgnoreCase("null") || str.trim().equalsIgnoreCase("(null)");
    }

    public static String filterNull(String str) {
        return isNull(str) ? "" : str;
    }

    public static boolean isTrueEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static String format(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    /**
     * 简单验证手机号码
     */
    public static boolean checkSimplePhone(String mobileNumber){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile("^1\\d{10}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     * 简单验证邮编
     */
    public static boolean checkPostCode(String mobileNumber){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+([.a-zA-Z0-9_-])+$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }


    /**
     * UTF8编码
     */
    public static String getUTF8XMLString(String xml) {
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }
}
