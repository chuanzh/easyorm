package cn.chuanz.orm.dbadapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import cn.chuanz.orm.DbConfBean;
import cn.chuanz.orm.DbConnectTool;
import cn.chuanz.util.FuncStatic;
 
public abstract class MysqlDb implements DbConnectTool {
	private static Logger logger = Logger.getLogger(MysqlDb.class);
	protected BasicDataSource dataSource = null;
	private String getConnectUrl(DbConfBean confBean) {
		String cons = "jdbc:mysql://"
				+ confBean.getIpAndPort()
				+ "/"
				+ confBean.getDbName()
				+ "?useOldAliasMetadataBehavior=true&autoReconnect=true&failOverReadOnly=false&characterEncoding="+getEncode();
		logger.info("db connect:" + cons);
		return cons;
	}
	protected abstract DbConfBean getMasterDb();
	protected abstract String getEncode();
	protected MysqlDb(){
		initDataSource();
	}
	protected void initDataSource() {
		try {
			dataSource = new BasicDataSource(); 
			dataSource.setValidationQuery("select 1");
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl(getConnectUrl(getMasterDb()));
			dataSource.setUsername(getMasterDb().getUserName());
			dataSource.setPassword(getMasterDb().getPassword());
			 
			HashMap<String, String> masterPoolConf = getMasterDb().getPoolConf();
			for(String key : masterPoolConf.keySet()){
				BeanUtils.setProperty(dataSource, key, masterPoolConf.get(key));
			}
			
		} catch (Exception e) {
			logger.error("数据库初始化错误",e);
		}
 	}
	@Override
	public Connection getConnection() throws SQLException{
		return this.dataSource.getConnection();
 	}
  
	@Override
	public String formatPagerSql(String sql, int startIndex, int length) {
		return sql + " limit " + startIndex + ","+length;
	}

	/**
	 * 返回某个表的所有字段,字段类型，字段注释， 是否自增长,
	 * @param table
	 * @return
	 */
	public List<HashMap<String, String>> allFields(String table){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			conn = this.getConnection();
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery("SHOW FULL COLUMNS FROM "+getMasterDb().getDbName()+"."+table);
			while (resultSet.next()){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("fieldName", resultSet.getString(1));
				map.put("fieldType", resultSet.getString(2));
				map.put("fieldComment", resultSet.getString(9));
				if(!FuncStatic.checkIsEmpty(resultSet.getString("Extra"))){
					if(resultSet.getString("Extra").contains("auto_increment")){
						map.put("fieldAutoIncreace", "true");
					}
				}
				list.add(map);
			}
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}finally{
			try {
				if(resultSet != null)
					resultSet.close();
			} catch (Exception e2) {
				logger.error(FuncStatic.errorTrace(e2));
			}
			try {
				if(stmt != null)
					stmt.close();
			} catch (Exception e2) {
				logger.error(FuncStatic.errorTrace(e2));
			}
			try {
				if(conn != null)
					conn.close();
			} catch (Exception e2) {
				logger.error(FuncStatic.errorTrace(e2));
			}
		}
		return list;
	}
	 
}
