package com.ejuzuo.server.elasticsearch.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ejuzuo.server.elasticsearch.domain.ActivityIndex;

/**
 * 继承了ElasticsearchRepository之后，会动态生成实现,只要增加的方法按照命名规则即可
 * 
 * @author user
 *
 */
public interface ActivityIndexRepository extends ElasticsearchRepository<ActivityIndex, Long> {

}
