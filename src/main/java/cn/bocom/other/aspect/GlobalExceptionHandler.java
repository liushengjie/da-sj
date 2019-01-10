package cn.bocom.other.aspect;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bocom.other.common.Code;
import cn.bocom.other.common.DataResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody 
    public DataResponse exceptionHandler(RuntimeException e, HttpServletResponse response) {
		DataResponse resp;
		resp = new DataResponse(Code.FAIL, e.getMessage());
		response.setStatus(500);
		return resp;
    }
}
