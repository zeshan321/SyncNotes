package com.aslam.zeshan.syncnotes.Util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public boolean checkString(String text) {
        if (text.length() < 1 || text.equals("") || text.replace(" ", "").length() < 1 || text.replaceAll("\\s", "").length() < 1 || TextUtils.isEmpty(text) || String.valueOf(text.charAt(0)).equals("") || String.valueOf(text.charAt(0)).equals(" ")) {
            return false;
        }

        return true;
    }

    public boolean isEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
}