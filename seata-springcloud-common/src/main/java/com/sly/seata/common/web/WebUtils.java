package com.sly.seata.common.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：yinchong
 * @create ：2019/7/23 21:06
 * @description：
 * @modified By：
 * @version:
 */
public abstract class WebUtils {

    private static ServletRequestAttributes getAttribute() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getAttribute().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getAttribute().getResponse();
    }
}
