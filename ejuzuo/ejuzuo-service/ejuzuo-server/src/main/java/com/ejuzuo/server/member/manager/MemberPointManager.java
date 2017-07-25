package com.ejuzuo.server.member.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.option.MemberPointOption;

import java.math.BigDecimal;
import java.util.List;

public interface MemberPointManager {
    int deleteById(Integer id);

    MemberPoint save(MemberPoint record);

    MemberPoint selectById(Integer id);

    int updateById(MemberPoint record);

    MemberPoint queryByMemberId(Integer memberId);

    /**
     * 给用户加积分
     * @param memberPoint
     * @return
     */
    ModelResult<Boolean> addPoint(MemberPoint memberPoint, MemberPointLog memberPointLog);

	PageResult<MemberPoint> queryPage(DataPage<MemberPoint> dataPage, MemberPointOption qVo);

    ModelResult<Integer> count(MemberPointOption qVo);

    /**
     * 单个支付
     * @param downloadInfo
     * @return
     */
    ModelResult<DownloadInfo> pay(DownloadInfo downloadInfo);

    /**
     * 一起支付
     * @return
     */
    ModelResult<Boolean> totalPay(BigDecimal totalMoney, List<DownloadInfo> downloadList, Integer memberId);

	ModelResult<Integer> updateBalance(Integer id, Integer memberId, Integer change);

}