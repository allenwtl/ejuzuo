package com.ejuzuo.server.elasticsearch.manager.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.server.elasticsearch.domain.DesignerWorkIndex;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;
import com.ejuzuo.server.elasticsearch.repositories.DesignerWorkIndexRepository;

@Repository
public class DesignerWorkIndexManagerImpl implements BaseIndexManager<DesignerWork> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private DesignerWorkIndexRepository designerWorkIndexRepository;

    @PostConstruct
    public void init() {
        /*
         * if(!elasticsearchTemplate.indexExists(ContentIndex.class)){
         * elasticsearchTemplate.createIndex(ContentIndex.class); }
         * elasticsearchTemplate.putMapping(ContentIndex.class);
         */
    }

    @Override
    public void createIndex(DesignerWork obj) {
    	DesignerWorkIndex snsMsgIndex = new DesignerWorkIndex(obj);
    	designerWorkIndexRepository.save(snsMsgIndex);
    }

	@Override
	public void deleteById(Integer id) {
		designerWorkIndexRepository.delete(id.longValue());
	}
}
