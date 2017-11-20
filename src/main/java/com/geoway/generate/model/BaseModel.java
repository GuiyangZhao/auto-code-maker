package com.geoway.generate.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/17 14:04
 * @description: 类型基础信息
 */
public class BaseModel {

  /**
   * 包路径
   */
  private String packageStr;

  /**
   * 需要导入的包
   */
  private Map<String,BaseModel> imports;

  /**
   * 类名
   */
  private String className;
  /**
   * 全类型的名,包含 包的名称
   */
  private String reference;
  /**
   * 主键的名称,目前只支持唯一主键 ,不支持联合主键
   */
  /**
   * 需要导入的包
   */
  private Set<String> importJavaPackage = new HashSet<String>();

  private String primaryKeyName;

  public String getPackageStr() {
    return packageStr;
  }

  public void setPackageStr(String packageStr) {
    this.packageStr = packageStr;
  }

  public Map<String, BaseModel> getImports() {
    return imports;
  }

  public void setImports(Map<String, BaseModel> imports) {
    this.imports = imports;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getPrimaryKeyName() {
    return primaryKeyName;
  }

  public void setPrimaryKeyName(String primaryKeyName) {
    this.primaryKeyName = primaryKeyName;
  }

  public Set<String> getImportJavaPackage() {
    return importJavaPackage;
  }

  public void setImportJavaPackage(Set<String> importJavaPackage) {
    this.importJavaPackage = importJavaPackage;
  }
}
