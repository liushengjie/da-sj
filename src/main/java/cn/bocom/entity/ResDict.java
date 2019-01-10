package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res_dict")
@ApiModel("ResDict（）")
public class ResDict implements Serializable {
    @Id
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String code;

    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 类别代码
     */
    @Column(name = "code_type")
    @ApiModelProperty(value ="类别代码",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String codeType;

    /**
     * 备注
     */
    @ApiModelProperty(value ="备注",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value ="创建时间",required = false)
    @ColumnType(jdbcType=JdbcType.TIMESTAMP)
    private Date createTime;

    /**
     * 是否可用，1可用，0不可用
     */
    @ApiModelProperty(value ="是否可用，1可用，0不可用",required = false)
    @ColumnType(jdbcType=JdbcType.CHAR)
    private String enabled;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public ResDict setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public ResDict setCode(String code) {
        this.code = code == null ? null : code.trim();
        return this;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public ResDict setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    /**
     * 获取类别代码
     *
     * @return code_type - 类别代码
     */
    public String getCodeType() {
        return codeType;
    }

    /**
     * 设置类别代码
     *
     * @param codeType 类别代码
     */
    public ResDict setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
        return this;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public ResDict setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
        return this;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public ResDict setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 获取是否可用，1可用，0不可用
     *
     * @return enabled - 是否可用，1可用，0不可用
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 设置是否可用，1可用，0不可用
     *
     * @param enabled 是否可用，1可用，0不可用
     */
    public ResDict setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
        return this;
    }

    public enum FieldEnum {
        ID("id","id"),
		CODE("code","code"),
		NAME("name","name"),
		CODE_TYPE("codeType","code_type"),
		REMARK("remark","remark"),
		CREATE_TIME("createTime","create_time"),
		ENABLED("enabled","enabled");

        private String javaFieldName;

        private String dbFieldName;

        FieldEnum(String javaFieldName, String dbFieldName) {
            this.javaFieldName = javaFieldName;
            this.dbFieldName = dbFieldName;
        }

        public String javaFieldName() {
            return javaFieldName;
        }

        public String dbFieldName() {
            return dbFieldName;
        }
    }
}