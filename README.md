# easyorm
对基础的JDBC进行封装，支持多种数据库如：oracle，MySQL，sql server；数据库事务，读写分离。

DbBasicService封装JDBC，包括的基础操作数据库的方法，需要传入一个数据库连接对象DbConnect
可以通过DbFactory实例化一个DbBasicService对象

#配置说明
主配置文件：src/main/resources/cfg.properties   
里面包括数据配置等，如果需要打印SQL，设置showSql=true  
日志配置文件：src/main/resources/log4j.xml  

#使用方法
新建一个数据库连接对象，如果是MySQL连接，继承MysqlDb  
初始化一个DbBasicService：  
```Java
DbBasicService dbService = DbFactory.instanceService(DbTestConnect.instance());  
```
使用dbService可直接操作数据库，注意使用完成后要使用dbService.freeResource();释放数据库连接  

另外可以使用query对象来查询，返回对应的实例：  
比如有TUserQuery  
```Java
TUserQuery query = DbFactory.instance(dbService, TUserQuery.class);  
query.setName("zhangsan");
List<TUserRowData> list = query.queryRows();  
```
使用TUserRowData对象进行插入操作
```Java
DbBasicService dbService = DbFactory.instanceService(DbTestConnect.instance());
TUserRowData row = DbFactory.instance(dbService, TUserRowData.class);
row.setName("张晓明");
row.setAge(20);
row.setAddress("北京市朝阳区");
row.setIntroduction("我叫张晓明");
row.setInsertTime(new Date());
row.insert();
dbService.freeResource();
```
使用TuserRowData对象进行更新操作，使用DbFactory.find方法可直接根据主键查询对象
```Java
DbBasicService dbService = DbFactory.instanceService(DbTestConnect.instance());
//TUserRowData row = DbFactory.find(dbService, TUserRowData.class, 3);
TUserRowData row = DbFactory.instance(dbService, TUserRowData.class);
row.setId(3);
row.setIntroduction("我叫张晓明明");
row.setInsertTime(new Date());
row.update();
dbService.freeResource();
```
具体可以查看src/test/java下MysqlTest.java类  
