package cn.chuanz.easyorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.chuanz.easyorm.util.FuncStatic;

public class OracleConnectUtil {
	
	private static Logger logger = Logger.getLogger(OracleConnectUtil.class);
	
	private static String jdbcUrl = null;
	private static String userName = null;
	private static String password = null;
	
	private static Connection con = null;

	static {
		try{   
		    Class.forName("oracle.jdbc.driver.OracleDriver") ;   
	    }catch(ClassNotFoundException e) {
	    	logger.error("jdbc 驱动加载失败！");
		    e.printStackTrace() ;   
		}  
		
		jdbcUrl = "jdbc:oracle:thin:@"+"58.83.130.90:1521"+ ":"+ "ora9i";    
		userName = "flightdyn";  
		password = "flight0515";
	}
	
	private static Connection getConnect() {
		if (con == null) {
			try{   
				con = DriverManager.getConnection(jdbcUrl, userName, password );   
			}catch(SQLException se){   
				logger.error("获取数据库连接失败!");
			}
		}
	    return con;
	}
	
	public static List<HashMap<String, String>> queryBySql(String sql) {
		List<HashMap<String, String>> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnect();
			stmt = con.createStatement();   
		    rs = stmt.executeQuery(sql);
		    int columnCount = rs.getMetaData().getColumnCount();
		    while(rs.next()) {
		    	HashMap<String, String> map = new HashMap<String,String>();
		    	for (int i=1;i<=columnCount;i++) {
		    		map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
		    	}
		    	list.add(map);
		    }
		    
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				rs = null;
				stmt = null;
			} catch (Exception e) {
				logger.error(FuncStatic.errorTrace(e));
			}
		}
		 
		return list;
	}
	
	public static void main(String[] args) {
		List<HashMap<String, String>> list = OracleConnectUtil.queryBySql("SELECT * FROM DAY_FLY_DTINFO WHERE DTFS_FLIGHTDEPCODE='CDG' AND DTFS_FLIGHTARRCODE='FRA' AND DTFS_FLIGHTNO='LH1027' AND DTFS_FLIGHTDATE='2016-09-05'");
		for (HashMap<String, String> map : list) {
			for(String key : map.keySet()) {
				System.out.println(key+":"+map.get(key));
			}
			System.out.println("=====================================");
		}
	}
	
}
