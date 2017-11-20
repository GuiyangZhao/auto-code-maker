package com.geoway.generate.handler.impl;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.model.ActionInfo;
import com.geoway.generate.model.BaseModel;
import com.geoway.generate.model.EntityInfo;
import com.geoway.generate.model.ServiceInfo;
import com.geoway.generate.model.VoInfo;
import com.geoway.generate.util.PropertyUtil;
import com.geoway.generate.util.StringUtil;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 21:05
 * @description:
 */
public class ActionHandler extends BaseHandler<ActionInfo> {

  public ActionHandler(String ftlName, ActionInfo info) {
    this.ftlName = ftlName;
    this.info = info;
    String baseStr = Configuration.getString("base.baseDir");
    if (null == baseStr || "".equals(baseStr)) {
      this.savePath = PropertyUtil.getWorkSpaceSrcPath() + File.separator
          + (Configuration.getString("action.package").replace(".", File.separator))
          + File.separator + info.getClassName() + ".java";
    } else {
      this.savePath = Configuration.getString("base.baseDir")
          + File.separator + Configuration.getString("action.path")
          + File.separator + info.getClassName() + ".java";
    }
  }

  @Override
  public void combileParams(ActionInfo info) {
    Map<String, BaseModel> importModels = info.getImports();
    ServiceInfo serviceInfo = (ServiceInfo) importModels.get(Constants.SERVICE);
    EntityInfo entityInfo = (EntityInfo) importModels.get(Constants.ENTITY);
    VoInfo voInfo = (VoInfo) importModels.get(Constants.VO);

    String serviceName = serviceInfo.getClassName();
    String entityName = entityInfo.getClassName();

    this.param.put("serviceName", serviceName);
    this.param.put("serviceNameVar",
        StringUtil.camel(serviceName.startsWith("I", 0) ? serviceName.substring(1) : serviceName));
    this.param.put("entityName", entityName);
    this.param.put("entityNameVar", StringUtil.camel(entityName));
    this.param.put("voName", voInfo.getClassName());
    this.param.put("voNameVar", StringUtil.camel(voInfo.getClassName()));


  }
}
