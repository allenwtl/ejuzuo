package com.ejuzuo.server.designer.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.option.DesignerWorkOption;
import com.ejuzuo.common.service.DesignerWorkService;
import com.ejuzuo.common.vo.DesignerWorkVO;
import com.ejuzuo.server.designer.manager.DesignerWorkManager;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */

@Service("designerWorkService")
public class DesignerWorkServiceImpl implements DesignerWorkService {

    @Resource
    private DesignerWorkManager designerWorkManager ;
    @Autowired
	private TaskExecutor taskExecutor;
	@Resource
	private BaseIndexManager<DesignerWork> baseIndexManager;

    @Override
    public ModelResult<DesignerWork> save(DesignerWork designerWork) {
    	DesignerWork obj = designerWorkManager.save(designerWork);
//    	this.updateESIndex(obj.getId());
        return new ModelResult<>(obj);
    }

    @Override
    public ModelResult<Boolean> updateById(DesignerWork designerWork) {
        int size = designerWorkManager.updateById(designerWork);
        if(size == 1){
        	this.updateESIndex(designerWork.getId());
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>().withError("exception", "更新报错");
    }

    @Override
    public ModelResult<List<DesignerWork>> queryList(DesignerWorkOption option) {
        return designerWorkManager.queryList(option);
    }

	@Override
	public PageResult<DesignerWork> queryPage(DataPage<DesignerWork> dataPage, DesignerWorkOption option) {
		PageResult<DesignerWork> result = new PageResult<DesignerWork>();
		dataPage = designerWorkManager.queryPage(dataPage, option);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public ModelResult<DesignerWork> queryById(Integer id,DesignerWorkOption option) {
		ModelResult<DesignerWork> result = new ModelResult<DesignerWork>();
		DesignerWork obj = designerWorkManager.selectById(id,option);
		result.setModel(obj);
		return result;
	}

    @Override
    public ModelResult<DesignerWorkVO> queryVOById(Integer id) {
        return new ModelResult<>(designerWorkManager.queryVOById(id));
    }

    @Override
    public ModelResult<Integer> countWork(Integer memberId, EditStatus editStatus, Status status) {
        return designerWorkManager.countWork(memberId, editStatus, status);
    }

    @Override
    public PageResult<DesignerWorkVO> queryUnion(String area, String style, Integer type , DataPage<DesignerWork> dataPage) {
        return designerWorkManager.queryUnion(area, style, type, dataPage);
    }

    @Override
    public ModelResult<Integer> countUnion(String area, String style, Integer type) {
        return designerWorkManager.countUnion(area, style, type);
    }

    @Override
    public ModelResult<DataPage<DesignerWorkVO>> queryByDesigner(DesignerWorkOption designerWorkOption, DataPage<DesignerWork> dataPage) {
        return designerWorkManager.queryByDesigner(designerWorkOption, dataPage);
    }

    @Override
    public ModelResult<Integer> countByDesigner(Integer memberId) {
        return designerWorkManager.countByDesigner(memberId);
    }

	@Override
	public ModelResult<Boolean> updateStatus(Integer id, Integer status) {
		ModelResult<Boolean> result = new ModelResult<Boolean>();
		Boolean updated = designerWorkManager.updateStatus(id,status);
		if (updated) {
			this.updateESIndex(id);
		}
		result.setModel(updated);
		return result;
	}

    @Override
    public ModelResult<Boolean> deleteByMemberId(Integer memberId, Integer id) {
        return designerWorkManager.deleteById(id, memberId);
    }

    private void updateESIndex(Integer id) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				DesignerWork obj = designerWorkManager.selectById(id, null);
				if (obj.getStatus().equals(Status.STATUS.getIndex()) && obj.getEditStatus().equals(EditStatus.PUBLISHED.getIndex())) {
					baseIndexManager.createIndex(obj);
				}else {
					baseIndexManager.deleteById(id);
				}
			}
		});
	}
}
