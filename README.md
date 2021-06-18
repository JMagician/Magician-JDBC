<br/>

<div align=center>
<img width="260px;" src="http://magician-io.com/img/logo-white.png"/>
</div>

<br/>

<div align=center>

<img src="https://img.shields.io/badge/licenes-MIT-brightgreen.svg"/>
<img src="https://img.shields.io/badge/jdk-11+-brightgreen.svg"/>
<img src="https://img.shields.io/badge/maven-3.5.4+-brightgreen.svg"/>
<img src="https://img.shields.io/badge/release-master-brightgreen.svg"/>

</div>
<br/>

<div align=center>
Magician's official JDBC component
</div>


## Introduction

Magician-JDBC is the official JDBC component of Magician, which can quickly implement database operations

## installation steps

### 1. Import dependencies

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
### 2. Create Datasource
```java
/*
 * In theory, any data source that implements the DataSource interface is supported
 * This code can be stored in another class in actual combat
 * Here is an example using druid
 */
DruidDataSource dataSource = new DruidDataSource();

Properties properties = new Properties();
properties.put("druid.name", "local");
properties.put("druid.url", "jdbc:mysql://127.0.0.1:3306/martian-test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=true&useSSL=false");
properties.put("druid.username", "root");
properties.put("druid.password", "123456");
properties.put("druid.driverClassName", Driver.class.getName());

dataSource.setConnectProperties(properties);
```

### 3. Create JDBC
```java
// Create JDBC, it is recommended to execute it only once when the project starts
MagicianJDBC.createJDBC()
        .addDataSource("a", dataSource)// Add data source, this method can be called multiple times to add multiple data sources
        .defaultDataSourceName("a");// Set the name of the default data source
```

### Operational Database
```java
/* ************** Operate the database, these codes need to be written into the corresponding DAO in actual combat ************ */

// Query data with primary key=102 from the test table
String sql = SqlBuilder.select("test").byPrimaryKey("id").builder();
DemoDTO param = new DemoDTO();
param.setId(102);
DemoDTO demoDTO = JdbcTemplate.create().selectOne(sql, param, DemoDTO.class);

// Delete data with primary key=103
String sql2 = SqlBuilder.delete("test").byPrimaryKey("id").builder();
DemoDTO param2 = new DemoDTO();
param2.setId(103);
JdbcTemplate.create().update(sql2, param2);

// Save the Demo to the database
DemoDTO demo = new DemoDTO();
demo.setCreateTime(new Date());
demo.setName("testName");

String sql3 = SqlBuilder.insert("test").column(DemoDTO.class).builder();
JdbcTemplate.create().update(sql3, demo);

// Modify the name of the data with primary key=105 to testName, and createTime to the current time
DemoDTO demo2 = new DemoDTO();
demo2.setCreateTime(new Date());
demo2.setName("testName");
demo2.setId(105);

String sql5 = SqlBuilder.update("test").column(DemoDTO.class).where("id = #{id}").builder();
JdbcTemplate.create().update(sql5, demo2);

// Query data with name=testName
DemoDTO demo3 = new DemoDTO();
demo3.setName("testName");
List<DemoDTO> demoDTOList = JdbcTemplate.create().selectList("select * from test where name=#{name}", demo3, DemoDTO.class);
// In addition to these, there are many ways to manipulate the database in JdbcTemplate
```

## Documentation and examples
- Document: [http://magician-io.com/docs/en/jdbc/index.html](http://magician-io.com/docs/jdbc/index.html)
- Example: [https://github.com/yuyenews/yuyenews-Magician-JDBC-Example](https://github.com/yuyenews/yuyenews-Magician-JDBC-Example)