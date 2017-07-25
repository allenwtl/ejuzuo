package com.ejuzuo.comment;

import com.ejuzuo.BaseTest;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.type.CommentMask;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.vo.CommentVO;
import com.ejuzuo.server.member.manager.CommentManager;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/25 0025.
 */
public class CommentManagerTest extends BaseTest {


    @Resource
    private CommentManager commentManager ;


    @Test
    public void testQuery(){
        CommentOption option = new CommentOption();
        option.setObjectId(1);
        option.setObjectType(0);
        option.setStatus(Status.STATUS.getIndex());
        option.setStartIndex(0);
        option.setPageSize(5);
        option.setMasked(CommentMask.un_pingbi.getIndex());
        List<CommentVO> commentVOList = commentManager.queryVOList(option).getModel() ;
        System.out.println(commentVOList);
    }

}
