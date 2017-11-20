package ${packageStr};

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.geoway.safety.supervision.common.dao.IBaseDao;

${importStr}

/**
* @author: zhaoguiyang
* @email: zhaoguiyang@geoway.com.cn
* @date:  ${time}
* @description:
*/
@Repository
public interface ${className} extends IBaseDao<${entityClassName}>{

}