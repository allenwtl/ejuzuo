package com.ejuzuo.server.activity.service.impl;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.ActivityInfo;
import com.ejuzuo.common.domain.ContentInfo;
import com.ejuzuo.common.domain.type.ContentInfoRelatedType;
import com.ejuzuo.common.option.ActivityInfoOption;
import com.ejuzuo.common.service.ActivityInfoService;
import com.ejuzuo.common.util.StringUtil;
import com.ejuzuo.common.vo.ActivityInfoVO;
import com.ejuzuo.server.activity.manager.ActivityInfoManager;
import com.ejuzuo.server.content.manager.ContentInfoManager;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;

@Service("activityInfoService")
public class ActivityInfoServiceImpl implements ActivityInfoService{

	@Resource
	private ActivityInfoManager activityInfoManager;
	@Resource
	private ContentInfoManager contentInfoManager;
	@Autowired
    private TransactionTemplate transactionTemplate;
	@Autowired
	private TaskExecutor taskExecutor;
	@Resource
	private BaseIndexManager<ActivityInfo> baseIndexManager;
	private static transient final Logger logger = LoggerFactory.getLogger(ActivityInfoServiceImpl.class);
	
	@Override
	public ModelResult<ActivityInfo> save(ActivityInfo obj) {
		ModelResult<ActivityInfo> result = new ModelResult<ActivityInfo>();
		result.setModel(activityInfoManager.save(obj));
		return result;
	}

	@Override
	public ModelResult<ActivityInfo> queryById(Integer id) {
		ModelResult<ActivityInfo> result = new ModelResult<ActivityInfo>();
		ActivityInfo obj = activityInfoManager.queryById(id);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<ActivityInfoVO> queryVOById(Integer id) {
		return new ModelResult<>(activityInfoManager.queryVOById(id));
	}

	@Override
	public PageResult<ActivityInfo> queryPage(DataPage<ActivityInfo> dataPage, ActivityInfoOption option) {
		PageResult<ActivityInfo> result = new PageResult<ActivityInfo>();
		dataPage = activityInfoManager.queryPage(dataPage, option);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public ModelResult<ActivityInfo> add(ActivityInfo obj, String content) {
		ModelResult<ActivityInfo> result = new ModelResult<ActivityInfo>();
        if (StringUtils.isBlank(content)) {
            content = "";
        }
        final String finalContent = content;
        ActivityInfo model = transactionTemplate.execute(new TransactionCallback<ActivityInfo>() {
            @Override
            public ActivityInfo doInTransaction(TransactionStatus status) {
            	ActivityInfo activityInfo = activityInfoManager.save(obj);
            	ContentInfo contentInfo = new ContentInfo();
            	contentInfo.setContent(finalContent);
            	contentInfo.setRelatedType(ContentInfoRelatedType.ACTIVITY.getIndex());
            	contentInfo.setRelatedId(activityInfo.getId());
            	contentInfo.setUpdateTime(Calendar.getInstance());
            	contentInfoManager.insert(contentInfo);
				return activityInfo;
            }
        });
        result.setModel(model);
        logger.info("活动新增 成功 [{}]", obj.getTitle());
        return result;
	}

	@Override
	public BaseResult updateWithContent(ActivityInfo obj) {
		BaseResult baseResult = new BaseResult();
        if (StringUtils.isBlank(obj.getContent())) {
        	obj.setContent("");
        }
        Boolean updated = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
            	ActivityInfo activityInfo = activityInfoManager.update(obj);
            	ContentInfo contentInfo = new ContentInfo();
            	contentInfo.setContent(obj.getContent());
            	contentInfo.setRelatedType(ContentInfoRelatedType.ACTIVITY.getIndex());
            	contentInfo.setRelatedId(activityInfo.getId());
            	contentInfo.setUpdateTime(Calendar.getInstance());
            	contentInfoManager.updateByRelate(contentInfo);
				return true;
            }
        });
        if (!updated) {
            baseResult.withError("UPDATE", "活动更新失败");
            return baseResult;
        }
        
        // 更新 es 索引
        updateESIndex(obj.getId());
        logger.info("活动更新 成功 [{}]", obj.getTitle());
        return baseResult;
	}

	@Override
	public PageResult<ActivityInfoVO> queryVOPage(DataPage<ActivityInfoVO> dataPage, ActivityInfoOption option) {
		PageResult<ActivityInfoVO> result = new PageResult<>();
		dataPage = activityInfoManager.queryVOPage(dataPage, option);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public ModelResult<Integer> count(ActivityInfoOption option) {
		return activityInfoManager.count(option);
	}

	@Override
	public ModelResult<ActivityInfo> queryWithContent(Integer id) {
		ModelResult<ActivityInfo> result = new ModelResult<ActivityInfo>();
		ActivityInfo obj = activityInfoManager.queryWithContent(id);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<Integer> updateViewCount(Integer id) {
		ModelResult<Integer> result = new ModelResult<Integer>();
		result.setModel(activityInfoManager.updateViewCount(id));
		return result;
	}
	
	@Override
	public ModelResult<Boolean> updateStatus(Integer id, Integer status) {
		ModelResult<Boolean> result = new ModelResult<Boolean>();
		Boolean updated = activityInfoManager.updateStatus(id,status);
		if (updated) {
			if (status == Status.STATUS.getIndex()) {
				this.updateESIndex(id);
			}else if (status == Status.UN_STATUS.getIndex()) {
				this.deleteESIndex(id);
			}
		}
		result.setModel(updated);
		return result;
	}
	
	private void deleteESIndex(Integer id) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				baseIndexManager.deleteById(id);
			}
		});
	}

	private void updateESIndex(Integer id) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				ActivityInfo obj = activityInfoManager.queryWithContent(id);
				if (obj.getStatus().equals(Status.STATUS.getIndex()) && obj.getEditStatus().equals(EditStatus.PUBLISHED.getIndex())) {
					obj.setContent(StringUtil.dealHtmlString(obj.getContent()));
					baseIndexManager.createIndex(obj);
				}
			}
		});
	}
}
