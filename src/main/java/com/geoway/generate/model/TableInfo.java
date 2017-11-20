package com.geoway.generate.model;

import java.util.List;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:38
 * @description:
 */
public class TableInfo {

  private String name;
  private String type;
  private String tableRemark;
  private List<ColumnInfo> columnList;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTableRemark() {
    return tableRemark;
  }

  public void setTableRemark(String tableRemark) {
    this.tableRemark = tableRemark;
  }

  public List<ColumnInfo> getColumnList() {
    return columnList;
  }

  public void setColumnList(List<ColumnInfo> columnList) {
    this.columnList = columnList;
  }

  @Override
  public String toString() {
    return "TableInfo{" +
        "name='" + name + '\'' +
        ", type='" + type + '\'' +
        ", tableRemark='" + tableRemark + '\'' +
        ", columnList=" + columnList +
        '}';
  }
}
