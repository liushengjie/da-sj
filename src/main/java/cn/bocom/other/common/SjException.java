package cn.bocom.other.common;

/**
 * 异常类
 * @author liushengjie
 * @version $Id: SjException.java, v 0.1 2019年1月11日 下午1:54:32 liushengjie Exp $
 */
public class SjException extends RuntimeException{
    private static final long serialVersionUID = 42L;

    public SjException(String msg) {
        super(msg);
    }

    public SjException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SjException(Throwable cause) {
        super(cause);
    }
}
