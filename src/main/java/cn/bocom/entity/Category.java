package cn.bocom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_category")
@ApiModel("Category（）")
public class Category implements Serializable {
    /**
     * 类别ID
     */
    @Id
    @ApiModelProperty(value = "类别ID", required = false)
    @ColumnType(jdbcType = JdbcType.INTEGER)
    private Integer id;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID", required = false)
    @ColumnType(jdbcType = JdbcType.INTEGER)
    private Integer pid;

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String name;

    /**
     * 分类（资源、服务）
     */
    @ApiModelProperty(value = "分类（资源、服务）", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String type;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String sort;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", required = false)
    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    @Transient
    private String type_name;
    @Transient
    private String create_time_str;
    @Transient
    private String levels;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCreate_time_str() {
        return create_time_str;
    }

    public void setCreate_time_str(String create_time_str) {
        this.create_time_str = create_time_str;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 获取类别ID
     *
     * @return id - 类别ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置类别ID
     *
     * @param id 类别ID
     */
    public Category setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * 获取父ID
     *
     * @return pid - 父ID
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父ID
     *
     * @param pid 父ID
     */
    public Category setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    /**
     * 获取类别名称
     *
     * @return name - 类别名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类别名称
     *
     * @param name 类别名称
     */
    public Category setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    /**
     * 获取分类（资源、服务）
     *
     * @return type - 分类（资源、服务）
     */
    public String getType() {
        return type;
    }

    /**
     * 设置分类（资源、服务）
     *
     * @param type 分类（资源、服务）
     */
    public Category setType(String type) {
        this.type = type == null ? null : type.trim();
        return this;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public String getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public Category setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
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
    public Category setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public enum FieldEnum {
        ID("id", "id"), PID("pid", "pid"), NAME("name", "name"), TYPE("type", "type"), SORT("sort",
                "sort"), CREATE_TIME("createTime", "create_time");

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
