package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberCare;

/**
 * Created by tianlun.wu on 2016/4/26 0026.
 */
public interface MemberCareService {

    /**
     * 关注某人或者取消关注
     * @param memberCare
     * @return
     */
    ModelResult<Integer> follow(MemberCare memberCare);

    /**
     * 统计自己的粉丝和自己关注的人
     * @param option
     * @return
     */
    ModelResult<Integer> count(MemberCare option);


    PageResult<MemberCare> queryPage(MemberCare option, DataPage<MemberCare> dataPage);

}
