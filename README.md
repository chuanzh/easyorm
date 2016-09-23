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
使用dbService可直接操作数据库  

另外可以使用query对象来查询，返回对应的实例：  
比如有TUserQuery  
```Java
TUserQuery query = DbFactory.instance(dbService, TUserQuery.class);  
List<TUserRowData> list = query.queryRows();  
```
具体可以查看src/test/java下MysqlTest.java类  
