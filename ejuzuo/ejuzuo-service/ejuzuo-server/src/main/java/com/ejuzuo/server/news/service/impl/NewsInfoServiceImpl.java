package com.ejuzuo.server.news.service.impl;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.ContentInfo;
import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.common.domain.type.ContentInfoRelatedType;
import com.ejuzuo.common.option.NewsInfoOption;
import com.ejuzuo.common.service.NewsInfoService;
import com.ejuzuo.common.util.StringUtil;
import com.ejuzuo.common.vo.NewsInfoVO;
import com.ejuzuo.server.content.manager.ContentInfoManager;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;
import com.ejuzuo.server.news.manager.NewsInfoManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service("newsInfoService")
public class NewsInfoServiceImpl implements NewsInfoService {

	@Resource
	private NewsInfoManager newsInfoManager;
	@Resource
	private ContentInfoManager contentInfoManager;
	@Autowired
    private TransactionTemplate transactionTemplate;
	@Autowired
	private TaskExecutor taskExecutor;
	@Resource
	private BaseIndexManager<NewsInfo> baseIndexManager;
	private static transient final Logger logger = LoggerFactory.getLogger(NewsInfoServiceImpl.class);
	
	@Override
	public ModelResult<NewsInfo> queryById(Integer id) {
		ModelResult<NewsInfo> result = new ModelResult<NewsInfo>();
		NewsInfo obj = newsInfoManager.queryById(id);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<NewsInfo> save(NewsInfo obj) {
		ModelResult<NewsInfo> result = new ModelResult<NewsInfo>();
		newsInfoManager.insert(obj);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<Integer> updateViewCount(Integer id) {
		ModelResult<Integer> result = new ModelResult<Integer>();
		result.setModel(newsInfoManager.updateViewCount(id));
		return result;
	}

	@Override
	public PageResult<NewsInfo> queryPage(DataPage<NewsInfo> dataPage, NewsInfoOption qVo) {
		PageResult<NewsInfo> result = new PageResult<NewsInfo>();
		dataPage = newsInfoManager.queryPage(dataPage, qVo);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public ModelResult<NewsInfo> add(NewsInfo obj, String content) {
		ModelResult<NewsInfo> result = new ModelResult<NewsInfo>();
        if (StringUtils.isBlank(content)) {
            content = "";
        }
        final String finalContent = content;
        NewsInfo model = transactionTemplate.execute(new TransactionCallback<NewsInfo>() {
            @Override
            public NewsInfo doInTransaction(TransactionStatus status) {
            	NewsInfo newsInfo = newsInfoManager.insert(obj);
            	ContentInfo contentInfo = new ContentInfo();
            	contentInfo.setContent(finalContent);
            	contentInfo.setRelatedType(ContentInfoRelatedType.NEWS.getIndex());
            	contentInfo.setRelatedId(newsInfo.getId());
            	contentInfo.setUpdateTime(Calendar.getInstance());
            	contentInfoManager.insert(contentInfo);
				return newsInfo;
            }
        });
        logger.info("资讯新增 成功 [{}]", model.getTitle());
        result.setModel(model);
        return result;
	}

	@Override
	public BaseResult updateWithContent(NewsInfo obj) {
		BaseResult baseResult = new BaseResult();
        if (StringUtils.isBlank(obj.getContent())) {
            obj.setContent("");
        }
        Boolean updated = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
            	NewsInfo newsInfo = newsInfoManager.update(obj);
            	ContentInfo contentInfo = new ContentInfo();
            	contentInfo.setContent(obj.getContent());
            	contentInfo.setRelatedType(ContentInfoRelatedType.NEWS.getIndex());
            	contentInfo.setRelatedId(newsInfo.getId());
            	contentInfo.setUpdateTime(Calendar.getInstance());
            	contentInfoManager.updateByRelate(contentInfo);
				return true;
            }
        });
        if (!updated) {
            baseResult.withError("UPDATE", "资讯更新失败");
            return baseResult;
        }
        // 更新 es 索引
        updateESIndex(obj.getId());
        logger.info("资讯更新 成功 [{}]", obj.getTitle());
        return baseResult;
	}

	@Override
	public ModelResult<Integer> count(NewsInfoOption qVo) {
		return newsInfoManager.count(qVo);
	}

	@Override
	public PageResult<NewsInfoVO> queryVOPage(DataPage<NewsInfoVO> dataPage, NewsInfoOption qVo) {
		return newsInfoManager.queryVOPage(dataPage, qVo);
	}

	@Override
	public ModelResult<NewsInfo> queryWithContent(Integer id) {
		ModelResult<NewsInfo> result = new ModelResult<NewsInfo>();
		NewsInfo obj = newsInfoManager.queryWithContent(id);
		result.setModel(obj);
		return result;
	}
	
	@Override
	public ModelResult<Boolean> updateStatus(Integer id, Integer status) {
		ModelResult<Boolean> result = new ModelResult<Boolean>();
		Boolean updated = newsInfoManager.updateStatus(id,status);
		if (updated) {
			if (status == Status.STATUS.getIndex()) {
				logger.info("更新elasticsearch索引");
				this.updateESIndex(id);
			}else if (status == Status.UN_STATUS.getIndex()) {
				logger.info("删除elasticsearch索引");
				this.deleteESIndex(id);
			}
		}
		result.setModel(updated);
		return result;
	}

	@Override
	public ModelResult<List<NewsInfo>> queryList(NewsInfoOption qVo, Integer size) {
		return newsInfoManager.queryList(qVo, size);
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
				NewsInfo obj = newsInfoManager.queryWithContent(id);
				if (obj.getStatus().equals(Status.STATUS.getIndex()) && obj.getEditStatus().equals(EditStatus.PUBLISHED.getIndex())) {
					obj.setContent(StringUtil.dealHtmlString(obj.getContent()));
					baseIndexManager.createIndex(obj);
				}
			}
		});
	}
}
