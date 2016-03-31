/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.da.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class BooleanToCharYnTypeHandler implements TypeHandler<Boolean> {

    private final String TRUE = "Y";
    private final String FALSE = "N";
    
    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        // 
        if (parameter) {
            ps.setString(i, TRUE);
        }
        else {
            ps.setString(i, FALSE);
        }
    }

    @Override
    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        // 
        String yn = rs.getString(columnName);
        return yn.equals(TRUE);
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
        // 
        String yn = rs.getString(columnIndex);
        return yn.equals(TRUE);
    }

    @Override
    public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 
        String yn = cs.getString(columnIndex);
        return yn.equals(TRUE);
    }
}
