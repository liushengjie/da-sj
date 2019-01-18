package cn.bocom.r_service.process;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理器服务类
 * @author liushengjie
 * @version $Id: ProcessService.java, v 0.1 2019年1月17日 下午2:57:09 liushengjie Exp $
 */
@Component
public class ProcessService {
    private static Logger logger = LoggerFactory.getLogger(ProcessService.class);
    
    /**
     * 根据处理器类型获取可用功能
     * @param processName
     * @param procType
     * @return
     */
    public List<Map<String, Object>> fetchAvailableFunc(int datasourceType, String procType) {
        IProcess<?> processor = ProcessUtil.processObj(datasourceType);
        return processor.availableFunction(procType);
    }
}
