package com.geoway.generate.task;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import com.geoway.generate.framework.AbstractApplicationTask;
import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.handler.BaseHandler;
import com.geoway.generate.handler.impl.DaoHandler;
import com.geoway.generate.model.DaoInfo;
import com.geoway.generate.model.EntityInfo;
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
public class DaoTask extends AbstractApplicationTask {

  private static String DAO_FTL = "template/Dao.ftl";

  private List<DaoInfo> daoInfos;

  @SuppressWarnings("unchecked")
  @Override
  protected boolean doInternal(ApplicationContext context) throws Exception {
    logger.info("开始生成dao");

    daoInfos = (List<DaoInfo>) context.getAttribute("daoList");

    BaseHandler<DaoInfo> handler = null;
    for (DaoInfo daoInfo : daoInfos) {
      handler = new DaoHandler(DAO_FTL, daoInfo);
      handler.execute();
    }

    logger.info("生成dao完成");
    return false;
  }

  @Override
  protected void doAfter(ApplicationContext context) throws Exception {
    super.doAfter(context);

    List<MapperInfo> mapperInfos = new ArrayList<MapperInfo>();
    MapperInfo mapperInfo = null;
    for (DaoInfo daoInfo : daoInfos) {
      mapperInfo = new MapperInfo();
      mapperInfo.setDaoInfo(daoInfo);
      mapperInfo.setEntityInfo((EntityInfo) daoInfo.getImports().get(Constants.ENTITY));
      mapperInfo.setFileName(
          PropertyUtil.getNamePrefix(Constants.MAPPER_XML) + ((EntityInfo) daoInfo.getImports().get(Constants.ENTITY)).getEntityName()
              + PropertyUtil.getNameSuffix(Constants.MAPPER_XML));
      mapperInfo.setNamespace(daoInfo.getPackageStr() + "." + daoInfo.getClassName());
      mapperInfos.add(mapperInfo);
    }
    context.setAttribute("mapperInfos", mapperInfos);

  }
}

