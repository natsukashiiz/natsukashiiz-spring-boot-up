package com.natsukashiiz.iicommon.utils;

import java.util.Objects;

public class ValidateUtil {
    private static final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String ipv4Regex = "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";
    private static final String imgRegex = "^.*(?:jpg|jpeg|png)$";

    public static boolean invalidIpv4(String str) {
        return Objects.isNull(str) || !str.matches(ipv4Regex);
    }

    public static boolean invalidEmail(String str) {
        return Objects.isNull(str) || !str.matches(emailRegex);
    }

    public static boolean invalidUsername(String str) {
        return Objects.isNull(str) || notRange(str, 4, 30);
    }

    public static boolean invalidPassword(String str) {
        return Objects.isNull(str) || notRange(str, 8, 30);
    }

    public static boolean invalidJPG(String type) {
        return Objects.isNull(type) || !type.equals("jpg");
    }

    public static boolean invalidImage(String type) {
        return Objects.isNull(type) || !type.matches(imgRegex);
    }
    public static boolean notRange(String str, int min, int max) {
        int len = str.length();
        return len < min || len > max;
    }

}
