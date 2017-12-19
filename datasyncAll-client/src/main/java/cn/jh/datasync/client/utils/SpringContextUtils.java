package cn.jh.datasync.client.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * ApplictionContext的工具,set设置,get获得
 * @author wanlongfei
 *
 */
public class SpringContextUtils {
    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringContextUtils.context = context;
    }
    public static ApplicationContext getContext(){
        return context;
    }
}