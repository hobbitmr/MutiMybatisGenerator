# MybatisGenerator-hobbit
 一个基于mybatis-generator 1.3.5 的版本上修改的代码生成器.目前只适合mysql数据库
## 功能点如下:
1.优化获取mysql的字段备注
2.生成基本增删改查
3.生成根据条件进行增删改查
4.生成批量删除和批量插入操作
5.生产查询的时候可以指定返回列的值

## 使用指南
1.下载源码,编译后.通过maven应用:       
```
            <plugin>
                <!--mybatis数据库逆向插件-->
                    <groupId>org.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-hobbit-maven-plugin</artifactId>
                    <version>1.0.0</version>
                <configuration>
                    <configurationFile>${project.basedir}/src/main/resources/generatorConfig.xml</configurationFile>
                </configuration>
            </plugin>
```
##### 2.在配置generatorConfig.xml中配置
````
            <context id="prod" targetRuntime="MyBatis3" defaultModelType="mutilCRUD">
````
##### 3.在项目中执maven插件
````
mybatis-generator-hobbit:generate
````