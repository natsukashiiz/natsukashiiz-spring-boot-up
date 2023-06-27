package com.natsukashiiz.iicommon.utils;

import com.natsukashiiz.iicommon.common.DeviceState;
import com.natsukashiiz.iicommon.model.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;


public class Comm {
    public static String encodeString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decodeString(String str) {
        byte[] decodedBytes = Base64.getDecoder().decode(str);
        return new String(decodedBytes);
    }
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.USER_AGENT);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }

    public static Pageable getPaginate(Pagination paginate) {
        return PageRequest.of(paginate.getPage() > 0 ? paginate.getPage() - 1 : 0, paginate.getLimit(), Sort.Direction.fromString(paginate.getSortType()), paginate.getSortBy());
    }

    public static Integer getDeviceType(String userAgent) {
        String deviceType;

        String iPhone = "iPhone";
        String Android = "Android";
        String Windows = "Windows";
        String Unknown = "Unknown";

        if (userAgent.contains(iPhone)) {
            deviceType = iPhone;
        } else if (userAgent.contains(Android)) {
            deviceType = Android;
        } else if (userAgent.contains(Windows)) {
            deviceType = Windows;
        } else {
            deviceType = Unknown;
        }

        return DeviceState.valueOf(deviceType).getValue();
    }


    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
