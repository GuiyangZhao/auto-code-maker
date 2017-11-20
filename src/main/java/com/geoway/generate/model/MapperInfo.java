package com.geoway.generate.model;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:37
 * @description:
 */
public class MapperInfo {

  /**
   * XXXMapper.xml
   */
  private String fileName;


  private String namespace;

  private DaoInfo daoInfo;

  private EntityInfo entityInfo;


  public String getFileName() {
    return fileName;
  }


  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  public String getNamespace() {
    return namespace;
  }


  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }


  public DaoInfo getDaoInfo() {
    return daoInfo;
  }


  public void setDaoInfo(DaoInfo daoInfo) {
    this.daoInfo = daoInfo;
  }


  public EntityInfo getEntityInfo() {
    return entityInfo;
  }


  public void setEntityInfo(EntityInfo entityInfo) {
    this.entityInfo = entityInfo;
  }
}

