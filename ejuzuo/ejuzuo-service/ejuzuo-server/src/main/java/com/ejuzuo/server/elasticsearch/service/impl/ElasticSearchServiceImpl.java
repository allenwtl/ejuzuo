package com.ejuzuo.server.elasticsearch.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.base.Strings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSON;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.service.ElasticSearchService;
import com.ejuzuo.common.vo.ContentSearchResult;
import com.ejuzuo.server.elasticsearch.constant.ElasticSearchConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("elasticSearchService")
public class ElasticSearchServiceImpl implements ElasticSearchService {

	private static final transient Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);
	@Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public ModelResult<DataPage<ContentSearchResult>> searchContentByKeyword(String keyword,Integer objectType, List<String> filterField,
			DataPage<ContentSearchResult> dataPage) {
		logger.info("查询elasticsearc，关键字:{},查询类别:{},查询字段:{}",keyword,objectType,JSON.toJSONString(filterField));
		ModelResult<DataPage<ContentSearchResult>> result = new ModelResult<DataPage<ContentSearchResult>>();
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withIndices(ElasticSearchConstant.CONTENT_INDEX);
        if (objectType != null) {
			if (objectType == ObjectType.zixun.getIndex()) {
				nativeSearchQueryBuilder.withTypes(ElasticSearchConstant.NEWS_TYPE);
			}else if (objectType == ObjectType.huodong.getIndex()) {
				nativeSearchQueryBuilder.withTypes(ElasticSearchConstant.ACTIVITY_TYPE);
			}else if (objectType == ObjectType.shuzijiaju.getIndex()) {
				nativeSearchQueryBuilder.withTypes(ElasticSearchConstant.DIGITALFURNITURE_TYPE);
			}else if (objectType == ObjectType.shejishi.getIndex()) {
				nativeSearchQueryBuilder.withTypes(ElasticSearchConstant.DESIGNER_TYPE);
			}else if (objectType == ObjectType.shejizuopin.getIndex()) {
				nativeSearchQueryBuilder.withTypes(ElasticSearchConstant.DESIGNERWORK_TYPE);
			}
		}
        if(filterField==null||filterField.size()==0){
            filterField=new ArrayList<String>();
            filterField.add("title");
            filterField.add("brief");
            filterField.add("content");
        }
        if (StringUtils.isNoneBlank(keyword)) {
            BoolQueryBuilder boolQuery = boolQuery();
            if(filterField.contains("title")){
                boolQuery.should(matchPhraseQuery("title", keyword));
            }if(filterField.contains("brief")){
                boolQuery.should(matchPhraseQuery("brief", keyword));
            }if(filterField.contains("content")){
                boolQuery.should(matchPhraseQuery("content", keyword));
            }
            nativeSearchQueryBuilder.withQuery(boolQuery);
        }

        HighlightBuilder.Field title = new HighlightBuilder.Field("title");
        title.preTags("<span style=\'color:red\'>");
        title.postTags("</span>");
        HighlightBuilder.Field des = new HighlightBuilder.Field("brief");
        des.preTags("<span style=\'color:red\'>");
        des.postTags("</span>");
        HighlightBuilder.Field content = new HighlightBuilder.Field("content");
        content.preTags("<span style=\'color:red\'>");
        content.postTags("</span>");
        nativeSearchQueryBuilder.withHighlightFields(title, des,content);
        nativeSearchQueryBuilder.withPageable(new PageRequest(dataPage.getPageNo() - 1, dataPage.getPageSize())); // spring
                                                                                                                  // data的接口是从第0页开始，不要问我为什么
        SearchQuery searchQuery = nativeSearchQueryBuilder.build();
        Sort sort = new Sort(Direction.DESC, "publishTime");
        searchQuery.addSort(sort);

        Long totalCount = elasticsearchTemplate.count(searchQuery, null);
        List<ContentSearchResult> contentIndexList = elasticsearchTemplate.query(searchQuery,
                new ResultsExtractor<List<ContentSearchResult>>() {

                    public List<ContentSearchResult> extract(SearchResponse response) {
                        //long totalCount = response.getHits().totalHits();
                        List<ContentSearchResult> results = new ArrayList<ContentSearchResult>();
                        ObjectMapper mapper = new ObjectMapper();
                        for (SearchHit hit : response.getHits()) {
                            if (hit != null) {
                                ContentSearchResult result = null;
                                if (!Strings.isNullOrEmpty(hit.sourceAsString())) {
                                    try {
                                        result = mapper.readValue(hit.sourceAsString(), ContentSearchResult.class);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    //result = JSON.toJavaObject(hit.getFields().values(), ContentSearchResult.class);
                                }
                                //获取对应的高亮域
                                Map<String, HighlightField> hf = hit.highlightFields();
                                //从设定的高亮域中取得指定域
                                HighlightField titleField = hf.get("title");
                                if (titleField != null) {
                                    //取得定义的高亮标签
                                    Text[] titleTexts = titleField.fragments();
                                    //为title串值增加自定义的高亮标签
                                    String title = "";
                                    for (Text text : titleTexts) {
                                        title += text;
                                    }
                                    //将追加了高亮标签的串值重新填充到对应的对象
                                    result.setTitle(title);
                                }
                                //从设定的高亮域中取得指定域
                                HighlightField descField = hf.get("brief");
                                if (descField != null) {
                                    //取得定义的高亮标签
                                    Text[] descTexts = descField.fragments();
                                    //为title串值增加自定义的高亮标签
                                    String desc = "";
                                    for (Text text : descTexts) {
                                        desc += text;
                                    }
                                    //将追加了高亮标签的串值重新填充到对应的对象
                                    result.setBrief(desc);
                                }
                                //从设定的高亮域中取得指定域
                                HighlightField contentField = hf.get("content");
                                if (contentField != null) {
                                    //取得定义的高亮标签
                                    Text[] contentTexts = contentField.fragments();
                                    //为content串值增加自定义的高亮标签
                                    String content = "";
                                    for (Text text : contentTexts) {
                                    	content += text;
                                    	break;
                                    }
                                    //将追加了高亮标签的串值重新填充到对应的对象
                                    result.setContent(content+"...");
                                }else {
                                	if (StringUtils.isNotBlank(result.getContent()) && result.getContent().length() >= 100) {
                                		result.setContent(result.getContent().substring(0, 100)+"...");
									}
								}
                                
                                results.add(result);
                            }
                        }
                        return results;
                    }
                });
        logger.info("查询总数:{}",totalCount);
        dataPage.setDataList(contentIndexList);
        dataPage.setTotalCount(totalCount);
		return result.withModel(dataPage);
	}
}
