package com.ejuzuo.server.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ejuzuo.common.BaseType;

public class GenericTypeHandler<E extends BaseType> extends BaseTypeHandler<E>{

    private Class<E> type;

    public GenericTypeHandler(Class<E> type) {
      if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
      this.type = type;
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
    	ps.setInt(i, parameter.getIndex());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
       int index = rs.getInt(columnName);
       if(rs.wasNull()) {
            return null;
        }  
        return BaseType.valueOf(type, index);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    	int index = rs.getInt(columnIndex);
    	if(rs.wasNull()) {
            return null;
        } 
        return BaseType.valueOf(type, index);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    	int index = cs.getInt(columnIndex);
    	if(cs.wasNull()) {
            return null;
        }
        return BaseType.valueOf(type, index);
    }
}
