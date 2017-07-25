package com.ejuzuo.server.designer.manager;


import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.option.DesignerOption;
import com.ejuzuo.common.vo.DesignerVO;

import java.util.List;

public interface DesignerManager {
    int deleteById(Integer id);

    Designer save(Designer record);

    Designer selectById(Integer id);

    ModelResult<Boolean> updateById(Designer record);

    ModelResult<Designer> queryByMemberId(Integer memberId);

    ModelResult<DesignerVO> queryVOByMemberId(Integer memberId);
    
    ModelResult<DesignerVO> queryVOByDesignerId(Integer designerId);

    ModelResult<List<Designer>> queryList(DesignerOption option);

    ModelResult<DataPage<Designer>> adminQueryPage(DesignerOption option, DataPage<Designer> dataPage);

    PageResult<Designer> queryPage(DesignerOption option, DataPage<Designer> dataPage);

    ModelResult<DataPage<Designer>> queryHomePage(DesignerOption option, DataPage<Designer> dataPage);

    ModelResult<Integer> count(String area, String style, Integer type);

    ModelResult<DataPage<DesignerVO>> queryDesigner(String area, String style, Integer type, DataPage<DesignerVO> dataPage);

	BaseResult updateHomed(Integer id, Integer homed);
}