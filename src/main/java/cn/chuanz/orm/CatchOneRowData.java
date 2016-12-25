package cn.chuanz.orm;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import cn.chuanz.util.FuncStatic;

public class CatchOneRowData {
	private static Logger logger = Logger.getLogger ( CatchOneRowData.class) ;
	private HashMap<String, Object> cacheData = new HashMap<String, Object>();
	private HashMap<String, Object> newData = new HashMap<String, Object>();
	

	/**
	 * @param columnName
	 * @param value
	 */
	public void setValue(String columnName, Object value) {
		newData.put(columnName, value);
		cacheData.put(columnName, value);
	}

	public Object getValue(String column){
		return cacheData.get(column);
	}
	/** @param column */
	public String getValueString(String column) {
		try {
			if(cacheData.get(column) == null){
				return null;
			}
			return cacheData.get(column).toString();
		} catch (Exception e) {
			return null;
		}
	}

	/** @param columnName */
	public Date getValueDate(String columnName) {
		try {
			return  (Date) this.getValue(columnName);
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
			return null;
		}
	}

	/** @param columnName */
	public Integer getValueInt(String columnName) {
		try {
			if(cacheData.get(columnName) == null){
				return null;
			}
			
			String value = this.getValueString(columnName);
			return Integer.parseInt(value) ;
		} catch (Exception e) {
			return null;
		}
	}

	/** @param columnName */
	public Long getValueLong(String columnName) {
		try {
			if(cacheData.get(columnName) == null){
				return null;
			}
			
			String value = this.getValueString(columnName);
			return Long.parseLong(value) ;
		} catch (Exception e) {
			return null;
		}
	}
	
	/** @param columnName */
	public Character getValueChar(String columnName) {
		try {
			if(cacheData.get(columnName) == null){
				return null;
			}
			
			String value = this.getValueString(columnName);
			return value.charAt(0) ;
		} catch (Exception e) {
			return null;
		}
	}

	public HashMap<String, Object> getNewData() {
		return this.newData;
	}

	/** @param data */
	public void putData(HashMap<String, Object> data) {
		this.cacheData = data;
	}

	/**
	 * @param columnName
	 * @param value
	 */
	public void setCacheValue(String columnName, String value) {
		cacheData.put(columnName, value);
	}
	
	public void printData(){
		System.out.println("=============================");
		for(String column : this.cacheData.keySet()){
			System.out.println(column+":"+this.cacheData.get(column));
		}
	}
	
	public void printNewData(){
		System.out.println("=============================");
		for(String column : this.newData.keySet()){
			System.out.println(column+":"+this.newData.get(column));
		}
	}
}