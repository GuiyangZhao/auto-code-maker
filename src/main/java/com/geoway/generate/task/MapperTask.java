package com.geoway.generate.task;

import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.handler.impl.MapperHandler;
import com.geoway.generate.model.MapperInfo;
import java.util.List;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:48
 * @description:
 */
public class MapperTask extends AbstractApplicationTask {

  private static String MAPPER_FTL = "template/Mapper.ftl";

  @SuppressWarnings("unchecked")
  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("开始生成Mapper");

    List<MapperInfo> list = (List<MapperInfo>) context.getAttribute("mapperInfos");

    BaseHandler<MapperInfo> handler = null;
    for (MapperInfo mapperInfo : list) {
      handler = new MapperHandler(MAPPER_FTL, mapperInfo);
      handler.execute();
    }

    logger.info("生成Mapper完成");
    return false;
  }

  @Override
  protected void doAfter(ApplicationContext context) throws Exception {
    super.doAfter(context);
  }
}

