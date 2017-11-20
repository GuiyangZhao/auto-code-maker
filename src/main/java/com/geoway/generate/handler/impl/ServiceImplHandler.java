package com.geoway.generate.handler.impl;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.model.BaseModel;
import com.geoway.generate.model.DaoInfo;
import com.geoway.generate.model.EntityInfo;
import com.geoway.generate.model.ServiceImplInfo;
import com.geoway.generate.model.ServiceInfo;
import com.geoway.generate.model.VoInfo;
import com.geoway.generate.util.PropertyUtil;
import com.geoway.generate.util.StringUtil;
import java.io.File;
import java.util.Map;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 21:05
 * @description:
 */
public class ServiceImplHandler extends BaseHandler<ServiceImplInfo> {

  public ServiceImplHandler(String ftlName, ServiceImplInfo info) {
    this.ftlName = ftlName;
    this.info = info;
    String baseStr = Configuration.getString("base.baseDir");
    if (null == baseStr || "".equals(baseStr)) {
      this.savePath = PropertyUtil.getWorkSpaceSrcPath() + File.separator
          + (Configuration.getString("serviceImpl.package").replace(".", File.separator))
          + File.separator + info.getClassName() + ".java";
    } else {
      this.savePath = Configuration.getString("base.baseDir")
          + File.separator + Configuration.getString("serviceImpl.path")
          + File.separator + info.getClassName() + ".java";
    }
  }

  @Override
  public void combileParams(ServiceImplInfo info) {
    Map<String, BaseModel> importModels = info.getImports();
    ServiceInfo serviceInfo= (ServiceInfo) importModels.get(Constants.SERVICE);
    DaoInfo daoInfo = (DaoInfo) importModels.get(Constants.DAO);
    EntityInfo entityInfo = (EntityInfo) importModels.get(Constants.ENTITY);
    VoInfo voInfo = (VoInfo) importModels.get(Constants.VO);

    this.param.put("serviceName", serviceInfo.getClassName());
    this.param.put("daoName", daoInfo.getClassName());
    this.param.put("entityName", entityInfo.getClassName());
    this.param.put("voName",voInfo.getClassName());

    //变量值
    this.param.put("serviceNameVar", StringUtil.camel(serviceInfo.getClassName()));
    this.param.put("daoNameVar", StringUtil.camel(
        daoInfo.getClassName().startsWith("I", 0) ? daoInfo.getClassName().substring(1) : daoInfo.getClassName()));
    this.param.put("entityNameVar", StringUtil.camel(entityInfo.getClassName()));
    this.param.put("voNameVar",StringUtil.camel(voInfo.getClassName()));

  }
}
