package com.geoway.generate.framework.context;

import com.geoway.generate.framework.context.ApplicationContext;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:18
 * @description:
 */
public class SimpleApplicationContext extends ApplicationContext {
  @Override
  public void setAttribute(String key, Object obj) {
    this.ctx.put(key, obj);
  }

  @Override
  public Object getAttribute(String key) {
    return this.ctx.get(key);
  }

}
