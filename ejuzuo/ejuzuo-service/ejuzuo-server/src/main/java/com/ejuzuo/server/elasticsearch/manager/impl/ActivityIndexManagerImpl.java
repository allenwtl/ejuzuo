package com.ejuzuo.server.elasticsearch.manager.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import com.ejuzuo.common.domain.ActivityInfo;
import com.ejuzuo.server.elasticsearch.domain.ActivityIndex;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;
import com.ejuzuo.server.elasticsearch.repositories.ActivityIndexRepository;

@Repository
public class ActivityIndexManagerImpl implements BaseIndexManager<ActivityInfo> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ActivityIndexRepository activityIndexRepository;

    @PostConstruct
    public void init() {
        /*
         * if(!elasticsearchTemplate.indexExists(ContentIndex.class)){
         * elasticsearchTemplate.createIndex(ContentIndex.class); }
         * elasticsearchTemplate.putMapping(ContentIndex.class);
         */
    }

    @Override
    public void createIndex(ActivityInfo obj) {
    	ActivityIndex snsMsgIndex = new ActivityIndex(obj);
    	activityIndexRepository.save(snsMsgIndex);
    }

	@Override
	public void deleteById(Integer id) {
		activityIndexRepository.delete(id.longValue());
	}
}
