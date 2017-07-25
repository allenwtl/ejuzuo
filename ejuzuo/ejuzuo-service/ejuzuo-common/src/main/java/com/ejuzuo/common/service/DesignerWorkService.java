package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.option.DesignerWorkOption;
import com.ejuzuo.common.vo.DesignerWorkVO;

import java.util.List;

/**
 * Created by allen on 2016/4/4.
 */


public interface DesignerWorkService {

    ModelResult<DesignerWork> save(DesignerWork designerWork);

    ModelResult<Boolean> updateById(DesignerWork designerWork);

    ModelResult<List<DesignerWork>> queryList(DesignerWorkOption option);
    
    PageResult<DesignerWork> queryPage(DataPage<DesignerWork> dataPage, DesignerWorkOption option);
    
    ModelResult<DesignerWork> queryById(Integer id, DesignerWorkOption option);

    ModelResult<DesignerWorkVO> queryVOById(Integer id);

    ModelResult<Integer> countWork(Integer memberId, EditStatus editStatus, Status status);

    PageResult<DesignerWorkVO> queryUnion(String area, String style, Integer type, DataPage<DesignerWork> dataPage);

    ModelResult<Integer> countUnion(String area, String style, Integer type);

    ModelResult<DataPage<DesignerWorkVO>> queryByDesigner(DesignerWorkOption designerWorkOption, DataPage<DesignerWork> dataPage);

    ModelResult<Integer> countByDesigner(Integer memberId);

	ModelResult<Boolean> updateStatus(Integer id, Integer status);

    ModelResult<Boolean> deleteByMemberId(Integer memberId, Integer id);
}
