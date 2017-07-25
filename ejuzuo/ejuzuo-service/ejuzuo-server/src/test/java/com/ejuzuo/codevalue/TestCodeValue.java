package com.ejuzuo.codevalue;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.BaseTest;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/5 0005.
 */
public class TestCodeValue extends BaseTest {

    @Resource
    private CodeValueManager codeValueManager;

    @Test
    public void testSave() {
        CodeValue codeValue = new CodeValue();
        codeValue.setCollectionCode(String.valueOf(CollectionCode.fengge.getIndex()));
        codeValue.setOrderNo(1245);
        codeValue.setStatus(Status.STATUS.getIndex());
        codeValue.setParentCode(null);
        codeValue.setValueCode("11001");
        codeValue.setValueName("风格的第一个");
        codeValueManager.save(codeValue);
    }

    @Test
    public void testQueryList(){
        List<CodeValue> list = codeValueManager.queryList(CollectionCode.kongjian, null, null).getModel();
        System.out.println(list.size());
    }


    @Test
    public void testQueryByPage(){
        DataPage<CodeValue> dataPage = new DataPage<>(5);
        CodeValue codeValue = new CodeValue();
        codeValue.setCollectionCode(String.valueOf(CollectionCode.kongjian.getIndex()));
        DataPage<CodeValue> returnDataPage  = codeValueManager.queryPage(dataPage, codeValue);
        System.out.println(returnDataPage.getDataList().size());
    }

}


