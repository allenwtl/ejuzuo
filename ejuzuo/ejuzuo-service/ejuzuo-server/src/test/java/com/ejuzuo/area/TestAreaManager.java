package com.ejuzuo.area;

import com.ejuzuo.BaseTest;
import com.ejuzuo.server.area.manager.AreaManager;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */
public class TestAreaManager  extends BaseTest {

    @Resource
    private AreaManager areaManager ;

    @Test
    public void testQueryAll(){
        System.out.println(areaManager.queryAll(null).getModel().size());
    }


}
