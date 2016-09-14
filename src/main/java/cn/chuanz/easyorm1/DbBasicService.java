package cn.chuanz.easyorm1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.chuanz.easyorm.util.FuncStatic;


public class DbBasicService implements NeedConnect {
	private static Logger logger = Logger.getLogger(DbBasicService.class);
	private DbConnectTool dbConnect = null;
	private MapTable table = null;
	private HashMap<String, Object> newData = null;
	private Connection tmpConn = null;
	private List<Statement> tmpStatement = null;
	private String threadId = null;
	protected DbBasicService() {
	}

	public void setThreadId(String var){
		this.threadId = var;
	}
	public String getThreadId(){
		return this.threadId;
	}
	private Connection getConnection(){
		try {
			tmpConn = this.dbConnect.getConnection();
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}
		return null;
	}
	@Override
	public void initConnect(DbConnectTool connect) {
		this.dbConnect = connect;
	}

	/** @param table */
	public void setMapTable(MapTable table) {
		this.table = table;
	}

	/** @param value */
	public void setNewData(HashMap<String, Object> value) {
		newData = value;
	}

	public String queryCreateSql(ConditionTool condtionTool) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(table.getSelectColumns());
		sql.append(" from ");
		sql.append(table.getTableName());
		if (condtionTool != null && condtionTool.hasCondition()) {
			sql.append(" where ");
			sql.append(this.getConditionStr(condtionTool));
		}
		sql.append(" order by ");
		sql.append(this.orderStr(condtionTool));

		String querySql = sql.toString();
		if (condtionTool.getReadLength() != 0 || condtionTool.getStartIndex() != 0) {
			querySql = this.dbConnect.formatPagerSql(querySql,
					condtionTool.getStartIndex(), condtionTool.getReadLength());
		}
		return querySql;
	}


	public List<HashMap<String, Object>> queryExec(ConditionTool condtionTool) throws Exception {
		String[] cs = table.getSelectColumns().split(",");
		String sql = this.queryCreateSql(condtionTool);
		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Statement resultStatement = null;
		ResultSet resultSet = null;
		try {
			resultStatement = this.getConnection().createStatement();
			resultSet = resultStatement.executeQuery(sql.toString());
			while (resultSet.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (String column : cs) {
					if(table.isDateColumn(column)){
						java.sql.Timestamp sqlDate = resultSet.getTimestamp(column);
						if(sqlDate != null){
							map.put(column, new Date(sqlDate.getTime()));
						}
					}else{
						map.put(column, resultSet.getString(column));
					}
				}
				list.add(map);
				
			}
		} catch (Exception e) {
			throw e;
		} finally {
			freeResult(resultSet);
			closeStatement(resultStatement);
		}
		return list;
	}

	public long queryExecCount(ConditionTool condtionTool) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as c from ");
		sql.append(table.getTableName());
		if (condtionTool != null && condtionTool.hasCondition()) {
			sql.append(" where ");
			sql.append(this.getConditionStr(condtionTool));
		}
		String querySql = sql.toString();

		ResultSet resultSet = null;
		Statement resultStatement = null;
		try {
			resultStatement = this.getConnection().createStatement();
			resultSet = resultStatement.executeQuery(querySql);
			resultSet.next();
			return resultSet.getLong(1);
		} catch (Exception e) {
			throw e;
		} finally {
			freeResult(resultSet);
			closeStatement(resultStatement);
		}
	}

	/** @param 调用完后需手动释放资源  */
	public ResultSet queryExecResultSet(String sql) throws Exception {
		Statement resultStatement = null;
		try {
			resultStatement = this.getConnection().createStatement();
			if(tmpStatement == null){
				tmpStatement = new ArrayList<Statement>();
			}
			tmpStatement.add(resultStatement);
			return resultStatement.executeQuery(sql.toString());
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		} 
		return null;
	}

	public String queryExecSqlOneValue(String sql) throws Exception {
		HashMap<String, String> map = this.queryExecSqlOneRow(sql);
		if(map != null){
			return map.values().iterator().next();
		}else{
			return null;
		}
	}
	
	public HashMap<String, String> queryExecSqlOneRow(String sql) throws Exception {
		List<HashMap<String, String>> list = this.queryExecSql(sql);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	public List<HashMap<String, String>> queryExecSql(String sql) throws Exception {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Statement resultStatement = null;
		ResultSet resultSet = null;
		try {
			resultStatement = this.getConnection().createStatement();
			resultSet = resultStatement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				for (int i=1; i <=columnCount; i++ ) {
					map.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
				}
				list.add(map);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			freeResult(resultSet);
			closeStatement(resultStatement);
		}
		return list;
	}
	
	private void freeResult(ResultSet result) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}
	}
	private void closeStatement(Statement resultStatement) {
		try {
			if (resultStatement != null) {
				resultStatement.close();
			}
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}
	}
	public void freeResource() {
		try {
			if(tmpStatement != null){
				for(Statement stmt : tmpStatement){
					if(!stmt.isClosed()){
						stmt.close();
					}
				}
			}
			if (this.tmpConn != null) {
 				tmpConn.close();
			}
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}
		tmpStatement = null;
		tmpConn = null;
	}

	public String updateCreateSql(ConditionTool condtionTool) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(table.getTableName());
		sql.append(" set ");
		for (String column : newData.keySet()) {
			sql.append(column);
			sql.append("=");
			sql.append(this.formatSqlValue(newData.get(column)));
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		if (condtionTool != null && condtionTool.hasCondition()) {
			sql.append(" where ");
			sql.append(this.getConditionStr(condtionTool));
		}
		 
		return sql.toString();
	}

	public int updateExec(ConditionTool condtionTool) throws Exception {
		String sql = updateCreateSql(condtionTool);

		Statement resultStatement = null;
		try {
			resultStatement = this.getConnection().createStatement();
			int i = resultStatement.executeUpdate(sql);
			return i;
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}finally{
			this.closeStatement(resultStatement);
		}
		return 0;
		
	}

	public String insertCreateSql() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(table.getTableName());
		sql.append(" (");
		for (String column : newData.keySet()) {
			sql.append(column);
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") values (");
		for (String column : newData.keySet()) {
			sql.append(this.formatSqlValue(newData.get(column)));
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return sql.toString();
	}

	public void insertExec() throws Exception {
		String sql = insertCreateSql();
		Statement resultStatement = null;
		try {
			resultStatement = this.getConnection().createStatement();
			resultStatement.executeUpdate(sql);
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}finally{
			this.closeStatement(resultStatement);
		}
 	}

	public String insertExecReauto() throws Exception {
		String sql = insertCreateSql();
		String reid = null;
		Statement resultStatement = null;
		try {
			resultStatement = this.getConnection().createStatement();
			resultStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (resultStatement.getGeneratedKeys().next()) {
				ResultSet rs = resultStatement.getGeneratedKeys();
				try {
					if (rs.next()) {
						reid = rs.getObject(1).toString();
					}
				} catch (Exception e) {
					throw e;
				} finally {
					this.freeResult(rs);
				}
			}
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}finally {
			this.closeStatement(resultStatement);
		}
		return reid;
	}

	public String deleteCreateSql(ConditionTool condtionTool) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" delete from ");
		sql.append(this.table.getTableName());
		if (condtionTool != null && condtionTool.hasCondition()) {
			sql.append(" where ");
			sql.append(this.getConditionStr(condtionTool));
		}
		return sql.toString();
	}

	public int deleteExec(ConditionTool condtionTool) throws Exception {
		String sql = deleteCreateSql(condtionTool);
		return execSql(sql);
	}

	/** @param sql */
	public int execSql(String sql) throws Exception {
		Statement resultStatement = null;
		try {
			resultStatement = this.getConnection().createStatement();
			int i = resultStatement.executeUpdate(sql);
			this.closeStatement(resultStatement);
			return i;
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
		}finally {
			this.closeStatement(resultStatement);
		}
		return 0;
	}

	private String formatSqlValue(Object value) {
		if(value == null){
			return " null ";
		}
		else if(value instanceof Date ){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			return "'" + df.format(value) + "'";
		}else{
			return "'" + value.toString().replaceAll("'", "''").replace("\\", "\\\\") + "'";
		}
	}

	private String getConditionOperator(ConditionOperator conditionOperatorEnum) {
		String o = null;
		switch (conditionOperatorEnum) {
		case EQ:
			o = " = ";
			break;
		case GE:
			o = " >= ";
			break;
		case GT:
			o = " > ";
			break;
		case LE:
			o = " <= ";
			break;
		case LT:
			o = " < ";
			break;
		case NOT:
			o = " != ";
			break;
		case LIKE:
			o = " like ";
			break;
		case IS:
			o = " is ";
			break;
		case IS_NOT:
			o = " is not ";
			break;
		case IN:
			o = " in ";
			break;
		}
		return o;
	}

	private String getConditionStr(ConditionTool condtionTool) {
		StringBuilder str = new StringBuilder();
		if (condtionTool != null) {
			for (AndCondition ac : condtionTool.getListCondition()) {
				str.append(ac.column);
				str.append(this.getConditionOperator(ac.operator));
				if (ac.operator == ConditionOperator.IS
						|| ac.operator == ConditionOperator.IS_NOT
						|| ac.operator == ConditionOperator.IN)
					str.append(ac.value);
				else
					str.append(this.formatSqlValue(ac.value));
				str.append(" and ");
			}

			for (OrCondition oc : condtionTool.getListOrCondition()) {
				str.append("(");
				for (int i = 0; i < oc.column.size(); i++) {
					str.append(oc.column.get(i));
					str.append(this.getConditionOperator(oc.operator.get(i)));
					if (oc.operator.get(i) == ConditionOperator.IS
							|| oc.operator.get(i) == ConditionOperator.IS_NOT
							|| oc.operator.get(i) == ConditionOperator.IN)
						str.append(oc.value.get(i));
					else
						str.append(this.formatSqlValue(oc.value.get(i)));
					str.append(" or ");
				}
				str.delete(str.length() - 4, str.length());
				str.append(")");
				str.append(" and ");
			}
		}

		if (str.length() > 5) {
			return str.substring(0, str.length() - 5);
		}
		return null;
	}

	private String orderStr(ConditionTool condtionTool) {
		if (FuncStatic.checkIsEmpty(condtionTool.getOrderStr())  )
			return table.getKeyColumns();
		else
			return condtionTool.getOrderStr();
	}
}
