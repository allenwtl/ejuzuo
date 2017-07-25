package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.domain.type.MemberLockType;
import com.ejuzuo.common.exception.common.MsgCode;
import com.ejuzuo.common.option.MemberOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.member.manager.MemberManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/3/28 0028.
 */

@Component("memberManager")
public class MemberManagerImpl implements MemberManager {

    private static final Logger logger = LoggerFactory.getLogger(MemberManagerImpl.class);


    @Resource(name = "dao")
    private GenericDao dao;


    @Override
    public ModelResult<MsgCode> deleteById(Integer id) {
        return null;
    }

    @Override
    public ModelResult<Member> save(Member record) {
        try {
            record.setLocked(MemberLockType.unLock.getIndex());
            record.setRegisterTime(Calendar.getInstance());
            record.setCreateTime(Calendar.getInstance());
            record.setUpdateTime(Calendar.getInstance());
            dao.insertAndReturnAffectedCount("MemberMapper.save", record);
        } catch (Exception e) {
            logger.error("member 插入报错:{}", e);
            return new ModelResult().withError("save.exception", "插入报错");
        }

        return new ModelResult<>(record);
    }

    @Override
    public ModelResult<Boolean> updateById(Member member) {
        try {
            member.setUpdateTime(Calendar.getInstance());
            dao.updateByObj("MemberMapper.updateById", member);
        } catch (Exception e) {
            logger.error("MemberMapper.updateById 更新报错", e);
            return new ModelResult<>().withError("exception", "更新报错");
        }
        return new ModelResult<>(Boolean.TRUE);
    }

    @Override
    public ModelResult<Member> queryByAccount(String account) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("account", account);
            Member member = dao.queryUnique("MemberMapper.selectByOption", param);
            return new ModelResult<>(member);
        } catch (Exception e) {
            logger.info("查询用户：{}:报错", account, e);
        }
        return new ModelResult().withError("query.exception", "查询报错");
    }

    @Override
    public ModelResult<Member> queryByMemberId(Integer memberId) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("id", memberId);
            Member member = dao.queryUnique("MemberMapper.selectByOption", param);
            return new ModelResult<>(member);
        } catch (Exception e) {
            logger.info("查询用户：{}:报错", memberId, e);
        }
        return new ModelResult().withError("query.exception", "查询报错");
    }

    @Override
    public ModelResult<List<Member>> queryByOption(MemberOption option) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        return new ModelResult<>(dao.queryList("MemberMapper.selectByOption", param));
    }

    @Override
    public ModelResult<Boolean> judgeEmailOrMobileIsExist(String code, CheckCodeRecordDestType destType) {
        try {
            Map<String, Object> param = new HashMap<>();
            /*if (destType.getIndex() == CheckCodeRecordDestType.email.getIndex()) {
                param.put("email", code);
            } else if (destType.getIndex() == CheckCodeRecordDestType.mobile.getIndex()) {
                param.put("mobile", code);
            }*/
            param.put("account", code);

            List<Member> memberList = dao.queryList("MemberMapper.selectByOption", param);
            if (memberList != null && memberList.size() > 0) {
                logger.info(":{},已经被注册了", code);
                return new ModelResult<>(Boolean.TRUE);
            }
            return new ModelResult<>(Boolean.FALSE);
        } catch (Exception e) {
            logger.info("judgeEmailOrMobileIsExist：{}:报错", code, e);
        }
        return new ModelResult().withError("query.exception", "查询报错");
    }

    @Override
    public ModelResult<Member> queryById(Integer id) {
        try {
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("id", id);
            Member member = dao.queryUnique("MemberMapper.selectByOption", map);
            return new ModelResult<>(member);
        } catch (Exception e) {
            logger.info("查询用户：{}:报错", id, e);
        }
        return new ModelResult().withError("query.exception", "查询报错");
    }

    @Override
    public PageResult<Member> queryByPage(MemberOption option, DataPage<Member> dataPage) {
    	PageResult<Member> result = new PageResult<Member>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
    	if (option != null) {
			if (option.getId() != null) {
				map.put("id", option.getId());
			}
			if (option.getAccount() != null) {
				map.put("account", option.getAccount());
			}
			if (option.getNickName() != null) {
				map.put("nickName", option.getNickName());
			}
			if (option.getActiveStatus() != null) {
				map.put("activeStatus", option.getActiveStatus());
			}
			if (option.getStatus() != null ) {
				map.put("status", option.getStatus());
			}
			if (option.getLocked() != null) {
				map.put("locked", option.getLocked());
			}
			if (option.getVerified() != null) {
				map.put("verified", option.getVerified());
			}
			Calendar beginDate = option.getBeginDate();
			Calendar endDate = option.getEndDate();
			if (beginDate != null) {
				beginDate.set(Calendar.HOUR_OF_DAY, 0);
				beginDate.set(Calendar.MINUTE, 0);
				beginDate.set(Calendar.SECOND, 0);
				beginDate.set(Calendar.MILLISECOND, 0);
				map.put("beginDate", beginDate);
			}
			if (endDate != null) {
				endDate.set(Calendar.HOUR_OF_DAY, 23);
				endDate.set(Calendar.MINUTE, 59);
				endDate.set(Calendar.SECOND, 59);
				endDate.set(Calendar.MILLISECOND, 999);
				map.put("endDate", endDate);
			}
		}
    	dataPage = dao.queryPage("MemberMapper.countPage", "MemberMapper.queryPage", map, dataPage);
    	result.setPage(dataPage);
    	return result;
    }

	@Override
	public ModelResult<Boolean> update(Integer id, Integer locked, Integer status) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			if (locked != null) {
				map.put("locked", locked);
			}
			if (status != null) {
				map.put("status", status);
			}
			dao.update("MemberMapper.update", map);
        } catch (Exception e) {
            logger.error("MemberMapper.update 更新报错", e);
            return new ModelResult<>().withError("exception", "更新报错");
        }
        return new ModelResult<>(Boolean.TRUE);
	}

	@Override
	public List<Member> querybyIds(List<Integer> memberIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberIds", memberIds);
		return dao.queryList("MemberMapper.querybyIds", map);
	}
}
