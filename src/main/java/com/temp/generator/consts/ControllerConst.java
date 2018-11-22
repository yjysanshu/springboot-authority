package com.temp.generator.consts;

public class ControllerConst extends CommonConst {

    public static final String ADMIN_CONTROLLER_PACKAGE = ".permission.controller";
    public static final String CLASS_NAME_CONTROLLER = "Controller";

    public static final String TXT_IMPORT_CUSTOM_INFO = "import com.temp.permission.model.request.[ModelClass]Request;\n" +
            "import com.temp.permission.service.[ModelClass]Service;\n" +
            "import com.temp.permission.util.FormatUtil;\n";

    public static final String TXT_IMPORT_FRAMEWORK_INFO = "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.web.bind.annotation.RequestBody;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n\n";

    public static final String TXT_IMPORT_SYSTEM_INFO = "import java.util.HashMap;\n" +
            "import java.util.Map;\n\n";

    public static final String TXT_CONTROLLER =
            "@RestController\n" +
            "@RequestMapping(\"/[modelClass]\")\n" +
            "public class [ModelClass]Controller {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private [ModelClass]Service service;\n" +
            "\n" +
            "    @RequestMapping(\"/list\")\n" +
            "    public Map list(@RequestBody [ModelClass]Request request) {\n" +
            "        Map<String, Object> map = new HashMap<>();\n" +
            "        map.put(\"list\", service.getPageList(request));\n" +
            "        map.put(\"total\", service.getTotal(request));\n" +
            "        return FormatUtil.success(map);\n" +
            "    }\n" +
            "\n" +
            "    @RequestMapping(\"/save\")\n" +
            "    public Map save(@RequestBody [ModelClass]Request request) {\n" +
            "        return FormatUtil.success(service.save(request));\n" +
            "    }\n" +
            "\n" +
            "    @RequestMapping(\"/del\")\n" +
            "    public Map delete(@RequestBody [ModelClass]Request request) {\n" +
            "        if (service.delete(request.getId()) > 0) {\n" +
            "            return FormatUtil.success();\n" +
            "        }\n" +
            "        return FormatUtil.fail();\n" +
            "    }\n" +
            "}";
}
