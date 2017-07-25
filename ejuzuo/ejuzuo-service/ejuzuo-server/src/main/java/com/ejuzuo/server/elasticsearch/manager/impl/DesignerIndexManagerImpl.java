package com.ejuzuo.server.elasticsearch.manager.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.server.elasticsearch.domain.DesignerIndex;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;
import com.ejuzuo.server.elasticsearch.repositories.DesignerIndexRepository;

@Repository
public class DesignerIndexManagerImpl implements BaseIndexManager<Designer> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private DesignerIndexRepository designerIndexRepository;

    @PostConstruct
    public void init() {
        /*
         * if(!elasticsearchTemplate.indexExists(ContentIndex.class)){
         * elasticsearchTemplate.createIndex(ContentIndex.class); }
         * elasticsearchTemplate.putMapping(ContentIndex.class);
         */
    }

    @Override
    public void createIndex(Designer obj) {
    	DesignerIndex snsMsgIndex = new DesignerIndex(obj);
    	designerIndexRepository.save(snsMsgIndex);
    }

	@Override
	public void deleteById(Integer id) {
		designerIndexRepository.delete(id.longValue());
	}
}
