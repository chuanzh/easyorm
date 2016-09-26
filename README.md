# easyorm
对基础的JDBC进行封装，支持多种数据库如：oracle，MySQL，sql server；数据库事务，读写分离。

DbBasicService封装JDBC，包括的基础操作数据库的方法，需要传入一个数据库连接对象DbConnect
可以通过DbFactory实例化一个DbBasicService对象

#配置说明
主配置文件：src/main/resources/cfg.properties   
里面包括数据配置等，如果需要打印SQL，设置showSql=true  
如，数据库配置：
```xml
#主库配置
test_db_ipandport=127.0.0.1:3306
test_db_name=test
test_db_username=dbuser
test_db_password=123456
test_db_poolconf=maxActive=200; maxIdle=50; maxWait=30000; removeAbandoned=true; removeAbandonedTimeout=10;

#从库配置，多个用“;”隔开
test_slave_db_ipandport=127.0.0.2:3306;127.0.0.3:3306;127.0.0.4:3306
test_slave_db_name=test;test;test
test_slave_db_username=dbuser;dbuser;dbuser
test_slave_db_password=123456;123456;123456
test_slave_db_poolconf=maxActive=200; maxIdle=50; maxWait=30000; removeAbandoned=true; removeAbandonedTimeout=10;
```
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
row.setName("zhangsan");
row.setAge(20);
row.setAddress("BeiJing");
row.setIntroduction("My Name is chuan.zhang");
row.setInsertTime(new Date());
row.insert();
dbService.freeResource();
```
使用TUserRowData对象进行更新操作，使用DbFactory.find方法可直接根据主键查询对象
```Java
DbBasicService dbService = DbFactory.instanceService(DbTestConnect.instance());
//TUserRowData row = DbFactory.find(dbService, TUserRowData.class, 3);
TUserRowData row = DbFactory.instance(dbService, TUserRowData.class);
row.setId(3);
row.setIntroduction("Hello, This is my introduction");
row.setInsertTime(new Date());
row.update();
dbService.freeResource();
```
直接通过SQL语句查询，有两种方式：
通过statement，和prepareStatement对象，若不指定参数，则会使用statement,指定参数使用prepareStatement
```Java
String sql = "select * from t_user";
List<HashMap<String,String>> list = dbService.queryExecSql(sql);
```
```Java
String sql = "select * from t_user where name like ?";
List<HashMap<String,String>> list = dbService.queryExecSql(sql,new Object[]{"张%"});
```
数据库事务使用，调用dbService.UseTransction()方法，若开启事务则默认使用主库的配置，插入两条用户关系数据：
```Java
dbService.UseTransction();
String sql = "insert into user(name,sex,age) values(?,?,?)";
dbService.execSql(sql,new Object[]{"zhangsan",1,20});
sql = "insert into user_relation(role,power,sign) value(?,?,?)";
dbService.execSql(sql,new Object[]{"2","100","this is my sign"});
dbService.commit();
```
具体可以查看src/test/java下MysqlTest.java类  

自动生成query,rowData,MapTable类，  
MapTable：表字段映射关系  
rowData：封装表一行记录数据，进行更新、插入、删除操作。  
query：数据查询类，返回rowData对象或rowData集合  
具体可查看：/src/main/java/cn/chuanz/codetool/CreateFile.java类  
```Java
//配置表  若为null 或者 ""  则生成全库表的java类
String tableName = "t_user"; 
//包名
String packagePath = "com.chuanz.db";
//生成文件路径
foldName = "D:/v/";
```

