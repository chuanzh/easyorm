package cn.chuanz.codetool;

import java.util.HashMap;

import cn.chuanz.util.FuncStatic;

public class FieldInfo {
	private String fieldName = null;
	private String fieldType = null;
	private String fieldComment = null;//ht
	private boolean fieldAutoIncreace = false;
	private boolean isKey = false;
	/**
	 * 传入字段信息，用逗号分隔
	 * fieldName, fieldType,
	 * @param value
	 * @return
	 */
	public static FieldInfo instance(HashMap<String, String> value){
		FieldInfo info = new FieldInfo();
		info.fieldName = value.get("fieldName").trim();
		info.fieldType = value.get("fieldType").trim();
		info.fieldComment = value.get("fieldComment").trim();
		if("true".equals(value.get("fieldAutoIncreace"))){
			info.fieldAutoIncreace = true;
		}
		return info;
	}
	
	public boolean getFieldAutoIncreace(){
		return this.fieldAutoIncreace;
	}

	public String getFieldComment() {
		return fieldComment;
	}
	public void setFieldComment(String fieldComment) {
		this.fieldComment = fieldComment;
	}
	public String getFieldName() {
		return fieldName;
	}
	public String getFieldNameHump() {
		return FuncStatic.convertUpperCaseToHump(fieldName.toUpperCase());
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType.toLowerCase();
	}
	public void setIsKey(){
		this.isKey = true;
	}
	public boolean getIfKey(){
		return this.isKey;
	}
	
	public String getFieldAnnot(){
		if(this.fieldType.indexOf("bigint") > -1){
			return "@FieldBigNumber";
		}
		if(this.fieldType.indexOf("int") > -1){
			return "@FieldNumber";
		} 
		if(this.fieldType.indexOf("time") > -1){
			return "@FieldDate";
		}
		if(this.fieldType.indexOf("text") > -1){
			return "@FieldClob";
		}
		if(this.fieldType.indexOf("char(1)") > -1){
			return "@FieldChar";
		}
		return "@FieldString"; 
	}
}
