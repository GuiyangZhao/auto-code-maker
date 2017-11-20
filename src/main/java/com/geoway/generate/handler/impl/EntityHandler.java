package com.geoway.generate.handler.impl;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.model.ColumnInfo;
import com.geoway.generate.model.EntityInfo;
import com.geoway.generate.util.PropertyUtil;
import com.geoway.generate.util.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:33
 * @description:
 */
public class EntityHandler extends BaseHandler<EntityInfo> {

  public EntityHandler(String ftlName, EntityInfo info) {
    this.ftlName = ftlName;
    this.info = info;
    String baseStr = Configuration.getString("base.baseDir");
    if (null == baseStr || "".equals(baseStr)) {
      this.savePath = PropertyUtil.getWorkSpaceSrcPath() + File.separator
          + (Configuration.getString("entity.package").replace(".", File.separator))
          + File.separator + info.getClassName() + ".java";
    } else {
      this.savePath = Configuration.getString("base.baseDir")
          + File.separator + Configuration.getString("entity.path")
          + File.separator + info.getClassName() + ".java";
    }

  }

  @Override
  public void combileParams(EntityInfo entityInfo) {
    //组装Vo的相关信息
    String voClassName = PropertyUtil.getNamePrefix(Constants.VO) +
        entityInfo.getClassName() + PropertyUtil.getNameSuffix(Constants.VO);
    String voClassVar = StringUtil.camel(voClassName);

    this.param.put("entityDesc", entityInfo.getEntityDesc());
    this.param.put("className", entityInfo.getClassName());
    this.param.put("tableName", entityInfo.getTableName());
    this.param.put("tableRemark", entityInfo.getTableRemark());
    //生成属性，getter,setter方法
    StringBuilder sb = new StringBuilder();
    StringBuilder sbMethods = new StringBuilder();
    StringBuilder sbConstruct = new StringBuilder();

    Map<String, String> propRemarks = entityInfo.getPropRemarks();
    Map<String, ColumnInfo> columnInfoMap = entityInfo.getColumnInfoMap();
    List<String> primaryKeys = new ArrayList<>(1);
    ColumnInfo columnInfo;

    //处理构造方法前缀
    //生成无参构造方法
    sbConstruct.append("\tpublic ").append(entityInfo.getClassName())
        .append("(){\n\t}\n");
    //生成带参构造方法
    sbConstruct.append("\tpublic ").append(entityInfo.getClassName())
        .append("(")
        .append(voClassName).append(" ").append(voClassVar)
        .append("){\r\n");

    for (Entry<String, String> entry : entityInfo.getPropTypes().entrySet()) {
      String propName = entry.getKey();
      String propType = entry.getValue();
      columnInfo = columnInfoMap.get(propName);
      //TODO 根据ColumbInfo进行精简代码
      //注释、类型、名称,相关的注解信息
      sb.append("\t/**\r\n")
          .append("\t *")
          .append(propRemarks.get(propName) == null ? "TODO:描述信息" : propRemarks.get(propName))
          .append("\r\n")
          .append("\t */\r\n");
      //处理注解信息
      sb.append("\t@TableField(value=\"").append(StringUtil.convertPropName2FieldName(propName))
          .append("\") \n");
      if (columnInfo.isPrimaryKey()) {
        sb.append("\t@TableId\n");
        sb.append("\t@NotBlank\n");
        primaryKeys.add(propName);
      } else {
        //生成构造函数
        sbConstruct.append("\t\tthis.").append(propName).append("=")
            .append(voClassVar).append(".get").append(StringUtil.capitalize(propName))
            .append("();\r\n");
      }
      sb.append("\tprivate ").append(propType).append(" ").append(propName).append(";\r\n\n");

      //生成set & get方法
      sbMethods.append("\tpublic ").append(propType).append(" get")
          .append(propName.substring(0, 1).toUpperCase())
          .append(propName.substring(1)).append("() {\r\n")
          .append("\t\treturn ").append(propName).append(";\r\n")
          .append("\t}\r\n");

      sbMethods.append("\tpublic void set").append(propName.substring(0, 1).toUpperCase())
          .append(propName.substring(1)).append("(").append(propType).append(" ")
          .append(propName).append(") {\r\n")
          .append("\t\tthis.").append(propName).append(" = ").append(propName)
          .append(";\r\n\t}\r\n").append("\r\n");
    }
    sbConstruct.append("\t}");
    entityInfo.setPrimaryKeys(primaryKeys);
    this.param.put("propertiesStr", sb.toString());
    this.param.put("constructStr", sbConstruct.toString());
    this.param.put("methodStr", sbMethods.toString());


  }
}
