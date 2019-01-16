package cn.bocom.r_service.process;

import java.util.List;
import java.util.Map;

import cn.bocom.r_entity.process.Processor.ProcessEnum;

public class ProcessUtil {
    /**
     * 获取可用的处理器
     * @param processName
     * @return
     */
    public static List<Map<String, Object>> availableProcess(List<String> processName) {
        processName.stream().forEach(name -> {
            ProcessEnum p =  ProcessEnum.match(name, null);
        });
        return null;
    }
}
