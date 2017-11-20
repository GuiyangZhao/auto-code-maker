package com.geoway.generate.handler.impl;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.model.BaseModel;
import com.geoway.generate.model.DaoInfo;
import com.geoway.generate.model.EntityInfo;
import com.geoway.generate.model.ServiceInfo;
import com.geoway.generate.model.VoInfo;
import com.geoway.generate.util.PropertyUtil;
import com.geoway.generate.util.StringUtil;
import java.io.File;
import java.util.Map;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:33
 * @description:
 */
public class ServiceHandler extends BaseHandler<ServiceInfo> {

  public ServiceHandler(String ftlName, ServiceInfo info) {
    this.ftlName = ftlName;
    this.info = info;
    String baseStr = Configuration.getString("base.baseDir");
    if (null == baseStr || "".equals(baseStr)) {
      this.savePath = PropertyUtil.getWorkSpaceSrcPath() + File.separator
          + (Configuration.getString("service.package").replace(".", File.separator))
          + File.separator + info.getClassName() + ".java";
    } else {
      this.savePath = Configuration.getString("base.baseDir")
          + File.separator + Configuration.getString("service.path")
          + File.separator + info.getClassName() + ".java";
    }
  }

  @Override
  public void combileParams(ServiceInfo info) {
    Map<String, BaseModel> importModels = info.getImports();
    EntityInfo entityInfo = (EntityInfo) importModels.get(Constants.ENTITY);
    VoInfo voInfo = (VoInfo) importModels.get(Constants.VO);
    this.param.put("entityClassName", entityInfo.getClassName());
    this.param.put("voName", voInfo.getClassName());
    this.param.put("voNameVar", StringUtil.camel(voInfo.getClassName()));
    this.param.put("entityName", entityInfo.getEntityName());
    this.param.put("entityNameVar", StringUtil.camel(entityInfo.getEntityName()));
  }

}

