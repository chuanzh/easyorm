package cn.chuanz.db.query;

import java.util.List;

import cn.chuanz.db.maptable.T_USER;
import cn.chuanz.db.rowdata.TUserRowData;
import cn.chuanz.orm.BaseRowsSet;
import cn.chuanz.orm.ConditionOperator;
import cn.chuanz.orm.MapTable;
 
public class TUserQuery extends BaseRowsSet{

	/**
	 * 
	 */
	public void ctnId(String value) {
		conditionTool.addCondition(T_USER.f_id,value,ConditionOperator.EQ);
	}
	/**
	 * 
	 */
	public void ctnId(String value,ConditionOperator operator) {
		conditionTool.addCondition(T_USER.f_id,value,operator);
	}
	/**
	 * 
	 */
	public void ctnName(String value) {
		conditionTool.addCondition(T_USER.f_name,value,ConditionOperator.EQ);
	}
	/**
	 * 
	 */
	public void ctnName(String value,ConditionOperator operator) {
		conditionTool.addCondition(T_USER.f_name,value,operator);
	}
	/**
	 * 
	 */
	public void ctnAge(String value) {
		conditionTool.addCondition(T_USER.f_age,value,ConditionOperator.EQ);
	}
	/**
	 * 
	 */
	public void ctnAge(String value,ConditionOperator operator) {
		conditionTool.addCondition(T_USER.f_age,value,operator);
	}
	/**
	 * 
	 */
	public void ctnAddress(String value) {
		conditionTool.addCondition(T_USER.f_address,value,ConditionOperator.EQ);
	}
	/**
	 * 
	 */
	public void ctnAddress(String value,ConditionOperator operator) {
		conditionTool.addCondition(T_USER.f_address,value,operator);
	}
	/**
	 * 
	 */
	public void ctnIntroduction(String value) {
		conditionTool.addCondition(T_USER.f_introduction,value,ConditionOperator.EQ);
	}
	/**
	 * 
	 */
	public void ctnIntroduction(String value,ConditionOperator operator) {
		conditionTool.addCondition(T_USER.f_introduction,value,operator);
	}
	/**
	 * 
	 */
	public void ctnInsertTime(String value) {
		conditionTool.addCondition(T_USER.f_insert_time,value,ConditionOperator.EQ);
	}
	/**
	 * 
	 */
	public void ctnInsertTime(String value,ConditionOperator operator) {
		conditionTool.addCondition(T_USER.f_insert_time,value,operator);
	}
	 
	public List<TUserRowData> queryRows() throws Exception{
		return this.queryRows(TUserRowData.class);
	} 
	
	@Override
	protected MapTable getMapTable() {
 		return T_USER.instance();
	}
	 
}
