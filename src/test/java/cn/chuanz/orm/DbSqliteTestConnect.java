package cn.chuanz.orm;

import cn.chuanz.orm.dbadapter.SqliteDb;
import cn.chuanz.util.ConfigRead;

public class DbSqliteTestConnect extends SqliteDb {

	private static DbSqliteTestConnect connect = null;
	private DbConfBean masterBean = null;
	private DbSqliteTestConnect (){
	}
	public static synchronized DbSqliteTestConnect instance(){
		if(connect == null)
			connect = new DbSqliteTestConnect();
		return connect;
	}
	
 

	@Override
	protected String getEncode() {
		return "utf-8";
	}
	@Override
	public boolean printSql() {
		return ConfigRead.readBooleanValue("showSql");
	}
	@Override
	protected DbConfBean getMasterDb() {
		if(masterBean == null){
			masterBean = new DbConfBean();
			masterBean.setDbName(ConfigRead.readValue("sqlite_test_db_name"));
		}
		return masterBean;
	}

}
