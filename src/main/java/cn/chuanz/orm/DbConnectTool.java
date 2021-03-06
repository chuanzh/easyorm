package cn.chuanz.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface DbConnectTool {
	
	public Connection getConnection(boolean writeFlag) throws SQLException;
	
	/**
	 * 是否将执行的SQL打印到日志
	 * @return 
	 */
	public boolean printSql();
	
	public String formatPagerSql(String sql, int startIndex, int length);
	 
	public List<String> allTablesName()  ;
	
	/**
	 * 返回某个表的所有字段及字段类型，字段与字段类型之间用逗号分隔
	 * @param table
	 * @return
	 */
	public List<HashMap<String, String>> allFields(String table);
	
}
