package cn.bocom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.entity.Processor;
import cn.bocom.mapper.main.ProcessorMapper;

public class ProcessorService {
    private ProcessorMapper mapper;

    public List<Processor> queryProcessor(Processor processor) {
        return mapper.queryProcessor(processor);
    }

}
