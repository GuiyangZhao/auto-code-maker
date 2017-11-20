package com.geoway.generate.task;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.geoway.generate.config.Configuration;
import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.model.ColumnInfo;
import com.geoway.generate.model.TableInfo;
import com.geoway.generate.util.DbUtil;
import com.geoway.generate.util.PropertyUtil;
import com.geoway.generate.util.StringUtil;


/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:46
 * @description:
 */

public class InitTask extends AbstractApplicationTask {

  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("初始化任务");

    //加载属性文件
    //字段类型与属性类型的映射
    PropertyUtil.loadProp("columnType2PropType.properties");

    //属性类型与包类名的映射
    PropertyUtil.loadProp("propType2Package.properties");

    //属性类型与jdbc类型的映射，注意这里为了防止与上面冲突，属性类型前加了_
    PropertyUtil.loadProp("propType2JdbcType.properties");

    //获取所有需要生成的表名
    List<String> tableList = StringUtil
        .splitStr2List(Configuration.getString("base.tableNames"), ",");
    //统一转换成大写
    for (int i = 0; i < tableList.size(); i++) {
      tableList.set(i, tableList.get(i).toUpperCase());
    }
    logger.info("需要生成的表：{}", tableList);

    //对应的实体名
    List<String> entityNames = StringUtil
        .splitStr2List(Configuration.getString("base.entityNames"), ",");

    //实体对应的描述
    List<String> entityDescs = StringUtil
        .splitStr2List(Configuration.getString("base.entityDescs"), ",");

    //添加映射关系
    Map<String, String> table2Entity = new HashMap<String, String>();
    for (int i = 0; i < tableList.size(); i++) {
      table2Entity.put(tableList.get(i), entityNames.get(i));
    }

    Map<String, String> entity2Desc = new HashMap<String, String>();
    for (int i = 0; i < entityNames.size(); i++) {
      entity2Desc.put(entityNames.get(i), entityDescs.get(i));
    }

    //放入上下文
    context.setAttribute("tableName.to.entityName", table2Entity);
    context.setAttribute("entityName.to.desc", entity2Desc);

    //连接数据库
    Connection conn = null;
    ResultSet tableRS = null;
    ResultSet columnRS = null;
    ResultSet primaryKeyRS = null;

    try {
      conn = DbUtil.getConn();
      DatabaseMetaData dbMetaData = conn.getMetaData();

      String schemaPattern = Configuration.getString("base.schemaPattern");
      //获取配置信息中的表的名称
      String tableNamePartern = Configuration.getString("base.tableNames");
      //获取表的结果集
      tableRS = dbMetaData
          .getTables(null, schemaPattern, "%", new String[]{"TABLE"});
      //遍历
      Map<String, TableInfo> tableInfos = new HashMap<String, TableInfo>(5);
      while (tableRS.next()) {
        //表名
        String tableName = tableRS.getString("TABLE_NAME");
        if (tableList.contains(tableName)) {
          logger.info("*****************************");
          logger.info("tableName:{}", tableName);

          TableInfo tableInfo = new TableInfo();
          tableInfo.setName(tableName);

          //表注释
          String tableRemark = tableRS.getString("REMARKS");
          tableInfo.setTableRemark(tableRemark);
          logger.info("表{}的注释:{}", tableName, tableRemark);

          //表类型
          String tableType = tableRS.getString("TABLE_TYPE");
          tableInfo.setType(tableType);
          logger.info("表{}的类型:{}", tableName, tableType);

          //字段
          //获取列的结果集
          columnRS = dbMetaData.getColumns(null, schemaPattern, tableName.toUpperCase(), "%");

          //获取表的主键
          primaryKeyRS = dbMetaData.getPrimaryKeys(null, schemaPattern, tableName.toUpperCase());
          List<String> primaryCoulmnNames = new ArrayList<>(1);
          while (primaryKeyRS.next()){
            String primaryCoulmnName = primaryKeyRS.getString("COLUMN_NAME");
            if (null!=primaryCoulmnName&&!"".equals(primaryCoulmnName)){
              primaryCoulmnNames.add(primaryCoulmnName.toLowerCase());
              logger.info("主键字段名称：{}", primaryCoulmnNames);
            }
          }

          List<ColumnInfo> columnList = new ArrayList<ColumnInfo>();
          while (columnRS.next()) {
            String columnName = columnRS.getString("COLUMN_NAME").toLowerCase();
            String columnType = columnRS.getString("TYPE_NAME").toLowerCase();
            String columnRemark = columnRS.getString("REMARKS");
            logger.info("字段名称：{}, 字段类型：{}, 字段注释：{}", columnName, columnType, columnRemark);

            int len = columnRS.getInt("COLUMN_SIZE");
                        logger.info("字段长度：{}", len);

            int precision = columnRS.getInt("DECIMAL_DIGITS");
                        logger.info("字段类型精度：{}", precision);

            if (columnName == null || "".equals(columnName)) {
              continue;
            }
            int isNullAble = columnRS.getInt("NULLABLE");

            ColumnInfo ci = new ColumnInfo();
            //判断是否为主键
            if (primaryCoulmnNames.size()>0){
              if (primaryCoulmnNames.contains(columnName)){
                ci.setPrimaryKey(true);
              }
            }
            ci.setName(columnName);
            ci.setType(columnType);
            ci.setRemark(columnRemark);
            ci.setLen(len);
            ci.setPrecision(precision);
            ci.setNullable(isNullAble>0);
            columnList.add(ci);
          }
          logger.info("*****************************");
          tableInfo.setColumnList(columnList);
          tableInfos.put(tableName, tableInfo);
        }

      }

      //放入上下文
      context.setAttribute("tableInfos", tableInfos);
    } catch (Exception e) {
      logger.info("初始化任务异常", e);
      e.printStackTrace();
    } finally {
      DbUtil.closeReso(conn, null, tableRS);
      DbUtil.closeReso(null, null, columnRS);
      DbUtil.closeReso(null, null, primaryKeyRS);
    }

    return false;
  }

}
