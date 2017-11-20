package com.geoway.generate.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:36
 * @description:
 */
public class EntityInfo extends BaseModel{

  /**
   * 实体名
   */
  private String entityName;

  /**
   * 实体描述
   */
  private String entityDesc;



  /**
   * 表名
   */
  private String tableName;


  /**
   * 属性名以及对应的类型
   */
  private Map<String, String> propTypes;

  /**
   * 属性名以及注释的对应
   */
  private Map<String, String> propRemarks;

  /**
   * 属性名和jdbc类型的映射
   */
  private Map<String, String> propJdbcTypes;

  /**
   * 属性名和字段名的映射
   */
  private Map<String, String> propNameColumnNames;
  /**
   * 属性名和字段集合的映射
   */
  private Map<String,ColumnInfo> columnInfoMap;
  /**
   * 主键名称
   */
  private List<String> primaryKeys;
  /**
   * 数据库中表的注释信息
   */
  private String tableRemark;

  public String getTableRemark() {
    return tableRemark;
  }

  public void setTableRemark(String tableRemark) {
    this.tableRemark = tableRemark;
  }

  public Map<String, ColumnInfo> getColumnInfoMap() {
    return columnInfoMap;
  }

  public void setColumnInfoMap(
      Map<String, ColumnInfo> columnInfoMap) {
    this.columnInfoMap = columnInfoMap;
  }


  public Map<String, String> getPropJdbcTypes() {
    return propJdbcTypes;
  }


  public void setPropJdbcTypes(Map<String, String> propJdbcTypes) {
    this.propJdbcTypes = propJdbcTypes;
  }

  public Map<String, String> getPropRemarks() {
    return propRemarks;
  }

  public void setPropRemarks(Map<String, String> propRemarks) {
    this.propRemarks = propRemarks;
  }

  public Map<String, String> getPropTypes() {
    return propTypes;
  }

  public void setPropTypes(Map<String, String> propTypes) {
    this.propTypes = propTypes;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getEntityDesc() {
    return entityDesc;
  }

  public void setEntityDesc(String entityDesc) {
    this.entityDesc = entityDesc;
  }




  public String getTableName() {
    return tableName;
  }


  public void setTableName(String tableName) {
    this.tableName = tableName;
  }


  public Map<String, String> getPropNameColumnNames() {
    return propNameColumnNames;
  }


  public void setPropNameColumnNames(Map<String, String> propNameColumnNames) {
    this.propNameColumnNames = propNameColumnNames;
  }

  public List<String> getPrimaryKeys() {
    return primaryKeys;
  }

  public void setPrimaryKeys(List<String> primaryKeys) {
    this.primaryKeys = primaryKeys;
  }
}
