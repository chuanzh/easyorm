package cn.chuanz.codetool;

import java.util.ArrayList;
import java.util.List;

import cn.chuanz.util.FuncStatic;

public class TableInfo {
	private String packagePath = "";
	private String tableName = null;
	private List<FieldInfo> fields =  new ArrayList<FieldInfo>();
	
	public void setPackagePath(String path){
		this.packagePath = path;
	}
	
	public String getPackagePath(){
		return this.packagePath;
	}
	
	public void setTableName (String tableName){
		this.tableName = tableName.trim();
	}
	public String getTableName(){
		return this.tableName;
	}
	
	public String getTableNameHeadUp(){
		return FuncStatic.convertUpperCaseToHump(this.tableName.toUpperCase());
	}
	
	public void putFields(FieldInfo info){
		this.fields.add(info);
	}
	
	public List<FieldInfo> getFields(){
		return this.fields;
	}
	
	public String getTableNameUp(){
		return this.getTableName().toUpperCase();
	}
	
	public void setKeyField(String fieldName){
		int keycount = 0;
		if(!FuncStatic.checkIsEmpty(fieldName)){
			String[] keynames = fieldName.split(",");
			for(FieldInfo info : fields)
			{
				for(String keyname : keynames){
					if(info.getFieldName().equals(keyname)){
						info.setIsKey();
						keycount++;
					}
				}
			}
		}
		System.out.println("keycount:"+keycount);
	}
}
