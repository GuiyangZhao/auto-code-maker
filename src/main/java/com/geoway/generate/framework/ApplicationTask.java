package com.geoway.generate.framework;

import com.geoway.generate.framework.context.ApplicationContext;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:22
 * @description:
 */
public interface ApplicationTask extends Skipable {

  boolean perform(ApplicationContext context) throws Exception;

  boolean hasNext();

  void registerNextTask(ApplicationTask nextTask);

  ApplicationTask next();

  void initLogger(String applicationTaskId, String applicationId);
}