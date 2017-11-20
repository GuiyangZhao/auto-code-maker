package com.geoway.generate.task;

import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.handler.impl.VoHandler;
import com.geoway.generate.model.VoInfo;
import java.util.List;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:50
 * @description:
 */
public class VoTask extends AbstractApplicationTask {

  private static String VO_FTL = "template/Vo.ftl";

  @SuppressWarnings("unchecked")
  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("开始生成vo");
    List<VoInfo> voList = (List<VoInfo>) context.getAttribute("voList");

    BaseHandler<VoInfo> handler = null;
    for (VoInfo voInfo : voList) {
      handler = new VoHandler(VO_FTL, voInfo);
      handler.execute();
    }
    logger.info("结束生成vo");
    return false;
  }

}

