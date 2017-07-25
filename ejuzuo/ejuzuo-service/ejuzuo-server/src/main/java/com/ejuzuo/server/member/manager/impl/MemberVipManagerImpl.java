package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.constants.MemberVipLogTransType;
import com.ejuzuo.common.constants.MemberVipType;
import com.ejuzuo.common.domain.MemberVip;
import com.ejuzuo.common.domain.MemberVipLog;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.server.member.manager.MemberVipLogManager;
import com.ejuzuo.server.member.manager.MemberVipManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allen on 2016/4/3.
 */

@Component
public class MemberVipManagerImpl implements MemberVipManager {

    private static final Logger logger = LoggerFactory.getLogger(MemberVipManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Resource
    private MemberVipLogManager memberVipLogManager;

    @Resource(name = "transactionTemplate")
    private TransactionTemplate transactionTemplate;

    @Override
    public int deleteById(Integer id) {
        return dao.updateByObj("MemberVipMapper.deleteById", id);
    }

    @Override
    public MemberVip save(MemberVip record) {
        record.setCreateTime(Calendar.getInstance());
        record.setUpdateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("MemberVipMapper.save", record);
        return record;
    }

    @Override
    public MemberVip selectById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return dao.queryUnique("MemberVipMapper.selectById", param);
    }

    @Override
    public ModelResult<MemberVip> saveVipWithLog(MemberVip memberVip) {

        MemberVip temp = selectByMemberId(memberVip.getMemberId());
        if (temp != null) {
            logger.info("用户:[], vip 已经存在了", memberVip.getMemberId());
            return new ModelResult<>().withError("exception", "已经存在了vip记录了");
        }
        MemberVipLog memberVipLog = new MemberVipLog();
        memberVipLog.setMemberId(memberVip.getMemberId());
        memberVipLog.setPeriod(0);
        memberVipLog.setTransType(MemberVipLogTransType.qita.getIndex());
        memberVipLog.setRemark("初始化");
        memberVipLog.setCreator(memberVip.getCreator());

        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    save(memberVip);
                    memberVipLogManager.save(memberVipLog);
                }
            });
        } catch (Exception e) {
            logger.error("用户:[{}] 保存vip 和日志 报错:[{}]", memberVip.getMemberId(), e.getMessage(), e);
            return new ModelResult<>().withError("exception", e.getMessage());
        }

        return new ModelResult<>(memberVip);
    }

    @Override
    public MemberVip selectByMemberId(Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);
        return dao.queryUnique("MemberVipMapper.selectByMemberId", param);
    }

    @Override
    public MemberVip judgeVipByMemberId(Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);
        param.put("nowTime", Calendar.getInstance());
        return dao.queryUnique("MemberVipMapper.judgeVip", param);
    }

    @Override
    public MemberVip updateVipTime(Integer memberId, String updateAccount, MemberPointLogTransType memberPointLogTransType) {
        if (memberPointLogTransType.getIndex() == MemberPointLogTransType.DONATE_ONE.getIndex()
                || memberPointLogTransType.getIndex() == MemberPointLogTransType.DONATE_TWO.getIndex()) {

            MemberVip memberVip = selectByMemberId(memberId);
            if (memberVip == null) {
                final MemberVip tempVip = new MemberVip();
                tempVip.setMemberId(memberId);
                tempVip.setStartTime(DateUtil.getTheDayZero());
                tempVip.setCreator(updateAccount);
                tempVip.setUpdator(updateAccount);
                tempVip.setViped(MemberVipType.IS_VIP.getIndex());
                tempVip.setRemark("创建者:[" + updateAccount + "] 给用户:[" + memberId + "] 创建vip");
                int period = 0;
                if (memberPointLogTransType.getIndex() == MemberPointLogTransType.DONATE_ONE.getIndex()) {
                    tempVip.setEndTime(DateUtil.add(DateUtil.getTheDayMidnight(), Calendar.MONTH, 6));
                    period = 6;
                } else {
                    tempVip.setEndTime(DateUtil.add(DateUtil.getTheDayMidnight(), Calendar.MONTH, 18));
                    period = 18;
                }

                MemberVipLog memberVipLog = new MemberVipLog();
                memberVipLog.setMemberId(memberId);
                memberVipLog.setTransType(MemberVipLogTransType.zanzhu.getIndex());
                memberVipLog.setRelatedType(MemberVipLogTransType.zanzhu.getIndex());
                memberVipLog.setPeriod(period);
                memberVipLog.setRemark(tempVip.getRemark());
                memberVipLog.setCreator(updateAccount);

                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        save(tempVip);
                        memberVipLog.setRelatedId(tempVip.getId());
                        memberVipLogManager.save(memberVipLog);
                    }
                });

            } else {
                Calendar nowTime = Calendar.getInstance();
                MemberVip tempVip = new MemberVip();
                tempVip.setId(memberVip.getId());
                int period = 0;
                if (memberVip.getEndTime().compareTo(nowTime) < 0) {
                    tempVip.setStartTime(DateUtil.getTheDayZero());
                    if (memberPointLogTransType.getIndex() == MemberPointLogTransType.DONATE_ONE.getIndex()) {
                        tempVip.setEndTime(DateUtil.add(DateUtil.getTheDayMidnight(), Calendar.MONTH, 6));
                        period = 6;
                    } else {
                        tempVip.setEndTime(DateUtil.add(DateUtil.getTheDayMidnight(), Calendar.MONTH, 18));
                        period = 18;
                    }
                } else if (memberVip.getStartTime().compareTo(nowTime) < 0) {
                    if (memberPointLogTransType.getIndex() == MemberPointLogTransType.DONATE_ONE.getIndex()) {
                        tempVip.setEndTime(DateUtil.add(memberVip.getEndTime(), Calendar.MONTH, 6));
                        period = 6;
                    } else {
                        tempVip.setEndTime(DateUtil.add(memberVip.getEndTime(), Calendar.MONTH, 18));
                        period = 18;
                    }
                }

                tempVip.setUpdator(updateAccount);
                tempVip.setRemark("修改者:[" + updateAccount + "] 给用户:[" + memberId + "] 修改有效时间");

                MemberVipLog memberVipLog = new MemberVipLog();
                memberVipLog.setMemberId(memberId);
                memberVipLog.setTransType(MemberVipLogTransType.zanzhu.getIndex());
                memberVipLog.setRelatedType(MemberVipLogTransType.zanzhu.getIndex());
                memberVipLog.setPeriod(period);
                memberVipLog.setRelatedId(tempVip.getId());
                memberVipLog.setCreator(updateAccount);
                memberVipLog.setRemark(tempVip.getRemark());
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        updateById(tempVip);
                        memberVipLogManager.save(memberVipLog);
                    }
                });
            }

            memberVip = selectByMemberId(memberId);
            return memberVip;
        }

        return null;
    }

    @Override
    public int updateById(MemberVip record) {
        record.setUpdateTime(Calendar.getInstance());
        return dao.updateByObj("MemberVipMapper.updateById", record);
    }

    @Override
    public DataPage<MemberVip> queryPage(DataPage<MemberVip> dataPage, MemberVip qVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (qVo != null) {
            if (qVo.getMemberId() != null) {
                map.put("memberId", qVo.getMemberId());
            }
            if (qVo.getViped() != null) {
                map.put("viped", qVo.getViped());
            }
        }
        return dao.queryPage("MemberVipMapper.countPage", "MemberVipMapper.queryPage", map, dataPage);
    }
}
