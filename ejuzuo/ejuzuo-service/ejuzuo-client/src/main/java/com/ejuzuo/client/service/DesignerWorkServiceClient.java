package com.ejuzuo.client.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.memcachedclient.MemcachedClient;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.option.DesignerWorkOption;
import com.ejuzuo.common.service.DesignerWorkService;
import com.ejuzuo.common.vo.DesignerWorkVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/19 0019.
 */
@Service("designerWorkServiceClient")
public class DesignerWorkServiceClient implements DesignerWorkService {

    @Resource(name = "designerWorkService")
    private DesignerWorkService designerWorkService;

    @Resource(name = "memcachedClient")
    public MemcachedClient memcachedClient;

    @Override
    public ModelResult<DesignerWork> save(DesignerWork designerWork) {
        return designerWorkService.save(designerWork);
    }

    @Override
    public ModelResult<Boolean> updateById(DesignerWork designerWork) {
        return designerWorkService.updateById(designerWork);
    }

    @Override
    public ModelResult<List<DesignerWork>> queryList(DesignerWorkOption option) {
        return designerWorkService.queryList(option);
    }

    @Override
    public PageResult<DesignerWork> queryPage(DataPage<DesignerWork> dataPage, DesignerWorkOption option) {
        return designerWorkService.queryPage(dataPage, option);
    }

    @Override
    public ModelResult<DesignerWork> queryById(Integer id,DesignerWorkOption option) {
        return designerWorkService.queryById(id, option);
    }

    @Override
    public ModelResult<DesignerWorkVO> queryVOById(Integer id) {
        return designerWorkService.queryVOById(id);
    }

    @Override
    public ModelResult<Integer> countWork(Integer memberId, EditStatus editStatus, Status status) {
        return designerWorkService.countWork(memberId, editStatus, status);
    }

    @Override
    public PageResult<DesignerWorkVO> queryUnion(String area, String style, Integer type, DataPage<DesignerWork> dataPage) {
        return  designerWorkService.queryUnion(area, style, type, dataPage);
    }

    @Override
    public ModelResult<Integer> countUnion(String area, String style, Integer type) {
        return designerWorkService.countUnion(area, style, type);
    }

    @Override
    public ModelResult<DataPage<DesignerWorkVO>> queryByDesigner(DesignerWorkOption designerWorkOption, DataPage<DesignerWork> dataPage) {
        return designerWorkService.queryByDesigner(designerWorkOption, dataPage);
    }

    @Override
    public ModelResult<Integer> countByDesigner(Integer memberId) {
        return designerWorkService.countByDesigner(memberId);
    }

    @Override
    public ModelResult<Boolean> updateStatus(Integer id, Integer status) {
        return designerWorkService.updateStatus(id, status);
    }

    @Override
    public ModelResult<Boolean> deleteByMemberId(Integer memberId, Integer id) {
        return designerWorkService.deleteByMemberId(memberId, id);
    }
}
