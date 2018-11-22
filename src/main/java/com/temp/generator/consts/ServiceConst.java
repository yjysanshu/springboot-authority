package com.temp.generator.consts;

public class ServiceConst extends CommonConst {
    //待替换的文本
    public static final String REPLACE_REQUEST_TO_SAVE_MODEL = "\\[requestToSaveModel\\]";
    public static final String REPLACE_MODEL_TO_RESPONSE = "\\[modelToResponse\\]";
    public static final String REPLACE_REQUEST_TO_MODEL = "\\[requestToModel\\]";

    //替换的文本
    public static final String TXT_FIELD_REQUEST_TO_SAVE_MODEL = "        [modelClass].set[ColumnName](request.get[ColumnNameRe]());\n";
    public static final String TXT_FIELD_MODEL_TO_RESPONSE = "        response.set[ColumnNameRe]([modelClass].get[ColumnName]());\n";
    public static final String TXT_FIELD_MODEL_TO_RESPONSE_TIME = "        response.set[ColumnNameRe]((new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\")).format([modelClass].get[ColumnName]()));\n";
    public static final String TXT_FIELD_REQUEST_TO_MODEL = "        [modelClass].set[ColumnName](request.get[ColumnNameRe]());\n";

    public static final String COMMON_SERVICE_PACKAGE = ".permission.service";

    public static final String CLASS_NAME_SERVICE = "Service";

    public static final String TXT_IMPORT_CUSTOM_INFO = "import [package].permission.model.request.[ModelClass]Request;\n" +
            "import [package].permission.model.response.[ModelClass]Response;\n" +
            "import [package].permission.mapper.[ModelClass]Mapper;\n" +
            "import [package].permission.entity.[ModelClass];\n";

    public static final String TXT_IMPORT_FRAMEWORK_INFO = "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Service;\n\n";

    public static final String TXT_IMPORT_SYSTEM_INFO = "import java.text.SimpleDateFormat;" +
            "\nimport java.util.*;\n\n";

    public static final String TXT_SERVICE = "@Service\n" +
            "public class [ModelClass]Service {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private [ModelClass]Mapper mapper;\n" +
            "\n" +
            "    public List<[ModelClass]Response> getPageList([ModelClass]Request request) {\n" +
            "        [ModelClass] [modelClass]Search = formatModelDetail(request);\n" +
            "        Map<String, Object> map = new HashMap<>();\n" +
            "        map.put(\"[modelClass]\", [modelClass]Search);\n" +
            "        map.put(\"page\", request.getCurrentPage());\n" +
            "        map.put(\"size\", request.getLimit());\n" +
            "        List<[ModelClass]> [modelClass]List = mapper.queryPageList([modelClass]Search);\n" +
            "        List<[ModelClass]Response> list = new ArrayList<>();\n" +
            "        for ([ModelClass] [modelClass] : [modelClass]List) {\n" +
            "            [ModelClass]Response [modelClass]Response = formatResponseDetail([modelClass]);\n" +
            "            list.add([modelClass]Response);\n" +
            "        }\n" +
            "        return list;\n" +
            "    }\n" +
            "\n" +
            "    public Integer getTotal([ModelClass]Request request) {\n" +
            "        [ModelClass] [modelClass]Search = formatModelDetail(request);\n" +
            "        return mapper.queryCount([modelClass]Search);\n" +
            "    }\n" +
            "\n" +
            "    public Integer save([ModelClass]Request request) {\n" +
            "        [ModelClass] [modelClass];\n" +
            "        if (request.getId() != null) {\n" +
            "            [modelClass] = mapper.queryOne(request.getId());\n" +
            "        } else {\n" +
            "            [modelClass] = new [ModelClass]();\n" +
            "            [modelClass].set[ModelClass]CreateAt(new Date());\n" +
            "        }\n" +
            "[requestToSaveModel]" +
            "        if (request.getId() != null) {\n" +
            "            return mapper.update([modelClass]);\n" +
            "        } else {\n" +
            "            return mapper.add([modelClass]);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    public Integer delete(Integer id) {\n" +
            "        return mapper.delete(id);\n" +
            "    }\n" +
            "\n" +
            "    private [ModelClass]Response formatResponseDetail([ModelClass] [modelClass]) {\n" +
            "        [ModelClass]Response response = new [ModelClass]Response();\n" +
            "[modelToResponse]" +
            "        return response;\n" +
            "    }\n" +
            "\n" +
            "    private [ModelClass] formatModelDetail([ModelClass]Request request) {\n" +
            "        [ModelClass] [modelClass] = new [ModelClass]();\n" +
            "[requestToModel]" +
            "        return [modelClass];\n" +
            "    }\n" +
            "}";
}
