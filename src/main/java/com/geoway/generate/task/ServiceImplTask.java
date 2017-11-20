package com.geoway.generate.task;

import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.handler.impl.ServiceImplHandler;
import com.geoway.generate.model.ServiceImplInfo;
import java.util.List;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 20:23
 * @description:
 */
public class ServiceImplTask extends AbstractApplicationTask{
  private static String DAO_FTL = "template/ServiceImpl.ftl";
  private List<ServiceImplInfo> implInfos;

  @SuppressWarnings("unchecked")
  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("开始生成ServiceImplInfo");
    //通过context存储的serviceImplInfo
    implInfos = (List<ServiceImplInfo>) context.getAttribute("serviceImplList");
    BaseHandler<ServiceImplInfo> implTaskBaseHandler;
    //遍历建立处理方式
    for (ServiceImplInfo implInfo :implInfos){
      implTaskBaseHandler = new ServiceImplHandler(DAO_FTL,implInfo);
      implTaskBaseHandler.execute();
    }
    logger.info("生成ServiceImplInfo完成");
    return false;
  }
}
