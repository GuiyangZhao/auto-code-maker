package com.geoway.generate.task;

import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.handler.impl.ActionHandler;
import com.geoway.generate.model.ActionInfo;
import java.util.List;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 20:23
 * @description:
 */
public class ActionTask extends AbstractApplicationTask{
  private static String DAO_FTL = "template/Action.ftl";
  private List<ActionInfo> actionInfos;

  @SuppressWarnings("unchecked")
  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("Action");
    //通过context存储的serviceImplInfo
    actionInfos = (List<ActionInfo>) context.getAttribute("actionList");
    BaseHandler<ActionInfo> actionHandler;
    //遍历建立处理方式
    for (ActionInfo implInfo :actionInfos){
      actionHandler = new ActionHandler(DAO_FTL,implInfo);
      actionHandler.execute();
    }
    logger.info("生成Action完成");
    return false;
  }
}
