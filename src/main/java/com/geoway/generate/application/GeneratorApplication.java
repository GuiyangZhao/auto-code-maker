package com.geoway.generate.application;

import com.geoway.generate.framework.Application;
import com.geoway.generate.task.ActionTask;
import com.geoway.generate.task.CombineInfoTask;
import com.geoway.generate.task.DaoTask;
import com.geoway.generate.task.EntityTask;
import com.geoway.generate.task.InitTask;
import com.geoway.generate.task.MapperTask;
import com.geoway.generate.task.ServiceImplTask;
import com.geoway.generate.task.ServiceTask;
import com.geoway.generate.task.VoTask;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:52
 * @description:
 */
public class GeneratorApplication {
  public static void main(String[] args) {
    //程序入口
    Application application = new Application(GeneratorApplication.class.getSimpleName());
    application.parseArgs(args);
    application.setApplicationName(GeneratorApplication.class.getName());
    application.addApplicationTask(InitTask.class)
        .addApplicationTask(CombineInfoTask.class)
        .addApplicationTask(EntityTask.class)
        .addApplicationTask(DaoTask.class)
        .addApplicationTask(MapperTask.class)
        .addApplicationTask(VoTask.class)
        .addApplicationTask(ServiceTask.class)
        .addApplicationTask(ServiceImplTask.class)
        .addApplicationTask(ActionTask.class)
        .work();
  }
}
