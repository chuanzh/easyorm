package cn.chuanz.codetool;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import cn.chuanz.util.FuncFile;
import cn.chuanz.util.FuncStatic;
 
/**
 * 根据数据库中表生成对应的文件(info文件，query文件，maptable文件)
 *
 */
public class CreateFile {
	public static String foldName = null;
	public static String encode = "UTF-8";
	static {
		Velocity.addProperty(Velocity.ENCODING_DEFAULT, encode);
		Velocity.addProperty(Velocity.INPUT_ENCODING, encode);
		Velocity.addProperty(Velocity.OUTPUT_ENCODING, encode); 
		Velocity.addProperty(Velocity.FILE_RESOURCE_LOADER_PATH, CreateFile.class.getResource("").toString()
				.replaceAll("^file:/", ""));
		Velocity.init();
	}
	public static void main(String[] args) throws  Exception {
		
		//配置表  若为null 或者 ""  则生成全库表的java类
		String tableName = "t_user";//app_sites 
		String packagePath = "com.chuanz.db";
		foldName = "D:/v/";
		//程序开始
		CreateFile cmt = new CreateFile();
		CodeToolDbConnect db = CodeToolDbConnect.instance();
		if(FuncStatic.checkIsEmpty(tableName)){
			List<String> tables = db.allTablesName();
			for(String name:tables){
				tableName = name;
				TableInfo table = new TableInfo();
				table.setPackagePath(packagePath);
				table.setTableName(tableName);
				List<HashMap<String, String>> fields = db.allFields(tableName);
				for (HashMap<String, String> field : fields) {
					table.putFields(FieldInfo.instance(field));
				}
				table.setKeyField(db.getKeyFieldName(tableName));
				cmt.createQuery(table);
				cmt.createRowdata(table);
				cmt.createMapTable(table);
			}
		}else{
			TableInfo table = new TableInfo();
			table.setPackagePath(packagePath);
			table.setTableName(tableName);
			List<HashMap<String, String>> fields = db.allFields(tableName);
			for (HashMap<String, String> field : fields) {
				table.putFields(FieldInfo.instance(field));
			}
			table.setKeyField(db.getKeyFieldName(tableName));
			cmt.createQuery(table);
			cmt.createRowdata(table);
			cmt.createMapTable(table);
		}
		
	}
	/**
	 * if table is null
	 * 
	 * @param table
	 * @throws IOException 
	 */
	public void createMapTable(TableInfo table) throws IOException {
		String packageName = table.getPackagePath()+".maptable";
		 
		Context context = new VelocityContext();
		context.put("table", table);
		context.put("package", packageName);
		StringWriter sw = new StringWriter();
		Velocity.mergeTemplate("MapTable.vm", encode, context, sw);
		FuncFile.insertFile(this.foldName+"maptable/"+table.getTableNameUp()+".java", sw.toString());
		System.out.println(this.foldName+"maptable/"+table.getTableNameUp()+".java : complete! " );
 	}

	public void createRowdata(TableInfo table) throws IOException{
		String packageName = table.getPackagePath()+".rowdata";
		 
		Context context = new VelocityContext();
		context.put("table", table);
		context.put("package", packageName);
		StringWriter sw = new StringWriter();
		Velocity.mergeTemplate("Rowdata.vm", encode, context, sw);
		FuncFile.insertFile(this.foldName+"rowdata/"+table.getTableNameHeadUp()+"RowData.java", sw.toString());
		System.out.println(this.foldName+"rowdata/"+table.getTableNameHeadUp()+"RowData.java : complete! " );
	}
	
	public void createQuery(TableInfo table) throws IOException{
		String packageName = table.getPackagePath()+".query";
		 
		Context context = new VelocityContext();
		context.put("table", table);
		context.put("package", packageName);
		StringWriter sw = new StringWriter();
		Velocity.mergeTemplate("Query.vm", encode, context, sw);
		FuncFile.insertFile(this.foldName+"query/"+table.getTableNameHeadUp()+"Query.java", sw.toString());
		System.out.println(this.foldName+"query/"+table.getTableNameHeadUp()+"Query.java : complete! " );
	}
	
	
}
 