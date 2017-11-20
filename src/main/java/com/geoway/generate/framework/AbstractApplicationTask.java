package com.geoway.generate.framework;

import com.geoway.generate.framework.context.ApplicationContext;
import com.geoway.generate.log.LogFactory;
import com.geoway.generate.util.PropertyUtil;
import org.apache.logging.log4j.Logger;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:21
 * @description:
 */
public abstract class AbstractApplicationTask implements ApplicationTask {
  /**
   * 日志类
   */
  protected Logger logger;

  /**
   * VAR_SKIP_NEXT: 全局变量：是否跳过以下一个节点.
   *
   */
  protected final static String VAR_SKIP_NEXT = "isSkipNext";

  /**
   * nextTask: 下一个应用程序任务.
   *
   */
  private ApplicationTask nextTask = null;

  /**
   * hasNext: 是否有下一个应用程序任务.
   *
   */
  private boolean hasNext = false;

  /**
   * isSkipNext: 是否跳过下一个任务.
   *
   */
  private boolean isSkipNext = false;

  /**
   * Creates a new instance of AbstractApplicationTask.
   *
   */
  public AbstractApplicationTask() {
    super();
  }

  /**
   * 初始化日志
   * @param applicationTaskId
   * @param applicationId
   */
  @Override
  public void initLogger(String applicationTaskId, String applicationId) {
    this.logger = LogFactory.getApplicationTaskLogger(applicationTaskId, applicationId);
  }

  @Override
  public boolean perform(ApplicationContext context) throws Exception {
    // 如果在执行应用程序任务逻辑之前的操作成功，那么进入执行应用程序任务
    if (doBefore(context)) {
      boolean isReturnAll = doInternal(context);
      doAfter(context);
      return isReturnAll;
    } else {
      return false;
    }
  }

  @Override
  public boolean hasNext() {
    return this.hasNext;
  }

  @Override
  public void registerNextTask(ApplicationTask nextTask) {
    this.nextTask = nextTask;
    this.hasNext = !(null == nextTask);
  }

  @Override
  public ApplicationTask next() {
    return this.nextTask;
  }

  protected boolean doBefore(ApplicationContext context) throws Exception {
    PropertyUtil.setLogger(this.logger);
    return true;
  }

  protected abstract boolean doInternal(ApplicationContext context) throws Exception;

  protected void doAfter(ApplicationContext context) throws Exception {
    // 设置是否跳过下一个变量
    if (isSkipNext) {
      String skipMessage = "跳过此应用程序任务的下一个任务！";
      logger.info(skipMessage);
    }
  }

  @Override
  public void skipNext() {
    this.isSkipNext = true;
  }
}
