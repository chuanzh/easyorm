package cn.chuanz.orm;

import java.util.HashMap;
import java.util.List;

public class SqliteTest {

	public static void main(String[] args) throws Exception {
		DbBasicService dbService = DbFactory.instanceService(DbSqliteTestConnect.instance());
		dbService.execSql("CREATE TABLE t_user (ID INT PRIMARY KEY NOT NULL,NAME VARCHAR(20) NOT NULL,AGE INT NOT NULL,ADDRESS CHAR(50))");
		dbService.execSql("INSERT INTO t_user (ID,NAME,AGE,ADDRESS) VALUES (1, 'zhangchuan', 18, 'BeiJing');");
		List<HashMap<String, String>> list = dbService.queryExecSql("SELECT * FROM t_user");
		for (HashMap<String, String> map : list) {
			System.out.println(map);
		}
		dbService.freeResource();
	}
	
}
