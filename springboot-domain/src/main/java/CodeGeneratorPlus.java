import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * mybatis-plus代码生成器
 *
 * @author wuhongbin
 */
public class CodeGeneratorPlus {

    private static String jdbcUrl = "jdbc:mysql://rm-bp1j8k9s2330jz5jdyo.mysql.rds.aliyuncs.com:3306/security_jwt?useUnicode=true&characterEncoding=UTF-8";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "Whb18772916901";

    /**
     * 父级包名配置
     */
    private static String parentPackage = "com.wuhobin.springbootdomain";

    /**
     * 项目业务module
     */
    private static String moduleName = "springboot-domain";

    /**
     * 生成代码的 @author 值
     */
    private static String author = "wuhongbin";

    /**
     * 项目地址[改为自己本地项目位置]
     */
    private static String projectPath = "D:/idea_wuhongbin/springboot_study/springboot";

    /**
     * mapper.xml模板引擎
     */
    private static String mapperTemplatePath = "/templates/mapper.xml.ftl";

    /**
     * vo模板引擎
     */
    private static String entityTemplatePath = "/templates/vo.java.ftl";

    /**
     * do模板引擎
     */
    private static String dataObjectTemplatePath = "/templates/dataobject.java.ftl";

    /**
     * java mapper模板引擎
     */
    private static String javaMapperTemplatePath = "/templates/mapper.java.ftl";

    /**
     * vo生成目录
     */
    private static String voPath = "/src/main/java/com/wuhobin/springbootdomain/vo";

    /**
     * do生成目录
     */
    private static String doPath = "/src/main/java/com/wuhobin/springbootdomain/dataobject";

    /**
     * java Mapper生成目录
     */
    private static String mapperPath = "/src/main/java/com/wuhobin/springbootdomain/mapper";

    /**
     * 要生成代码的表名配置
     */
    private static String[] tables = {
            "t_user"
    };


    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder(jdbcUrl, jdbcUsername, jdbcPassword)
            .typeConvert(new MySqlTypeConvert() {
                @Override
                public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                    /**
                     *   tinyint转换成Boolean
                     */
                    if (fieldType.toLowerCase().contains("tinyint")) {
                        return DbColumnType.BOOLEAN;
                    }
                    /**
                     *  将数据库中datetime,date,timestamp转换成date
                     */
                    if (fieldType.toLowerCase().contains("datetime") || fieldType.toLowerCase().contains("date") || fieldType.toLowerCase().contains("timestamp")) {
                        return DbColumnType.DATE;
                    }
                    return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                }
            });


    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        //1、配置数据源
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                //2、全局配置
                .globalConfig(builder -> {
                    builder
                            .outputDir(projectPath + "/" + moduleName + "/src/main/java")  //指定输出目录
                            .author(author)    // 作者名
                            .disableOpenDir()  // 禁止打开输出目录
                            .dateType(DateType.ONLY_DATE) //时间策略
                            .commentDate("yyyy-MM-dd") // 注释日期
                            .enableSwagger();  //开启 swagger 模式
                })  //3、包配置
                .packageConfig(builder -> {
                    builder
                            .parent(parentPackage)
                            .entity("dataobject")   // Entity 包名
                            .mapper("mapper")       // Mapper 包名
                            .service("service")    //  Service 包名
                            .serviceImpl("service.impl") //Service Impl 包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/" + moduleName + "/src/main/resources/sqlmap/mapper"));
                })  // 4. 注入配置
                //.injectionConfig(consumer -> {
                //    consumer
                //            .beforeOutputFile((tableInfo, objectMap) -> {   // xml输出
                //                objectMap.put(projectPath + "/" + moduleName + "/src/main/resources/sqlmap/mapper"
                //                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML, mapperTemplatePath);
                //            })
                //            .beforeOutputFile((tableInfo, objectMap) -> {   // VO输出
                //                objectMap.put(projectPath + "/" + moduleName + voPath
                //                        + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA, entityTemplatePath);
                //            })
                //            .beforeOutputFile((tableInfo, objectMap) -> {   // DO输出
                //                objectMap.put(projectPath + "/" + moduleName + doPath
                //                        + "/" + tableInfo.getEntityName() + "DO" + StringPool.DOT_JAVA, dataObjectTemplatePath);
                //            })
                //            .beforeOutputFile((tableInfo, objectMap) -> {   // Mapper输出
                //                objectMap.put(projectPath + "/" + moduleName + mapperPath
                //                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA, javaMapperTemplatePath);
                //            });
                //
                //})
                .strategyConfig(builder -> {
                    builder
                            .enableCapitalMode()    //开启大写命名
                            .enableSkipView()   //创建实体类的时候跳过视图
                            .addInclude(tables)  // 设置需要生成的数据表名
                            .addTablePrefix("t")  //设置 过滤 表的前缀
                            .entityBuilder()       // Entity 策略配置
                            .enableLombok()       //开启 lombok 模型
                            .idType(IdType.AUTO)    // 全局主键类型
                            .formatFileName("%sDO")
                            .enableRemoveIsPrefix()  //开启 Boolean 类型字段移除 is 前缀
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略
                            .columnNaming(NamingStrategy.underline_to_camel)  //数据库表字段映射到实体的命名策略
                            .controllerBuilder()   // Controller 策略配置
                            .enableRestStyle()
                            .formatFileName("%sController")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp")
                            .mapperBuilder()
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper");
                })
                .templateConfig(builder -> {
                    builder
                            .disable(TemplateType.ENTITY)
                            .entity("/templates/dataobject.java")
                            //.mapperXml(null)
                            //.entity(null)
                            //.service(null)
                            //.serviceImpl(null)
                            .controller(null);// 不生成controller
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}
