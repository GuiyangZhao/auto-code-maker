package ${packageStr};

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.geoway.safety.supervision.common.constant.ValidType;
${importStr}

/**
* @author: zhaoguiyang
* @email: zhaoguiyang@geoway.com.cn
* @date:  ${time}
* @description:${tableRemark}
*/
@Entity
@TableName("${tableName}")
public class ${className} implements Serializable {
  private static final long serialVersionUID = 1L;

  ${propertiesStr}

  ${constructStr}

  ${methodStr}
}