package com.ejuzuo.server.keywork.manager;

import java.util.List;
import java.util.Set;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.FilterKeyword;

public interface FilterKeywordMananger {
	
	public FilterKeyword findById(Long id);
	
	public void updateFilterKeyword(FilterKeyword FilterKeyword);
	
	public void deleteFilterKeywordById(Long id);
	
	public void insertFilterKeyword(FilterKeyword FilterKeyword);
	
	/**
	 * 根据过滤词类型查找过滤关键词
	 * @param filterType
	 * @return
	 */
	public List<FilterKeyword> findFilterKeywordByType(Integer filterType);
	
	/**
	 * 根据预加载的过滤关键字，判断发表内容中是否包含被禁的关键字
	 * @param cmsMsgContent
	 * @return
	 */
	public Set<String> findBannedKeyword(String cmsMsgContent);
	
	/**
	 * 替换关键词
	 * @param content
	 * @return
	 */
	public String replaceKeywordInContent(String content);
	
	/**
	 * 审核关键词
	 * @param content
	 * @return
	 */
	public Set<String> hasJudgeKeyword(String content);
	
	/**
	 * 根据id和filterType查找
	 * @param id
	 * @param filterType
	 * @return
	 */
	public List<FilterKeyword> findFilterKeywordByIdAndType(Long id,Integer filterType);
	
	/**
	 * 分页查找过滤关键词
	 * @param id
	 * @param filterType
	 * @param dataPage
	 * @return
	 */
	public DataPage<FilterKeyword> findFilterKeywordByIdAndTypeInPage(Long id,Integer filterType,DataPage<FilterKeyword> dataPage);

}
