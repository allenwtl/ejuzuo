package com.ejuzuo.server.keywork.manager.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.FilterTypeConstant;
import com.ejuzuo.common.domain.FilterKeyword;
import com.ejuzuo.common.util.DataPageTools;
import com.ejuzuo.server.keywork.handler.KeywordHandler;
import com.ejuzuo.server.keywork.manager.FilterKeywordMananger;

@Repository
public class FilterKeywordManagerImpl implements FilterKeywordMananger {
	
	private transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GenericDao dao;
	
	@Autowired
	private KeywordHandler keywordHandler;
	
	@PostConstruct
	public void init(){
		Dictionary dictionary = Dictionary.initial(DefaultConfig.getInstance());
		initRejectKeyword();
		initReplaceKeyword();
		initJudgeKeyword();
	}
	
	private void initRejectKeyword(){
		List<FilterKeyword> filterKeywordList = this.findFilterKeywordByType(FilterTypeConstant.BANNED);
		Set<String> keywordSet = new HashSet<String>();
		for(FilterKeyword snsFilterKeyword:filterKeywordList){
			keywordHandler.addRejectKeywordId(snsFilterKeyword.getId());
			if(StringUtils.isNotBlank(snsFilterKeyword.getKeyword())){
				keywordSet.add(snsFilterKeyword.getKeyword());
			}
			keywordHandler.putRejectKeywordIntoSet(snsFilterKeyword.getKeyword());
		}
		Dictionary.getSingleton().addWords(keywordSet);
	}
	
	private void initReplaceKeyword(){
		DataPage<FilterKeyword> dataPage = new DataPage<FilterKeyword>(1,1500);
		Long replaceKeywordMaxId = keywordHandler.getMaxReplaceKeywordId();
		Integer filterType = FilterTypeConstant.REPLACE;
		dataPage = this.findFilterKeywordByIdAndTypeInPage(replaceKeywordMaxId, filterType, dataPage);
		List<FilterKeyword> dataList = dataPage.getDataList();
		Set<String> keywordSet = new HashSet<String>();
		for(FilterKeyword snsFilterKeyword:dataList){
			String keyword = snsFilterKeyword.getKeyword();
			Long replaceKeywordId = snsFilterKeyword.getId();
			keywordHandler.addReplaceKeywordId(replaceKeywordId);
			if(keyword.contains("=")){
				String[] keywords = StringUtils.split(keyword,"=");
				keywordHandler.putReplaceKeywordIntoMap(keywords[0], keywords[1]);
				keywordSet.add(keywords[0]);
			}
		}
		Dictionary.getSingleton().addWords(keywordSet);
		while(dataPage.isHasNext()){
			dataPage = new DataPage<FilterKeyword>(dataPage.getNextPage(), dataPage.getPageSize());
			dataPage = this.findFilterKeywordByIdAndTypeInPage(replaceKeywordMaxId, filterType, dataPage);
			dataList = dataPage.getDataList();
			keywordSet = new HashSet<String>();
			for(FilterKeyword snsFilterKeyword:dataList){
				String keyword = snsFilterKeyword.getKeyword();
				Long replaceKeywordId = snsFilterKeyword.getId();
				keywordHandler.addReplaceKeywordId(replaceKeywordId);
				if(keyword.contains("=")){
					String[] keywords = StringUtils.split(keyword,"=");
					if(StringUtils.isNotBlank(keywords[0])){
						keywordHandler.putReplaceKeywordIntoMap(keywords[0], keywords[1]);
						keywordSet.add(keywords[0]);
					}
				}
			}
			Dictionary.getSingleton().addWords(keywordSet);
		}
	}
	
	private void initJudgeKeyword(){
		DataPage<FilterKeyword> dataPage = new DataPage<FilterKeyword>(1,1500);
		Long judgeKeywordMaxId = keywordHandler.getMaxJudgeKeywordId();
		Integer filterType = FilterTypeConstant.MANAUAL_JUDGE;
		Set<String> keywordSet = new HashSet<String>();
		dataPage = this.findFilterKeywordByIdAndTypeInPage(judgeKeywordMaxId, filterType, dataPage);
		List<FilterKeyword> dataList = dataPage.getDataList();
		for(FilterKeyword snsFilterKeyword:dataList){
			String keyword = snsFilterKeyword.getKeyword();
			if(StringUtils.isNotBlank(keyword)){
				keywordSet.add(keyword);
			}
			Long judgeKeywordId = snsFilterKeyword.getId();
			keywordHandler.addJudgeKeywordId(judgeKeywordId);
			keywordHandler.putJudgeKeywordIntoSet(keyword);
		}
		Dictionary.getSingleton().addWords(keywordSet);
		while(dataPage.isHasNext()){
			dataPage = new DataPage<FilterKeyword>(dataPage.getNextPage(),dataPage.getPageSize());
			dataPage = this.findFilterKeywordByIdAndTypeInPage(judgeKeywordMaxId, filterType, dataPage);
			dataList = dataPage.getDataList();
			keywordSet = new HashSet<String>();
			for(FilterKeyword snsFilterKeyword:dataList){
				String keyword = snsFilterKeyword.getKeyword();
				if(StringUtils.isNoneBlank(keyword)){
					keywordSet.add(keyword);
				}
				Long judgeKeywordId = snsFilterKeyword.getId();
				keywordHandler.addJudgeKeywordId(judgeKeywordId);
				keywordHandler.putJudgeKeywordIntoSet(keyword);
			}
			Dictionary.getSingleton().addWords(keywordSet);
		}
	}
	
	/**
	 * 定时刷新内存中的FilterKeyword
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void refreshFilterKeyword(){
		Long id = keywordHandler.getMaxRejectKeywordId();
		logger.info("刷新snsFilterKeyword开始,lastMaxId:"+id);
		List<FilterKeyword> keyworList = findFilterKeywordByIdAndType(id, FilterTypeConstant.BANNED);
		Set<String> keywordSet = new HashSet<String>();
		for(FilterKeyword snsFilterKeyword:keyworList){
			keywordHandler.addRejectKeywordId(snsFilterKeyword.getId());
			keywordHandler.putRejectKeywordIntoSet(snsFilterKeyword.getKeyword());
			keywordSet.add(snsFilterKeyword.getKeyword());
		}
		Dictionary.getSingleton().addWords(keywordSet);
		logger.info("刷新snsFilterKeyword完毕，本次新增："+keyworList.size()+"条记录");
	}

	@Override
	public FilterKeyword findById(Long id) {
		return dao.queryUnique("FilterKeywordMapper.selectByPrimaryKey", id);
	}

	@Override
	public void updateFilterKeyword(
			FilterKeyword snsFilterKeyword) {
		dao.updateByObj("FilterKeywordMapper.updateByPrimaryKeySelective", snsFilterKeyword);
	}

	@Override
	public void deleteFilterKeywordById(Long id) {
		dao.updateByObj("FilterKeywordMapper.deleteByPrimaryKey", id);
	}

	@Override
	public void insertFilterKeyword(FilterKeyword snsFilterKeyword) {
		dao.insertAndSetupId("FilterKeywordMapper.insert", snsFilterKeyword);
	}

	@Override
	public List<FilterKeyword> findFilterKeywordByType(Integer filterType) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("filterLevel", filterType);
		return dao.queryList("FilterKeywordMapper.findFilterKeywordByType",map);
	}

	@Override
	public Set<String> findBannedKeyword(String snsMsgContent) {
		Set<String> resultSet = keywordHandler.getSplitKeywordFromContent(snsMsgContent);
		Set<String> keywordSet = new HashSet<String>();
		for(String keyword:resultSet){
			if(keywordHandler.containRejectKeyword(keyword)){
				keywordSet.add(keyword);
			}
		}
		return keywordSet;
	}

	@Override
	public List<FilterKeyword> findFilterKeywordByIdAndType(Long id,
			Integer filterType) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("filterType", filterType);
		return dao.queryList("FilterKeywordMapper.findFilterKeywordByIdAndType", map);
	}

	@Override
	public DataPage<FilterKeyword> findFilterKeywordByIdAndTypeInPage(
			Long id, Integer filterType, DataPage<FilterKeyword> dataPage) {
		Map<String, Object> params = DataPageTools.toMap(dataPage);
		params.put("id", id);
		params.put("filterLevel", filterType);
		return dao.queryPage("FilterKeywordMapper.findFilterKeywordByIdAndTypeInPageCount", "FilterKeywordMapper.findFilterKeywordByIdAndTypeInPage", params, dataPage);
	}

	@Override
	public String replaceKeywordInContent(String content) {
		return keywordHandler.replaceKeywordInContent(content);
	}

	@Override
	public Set<String> hasJudgeKeyword(String content) {
		return keywordHandler.hasJudgeKeyword(content);
	}

}
