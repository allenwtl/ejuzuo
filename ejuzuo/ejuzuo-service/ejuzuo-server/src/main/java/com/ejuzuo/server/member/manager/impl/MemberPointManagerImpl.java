package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.domain.type.DownloadPayStatus;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.MemberPointLogOption;
import com.ejuzuo.common.option.MemberPointOption;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.downloadinfo.manager.DownloadInfoManager;
import com.ejuzuo.server.member.manager.MemberManager;
import com.ejuzuo.server.member.manager.MemberPointLogManager;
import com.ejuzuo.server.member.manager.MemberPointManager;
import com.ejuzuo.server.member.manager.ShoppingCartManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by allen on 2016/4/3.
 */

@Component
public class MemberPointManagerImpl implements MemberPointManager {

    private static final Logger logger = LoggerFactory.getLogger(MemberPointManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Resource
    private MemberManager memberManager;

    @Resource
    private DownloadInfoManager downloadInfoManager;


    @Resource(name = "transactionTemplate")
    private TransactionTemplate transactionTemplate;

    @Resource
    private MemberPointLogManager memberPointLogManager;

    @Resource
    private ShoppingCartManager shoppingCartManager ;

    @Override
    public int deleteById(Integer id) {
        return dao.updateByObj("MemberPointMapper.deleteById", id);
    }

    @Override
    public MemberPoint save(MemberPoint record) {
        record.setCreateTime(Calendar.getInstance());
        record.setUpdateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("MemberPointMapper.save", record);
        return record;
    }

    @Override
    public MemberPoint selectById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return dao.queryUnique("MemberPointMapper.selectById", param);
    }

    @Override
    public int updateById(MemberPoint record) {
        try {
            MemberPoint memberPoint = queryByMemberId(record.getMemberId());
            if (memberPoint == null) {
                save(record);
            } else {
                record.setUpdateTime(Calendar.getInstance());
                dao.updateByObj("MemberPointMapper.updateById", record);
            }
        } catch (Exception e) {
            logger.error("更新会员积分", e);
            return 0;
        }
        return 1;
    }

    @Override
    public MemberPoint queryByMemberId(Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);
        return dao.queryUnique("MemberPointMapper.queryByMemberId", param);
    }

    @Override
    public ModelResult<Boolean> addPoint(MemberPoint memberPoint, MemberPointLog memberPointLog) {
        if (memberPointLog.getTransType() == MemberPointLogTransType.DONATE_ONE.getIndex()
                || memberPointLog.getTransType() == MemberPointLogTransType.DONATE_TWO.getIndex()) {
            MemberPointLogOption option = new MemberPointLogOption();
            option.setMemberId(memberPointLog.getMemberId());
            option.setRelatedId(memberPointLog.getRelatedId());
            option.setTransType(memberPointLog.getTransType());

            ModelResult<List<MemberPointLog>> modelResultList = memberPointLogManager.queryList(option);
            List<MemberPointLog> memberPointLogList = modelResultList.getModel();
            if (memberPointLogList != null && memberPointLogList.size() >0) {
                logger.info("用户:{}, 事务类型:{}, 关联类型:{} 关联ID:{} 已经存在", memberPointLog.getMemberId(),
                        memberPointLog.getTransType(), memberPointLog.getRelatedType(), memberPointLog.getRelatedId());
                return new ModelResult<>().withError("积分已经加上");
            }
        }

        try {
            logger.info("给用户:[],加积分:[],原因:[]", memberPointLog.getMemberId(), memberPointLog.getAmount(), memberPointLog.getRemark());
            memberPointLog.setCreateTime(Calendar.getInstance());
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    memberPointLogManager.save(memberPointLog);
                    updateById(memberPoint);
                }
            });
        } catch (Exception e) {
            logger.error("增加积分报错:", e);
            return new ModelResult<>().withError("exception", "赠送积分报错");
        }
        return new ModelResult<>(Boolean.TRUE);
    }


    @Override
    public PageResult<MemberPoint> queryPage(DataPage<MemberPoint> dataPage, MemberPointOption qVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (qVo != null) {
            if (qVo.getId() != null) {
                map.put("id", qVo.getId());
            }
            if (qVo.getMemberId() != null) {
                map.put("memberId", qVo.getMemberId());
            }
            Calendar startTime = qVo.getStartTime();
            Calendar endTime = qVo.getEndTime();
            if (startTime != null) {
                startTime.set(Calendar.HOUR_OF_DAY, 0);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.SECOND, 0);
                startTime.set(Calendar.MILLISECOND, 0);
                map.put("startTime", startTime);
            }
            if (endTime != null) {
                endTime.set(Calendar.HOUR_OF_DAY, 23);
                endTime.set(Calendar.MINUTE, 59);
                endTime.set(Calendar.SECOND, 59);
                endTime.set(Calendar.MILLISECOND, 999);
                map.put("endTime", endTime);
            }
        }
        dataPage = dao.queryPage("MemberPointMapper.countPage", "MemberPointMapper.queryPage", map, dataPage);
        PageResult<MemberPoint> result = new PageResult<MemberPoint>();
        result.setPage(dataPage);
        return result;
    }

    @Override
    public ModelResult<Integer> count(MemberPointOption qVo) {

        return new ModelResult<>(dao.queryCount("MemberPointMapper.countPage", JSONUtils.object2MapSpecail(qVo)));
    }

    @Override
    public ModelResult<DownloadInfo> pay(DownloadInfo downloadInfo) {
        MemberPoint memberPoint = queryByMemberId(downloadInfo.getMemberId());
        BigDecimal balance = new BigDecimal(memberPoint.getBalance());
        String msg = MessageFormatter.arrayFormat("用户:[{}],购买数字家居:[{}],账户余额:[{}],需要支付:[{}]",
                new Object[]{downloadInfo.getMemberId(), downloadInfo.getGoodsId(), memberPoint.getBalance(), downloadInfo.getActualPrice()}).getMessage();
        if (balance.compareTo(downloadInfo.getActualPrice()) < 0) {
            logger.info(msg);
            return new ModelResult<>().withError("exception", "余额不够");
        }

        memberPoint.setBalance(balance.subtract(downloadInfo.getActualPrice()).intValue());

        downloadInfo.setPayStatus(DownloadPayStatus.PAYED.getIndex());
        downloadInfo.setPayTime(Calendar.getInstance());
        downloadInfo.setExpire(DateUtil.add(Calendar.getInstance(), Calendar.DAY_OF_YEAR, 5));

        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setAmount(downloadInfo.getActualPrice().intValue());
        memberPointLog.setMemberId(downloadInfo.getMemberId());
        memberPointLog.setRelatedId(downloadInfo.getGoodsId());
        memberPointLog.setRelatedType(ObjectType.shuzijiaju.getIndex());
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointLog.setTransType(MemberPointLogTransType.DOWNLOAD.getIndex());
        memberPointLog.setCreator(memberManager.queryByMemberId(downloadInfo.getMemberId()).getModel().getAccount());
        memberPointLog.setRemark(msg);

        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    //更新用户积分、用户积分日志、用户订单状态
                    updateById(memberPoint);
                    memberPointLogManager.save(memberPointLog);
                    downloadInfoManager.updateById(downloadInfo);
                    shoppingCartManager.deleteByMemberIdAndGoodsId(memberPoint.getMemberId(), downloadInfo.getGoodsId());
                }
            });
        } catch (Exception e) {
            logger.error("用户:[{}] 购买数字家居:[{}] 支付失败", downloadInfo.getMemberId(), downloadInfo.getGoodsId(), e);
            return new ModelResult<>().withError("exception", "支付失败");
        }

        return new ModelResult<>(downloadInfo);
    }

    @Override
    public ModelResult<Boolean> totalPay(BigDecimal totalMoney, List<DownloadInfo> downloadList , Integer memberId) {
        MemberPoint memberPoint = queryByMemberId(memberId);
        BigDecimal balance = new BigDecimal(memberPoint.getBalance());

        List<Integer> downloadIds = new ArrayList<>();

        for (DownloadInfo item: downloadList){
            downloadIds.add(item.getId());
        }

        String msg = MessageFormatter.arrayFormat("用户:[{}],购买数字家居,订单id:[{}],账户余额:[{}],需要支付:[{}]",
                new Object[]{ memberId, downloadIds.toString(), memberPoint.getBalance(), totalMoney}).getMessage();
        if (totalMoney.compareTo(balance) > 0) {
            logger.info( msg );
            return new ModelResult<>().withError("exception", "积分不够");
        }

        memberPoint.setBalance(balance.subtract(totalMoney).intValue());

        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setAmount(totalMoney.intValue());
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointLog.setMemberId(memberId);
        memberPointLog.setRelatedType(ObjectType.shuzijiaju.getIndex());
        memberPointLog.setPointBalance(memberPoint.getBalance());
        memberPointLog.setTransType(MemberPointLogTransType.DOWNLOAD.getIndex());
        memberPointLog.setCreator(memberManager.queryByMemberId(memberId).getModel().getAccount());
        memberPointLog.setRemark(msg);

        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    //更新用户积分、用户积分日志、用户订单状态
                    updateById(memberPoint);
                    memberPointLogManager.save(memberPointLog);
                    for (DownloadInfo downloadInfo: downloadList){
                        downloadInfo.setPayStatus(DownloadPayStatus.PAYED.getIndex());
                        downloadInfo.setPayTime(Calendar.getInstance());
                        downloadInfo.setExpire(DateUtil.add(Calendar.getInstance(), Calendar.DAY_OF_YEAR, 5));
                        downloadInfoManager.updateById(downloadInfo);
                        shoppingCartManager.deleteByMemberIdAndGoodsId(memberPoint.getMemberId(), downloadInfo.getGoodsId());
                    }
                }
            });
        } catch (Exception e) {
            logger.error("用户:[{}] 购买数字家居, 订单id:[{}] 支付失败", memberId, downloadIds.toString(), e);
            return new ModelResult<>().withError("exception", "支付失败");
        }
        return new ModelResult<>(Boolean.TRUE);
    }

    @Override
    public ModelResult<Integer> updateBalance(Integer id, Integer memberId, Integer change) {
        ModelResult<Integer> result = new ModelResult<Integer>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("change", change);
        map.put("updateTime", Calendar.getInstance());
        map.put("memberId", memberId);
        Integer affecte = dao.update("MemberPointMapper.updateBalance", map);
        result.setModel(affecte);
        return result;
    }


}
