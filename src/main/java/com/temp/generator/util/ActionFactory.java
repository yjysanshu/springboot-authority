package com.temp.generator.util;

import com.temp.generator.execption.GeneratorException;
import com.temp.generator.service.generator.*;

public class ActionFactory {

    /**
     * 生成model代码
     * @param action 需要生成的类
     * @return BaseService的子类
     */
    public static BaseService getInstance(String action) throws GeneratorException {
        switch (action) {
            case "model":
                return new ModelService();
            case "modelRequest":
                return new ModelRequestService();
            case "modelResponse":
                return new ModelResponseService();
            case "service":
                return new ServiceService();
            case "mapper":
                return new MapperService();
            case "mapperXml":
                return new MapperXmlService();
            case "vue":
                return new VueService();
            case "controller":
                return new ControllerService();
        }
        throw new GeneratorException("不存在该请求");
    }
}
