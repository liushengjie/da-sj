package cn.bocom.r_service.process;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;

import com.google.common.collect.ImmutableMap;

import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;
import cn.bocom.r_entity.process.Processor.ProcessEnum;

public class ProcessUtil {
    /**
     * 获取处理器可用的功能
     * 
     * @param processName
     * @return
     */
    public static List<Map<String, Object>> availableFunc(List<String> processName,
            String procType) {
        List<Map<String, Object>> ret = Lists.newArrayList();
        List<ProcessEnum> processList = Lists.newArrayList();
        processName.stream().forEach(name -> {
            ProcessEnum p = ProcessEnum.match(name, null);
            processList.add(p);
        });

        processList.stream()
        .filter(p -> p.getProcType().equals(procType))
        .forEach(p -> {
            Map<String, Object> m = ImmutableMap.<String, Object>builder().put("id", p.getId())
                    .put("name", p.getName()).put("procType", p.getProcType()).build();
            ret.add(m);
        });
        return ret;
    }
    
    /**
     * 获取处理器实体类
     * @param datasourceType
     * @return
     */
    public static IProcess processObj(int datasourceType) {
        DataSourceEnum datasourceEnum = DataSourceEnum.match(datasourceType, null);
        return datasourceEnum.getProcessClass();
    }
}
