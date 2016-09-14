package cn.chuanz.easyorm1;

import java.util.HashMap;
 

public interface OneRowDataInit extends NeedDbBasicService{
	void initByKey(Object key) throws Exception;
	void putData(HashMap<String, Object> map);
}
