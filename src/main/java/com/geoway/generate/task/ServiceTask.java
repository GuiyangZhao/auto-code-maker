package com.geoway.generate.task;

import com.geoway.generate.common.Constants;
import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.handler.impl.DaoHandler;
import com.geoway.generate.handler.impl.ServiceHandler;
import com.geoway.generate.model.ServiceInfo;
import com.geoway.generate.model.MapperInfo;
import com.geoway.generate.model.ServiceInfo;
import com.geoway.generate.util.PropertyUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:44
 * @description:
 */
public class ServiceTask extends AbstractApplicationTask {

  private static String SERVICE_FTL = "template/Service.ftl";
  private List<ServiceInfo> serviceInfos;

  @Override
  protected void doAfter(ApplicationContext context) throws Exception {
    super.doAfter(context);

  }

  @SuppressWarnings("unchecked")
  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("开始生成Service");

    serviceInfos = (List<ServiceInfo>) context.getAttribute("serviceList");

    BaseHandler<ServiceInfo> handler = null;
    for (ServiceInfo serviceInfo : serviceInfos) {
      handler = new ServiceHandler(SERVICE_FTL, serviceInfo);
      handler.execute();
    }

    logger.info("生成Service完成");
    return false;
  }
}

