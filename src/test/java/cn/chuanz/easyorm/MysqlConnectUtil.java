package cn.chuanz.easyorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.chuanz.util.ConfigRead;
import cn.chuanz.util.FuncStatic;

public class MysqlConnectUtil {
	
	private static Logger logger = Logger.getLogger(MysqlConnectUtil.class);
	
	private static String jdbcUrl = null;
	private static String userName = null;
	private static String password = null;

	static {
		//��ʼ����������
		try{   
		    Class.forName("com.mysql.jdbc.Driver") ;   
	    }catch(ClassNotFoundException e) {
	    	logger.error("jdbc �����ʧ��");
		    e.printStackTrace() ;   
		}  
		
		jdbcUrl = "jdbc:mysql://"+ConfigRead.readValue("DB_MYSQL_IP")+"/"+ConfigRead.readValue("DB_MYSQL_NAME");    
		userName = ConfigRead.readValue("DB_MYSQL_USERNAME");  
		password = ConfigRead.readValue("DB_MYSQL_PASSWORD");
	}
	
	private static Connection getConnect() {
		Connection con = null;
	    try{   
	    	con = DriverManager.getConnection(jdbcUrl, userName, password );   
	    }catch(SQLException se){   
	    	logger.error("��ݿ�����ʧ��!");
	    }
	    return con;
	}
	
	public static List<HashMap<String, String>> queryBySql(String sql) {
		List<HashMap<String, String>> list = new ArrayList<>();
		Connection con = null;
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
				if (con != null) {
					con.close();
				}
				rs = null;
				stmt = null;
				con = null;
			} catch (Exception e) {
				logger.error(FuncStatic.errorTrace(e));
			}
		}
		 
		return list;
	}
	
	public static void main(String[] args) {
		List<HashMap<String, String>> list = MysqlConnectUtil.queryBySql("select * from t_user");
		for (HashMap<String, String> map : list) {
			for(String key : map.keySet()) {
				System.out.println(key+":"+map.get(key));
			}
			System.out.println("=====================================");
		}
	}
	
}
