package ${packageStr};

import java.util.List;

${importStr}

/**
* @author: zhaoguiyang
* @email: zhaoguiyang@geoway.com.cn
* @date:  ${time}
* @description:
*/
public interface ${className} {

 /**
  * 记录添加
  * @param ${voNameVar}
  * @return 添加成功记录数
  */
 Integer add${entityName}(${voName} ${voNameVar});

 /**
  * 根据主键更新记录
  * @param ${voNameVar}
  * @return 更新成功记录数
  */
 Integer update${entityName}(${entityName} ${entityNameVar});

 /**
  * 根据主键删除记录
  * @param ${primaryKeyVar} 主键
  */
 void delete${entityName}By${primaryKey}(String ${primaryKeyVar});

 /**
  * 获得索引值范围内的记录数
  * @param ${voNameVar}
  * @param pageIndex 索引值
  * @param pageSize 返回的总记录数
  * @return 获得指定范围内的记录数
  */
 List<${entityName}> get${entityName}List(${voName} ${voNameVar}, int pageIndex, int pageSize);

}