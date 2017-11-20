package com.geoway.generate.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StringUtil {

  /**
   * isEmpty:判断字符串是否为空. <br/>
   *
   * @author zhaoguiyang
   * @since JDK 1.6
   */
  public static boolean isEmpty(String str) {
    if (str == null || str.length() == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * subBySplit:根据分隔符截取字符串. <br/>
   *
   * @author zhaoguiyang
   * @since JDK 1.6
   */
  public static String subBySplit(String str, String split) {
    String sub = null;
    if (!isEmpty(str) && str.lastIndexOf(split) > -1) {
      sub = str.substring(str.lastIndexOf(split) + 1);
    }

    return sub;
  }

  /**
   * capitalize: 将字符串的首字母转换为大写. <br/>
   *
   * @param str 要转换的字符串
   * @return 转换之后的字符串
   * @author zhaoguiyang
   * @since JDK 1.6
   */
  public static String capitalize(String str) {
    if (isEmpty(str)) {
      return null;
    }
    char[] cs = str.toCharArray();
    if (cs[0] >= 'a' && cs[0] <= 'z') {
      cs[0] -= 32;
    }
    return new String(cs);
  }

  public static String camel(String str) {
    if (isEmpty(str)) {
      return null;
    }
    char[] cs = str.toCharArray();
    if (cs[0] >= 'A' && cs[0] <= 'Z') {
      cs[0] += 32;
    }
    return new String(cs);
  }

  /**
   * 将字符串拆分为list
   */
  public static List<String> splitStr2List(String str, String regex) {
    List<String> list = new ArrayList<String>();
    String[] strs = str.split(regex);
    for (String s : strs) {
      list.add(s.trim());
    }
    return list;
  }

  /**
   * 将数据库字段名，转为属性名 TODO Add comments here.
   */
  public static String convertFieldName2PropName(String fieldName) {
    String propName = "";
    String[] strs = fieldName.split("_");
    for (int i = 0; i < strs.length; i++) {
      if (i == 0) {
        propName = strs[0];
      } else {
        propName += strs[i].substring(0, 1).toUpperCase() + strs[i].substring(1);
      }
    }
    return propName;
  }

  /**
   * 将属性名字段转换为数据库字段名称
   *
   * @param properName 属性名称字段
   */
  public static String convertPropName2FieldName(String properName) {
    char[] chs = properName.toCharArray();
    List<Character> charList = new LinkedList<>();
    for (char ch : chs) {
      charList.add(ch);
    }
    for (int i = 0; i < chs.length; i++) {
      char c = chs[i];
      if (c >= 'A' && c <= 'Z') {
        charList.add(i + (charList.size() - chs.length), '_');
      }
    }
    char[] chars = new char[charList.size()];
    for (int i = 0; i < charList.size(); i++) {
      chars[i] = charList.get(i);
    }
    return new String(chars).toUpperCase();
  }

  public static void main(String[] args) {
    System.out.println(camel("Z"));
    String a="aaaa,1,,,,,,,";
    String[] strs = a.split(",");
    boolean is = " ".isEmpty();
    System.out.println(strs.toString());
  }
}
