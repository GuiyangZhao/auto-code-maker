package ${packageStr};

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

${importStr}

/**
* @author: zhaoguiyang
* @email: zhaoguiyang@geoway.com.cn
* @date:  ${time}
* @description:
*/
@Transactional(rollbackFor = Exception.class)
@Service
public class ${className} extends ServiceImpl<${daoName},${entityName}> implements ${serviceName}{

  @Autowired
  private ${daoName} ${daoNameVar};


  @Override
  public Integer add${entityName}(${voName} ${voNameVar}) {
    return ${daoNameVar}.insert(new ${entityName}(${voNameVar}));
  }

  @Override
  public Integer update${entityName}(${entityName} ${entityNameVar}) {
    return ${daoNameVar}.updateById(${entityNameVar});
  }

  @Override
  public void delete${entityName}By${primaryKey}(String ${primaryKeyVar}) {
    ${daoNameVar}.deleteById(${primaryKeyVar});
  }

  @Override
  public List<${entityName}> get${entityName}List(${voName} ${voNameVar}, int pageIndex, int pageSize) {
    EntityWrapper<${entityName}> wrapper = new EntityWrapper<>();
    wrapper.setEntity(new ${entityName}(${voNameVar}));
    return  ${daoNameVar}.selectPage(new Page<${entityName}>(pageIndex,pageSize),wrapper);
  }
}