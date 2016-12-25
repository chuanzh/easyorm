package cn.chuanz.orm;

import cn.chuanz.orm.dbadapter.MysqlDb;
import cn.chuanz.util.ConfigRead;
import cn.chuanz.util.FuncStatic;

public class DbTestConnect extends MysqlDb {

	private static DbTestConnect connect = null;
	private DbConfBean[] savleBeanArray = null;
	private DbConfBean masterBean = null;
	private DbTestConnect (){
	}
	public static synchronized DbTestConnect instance(){
		if(connect == null)
			connect = new DbTestConnect();
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
			masterBean.setDbName(ConfigRead.readValue("test_db_name"));
			masterBean.setIpAndPort(ConfigRead.readValue("test_db_ipandport"));
			masterBean.setUserName(ConfigRead.readValue("test_db_username"));
			masterBean.setPassword(ConfigRead.readValue("test_db_password"));
			masterBean.setPoolConfByStr(ConfigRead.readValue("test_db_poolconf"));
		}
		return masterBean;
	}
	@Override
	protected DbConfBean[] getSlaveDbArray() {
		String slaveIpStr = ConfigRead.readValue("test_slave_ipandport");
		if(FuncStatic.checkIsEmpty(slaveIpStr)){
			return null;
		}else{
			if(savleBeanArray == null){
				String[] slaveIpArray = FuncStatic.trim(slaveIpStr.trim(), ";").split(";");
				String[] slaveDbNameArray	= ConfigRead.readValue("test_slave_name").trim().split(";");
				String[] slaveUserNameArray	= ConfigRead.readValue("test_slave_username").trim().split(";");
				String[] slavePasswordArray	= ConfigRead.readValue("test_slave_password").trim().split(";");
				String  slavePoolconf = ConfigRead.readValue("test_slave_poolconf");
				savleBeanArray = new DbConfBean[slaveIpArray.length];
				for(int i=0; i<slaveIpArray.length; i++){
					DbConfBean bean = new DbConfBean();
					bean.setIpAndPort(slaveIpArray[i]);
					bean.setDbName(slaveDbNameArray[i]);
					bean.setUserName(slaveUserNameArray[i]);
					bean.setPassword(slavePasswordArray[i]);
					bean.setPoolConfByStr(slavePoolconf);
					savleBeanArray[i] = bean;
				}
			}
			return savleBeanArray;
		}
	}
	
}
