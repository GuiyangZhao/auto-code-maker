package com.geoway.generate.config;

import com.geoway.generate.util.StringHelper;
import com.geoway.generate.util.XMLHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author: zhaoguiyang
 * @email: zhaoguiyang@geoway.com.cn
 * @date: 2017/11/13 10:30
 * @description:
 */
public class Configuration {

  private static Logger logger = LogManager.getLogger(Configuration.class);

  @SuppressWarnings("rawtypes")
  private static Map items = new HashMap();

  private static String CONFIG_FILE_NAME = "configuration-geoway-genetator.xml";

  static {
    loadConfig();
  }

  /**
   * 读入配置文件
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void loadConfig() {
    try {

      Document document = XMLHelper.getDocument(Configuration.class, CONFIG_FILE_NAME);
      if (document != null) {
        Element systemElement = document.getRootElement();
        List catList = systemElement.elements("category");
        for (Iterator catIter = catList.iterator(); catIter.hasNext(); ) {
          Element catElement = (Element) catIter.next();
          String catName = catElement.attributeValue("name");
          if (StringHelper.isEmpty(catName)) {
            continue;
          }

          List itemList = catElement.elements("item");
          for (Iterator itemIter = itemList.iterator(); itemIter.hasNext(); ) {
            Element itemElement = (Element) itemIter.next();
            String itemName = itemElement.attributeValue("name");
            String value = itemElement.attributeValue("value");
            if (!StringHelper.isEmpty(itemName)) {
              items.put(catName + "." + itemName, value);
            }
          }
        }
      }
    } catch (Exception ex) {
      logger.error("读入配置文件出错", ex);
    } finally {
    }

  }

  /**
   * 获得字串配置值
   */
  public static String getString(String name) {
    String value = (String) items.get(name);
    return (value == null) ? "" : value;
  }

  /**
   * 获得字串配置值，若为空，则返回缺省值
   */
  public static String getString(String name, String defaultValue) {
    String value = (String) items.get(name);
    if (value != null && value.length() > 0) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 获得整型配置值
   */
  public static int getInt(String name) {
    String value = getString(name);
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ex) {
      logger.debug("配置文件key[" + name + "]配置错误，return 0", ex);
      return 0;
    }
  }

  /**
   * 获得整型配置值
   */
  public static int getInt(String name, int defaultValue) {
    String value = getString(name);
    if ("".equals(value)) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ex) {
      logger.debug("配置文件key[" + name + "]配置错误，return " + defaultValue, ex);
    }
    return defaultValue;
  }

  /**
   * 获得布尔型配置值
   */
  public static boolean getBoolean(String name) {
    String value = getString(name);
    return Boolean.valueOf(value).booleanValue();
  }

  /**
   * 获得双精度浮点数配置值
   */
  public static double getDouble(String name, double defaultValue) {
    String value = getString(name);
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException ex) {
      logger.error("配置文件key[" + name + "]配置错误，return " + defaultValue, ex);
    }
    return defaultValue;
  }

  @SuppressWarnings("rawtypes")
  public static Map getItems() {
    return items;
  }
}
