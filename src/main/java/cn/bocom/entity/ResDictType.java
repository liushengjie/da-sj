package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res_dict_type")
@ApiModel("ResDictType（）")
public class ResDictType implements Serializable {
    /**
     * ID
     */
    @Id
    @ApiModelProperty(value ="ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    /**
     * 类别代码
     */
    @Column(name = "type_code")
    @ApiModelProperty(value ="类别代码",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String typeCode;

    /**
     * 类别名称
     */
    @Column(name = "type_name")
    @ApiModelProperty(value ="类别名称",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String typeName;

    /**
     * 备注
     */
    @ApiModelProperty(value ="备注",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String remark;

    /**
     * 生成时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value ="生成时间",required = false)
    @ColumnType(jdbcType=JdbcType.TIMESTAMP)
    private Date createTime;

    /**
     * 是否可用 0不可用 1可用
     */
    @ApiModelProperty(value ="是否可用 0不可用 1可用",required = false)
    @ColumnType(jdbcType=JdbcType.CHAR)
    private String enabled;

    private static final long serialVersionUID = 1L;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public ResDictType setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * 获取类别代码
     *
     * @return type_code - 类别代码
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * 设置类别代码
     *
     * @param typeCode 类别代码
     */
    public ResDictType setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
        return this;
    }

    /**
     * 获取类别名称
     *
     * @return type_name - 类别名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置类别名称
     *
     * @param typeName 类别名称
     */
    public ResDictType setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
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
    public ResDictType setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
        return this;
    }

    /**
     * 获取生成时间
     *
     * @return create_time - 生成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置生成时间
     *
     * @param createTime 生成时间
     */
    public ResDictType setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 获取是否可用 0不可用 1可用
     *
     * @return enabled - 是否可用 0不可用 1可用
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 设置是否可用 0不可用 1可用
     *
     * @param enabled 是否可用 0不可用 1可用
     */
    public ResDictType setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
        return this;
    }

    public enum FieldEnum {
        ID("id","id"),
		TYPE_CODE("typeCode","type_code"),
		TYPE_NAME("typeName","type_name"),
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