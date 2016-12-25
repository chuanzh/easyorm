package cn.chuanz.db.rowdata;

import java.util.Date;

import org.apache.log4j.Logger;

import cn.chuanz.db.maptable.T_USER;
import cn.chuanz.orm.BaseOneRow;
import cn.chuanz.orm.MapTable;
import cn.chuanz.util.FuncStatic;

public class TUserRowData extends BaseOneRow{
	private static Logger logger = Logger.getLogger ( TUserRowData.class ) ;
	
	/**
	 * 
	 */
	public Integer getId() {
		try {
			return rowData.getValueInt(T_USER.f_id);
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
			return null;
		}
	}
	/**
	 * 
	 */
	public void setId(Integer value) {
		rowData.setValue(T_USER.f_id, value );
	}
	
	/**
	 * 
	 */
	public String getName() {
		return rowData.getValueString(T_USER.f_name);
	}
	/**
	 * 
	 */
	public void setName(String value) {
		rowData.setValue(T_USER.f_name, value );
	}
	
	/**
	 * 
	 */
	public Integer getAge() {
		try {
			return rowData.getValueInt(T_USER.f_age);
		} catch (Exception e) {
			logger.error(FuncStatic.errorTrace(e));
			return null;
		}
	}
	/**
	 * 
	 */
	public void setAge(Integer value) {
		rowData.setValue(T_USER.f_age, value );
	}
	
	/**
	 * 
	 */
	public String getAddress() {
		return rowData.getValueString(T_USER.f_address);
	}
	/**
	 * 
	 */
	public void setAddress(String value) {
		rowData.setValue(T_USER.f_address, value );
	}
	
	/**
	 * 
	 */
	public String getIntroduction() {
		return rowData.getValueString(T_USER.f_introduction);
	}
	/**
	 * 
	 */
	public void setIntroduction(String value) {
		rowData.setValue(T_USER.f_introduction, value );
	}
	
	/**
	 * 
	 */
	public Date getInsertTime() {
		return rowData.getValueDate(T_USER.f_insert_time);
	}
	/**
	 * 
	 */
	public void setInsertTime(Date value) {
		rowData.setValue(T_USER.f_insert_time, value );
	}
	
  
	@Override
	protected MapTable getMapTable() {
 		return T_USER.instance();
	}
 }
