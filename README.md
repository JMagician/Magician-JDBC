<h1> 
    <a href="https://magician-io.com">Magician-JDBC</a> ·
    <img src="https://img.shields.io/badge/licenes-MIT-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/jdk-11+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/maven-3.5.4+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/release-master-brightgreen.svg"/>
</h1>

Magician-JDBC 是Magician的官方JDBC组件，支持多数据源，无sql单表操作，复杂操作可以写sql，事务管理等

## 文档

这个版本的文档还没出，jar也暂时还没传到中央库，尽请期待，不过可以自己编译，跟着示例玩一下试试
[https://magician-io.com](https://magician-io.com)

## 示例

### 导入依赖

```xml
<dependency>
    <groupId>com.github.yuyenews</groupId>
    <artifactId>Magician-JDBC</artifactId>
    <version>last version</version>
</dependency>

<!-- mysql driver package -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.20</version>
</dependency>
<!-- druid connection pool -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.5</version>
</dependency>

<!-- This is the log package, which supports any package that can be bridged with slf4j -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>1.7.12</version>
</dependency>
```
### 创建数据源
```java
// 这里用druid做示例，实际上可以支持任意实现了DataSource接口的连接池
DruidDataSource dataSource = new DruidDataSource();

Properties properties = new Properties();
properties.put("druid.name", "local");
properties.put("druid.url", "jdbc:mysql://127.0.0.1:3306/martian-test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=true&useSSL=false");
properties.put("druid.username", "root");
properties.put("druid.password", "123456");
properties.put("druid.driverClassName", Driver.class.getName());

dataSource.setConnectProperties(properties);
```

### 将数据源添加到JDBC
```java
// Create JDBC, it is recommended to execute it only once when the project starts
MagicianJDBC.createJDBC()
        .addDataSource("a", dataSource)// Add data source, this method can be called multiple times to add multiple data sources
        .defaultDataSourceName("a");// Set the name of the default data source
```

### 单表操作

按条件查询
```java
List<Condition> conditionList = new ArrayList<>();
conditionList.add(Condition.get("id > ?", 10));
conditionList.add(Condition.get("and name = ?", 100));
conditionList.add(Condition.get("order by create_time", Condition.NOT_WHERE));

List<Map> result = JDBCTemplate.get().select("表名", conditionList, Map.class);
```

按条件删除
```java
List<Condition> conditionList = new ArrayList<>();
conditionList.add(Condition.get("id > ?", 10));
conditionList.add(Condition.get("and name = ?", 100));
conditionList.add(Condition.get("order by create_time", Condition.NOT_WHERE));

JDBCTemplate.get().delete("表名", conditionList);
```

插入一条数据
```java
DemoPO demoPO = new DemoPo();
demoPO.setName("bee");
demoPo.setAge(10);

JDBCTemplate.get().insert("表名", demoPO);
```
修改数据
```java
DemoPO demoPO = new DemoPo();
demoPO.setName("bee");
demoPo.setAge(10);

List<Condition> conditionList = new ArrayList<>();
conditionList.add(Condition.get("id = ?", 10));
conditionList.add(Condition.get("and name = ?", 100));

JDBCTemplate.get().update("表名", demoPO, conditionList);
```

## 自己写sql

查询

```java
DemoPO demoPO = new DemoPo();
demoPO.setName("bee");
demoPo.setAge(10);

List<Map> result = JDBCTemplate.get().selectList("select * from xxx where name={name} and age={age}", demoPO, Map.class);
```

增删改

```java
DemoPO demoPO = new DemoPo();
demoPO.setName("bee");
demoPo.setAge(10);

JDBCTemplate.get().exec("update xxx set xxx = {xxx}, ccc = {ccc} where name={name} and age={age}", demoPO);
```

除此之外，还支持事务管理 和分页查询，详情可以查看文档