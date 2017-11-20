package com.geoway.generate.handler;

import com.geoway.generate.model.BaseModel;
import com.geoway.generate.model.ServiceImplInfo;
import com.geoway.generate.model.ServiceInfo;
import com.geoway.generate.util.DateUtil;
import com.geoway.generate.util.FileHelper;
import com.geoway.generate.util.FreeMarkerUtil;
import com.geoway.generate.util.StringUtil;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:31
 * @description:
 */
public abstract class BaseHandler<T> {

  protected String ftlName;
  protected String savePath;
  protected Map<String, String> param = new HashMap<String, String>();
  protected T info;

  public String generateFinalStr() {
    String temp = FileHelper
        .readFileToString(this.getClass().getClassLoader().getResource("").getPath() + ftlName);
    String modelStr = FreeMarkerUtil.getProcessValue(param, temp);
    return modelStr;
  }

  /**
   * 保存到文件
   */
  public void saveToFile(String str) {
    FileHelper.writeToFile(savePath, str);
  }

  /**
   * 组装参数
   */
  public abstract void combileParams(T info);

  /**
   * 设置一些公共的参数.
   */
  public void beforeGenerate(T info) {
    String time = DateUtil.formatDataToStr(new Date(), "yyyy年MM月dd日");
    param.put("author", System.getProperty("user.name"));
    param.put("time", time);
    if (info instanceof BaseModel) {
      BaseModel baseModel = (BaseModel) info;
      String primaryKeyName = baseModel.getPrimaryKeyName();
      Map<String, BaseModel> importModels = baseModel.getImports();
      Set<String> importJavaPackages = baseModel.getImportJavaPackage();
      StringBuilder importStr = new StringBuilder();
      param.put("packageStr",baseModel.getPackageStr());
      param.put("className", baseModel.getClassName());
      param.put("classNameVar", StringUtil.camel(baseModel.getClassName()));
      param.put("reference", baseModel.getReference());
      param.put("packageClassName",baseModel.getReference());
      this.param.put("primaryKey", primaryKeyName);
      this.param.put("primaryKeyVar", StringUtil.camel(primaryKeyName));

      //组拼引入的包文件等
      importStr.append("\n\r");
      //java包之间的关系
      if(null!=importJavaPackages&&importJavaPackages.size()>0){
        for (String javaPackage : importJavaPackages) {
          importStr.append("import ").append(javaPackage).append(";\t\n");
        }
      }
      importStr.append("\n\r");
      //分层之间相互的依赖关系
      if (null != importModels && importModels.size() > 0) {
        Collection<BaseModel> models = importModels.values();
        Iterator<BaseModel> iterator = models.iterator();
        while (iterator.hasNext()){
          BaseModel model = iterator.next();
          String reference = model.getReference();
          if (null!=reference){
            importStr.append("import ").append(model.getReference()).append(";\t\n");
          }
        }
      }
      param.put("importStr", importStr.toString());

      String methodPrimaryKey = "!!ERROR!!";
      if (primaryKeyName.contains("code")||primaryKeyName.contains("Code")){
        methodPrimaryKey = "Code";
      }else if (primaryKeyName.contains("Guid")||primaryKeyName.contains("guid")){
        methodPrimaryKey = "Guid";
      }
      this.param.put("methodPrimaryKey",methodPrimaryKey);

      //清理
      importStr.setLength(0);
      importStr = null;
    }

  }

  /**
   * 生成文件
   */
  public void execute() {
    String str = null;
    beforeGenerate(info);
    combileParams(info);
    str = generateFinalStr();
    saveToFile(str);
  }
}
