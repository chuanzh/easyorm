package cn.chuanz.orm;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnectTool {
	
	public Connection getConnection() throws SQLException;
	
	public String formatPagerSql(String sql, int startIndex, int length);
}
