package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

public class AdminOperType extends BaseType {

	private static final long serialVersionUID = 7526697671723405632L;
	public AdminOperType(Integer index, String description) {
		super(index, description);
	}

	// 管理员基本操作 1 - 10
	public static AdminOperType LOGIN = new AdminOperType(1, "登录");
	public static AdminOperType LOGOUT = new AdminOperType(2, "登出");
	public static AdminOperType EDIT_PROFILE = new AdminOperType(3, "修改信息");
	public static AdminOperType EDIT_SELF_PWD = new AdminOperType(4, "修改密码");
	// 系统角色操作 30 - 39
	public static AdminOperType SYSTEM_ROLE_ADD = new AdminOperType(30, "角色新增");
	public static AdminOperType SYSTEM_ROLE_EDIT = new AdminOperType(31, "角色编辑");
	public static AdminOperType SYSTEM_ROLE_ENABLE = new AdminOperType(32, "角色启用");
	public static AdminOperType SYSTEM_ROLE_DISABLE = new AdminOperType(33, "角色禁用");
	public static AdminOperType SYSTEM_ROLE_AUTH = new AdminOperType(34, "角色授权");
	// 系统用户操作 40 - 49
	public static AdminOperType SYSTEM_USER_ADD = new AdminOperType(40, "用户新增");
	public static AdminOperType SYSTEM_USER_EDIT = new AdminOperType(41, "用户编辑");
	public static AdminOperType SYSTEM_USER_ENABLE = new AdminOperType(42, "用户启用");
	public static AdminOperType SYSTEM_USER_DISABLE = new AdminOperType(43, "用户禁用");
	public static AdminOperType SYSTEM_USER_EDIT_PWD = new AdminOperType(44, "用户密码修改");
	// 新闻 50-59
	public static AdminOperType NEWS_ADD = new AdminOperType(50, "新闻新增");
	public static AdminOperType NEWS_EDIT = new AdminOperType(51, "新闻编辑");
	public static AdminOperType NEWS_RELEASE = new AdminOperType(52, "新闻发布");
	public static AdminOperType NEWS_DISABLE = new AdminOperType(53, "新闻禁用");
	public static AdminOperType NEWS_ENABLE = new AdminOperType(54, "新闻启用");
	// 活动 60-69
	public static AdminOperType ACTIVITY_ADD = new AdminOperType(60, "活动新增");
	public static AdminOperType ACTIVITY_EDIT = new AdminOperType(61, "活动编辑");
	public static AdminOperType ACTIVITY_RELEASE = new AdminOperType(62, "活动发布");
	public static AdminOperType ACTIVITY_DISABLE = new AdminOperType(63, "活动禁用");
	public static AdminOperType ACTIVITY_ENABLE = new AdminOperType(64, "活动启用");
	// 数字家居 70-79
	public static AdminOperType DIGITALFURNITURE_ADD = new AdminOperType(70, "数字家居新增");
	public static AdminOperType DIGITALFURNITURE_EDIT = new AdminOperType(71, "数字家居编辑");
	public static AdminOperType DIGITALFURNITURE_SHELF = new AdminOperType(72, "数字家居上架");
	public static AdminOperType DIGITALFURNITURE_UNSHELF = new AdminOperType(73, "数字家居下架");
	public static AdminOperType DIGITALFURNITURE_DISABLE = new AdminOperType(74, "数字家居禁用");
	public static AdminOperType DIGITALFURNITURE_ENABLE = new AdminOperType(75, "数字家居启用");
	// 设计师 、设计作品80-89
	public static AdminOperType DESIGNER_ENABLE = new AdminOperType(80, "设计师审核通过");
	public static AdminOperType DESIGNER_DISABLE = new AdminOperType(81, "设计师审核退回");
	public static AdminOperType DESIGNERWORK_ENABLE = new AdminOperType(80, "设计作品审核通过");
	public static AdminOperType DESIGNERWORK_DISABLE = new AdminOperType(81, "设计作品审核退回");
	// 合作品牌 90-99
	public static AdminOperType BRAND_ADD = new AdminOperType(90, "品牌新增");
	public static AdminOperType BRAND_EDIT = new AdminOperType(99, "品牌编辑");
	public static AdminOperType BRAND_DISABLE = new AdminOperType(92, "品牌禁用");
	public static AdminOperType BRAND_ENABLE = new AdminOperType(93, "品牌启用");
	public static AdminOperType BRAND_DELETE = new AdminOperType(94, "品牌删除");
	// 码表100-109
	public static AdminOperType CODEVALUE_ADD = new AdminOperType(100, "码表新增");
	public static AdminOperType CODEVALUE_EDIT = new AdminOperType(101, "码表编辑");
	public static AdminOperType CODEVALUE_DISABLE = new AdminOperType(102, "码表禁用");
	public static AdminOperType CODEVALUE_ENABLE = new AdminOperType(103, "码表启用");
	// 评论110-119
	public static AdminOperType COMMENT_MASKED = new AdminOperType(110, "评论屏蔽");
	public static AdminOperType COMMENT_UNMASKED = new AdminOperType(111, "评论解屏");
	public static AdminOperType COMMENT_DISABLE = new AdminOperType(112, "评论禁用");
	public static AdminOperType COMMENT_ENABLE = new AdminOperType(113, "评论启用");
	// 系统消息120-129
	public static AdminOperType MSG_ADD = new AdminOperType(120, "系统消息新增");
	public static AdminOperType MSG_EDIT = new AdminOperType(121, "系统消息编辑");
	public static AdminOperType MSG_DISABLE = new AdminOperType(122, "系统消息禁用");
	public static AdminOperType MSG_ENABLE = new AdminOperType(123, "系统消息启用");
	
}
