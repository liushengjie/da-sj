package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "v_sys_code")
@ApiModel("SysCode（）")
public class SysCode implements Serializable {
    /**
     * 代码值
     */
    @ApiModelProperty(value ="代码值",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String code;

    /**
     * 父代码
     */
    @Column(name = "p_code")
    @ApiModelProperty(value ="父代码",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String pCode;

    /**
     * 代码名称
     */
    @ApiModelProperty(value ="代码名称",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 类别名称
     */
    @Column(name = "type_name")
    @ApiModelProperty(value ="类别名称",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String typeName;

    /**
     * 类别代码
     */
    @Column(name = "type_code")
    @ApiModelProperty(value ="类别代码",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String typeCode;

    private static final long serialVersionUID = 1L;

    /**
     * 获取代码值
     *
     * @return code - 代码值
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置代码值
     *
     * @param code 代码值
     */
    public SysCode setCode(String code) {
        this.code = code == null ? null : code.trim();
        return this;
    }

    /**
     * 获取父代码
     *
     * @return p_code - 父代码
     */
    public String getpCode() {
        return pCode;
    }

    /**
     * 设置父代码
     *
     * @param pCode 父代码
     */
    public SysCode setpCode(String pCode) {
        this.pCode = pCode == null ? null : pCode.trim();
        return this;
    }

    /**
     * 获取代码名称
     *
     * @return name - 代码名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置代码名称
     *
     * @param name 代码名称
     */
    public SysCode setName(String name) {
        this.name = name == null ? null : name.trim();
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
    public SysCode setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
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
    public SysCode setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
        return this;
    }

    public enum FieldEnum {
        CODE("code","code"),
		P_CODE("pCode","p_code"),
		NAME("name","name"),
		TYPE_NAME("typeName","type_name"),
		TYPE_CODE("typeCode","type_code");

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