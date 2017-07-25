package com.ejuzuo.server.elasticsearch.manager.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.server.elasticsearch.domain.DigitalFurnitureIndex;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;
import com.ejuzuo.server.elasticsearch.repositories.DigitalFurnitureIndexRepository;

@Repository
public class DigitalFurnitureIndexManagerImpl implements BaseIndexManager<DigitalFurniture> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private DigitalFurnitureIndexRepository digitalFurnitureIndexRepository;

    @PostConstruct
    public void init() {
        /*
         * if(!elasticsearchTemplate.indexExists(ContentIndex.class)){
         * elasticsearchTemplate.createIndex(ContentIndex.class); }
         * elasticsearchTemplate.putMapping(ContentIndex.class);
         */
    }

    @Override
    public void createIndex(DigitalFurniture obj) {
    	DigitalFurnitureIndex snsMsgIndex = new DigitalFurnitureIndex(obj);
    	digitalFurnitureIndexRepository.save(snsMsgIndex);
    }

	@Override
	public void deleteById(Integer id) {
		digitalFurnitureIndexRepository.delete(id.longValue());
	}
}
