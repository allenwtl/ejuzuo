package com.ejuzuo.digitalFurniture;

import com.ejuzuo.BaseTest;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.type.DigitalShelfStatus;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by allen on 2016/7/3.
 */
public class TestDigitalFurniture extends BaseTest {

    @Resource
    private DigitalFurnitureManager digitalFurnitureManager ;

    @Test
    public void testQueryLastTimeAndPayBrand(){
//        DigitalFurniture digitalFurniture = new DigitalFurniture();
//        digitalFurniture.setSpaceCategory("10002");
//        digitalFurniture.setStatus(Status.STATUS.getIndex());
//        digitalFurniture.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
//        List<DigitalFurniture> digitalFurnitureList = digitalFurnitureManager.queryLastTimeAndPayBrand(digitalFurniture).getModel();
//        System.out.println(digitalFurnitureList.size());
    }


    @Test
    public void testDeleteMemcachedLastTimeAndPayBrand(){
        digitalFurnitureManager.deleteMemcachedLastTimeAndPayBrand(3);
    }

}
