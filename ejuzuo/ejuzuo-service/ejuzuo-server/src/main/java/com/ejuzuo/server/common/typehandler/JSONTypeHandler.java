package com.ejuzuo.server.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSONObject;

/**
 *  JSON格式
 * @project JSONTypeHanler
 * @date 2013-7-3
 * Copyright (C) 2010-2016 www.2caipiao.com Inc. All rights reserved.
 */
public class JSONTypeHandler extends BaseTypeHandler<JSONObject>{

	@Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, (parameter == null ? null : parameter.toJSONString()));
    }

	@Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if(rs.wasNull()) {
			return null;
		}
		
		String value = rs.getString(columnName);
		return StringUtils.isEmpty(value) ? null : JSONObject.parseObject(value);
    }

	@Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if(rs.wasNull()) {
			return null;
		}
		
		String value = rs.getString(columnIndex);
		return StringUtils.isEmpty(value) ? null : JSONObject.parseObject(value);
    }

	@Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if(cs.wasNull()) {
			return null;
		}
		String value = cs.getString(columnIndex);
		return StringUtils.isEmpty(value) ? null : JSONObject.parseObject(value);
    }
}
