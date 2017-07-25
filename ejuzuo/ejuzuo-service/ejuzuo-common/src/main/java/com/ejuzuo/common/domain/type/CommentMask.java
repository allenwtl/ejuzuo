package com.ejuzuo.common.domain.type;

import com.ejuzuo.common.BaseType;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
public class CommentMask extends BaseType {
    private CommentMask(Integer index, String description) {
        super(index, description);
    }

    public static final CommentMask un_pingbi = new CommentMask(0, "未屏蔽");
    public static final CommentMask pingbi = new CommentMask(1, "已屏蔽");

}
