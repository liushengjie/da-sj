package cn.bocom.other.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 作者 liushengjie
 * @version 创建时间：2017年5月19日 下午2:59:34 类说明
 */
public class BaseResponse {

    @ApiModelProperty(value = "错误码")
    private Integer errno = Code.SUCCESS;

    @ApiModelProperty(value = "错误日志")
    private String error = "";

    @ApiModelProperty(value = "是否成功")
    private Boolean success = true;

    public BaseResponse() {}

    public BaseResponse(Integer errno, String error) {
        this.errno = errno;
        this.error = error;
        this.success = false;
    }

    public BaseResponse(Boolean success, Integer errno, String error) {
        this.errno = errno;
        this.error = error;
        this.success = success;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.errno = errno;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
