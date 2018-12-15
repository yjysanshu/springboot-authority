package com.temp.generator.consts;

public class ServiceConst extends CommonConst {
    //待替换的文本
    public static final String REPLACE_REQUEST_TO_SAVE_MODEL = "\\[containerToSaveModel\\]";
    public static final String REPLACE_MODEL_TO_RESPONSE = "\\[modelToResponse\\]";
    public static final String REPLACE_REQUEST_TO_MODEL = "\\[containerToModel\\]";

    //替换的文本
    public static final String TXT_FIELD_REQUEST_TO_SAVE_MODEL = "        [modelClass].set[ColumnName](container.get[ColumnNameRe]());\n";
    public static final String TXT_FIELD_MODEL_TO_RESPONSE = "        response.set[ColumnNameRe]([modelClass].get[ColumnName]());\n";
    public static final String TXT_FIELD_MODEL_TO_RESPONSE_TIME = "        response.set[ColumnNameRe]((new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\")).format([modelClass].get[ColumnName]()));\n";
    public static final String TXT_FIELD_REQUEST_TO_MODEL = "        [modelClass].set[ColumnName](container.get[ColumnNameRe]());\n";

    public static final String COMMON_SERVICE_PACKAGE = ".common.service";

    public static final String CLASS_NAME_SERVICE = "Service";

    public static final String TXT_IMPORT_CUSTOM_INFO = "import [package].admin.dto.[ModelClass]DTO;\n" +
            "import [package].admin.dto.list.[ModelClass]ListDTO;\n" +
            "import [package].common.mapper.[ModelClass]Mapper;\n" +
            "import [package].common.model.entity.[ModelClass];\n";

    public static final String TXT_IMPORT_FRAMEWORK_INFO = "import org.dozer.Mapper;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Service;\n\n";

    public static final String TXT_IMPORT_SYSTEM_INFO = "import java.text.SimpleDateFormat;" +
            "\nimport java.util.*;\n\n";

    public static final String TXT_SERVICE = "@Service\n" +
            "public class [ModelClass]Service {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private [ModelClass]Mapper mapper;\n" +
            "\n" +
            "    @Autowired\n" +
            "    private Mapper mapperTrans;\n" +
            "\n" +
            "    public List<[ModelClass]ListDTO> getPageList([ModelClass]DTO container) {\n" +
            "        [ModelClass] [modelClass]Search = mapperTrans.map(container, [ModelClass].class);\n" +
            "        Map<String, Object> map = new HashMap<>();\n" +
            "        map.put(\"[modelClass]\", [modelClass]Search);\n" +
            "        map.put(\"page\", container.getCurrentPage());\n" +
            "        map.put(\"size\", container.getLimit());\n" +
            "        List<[ModelClass]> [modelClass]List = mapper.queryPageList(map);\n" +
            "        List<[ModelClass]ListDTO> list = new ArrayList<>();\n" +
            "        for ([ModelClass] [modelClass] : [modelClass]List) {\n" +
            "            [ModelClass]ListDTO dto = mapperTrans.map([modelClass], [ModelClass]ListDTO.class);\n" +
            "            list.add(dto);\n" +
            "        }\n" +
            "        return list;\n" +
            "    }\n" +
            "\n" +
            "    public Integer getTotal([ModelClass]DTO container) {\n" +
            "        [ModelClass] [modelClass]Search = mapperTrans.map(container, [ModelClass].class);\n" +
            "        return mapper.queryCount([modelClass]Search);\n" +
            "    }\n" +
            "\n" +
            "    public Integer save([ModelClass]DTO container) {\n" +
            "        [ModelClass] [modelClass];\n" +
            "        if (container.getId() != null) {\n" +
            "            [modelClass] = mapper.queryOne(container.getId());\n" +
            "            if ([modelClass] != null) {\n" +
            "               [modelClass].setCreatedAt(null);\n" +
            "               [modelClass].setUpdatedAt(null);\n" +
            "            } else {\n" +
            "               [modelClass] = new [ModelClass]();\n" +
            "            }\n" +
            "        } else {\n" +
            "            [modelClass] = new [ModelClass]();\n" +
            "        }\n" +
            "[containerToSaveModel]" +
            "        if (container.getId() != null) {\n" +
            "            return mapper.update([modelClass]);\n" +
            "        } else {\n" +
            "            return mapper.add([modelClass]);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    public Integer delete(Integer id) {\n" +
            "        return mapper.delete(id);\n" +
            "    }\n" +
            "}";

    //没用了
//    "\n" +
//            "    private [ModelClass]Response formatResponseDetail([ModelClass] [modelClass]) {\n" +
//            "        [ModelClass]Response response = new [ModelClass]Response();\n" +
//            "[modelToResponse]" +
//            "        return response;\n" +
//            "    }\n" +
//            "\n" +
//            "    private [ModelClass] formatModelDetail([ModelClass]DTO container) {\n" +
//            "        [ModelClass] [modelClass] = new [ModelClass]();\n" +
//            "[containerToModel]" +
//            "        return [modelClass];\n" +
//            "    }\n" +
}
