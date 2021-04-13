<div align=center>
<img width="200px;" src="http://mars-framework.com/img/logo-black.png"/>
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
Magician的官方JDBC组件
</div>


## 项目简介

Magician-JDBC 是Magician的官方JDBC组件，可以很快捷的实现数据库操作

## 安装步骤

### 一、导入依赖

```xml
<dependency>
    <groupId>com.github.yuyenews</groupId>
    <artifactId>Magician-JDBC</artifactId>
    <version>最新版</version>
</dependency>

<!-- mysql驱动包 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.20</version>
</dependency>
<!-- druid数据源 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.5</version>
</dependency>

<!-- 这个是日志包，支持任意可以跟slf4j桥接的包 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>1.7.12</version>
</dependency>
```
### 二、创建数据源
```java
/*
 * 理论上支持任意 实现了 DataSource接口 的数据源
 * 这段代码在实战中 可以另起一个类去存放
 * 此处是用druid为例子的
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

### 三、创建JDBC资源
```java
// 创建JDBC的资源，建议只在项目启动的时候执行一次
MagicianJDBC.createJDBC()
        .addDataSource("a", dataSource)// 添加数据源，这个方法可以调用多次，添加多个数据源
        .defaultDataSourceName("a");// 设置默认数据源的名称
```

### 操作数据库
```java
/* ************** 操作数据库，这些代码 在实战中需要写到对应的DAO里面 ************ */
// 从test表查询主键=102的数据
DemoDTO demoDTO = JdbcTemplate.create().getOneByPrimaryKey("test","id", 102, DemoDTO.class);

// 删除主键=104的数据
JdbcTemplate.create().deleteByPrimaryKey("test","id", 104);

// 将DemoDTO保存到数据库
DemoDTO demo = new DemoDTO();
demo.setCreateTime(new Date());
demo.setName("testName");
JdbcTemplate.create().insert("test", demo);

// 修改主键=100的数据的 name为testName，createTime为当前时间
DemoDTO demo2 = new DemoDTO();
demo2.setCreateTime(new Date());
demo2.setName("testName");
demo2.setId(103);
JdbcTemplate.create().updateByPrimaryKey("test","id", demo2);

// 查询name=testName的数据
DemoDTO demo3 = new DemoDTO();
demo3.setName("testName");
List<DemoDTO> demoDTOList = JdbcTemplate.create().selectList("select * from test where name=#{name}", demo3, DemoDTO.class);

// 除了这些，JdbcTemplate里面还有很多操作数据库的方法
```

## 开发资源
- 开发文档: [http://magician-io.com/docs/jdbc/index.html](http://magician-io.com/docs/jdbc/index.html)
- 使用示例: [https://github.com/yuyenews/Magician-Example](https://github.com/yuyenews/Magician-Example)