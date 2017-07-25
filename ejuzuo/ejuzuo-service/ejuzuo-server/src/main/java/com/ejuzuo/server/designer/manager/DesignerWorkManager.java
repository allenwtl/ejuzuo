package com.ejuzuo.server.designer.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.option.DesignerWorkOption;
import com.ejuzuo.common.vo.DesignerWorkVO;

import java.util.List;

public interface DesignerWorkManager {
    ModelResult<Boolean> deleteById(Integer id, Integer memberId);

    DesignerWork save(DesignerWork record);

    DesignerWork selectById(Integer id,DesignerWorkOption option);

    DesignerWorkVO queryVOById(Integer id);

    int updateById(DesignerWork record);


    ModelResult<List<DesignerWork>> queryList(DesignerWorkOption option);

	DataPage<DesignerWork> queryPage(DataPage<DesignerWork> dataPage, DesignerWorkOption option);


    ModelResult<Integer> countWork(Integer memberId, EditStatus editStatus, Status status);

    PageResult<DesignerWorkVO> queryUnion(String area, String style, Integer type,  DataPage<DesignerWork> dataPage);

    ModelResult<Integer> countUnion(String area, String style, Integer type);

    ModelResult<DataPage<DesignerWorkVO>> queryByDesigner(DesignerWorkOption designerWorkOption, DataPage<DesignerWork> dataPage);

    ModelResult<Integer> countByDesigner(Integer memberId);

	Boolean updateStatus(Integer id, Integer status);
}