package com.geoway.generate.framework.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:19
 * @description:
 */
public abstract class ApplicationContext {
  protected Map<String, Object> ctx = new HashMap<String, Object>();

  public abstract void setAttribute(String key, Object obj);

  public abstract Object getAttribute(String key);


  public Map<String, Object> getCtx() {
    return ctx;
  }

  public void setCtx(Map<String, Object> ctx) {
    this.ctx = ctx;
  }
}
