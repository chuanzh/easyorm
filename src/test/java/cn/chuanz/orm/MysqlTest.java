package cn.chuanz.orm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.chuanz.db.query.TUserQuery;
import cn.chuanz.db.rowdata.TUserRowData;

public class MysqlTest {

	public static void main(String[] args) throws Exception {
		new MysqlTest().update();
		
	}
	
	private void query() throws Exception {
		DbBasicService dbService = DbFactory.instanceService(DbTestConnect.instance());
		TUserQuery query = DbFactory.instance(dbService, TUserQuery.class);
		//query.ctnName("zhangsan");
		List<TUserRowData> rows = query.queryRows();
		for (TUserRowData row : rows) {
			row.printData();
		}
		dbService.freeResource();
	}
	
	private void update() throws Exception {
		DbBasicService dbService = DbFactory.instanceService(DbTestConnect.instance());
		//TUserRowData row = DbFactory.find(dbService, TUserRowData, 3);
		TUserRowData row = DbFactory.instance(dbService, TUserRowData.class);
		row.setId(3);
		row.setIntroduction("我叫张晓明明");
		row.setInsertTime(new Date());
		row.update();
		dbService.freeResource();
	}
	
	private void insert() throws Exception {
		DbBasicService dbService = DbFactory.instanceService(DbTestConnect.instance());
		TUserRowData row = DbFactory.instance(dbService, TUserRowData.class);
		row.setName("张晓明");
		row.setAge(20);
		row.setAddress("北京市朝阳区");
		row.setIntroduction("我叫张晓明");
		row.setInsertTime(new Date());
		row.insert();
		dbService.freeResource();
	}
	
	private void queryBySql() throws Exception {
		DbBasicService dbService = DbFactory.instanceService(DbTestConnect.instance());
		
		String sql = "select * from t_user";
		List<HashMap<String,String>> list = dbService.queryExecSql(sql);
		for (HashMap<String,String> map : list) {
			for (String s : map.keySet()) {
				System.out.println(s+":"+map.get(s));
			}
			System.out.println("======================");
		}
		
		dbService.freeResource();
	}
	
}
