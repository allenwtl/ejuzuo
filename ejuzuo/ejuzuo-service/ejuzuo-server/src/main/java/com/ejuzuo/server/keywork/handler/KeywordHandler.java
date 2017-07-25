package com.ejuzuo.server.keywork.handler;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

@Component("keywordHandler")
public class KeywordHandler {

	private Set<String> judgeKeywordSet = new HashSet<String>();
	
	private TreeSet<Long> judgeKeywordIdSet = new TreeSet<Long>();
	
	private Map<String,String> replaceKeywordMap = new ConcurrentHashMap<String, String>();
	
	private TreeSet<Long> replaceKeywordIdSet = new TreeSet<Long>();
	
	private TreeSet<Long> rejectKeywordIdSet = new TreeSet<Long>();
	
	private Set<String> rejectKeywordSet = new HashSet<String>();
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void putRejectKeywordIntoSet(String keyword){
		rejectKeywordSet.add(keyword);
	}
	
	public void addRejectKeywordId(Long rejectKeywordId){
		rejectKeywordIdSet.add(rejectKeywordId);
	}
	
	public void putJudgeKeywordIntoSet(String keyword){
		judgeKeywordSet.add(keyword);
	}
	
	public void putReplaceKeywordIntoMap(String key,String value){
		replaceKeywordMap.put(key, value);
	}

	public void addJudgeKeywordId(Long judgeKeywordId){
		judgeKeywordIdSet.add(judgeKeywordId);
	}
	
	public void addReplaceKeywordId(Long replaceKeywordId){
		replaceKeywordIdSet.add(replaceKeywordId);
	}
	
	public Long getMaxJudgeKeywordId(){
		if(judgeKeywordIdSet.isEmpty()){
			return 0L;
		}else{
			return judgeKeywordIdSet.last();
		}
	}
	
	public Long getMaxReplaceKeywordId(){
		if(replaceKeywordIdSet.isEmpty()){
			return 0L;
		}else{
			return replaceKeywordIdSet.last();
		}
	}
	
	public Long getMaxRejectKeywordId(){
		if(rejectKeywordIdSet.isEmpty()){
			return 0L;
		}else{
			return rejectKeywordIdSet.last();
		}
	}
	
	/**
	 * 替换内容中的关键词
	 * @param content
	 * @return
	 */
	public String replaceKeywordInContent(String content){
		Set<String> splitKeywordSet = this.getSplitKeywordFromContent(content);
		for(String keyword:splitKeywordSet){
			if(replaceKeywordMap.keySet().contains(keyword)){
				String replaceValue = replaceKeywordMap.get(keyword);
				content = StringUtils.replace(content, keyword, replaceValue);
			}
		}
		return content;
	}
	
	/**
	 * 如果有包括待审核的关键词，返回待审核的关键词
	 * @param content
	 * @return
	 */
	public Set<String> hasJudgeKeyword(String content){
		Set<String> splitKeywordSet = this.getSplitKeywordFromContent(content);
		Set<String> resultSet = new HashSet<String>();
		for(String keyword:splitKeywordSet){
			if(judgeKeywordSet.contains(keyword)){
				resultSet.add(keyword);
			}
		}
		return resultSet;
	}
	
	/**
	 * 从消息中抽取关键词
	 * @param content
	 * @return
	 */
	public Set<String> getSplitKeywordFromContent(String content){
		StringReader reader = new StringReader(content);
		IKSegmenter ikSegmenter = new IKSegmenter(reader, false);
		Set<String> tags = new HashSet<String>();
		Lexeme lexeme = null;
		try {
			lexeme = ikSegmenter.next();
			while (lexeme != null) {
				String word = lexeme.getLexemeText();
				tags.add(word);
				lexeme = ikSegmenter.next();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return tags;
	}
	
	public boolean containRejectKeyword(String keyword){
		return rejectKeywordSet.contains(keyword);
	}
}
