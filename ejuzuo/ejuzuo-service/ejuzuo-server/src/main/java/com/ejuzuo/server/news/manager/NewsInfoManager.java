package com.ejuzuo.server.news.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.common.option.NewsInfoOption;
import com.ejuzuo.common.vo.NewsInfoVO;

import java.util.List;

public interface NewsInfoManager {

	NewsInfo insert(NewsInfo obj);

    NewsInfo queryById(Integer id);
    
    NewsInfo queryWithContent(Integer id);

    NewsInfo update(NewsInfo obj);
    
    Integer updateViewCount(Integer id);
    
    public DataPage<NewsInfo> queryPage(DataPage<NewsInfo> dataPage,NewsInfoOption qVo);

    ModelResult<Integer> count(NewsInfoOption qVo);

    PageResult<NewsInfoVO> queryVOPage(DataPage<NewsInfoVO> dataPage, NewsInfoOption qVo);

	Boolean updateStatus(Integer id, Integer status);

    ModelResult<List<NewsInfo>> queryList(NewsInfoOption qVo, Integer size);
}