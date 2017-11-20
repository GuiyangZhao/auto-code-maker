package ${packageStr};

import com.alibaba.fastjson.JSON;
import com.geoway.safety.supervision.common.action.BaseAction;
import com.geoway.safety.supervision.common.constant.ReturnCodeEnum;
import com.geoway.safety.supervision.common.constant.ValidType;
import com.geoway.safety.supervision.common.result.BaseResult;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

${importStr}

/**
* @author: zhaoguiyang
* @email: zhaoguiyang@geoway.com.cn
* @date:  ${time}
* @description:
*/
@RequestMapping("/${entityNameVar}")
@Controller
public class ${className} extends BaseAction{

  private static Logger logger = LoggerFactory.getLogger(${className}.class);

  @Autowired
  private  ${serviceName}  ${serviceNameVar};

  @RequestMapping(value = "/add", method = {RequestMethod.POST},
      produces = {"application/json;charset=UTF-8"})
  @ResponseBody
  public BaseResult<${entityName}> add${entityName}(HttpServletRequest request,
      @ModelAttribute @Validated({ValidType.Add.class}) ${voName} ${voNameVar}) throws Exception {
    BaseResult<${entityName}> result = new BaseResult<>();

    ${serviceNameVar}.add${entityName}(${voNameVar});
    result.setReturnCode(ReturnCodeEnum.SUCCESS.getStatus());
    result.setMessage(ReturnCodeEnum.SUCCESS.getDesc());
    
    logger.info("${className}.add${entityName} response, result is " + result + ", ${voNameVar} is "
      + JSON.toJSONString(${voNameVar}));
    
    return result;
  }
    
  @RequestMapping(value = "/update", method = {RequestMethod.POST},
    produces = {"application/json;charset=UTF-8"})
  @ResponseBody
  public BaseResult<${entityName}> update${entityName}(HttpServletRequest request,
      @ModelAttribute @Validated ${entityName} ${entityNameVar}) {
      BaseResult<${entityName}> result = new BaseResult<>();

      int res = ${serviceNameVar}.update${entityName}(${entityNameVar});
      result.setReturnCode(ReturnCodeEnum.SUCCESS.getStatus());
      result.setMessage(ReturnCodeEnum.SUCCESS.getDesc());

      logger.info(
        "${className}.update${entityName} response, result is " + result + ", ${entityNameVar} is " + JSON.toJSONString(res));
      return result;
  }

  @RequestMapping(value = "/delete", method = {RequestMethod.POST},
    produces = {"application/json;charset=UTF-8"})
  @ResponseBody
  public BaseResult<Object> delete${entityName}By${methodPrimaryKey}(HttpServletRequest request,
      @RequestParam("${primaryKeyVar}") String ${primaryKeyVar}) {
      BaseResult<Object> result = new BaseResult<>();
      logger.info("${className}.delete${entityName}By${methodPrimaryKey} request, ${primaryKeyVar} is " + ${primaryKeyVar});

      ${serviceNameVar}.delete${entityName}By${methodPrimaryKey}(${primaryKeyVar});
      result.setReturnCode(ReturnCodeEnum.SUCCESS.getStatus());
      result.setMessage(ReturnCodeEnum.SUCCESS.getDesc());

      logger.info("${className}.delete${entityName}By${methodPrimaryKey} response, result is " + result + "${primaryKeyVar} is " + ${primaryKeyVar});
      return result;
  }
            
  @RequestMapping(value = "list", method = {RequestMethod.POST,
      RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
  @ResponseBody
  public BaseResult<Object> get${entityName}List(HttpServletRequest request, @ModelAttribute ${voName} ${voNameVar},
      @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
    BaseResult<Object> result = new BaseResult<>();
    logger.info("${className}.get${entityName}List request, ${voNameVar} is " + JSON.toJSONString(${voNameVar}));

    List<${entityName}> ${entityNameVar}s = ${serviceNameVar}.get${entityName}List(${voNameVar}, pageIndex, pageSize);
    result.setReturnCode(ReturnCodeEnum.SUCCESS.getStatus());
    result.setMessage(ReturnCodeEnum.SUCCESS.getDesc());
    Map<String, Object> map = new HashMap<String, Object>(5);
    map.put("${entityNameVar}s", ${entityNameVar}s);
    result.setResult(map);

    logger.info("${className}.get${entityName}List response, result is " + JSON.toJSONString(result) + ", ${entityNameVar} is "
        + JSON.toJSONString(${entityNameVar}s));
    return result;
  }
}