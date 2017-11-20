package com.geoway.generate.handler.impl;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.model.ColumnInfo;
import com.geoway.generate.model.EntityInfo;
import com.geoway.generate.model.VoInfo;
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
 * @date: 2017/11/13 10:34
 * @description:
 */
public class VoHandler extends BaseHandler<VoInfo> {


  public VoHandler(String ftlName, VoInfo info) {
    this.ftlName = ftlName;
    this.info = info;
    String baseStr = Configuration.getString("base.baseDir");
    if (null == baseStr || "".equals(baseStr)) {
      this.savePath = PropertyUtil.getWorkSpaceSrcPath() + File.separator
          + (Configuration.getString("vo.package").replace(".", File.separator))
          + File.separator + info.getClassName() + ".java";
    } else {
      this.savePath = Configuration.getString("base.baseDir")
          + File.separator + Configuration.getString("vo.path")
          + File.separator + info.getClassName() + ".java";
    }
  }

  @Override
  public void combileParams(VoInfo info) {
    EntityInfo entityInfo = (EntityInfo) info.getImports().get(Constants.ENTITY);
    //组装Vo的相关信息
    String voClassName = PropertyUtil.getNamePrefix(Constants.VO) +
        entityInfo.getClassName() + PropertyUtil.getNameSuffix(Constants.VO);
    String voClassVar = StringUtil.camel(voClassName);



    this.param.put("packageStr", Configuration.getString("vo.package"));
    StringBuilder sb = new StringBuilder();
    for (String str : entityInfo.getImportJavaPackage()) {
      sb.append("import ").append(str).append(";\r\n");
    }
    this.param.put("importStr", sb.toString());
    this.param.put("entityDesc", entityInfo.getEntityDesc());
    this.param.put("className", voClassName);
    this.param.put("tableName", entityInfo.getTableName());
    this.param.put("tableRemark", entityInfo.getTableRemark());
    //生成属性，getter,setter方法
    sb = new StringBuilder();
    StringBuilder sbMethods = new StringBuilder();
    Map<String, String> propRemarks = entityInfo.getPropRemarks();
    Map<String, ColumnInfo> columnInfoMap = entityInfo.getColumnInfoMap();
    List<String> primaryKeys = new ArrayList<>(1);
    ColumnInfo columnInfo;
    for (Entry<String, String> entry : entityInfo.getPropTypes().entrySet()) {
      String propName = entry.getKey();
      String propType = entry.getValue();
      columnInfo = columnInfoMap.get(propName);
      //不暴露主键任何信息
      if (columnInfo.isPrimaryKey()) {
        continue;
      }
      //注释信息
      sb.append("\t/**\r\n")
          .append("\t *")
          .append(propRemarks.get(propName) == null ? "TODO:描述信息" : propRemarks.get(propName))
          .append("\r\n")
          .append("\t */\r\n");

      //处理注解信息之字符串
      if (propType.contains("String")) {
        sb.append("\t@Length(max = ").append(columnInfo.getLen())
            .append(",groups = {ValidType.Add.class,ValidType.Update.class},message = \"最大长度不能超过")
            .append(columnInfo.getLen()).append("\")\n");
        if (!columnInfo.isNullable()) {
          if (columnInfo.isPrimaryKey()) {
            sb.append("\t@NotBlank(groups = {ValidType.Add.class,ValidType.Update.class})\n");
          } else {
            sb.append("\t@NotBlank(groups = {ValidType.Add.class})\n");
          }
        }
      } else {
        if (!columnInfo.isNullable()) {
          sb.append("\t@NotNull(groups = {ValidType.Add.class})\n");
        }
      }
      //处理数据类型的精度问题,BigDecimal
      if (propType.contains("BigDecimal")) {
        int precision = columnInfo.getPrecision();
        int maxInt = columnInfo.getLen() - precision;
        sb.append("\t@Digits(integer = ").append(maxInt).append(",fraction = ").append(precision)
            .append(",groups = {ValidType.Add.class,ValidType.Update.class},message = \"整数部分不能超过")
            .append(maxInt).append("位,小数部分不能超过").append(precision).append("位\" )\n");
      }
      sb.append("\tprivate ").append(propType).append(" ").append(propName).append(";\r\n\n");

      //处理日期注解
      if (propType.contains("Date")) {
        sbMethods.append("\t@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
        sbMethods
            .append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\n");
      }
      sbMethods.append("\tpublic ").append(propType).append(" get")
          .append(propName.substring(0, 1).toUpperCase())
          .append(propName.substring(1)).append("() {\r\n")
          .append("\t\treturn ").append(propName).append(";\r\n")
          .append("\t}\r\n\r\n");
      //处理日期注解
      if (propType.contains("Date")) {
        sbMethods.append("\t@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
        sbMethods
            .append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\n");
      }
      sbMethods.append("\tpublic void set").append(propName.substring(0, 1).toUpperCase())
          .append(propName.substring(1)).append("(").append(propType).append(" ")
          .append(propName).append(") {\r\n")
          .append("\t\tthis.").append(propName).append(" = ").append(propName)
          .append(";\r\n\t}\r\n").append("\r\n\r\n");
    }
    entityInfo.setPrimaryKeys(primaryKeys);
    this.param.put("propertiesStr", sb.toString());
    this.param.put("methodStr", sbMethods.toString());

  }

}

