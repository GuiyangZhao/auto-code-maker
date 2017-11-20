package com.geoway.generate.util;

import com.geoway.generate.common.Constants;
import com.geoway.generate.config.Configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.Logger;


/**
 * ClassName:PropertyUtil <br/> Function: 属性文件工具类. <br/> Date:     2015年11月13日 下午6:01:19 <br/>
 *
 * @author zhaoguiyang
 * @see
 * @since JDK 1.6
 */
public class PropertyUtil {

  /**
   * 日志类
   */
  private static Logger logger;

  /**
   * 属性类
   */
  public static Properties properties = new Properties();

  /**
   * loadProp:加载属性文件. <br/>
   *
   * @author zhaoguiyang
   */
  public static void loadProp(String fileName) {
    InputStream is = null;
    Reader reader = null;

    //首先在classpath中找，如果找不到，则在工作目录下找
    is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
    logger.info("在classpath下找{},是否找到：{}", fileName, is == null ? "否" : "是");
    if (is == null) {
      //没找到，则在使用绝对路径，找到jar所在的根目录
      //String rootPath = System.getProperty("user.dir");
      String rootPath = System.getProperty("logDirPath");
      logger.info("rootPath:{}", rootPath);

      //截取一下文件名：BirthdayReminder/生日名单.xls ->
      if (StringUtil.subBySplit(fileName, "/") != null) {
        fileName = StringUtil.subBySplit(fileName, "/");
      } else if (StringUtil.subBySplit(fileName, "\\") != null) {
        fileName = StringUtil.subBySplit(fileName, "\\");
      }

      try {
        is = new FileInputStream(rootPath + File.separator + fileName);
        reader = new InputStreamReader(is, "UTF-8");
        logger.info("在classpath下没找到，在用户工作目录下找{},是否找到：{}", fileName, is == null ? "否" : "是");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        logger.info("获取输入流异常:{}", e);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        logger.info("不支持的编码异常:{}", e);
      }
    } else {
      try {
        reader = new InputStreamReader(is, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        logger.info("不支持的编码异常:{}", e);
      }
    }

    try {
      properties.load(reader);

      logger.info("加载属性文件成功：{}", fileName);
    } catch (IOException e) {
      e.printStackTrace();
      logger.info("加载属性文件异常：{}", fileName);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
          logger.info("输入流关闭异常:{}", e);
        }
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
          logger.info("字符流关闭异常:{}", e);
        }
      }
    }
  }

  /**
   * loadProp:加载属性文件. <br/>
   *
   * @author zhaoguiyang
   */
  public static void loadPropWithoutLog(String fileName) {
    InputStream is = null;
    Reader reader = null;

    //首先在classpath中找，如果找不到，则在工作目录下找
    is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
    SysoutUtil.printInfo("在classpath下找{0},是否找到：{1}", fileName, is == null ? "否" : "是");
    if (is == null) {
      //没找到，则在使用绝对路径，找到jar所在的根目录
      //String rootPath = System.getProperty("user.dir");
      String rootPath = System.getProperty("logDirPath");
      SysoutUtil.printInfo("rootPath:{0}", rootPath);

      //截取一下文件名：BirthdayReminder/生日名单.xls ->
      if (StringUtil.subBySplit(fileName, "/") != null) {
        fileName = StringUtil.subBySplit(fileName, "/");
      } else if (StringUtil.subBySplit(fileName, "\\") != null) {
        fileName = StringUtil.subBySplit(fileName, "\\");
      }

      try {
        is = new FileInputStream(rootPath + File.separator + fileName);
        reader = new InputStreamReader(is, "UTF-8");
        SysoutUtil
            .printInfo("在classpath下没找到，在用户工作目录下找{0},是否找到：{1}", fileName, is == null ? "否" : "是");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        SysoutUtil.printInfo("获取输入流异常:{0}", e);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        SysoutUtil.printInfo("不支持的编码异常:{0}", e);
      }
    } else {
      try {
        reader = new InputStreamReader(is, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        SysoutUtil.printInfo("不支持的编码异常:{0}", e);
      }
    }

    try {
      properties.load(reader);

      SysoutUtil.printInfo("加载属性文件成功：{0}", fileName);
    } catch (IOException e) {
      e.printStackTrace();
      SysoutUtil.printInfo("加载属性文件异常：{0}", fileName);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
          SysoutUtil.printInfo("输入流关闭异常:{0}", e);
        }
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
          SysoutUtil.printInfo("字符流关闭异常:{0}", e);
        }
      }
    }
  }

  /**
   * getInputStremByFileName:根据文件名获取输入流. <br/>
   *
   * @author zhaoguiyang
   */
  public static InputStream getInputStremByFileName(String fileName) {
    InputStream is = null;
    //首先在classpath中找，如果找不到，则在工作目录下找
    is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
    logger.info("在classpath下找{},是否找到：{}", fileName, is == null ? "否" : "是");

    if (is == null) {
      //没找到，则在使用绝对路径，找到jar所在的根目录
      //String rootPath = System.getProperty("user.dir");
      String rootPath = System.getProperty("logDirPath");
      logger.info("rootPath:{}", rootPath);

      //截取一下文件名：BirthdayReminder/生日名单.xls ->
      if (StringUtil.subBySplit(fileName, "/") != null) {
        fileName = StringUtil.subBySplit(fileName, "/");
      } else if (StringUtil.subBySplit(fileName, "\\") != null) {
        fileName = StringUtil.subBySplit(fileName, "\\");
      }

      try {
        is = new FileInputStream(rootPath + File.separator + fileName);
        logger.info("在classpath下没找到，在用户工作目录下找{},是否找到：{}", fileName, is == null ? "否" : "是");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        logger.info("获取输入流异常");
      }
    }
    return is;
  }

  /**
   * getInputStremByFileName:根据文件名获取输入流. <br/>
   *
   * @author zhaoguiyang
   */
  public static Reader getReaderByFileName(String fileName) {
    InputStream is = null;
    Reader reader = null;
    //首先在classpath中找，如果找不到，则在工作目录下找
    is = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
    logger.info("在classpath下找{},是否找到：{}", fileName, is == null ? "否" : "是");

    if (is == null) {
      //没找到，则在使用绝对路径，找到jar所在的根目录
      //String rootPath = System.getProperty("user.dir");
      String rootPath = System.getProperty("logDirPath");
      logger.info("rootPath:{}", rootPath);

      //截取一下文件名：BirthdayReminder/生日名单.xls ->
      if (StringUtil.subBySplit(fileName, "/") != null) {
        fileName = StringUtil.subBySplit(fileName, "/");
      } else if (StringUtil.subBySplit(fileName, "\\") != null) {
        fileName = StringUtil.subBySplit(fileName, "\\");
      }

      try {
        is = new FileInputStream(rootPath + File.separator + fileName);
        reader = new InputStreamReader(is, "UTF-8");
        logger.info("在classpath下没找到，在用户工作目录下找{},是否找到：{}", fileName, is == null ? "否" : "是");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        logger.info("获取输入流异常");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return reader;
  }

  /**
   * getValueByKey:通过key取对应的值. <br/>
   *
   * @author zhaoguiyang
   */
  public static String getValueByKey(String key) {
    String value = Constants.EMPTY_STR;
    if (key != null && key.length() > 0) {
      try {
        //首先在系统属性中获取，如果没有，则在配置中获取
        if (!StringUtil.isEmpty(System.getProperty(key))) {
          value = System.getProperty(key);
          // logger.info("在系统属性中获取{}的value为：{}", key, value);
        } else {
          value = properties.getProperty(key);
          // logger.info("在配置文件中获取{}的value为：{}", key, value);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return value;
  }

  /**
   * getValueByKey:通过key取对应的值. <br/>
   *
   * @author zhaoguiyang
   */
  public static String getValueByKeyWithoutLog(String key) {
    String value = Constants.EMPTY_STR;
    if (key != null && key.length() > 0) {
      try {
        //首先在系统属性中获取，如果没有，则在配置中获取
        if (!StringUtil.isEmpty(System.getProperty(key))) {
          value = System.getProperty(key);
          SysoutUtil.printInfo("在系统属性中获取{0}的value为：{1}", key, value);
        } else {
          value = properties.getProperty(key);
          SysoutUtil.printInfo("在配置文件中获取{0}的value为：{1}", key, value);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return value;
  }

  /**
   * setProperty:设置一个属性. <br/>
   *
   * @author zhaoguiyang
   */
  public static void setProperty(String key, String value) {
    properties.setProperty(key, value);
  }

  /**
   * getConfigurationPath: 获取配置路径. <br/> 这个方法可以获取jar文件所在文件夹的路径，不包含lib包。
   * <p/>
   *
   * @return 配置路径对象
   * @author zhaoguiyang
   */
  public static String getConfigurationPath() {
    String path = PropertyUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();

    if (!path.endsWith("/")) {
      path = path.substring(0, path.lastIndexOf("/") + 1);
      SysoutUtil.printInfo("jar文件所在文件夹的路径:{0}", path);
    } else {
      SysoutUtil.printInfo("classpath路径:{0}", path);
    }

//        String libDir = "/" + "lib" + "/";
//        if (path.endsWith(libDir)) {
//            path = path.replaceAll(libDir, "/");
//        }
    return path;
  }

  public static String getWorkSpaceSrcPath() {
    return System.getProperty("user.dir") + File.separator +
        (Configuration.getString("base.srcRoot").replace(".", File.separator));
  }

  public static void setLogger(Logger logger) {
    PropertyUtil.logger = logger;
  }

  /**
   * 获取前缀
   * @param nameFlag 常量中定义的类型 Constants.dao等类型
   * @return
   */
  public static String getNameSuffix(String nameFlag) {
    String suffix = Configuration.getString(nameFlag + ".suffix");
    switch (nameFlag) {
      case Constants.DAO:
        if (null == suffix) {
          suffix = Constants.DAO_SUFFIX;
        }
        break;
      case Constants.ENTITY:
        if (null == suffix) {
          suffix = Constants.ENTITY_SUFFIX;
        }
        break;
      case Constants.MAPPER_XML:
        if (null == suffix) {
          suffix = Constants.MAPPER_XML_SUFFIX;
        }
        break;
      case Constants.VO:
        if (null == suffix) {
          suffix = Constants.VO_SUFFIX;
        }
        break;
      case Constants.SERVICE:
        if (null == suffix) {
          suffix = Constants.SERVICE_SUFFIX;
        }
        break;
      case Constants.SERVICE_IMPL:
        if (null == suffix) {
          suffix = Constants.SERVICE_IMPL_SUFFIX;
        }
        break;
      case Constants.ACTION:
        if (null == suffix) {
          suffix = Constants.ACTION_SUFFIX;
        }
        break;
      default:
        break;
    }
    return suffix;
  }

  /**
   * 获取后缀
   * @param nameFlag 常量中定义的类型
   * @return
   */
  public static String getNamePrefix(String nameFlag) {
    String prefix = Configuration.getString(nameFlag + ".prefix");
    switch (nameFlag) {
      case Constants.DAO:
        if (null == prefix) {
          prefix = Constants.DAO_PREFIX;
        }
        break;
      case Constants.ENTITY:
        if (null == prefix) {
          prefix = Constants.ENTITY_PREFIX;
        }
        break;
      case Constants.MAPPER_XML:
        if (null == prefix) {
          prefix = Constants.MAPPER_XML_PREFIX;
        }
        break;
      case Constants.VO:
        if (null == prefix) {
          prefix = Constants.VO_PREFIX;
        }
        break;
      case Constants.SERVICE:
        if (null == prefix) {
          prefix = Constants.SERVICE_PREFIX;
        }
        break;
      case Constants.SERVICE_IMPL:
        if (null == prefix) {
          prefix = Constants.SERVICE_IMPL_PREFIX;
        }
        break;
      case Constants.ACTION:
        if (null == prefix) {
          prefix = Constants.ACTION_PREFIX;
        }
        break;
      default:
        break;
    }
    return prefix;
  }


  public static void main(String[] args) {
//        loadProp("jdbc.properties");
//        System.out.println(PropertyUtil.getValueByKey("driver"));
//        loadProp("system.properties");
//        File f = new File("E:/武汉/张伟/以图搜图/有匹配排重(整理后)/SUV有匹配");
//        boolean isSuccess = f.mkdirs();
//        System.out.println(isSuccess);
    String rootPath = System.getProperty("user.dir") + File.separator +
        (Configuration.getString("base.srcRoot").replace(".", File.separator));
    URL url = Thread.currentThread().getContextClassLoader().getResource("");
    System.out.println(rootPath);
  }
}

