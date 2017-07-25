package com.ejuzuo.server.elasticsearch.manager.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.server.elasticsearch.domain.NewsIndex;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;
import com.ejuzuo.server.elasticsearch.repositories.NewsIndexRepository;

@Repository
public class NewsIndexManagerImpl implements BaseIndexManager<NewsInfo> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private NewsIndexRepository newsIndexRepository;

    @PostConstruct
    public void init() {
        /*
         * if(!elasticsearchTemplate.indexExists(ContentIndex.class)){
         * elasticsearchTemplate.createIndex(ContentIndex.class); }
         * elasticsearchTemplate.putMapping(ContentIndex.class);
         */
    }

    @Override
    public void createIndex(NewsInfo newsInfo) {
        NewsIndex snsMsgIndex = new NewsIndex(newsInfo);
        newsIndexRepository.save(snsMsgIndex);
    }

	@Override
	public void deleteById(Integer id) {
		newsIndexRepository.delete(id.longValue());
	}
}
