package cn.chuanz.db.maptable;

import cn.chuanz.orm.MapTable;
import cn.chuanz.orm.fileannotation.FieldAuto;
import cn.chuanz.orm.fileannotation.FieldDate;
import cn.chuanz.orm.fileannotation.FieldKey;
import cn.chuanz.orm.fileannotation.FieldNumber;
import cn.chuanz.orm.fileannotation.FieldString;
 
public class T_USER extends MapTable{
	public static final String TABLE_NAME = "t_user"; 
	
	/**
	 * 
	 */
	@FieldKey
	@FieldAuto
	@FieldNumber
	public static final String f_id = "id";
	
	/**
	 * 
	 */
	@FieldString
	public static final String f_name = "name";
	
	/**
	 * 
	 */
	@FieldNumber
	public static final String f_age = "age";
	
	/**
	 * 
	 */
	@FieldString
	public static final String f_address = "address";
	
	/**
	 * 
	 */
	@FieldString
	public static final String f_introduction = "introduction";
	
	/**
	 * 
	 */
	@FieldDate
	public static final String f_insert_time = "insert_time";
	
	 
	private T_USER(){}
	private static T_USER instanceObj = null;
	public static T_USER instance(){
		if(instanceObj == null){
			instanceObj = new T_USER();
		}
		return instanceObj;
	}
}
 