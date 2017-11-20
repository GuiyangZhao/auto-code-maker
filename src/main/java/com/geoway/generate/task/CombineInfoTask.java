package com.geoway.generate.task;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.model.ActionInfo;
import com.geoway.generate.model.BaseModel;
import com.geoway.generate.model.ColumnInfo;
import com.geoway.generate.model.DaoInfo;
import com.geoway.generate.model.EntityInfo;
import com.geoway.generate.model.ServiceImplInfo;
import com.geoway.generate.model.ServiceInfo;
import com.geoway.generate.model.TableInfo;
import com.geoway.generate.model.VoInfo;
import com.geoway.generate.util.PropertyUtil;
import com.geoway.generate.util.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:43
 * @description:
 */
public class CombineInfoTask extends AbstractApplicationTask {

  @SuppressWarnings("unchecked")
  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("组装信息");

    //获取实体相关的配置
    String packageName = Configuration.getString("entity.package");
    //存放路径
    String path = Configuration.getString("entity.path");

    logger.info("所有实体的包名为{}， 路径为：{}", packageName, path);
    List<DaoInfo> daoList = new ArrayList<DaoInfo>();
    List<VoInfo> voList = new ArrayList<VoInfo>();
    List<ServiceInfo> serviceList = new ArrayList<ServiceInfo>();
    List<ServiceImplInfo> serviceImplList = new ArrayList<ServiceImplInfo>();
    List<ActionInfo> actionList = new ArrayList<ActionInfo>();
    //引用包的相关信息
    Map<String,BaseModel> actionImports ;
    Map<String,BaseModel> serviceImports ;
    Map<String,BaseModel> serviceImplImports ;
    Map<String,BaseModel> voImports ;
    Map<String,BaseModel> daoImports ;
    Map<String,BaseModel> entityImports ;
    //主键集合,目前只支持一个主键信息
    List<String> initPrimaryKeys ;
    String primaryKeyName = "!!Error!!";

    //获取表和实体的映射集合
    Map<String, String> table2Entities = (Map<String, String>) context
        .getAttribute("tableName.to.entityName");
    Map<String, String> entity2Desc = (Map<String, String>) context
        .getAttribute("entityName.to.desc");
    Map<String, TableInfo> tableInfos = (Map<String, TableInfo>) context.getAttribute("tableInfos");

    List<EntityInfo> entityInfos = new ArrayList<EntityInfo>();
    for (Entry<String, String> entry : table2Entities.entrySet()) {
      initPrimaryKeys = new ArrayList<>(1);
      EntityInfo entityInfo = new EntityInfo();
      //表名
      String tableName = entry.getKey();
      //实体名
      String entityName = entry.getValue();
      //表信息
      TableInfo tableInfo = tableInfos.get(tableName);

      Set<String> javaImports = new HashSet<String>();
      Map<String, String> propTypes = new LinkedHashMap<String, String>();
      Map<String, String> propRemarks = new LinkedHashMap<String, String>();
      Map<String, String> propJdbcTypes = new LinkedHashMap<String, String>();
      Map<String, String> propName2ColumnNames = new LinkedHashMap<String, String>();
      Map<String,ColumnInfo> columnInfoMap = new LinkedHashMap<>(10);


      entityInfo.setTableName(tableName);
      entityInfo.setTableRemark(tableInfo.getTableRemark());
      entityInfo.setEntityName(entityName);
      entityInfo.setEntityDesc(entity2Desc.get(entityName));
      entityInfo.setClassName(
          PropertyUtil.getNamePrefix(Constants.ENTITY) + entityName + PropertyUtil
              .getNameSuffix(Constants.ENTITY));
      entityInfo.setPackageStr(packageName);

      //遍历表字段信息,清理不必要的主键信息
      initPrimaryKeys.clear();
      List<ColumnInfo> columns = tableInfo.getColumnList();
      for (ColumnInfo columnInfo : columns) {
        String fieldName = columnInfo.getName();
        String fieldType = columnInfo.getType();

        //通过字段名生成属性名
        String propName = StringUtil.convertFieldName2PropName(fieldName);
        String propType = PropertyUtil.getValueByKey(fieldType);
        //获得主键的名称
        if (columnInfo.isPrimaryKey()){
          initPrimaryKeys.add(propName);
        }
        propTypes.put(propName, propType);
        propRemarks.put(propName, columnInfo.getRemark());
        propJdbcTypes.put(propName, PropertyUtil.getValueByKey("_" + propType));
        propName2ColumnNames.put(propName, columnInfo.getName().toUpperCase());
        columnInfoMap.put(propName,columnInfo);
      }
      logger.info("属性类型：{}", propTypes);
      logger.info("属性jdbcTypes：{}", propJdbcTypes);

      //获取此实体所有的类型
      Collection<String> types = propTypes.values();

      for (String type : types) {
        if (!StringUtil.isEmpty(PropertyUtil.getValueByKey(type))) {
          javaImports.add(PropertyUtil.getValueByKey(type));
        }
      }
      logger.info("imports:{}", javaImports);
      entityInfo.setPropTypes(propTypes);
      entityInfo.setPropRemarks(propRemarks);
      entityInfo.setPropJdbcTypes(propJdbcTypes);
      entityInfo.setPropNameColumnNames(propName2ColumnNames);
      entityInfo.setImportJavaPackage(javaImports);
      entityInfo.setColumnInfoMap(columnInfoMap);
      entityInfo.setReference(entityInfo.getPackageStr() + "." + entityInfo.getClassName());
      entityInfo.setPrimaryKeys(initPrimaryKeys);
      //获取主键信息
      if (initPrimaryKeys.size()>0){
        primaryKeyName =StringUtil.capitalize(initPrimaryKeys.get(0));
      }
      //entity
      entityInfo.setPrimaryKeyName(primaryKeyName);
      entityInfos.add(entityInfo);
      initPrimaryKeys = null;
    }

    /****************************************************************/

    //组装息
    DaoInfo daoInfo;
    VoInfo voInfo;
    ServiceInfo serviceInfo;
    ServiceImplInfo serviceImplInfo;
    ActionInfo actionInfo;

    for (EntityInfo entityInfo : entityInfos) {
      //在塞入之前需要清空上述包引入的集合
      //引用包的相关信息
      actionImports = new HashMap<String,BaseModel>(2);
      serviceImports = new HashMap<String,BaseModel>(2);
      serviceImplImports = new HashMap<String,BaseModel>(2);
      voImports = new HashMap<String,BaseModel>(2);
      daoImports = new HashMap<String,BaseModel>(2);
      entityImports = new HashMap<String,BaseModel>(2);

      //获得当前的primaryKey
      primaryKeyName = entityInfo.getPrimaryKeyName();


      //VO
      voInfo = new VoInfo();
      voInfo.setPackageStr(Configuration.getString("vo.package"));
      voInfo.setClassName(
          PropertyUtil.getNamePrefix(Constants.VO) + entityInfo.getEntityName() + PropertyUtil
              .getNameSuffix(Constants.VO));
      voInfo.setReference(voInfo.getPackageStr() + "." + voInfo.getClassName());
      voInfo.setPrimaryKeyName(primaryKeyName);

      //DAO
      daoInfo = new DaoInfo();
      daoInfo.setPackageStr(Configuration.getString("dao.package"));
      daoInfo.setClassName(
          PropertyUtil.getNamePrefix(Constants.DAO) + entityInfo.getEntityName() + PropertyUtil
              .getNameSuffix(Constants.DAO));
      daoInfo.setReference(daoInfo.getPackageStr()+"."+daoInfo.getClassName());
      daoInfo.setPrimaryKeyName(primaryKeyName);


      //Service
      serviceInfo = new ServiceInfo();
      serviceInfo.setPackageStr(Configuration.getString("service.package"));
      serviceInfo.setClassName(
          PropertyUtil.getNamePrefix(Constants.SERVICE) + entityInfo.getEntityName()
              + PropertyUtil.getNameSuffix(Constants.SERVICE));
      serviceInfo.setReference(serviceInfo.getPackageStr()+"."+serviceInfo.getClassName());
      serviceInfo.setPrimaryKeyName(primaryKeyName);


      //ServiceImpl
      serviceImplInfo = new ServiceImplInfo();
      serviceImplInfo.setPackageStr(Configuration.getString("serviceImpl.package"));
      serviceImplInfo.setClassName(
          PropertyUtil.getNamePrefix(Constants.SERVICE_IMPL) + entityInfo.getEntityName()
              + PropertyUtil.getNameSuffix(Constants.SERVICE_IMPL));
      serviceImplInfo.setReference(serviceImplInfo.getPackageStr()+"."+serviceImplInfo.getClassName());
      serviceImplInfo.setPrimaryKeyName(primaryKeyName);

      //Action
      actionInfo = new ActionInfo();
      actionInfo.setPackageStr(Configuration.getString("action.package"));
      actionInfo.setClassName(
          PropertyUtil.getNamePrefix(Constants.ACTION) + entityInfo.getEntityName()
              + PropertyUtil.getNameSuffix(Constants.ACTION));
      actionInfo.setReference(actionInfo.getPackageStr()+"."+actionInfo.getClassName());
      actionInfo.setPrimaryKeyName(primaryKeyName);

      /**组件各层之间的引用关系**/
      //entity层
      entityImports.put(Constants.VO,voInfo);
      //添加java的包

      //dao层
      daoImports.put(Constants.VO,voInfo);
      daoImports.put(Constants.ENTITY,entityInfo);
      //service层
      serviceImports.put(Constants.ENTITY,entityInfo);
      serviceImports.put(Constants.VO,voInfo);
      //serviceImpl层
      serviceImplImports.put(Constants.SERVICE,serviceInfo);
      serviceImplImports.put(Constants.ENTITY,entityInfo);
      serviceImplImports.put(Constants.VO,voInfo);
      serviceImplImports.put(Constants.DAO,daoInfo);
      //action层
      actionImports.put(Constants.SERVICE,serviceInfo);
      actionImports.put(Constants.ENTITY,entityInfo);
      actionImports.put(Constants.VO,voInfo);
      //vo层
      voImports.put(Constants.ENTITY,entityInfo);


      //添加引入包的信息到模型中去
      entityInfo.setImports(entityImports);
      voInfo.setImports(voImports);
      daoInfo.setImports(daoImports);
      serviceInfo.setImports(serviceImports);
      serviceImplInfo.setImports(serviceImplImports);
      actionInfo.setImports(actionImports);
      //添加组件到集合中去
      voList.add(voInfo);
      daoList.add(daoInfo);
      serviceList.add(serviceInfo);
      serviceImplList.add(serviceImplInfo);
      actionList.add(actionInfo);
    }






    /*****************************************************************/
    context.setAttribute("entityInfos", entityInfos);
    context.setAttribute("daoList", daoList);
    context.setAttribute("voList", voList);
    context.setAttribute("serviceList", serviceList);
    context.setAttribute("serviceImplList", serviceImplList);
    context.setAttribute("actionList", actionList);
    return false;
  }

}
