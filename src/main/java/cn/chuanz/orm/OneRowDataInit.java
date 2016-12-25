package cn.chuanz.orm;

import java.util.HashMap;
 

public interface OneRowDataInit extends NeedDbBasicService{
	void initByKey(Object key) throws Exception;
	void putData(HashMap<String, Object> map);
}
