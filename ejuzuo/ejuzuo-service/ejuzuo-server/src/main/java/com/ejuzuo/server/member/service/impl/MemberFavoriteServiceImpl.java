package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberFavorite;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.service.MemberFavoriteService;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.common.vo.FavoriteVO;
import com.ejuzuo.server.member.manager.MemberFavoriteManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */

@Service("memberFavoriteService")
public class MemberFavoriteServiceImpl implements MemberFavoriteService {

    @Resource
    private MemberFavoriteManager memberFavoriteManager ;
    
	@Resource
	private MemberService memberService;

    @Override
    public ModelResult<MemberFavorite> save(MemberFavorite memberFavorite) {
        return memberFavoriteManager.save(memberFavorite);
    }

    @Override
    public ModelResult<DataPage<MemberFavorite>> queryByPage(MemberFavoriteOption option, DataPage<MemberFavorite> dataPage) {
        return memberFavoriteManager.queryByPage(option, dataPage);
    }

    @Override
    public ModelResult<Boolean> deleteById(Integer id) {
        return memberFavoriteManager.deleteById(id);
    }

    @Override
    public ModelResult<Boolean> deleteByIds(List<Integer> ids, Integer memberId) {
        return memberFavoriteManager.deleteByIds(ids, memberId);
    }

    @Override
    public ModelResult<Integer> count(MemberFavoriteOption option) {
        return memberFavoriteManager.count(option);
    }

    @Override
    public ModelResult<List<MemberFavorite>> queryList(MemberFavoriteOption option) {
        return memberFavoriteManager.queryList(option);
    }

    @Override
    public ModelResult<Integer> collect(MemberFavorite memberFavorite) {
    	//a)操作是取消收藏，不受权限控制。
    	MemberFavoriteOption option = new MemberFavoriteOption();
        option.setMemberId(memberFavorite.getMemberId());
        option.setObjectId(memberFavorite.getObjectId());
        option.setObjectType(memberFavorite.getObjectType());

        List<MemberFavorite> memberFavoriteList = queryList(option).getModel();
        if (memberFavoriteList != null && !memberFavoriteList.isEmpty()) {
            deleteById(memberFavoriteList.get(0).getId());
            option.setMemberId(null);
            return new ModelResult<>(this.count(option).getModel());
        }
    	
    	//b)操作是收藏，需要授权控制。    	
		//普通会员不能收藏。
		boolean canCollect = false;	
		if(memberService.isDesingerByMemberId(memberFavorite.getMemberId())){
			//高级会员只能收藏200件			
			MemberFavoriteOption mf = new MemberFavoriteOption();
			mf.setMemberId(memberFavorite.getMemberId());			
			Integer count = memberFavoriteManager.count(mf).getModel();//个人收藏总数
			if (count >= 200) {
				//高级会员收藏不能超过200件
				return new ModelResult<Integer>(0).withError("error", "您好！您的收藏容量已满，请及时删减收藏内容或及时升级为VIP会员获取无限收藏容量！");
			}
			canCollect = true;		
		}		
		
		if (!canCollect) {
			//VIP会员收藏数量不限制
			if(memberService.isVipByMemberId(memberFavorite.getMemberId())) {
				canCollect = true;
			}
		}
		
		if(!canCollect) {	
			//普通会员不能收藏！
			return new ModelResult<Integer>(0).withError("error", "您好！普通会员暂时不支持收藏功能，请及时通过完善个人验证/公司验证资料升级成为高级会员或者成为VIP会员使用收藏功能！");
		}    	
    	
        return memberFavoriteManager.collect(memberFavorite);
    }

    @Override
    public ModelResult<List<FavoriteVO>> queryVOList(MemberFavoriteOption option) {
        return memberFavoriteManager.queryVOList(option);
    }

    @Override
    public PageResult<FavoriteVO> queryVOPage(MemberFavoriteOption option, DataPage<MemberFavorite> dataPage) {
        return memberFavoriteManager.queryVOPage(option, dataPage);
    }
}
