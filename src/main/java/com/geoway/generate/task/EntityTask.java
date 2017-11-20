package com.geoway.generate.task;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.handler.impl.EntityHandler;
import com.geoway.generate.model.ActionInfo;
import com.geoway.generate.model.DaoInfo;
import com.geoway.generate.model.EntityInfo;
import com.geoway.generate.model.ServiceImplInfo;
import com.geoway.generate.model.ServiceInfo;
import com.geoway.generate.model.VoInfo;
import com.geoway.generate.util.PropertyUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:45
 * @description:
 */
public class EntityTask extends AbstractApplicationTask {

  private static String ENTITY_FTL = "template/Entity.ftl";

  private List<EntityInfo> entityInfos;

  @SuppressWarnings("unchecked")
  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("开始生成实体");

    //获取实体信息
    entityInfos = (List<EntityInfo>) context.getAttribute("entityInfos");

    BaseHandler<EntityInfo> handler = null;
    for (EntityInfo entityInfo : entityInfos) {
      handler = new EntityHandler(ENTITY_FTL, entityInfo);
      handler.execute();
    }
    logger.info("生成实体类完成");
    return false;
  }

  @Override
  protected void doAfter(ApplicationContext context) throws Exception {
    super.doAfter(context);



  }

}

