package com.ejuzuo.web.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tianlun.wu on 2016/5/6 0006.
 */
public class ExecutorUtil {


    public static ExecutorService singleThread(){
        return Executors.newSingleThreadExecutor();
    }

    public static ExecutorService fixedThread(){
        return Executors.newFixedThreadPool(5);
    }

}
