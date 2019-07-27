package com.temp.generator.consts;

public class ControllerConst extends CommonConst {

    public static final String ADMIN_CONTROLLER_PACKAGE = ".common.controller";
    public static final String CLASS_NAME_CONTROLLER = "Controller";

    public static final String TXT_IMPORT_CUSTOM_INFO = "import com.temp.common.model.ResponseData;\n" +
            "import com.temp.admin.dto.[ModelClass]DTO;\n" +
            "import com.temp.common.service.[ModelClass]Service;\n" +
            "import com.temp.common.util.FormatUtil;\n";

    public static final String TXT_IMPORT_FRAMEWORK_INFO = "import io.swagger.annotations.Api;\n" +
            "import io.swagger.annotations.ApiOperation;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.web.bind.annotation.RequestBody;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RequestMethod;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n\n";

    public static final String TXT_IMPORT_SYSTEM_INFO = "import java.util.HashMap;\n" +
            "import java.util.Map;\n\n";

    public static final String TXT_CONTROLLER =
            "@Api(description = \"xxxx模块\")\n" +
                    "@RestController(\"admin[ModelClass]Controller\")\n" +
                    "@RequestMapping(\"/admin/[modelClass]\")\n" +
                    "public class [ModelClass]Controller {\n" +
                    "\n" +
                    "    @Autowired\n" +
                    "    private [ModelClass]Service service;\n" +
                    "\n" +
                    "    @ApiOperation(value = \"xxxx列表\", notes = \"根据查询信息，查询列表\")\n" +
                    "    @RequestMapping(value = \"/list\", method = { RequestMethod.POST })\n" +
                    "    public ResponseData list(@RequestBody [ModelClass]DTO container) {\n" +
                    "        Map<String, Object> map = new HashMap<>();\n" +
                    "        map.put(\"list\", service.getPageList(container));\n" +
                    "        map.put(\"total\", service.getTotal(container));\n" +
                    "        return FormatUtil.success(map);\n" +
                    "    }\n" +
                    "\n" +
                    "    @ApiOperation(value = \"新增或修改xxxx\", notes = \"根据ID来新增或修改信息\")\n" +
                    "    @RequestMapping(value = \"/save\", method = { RequestMethod.POST })\n" +
                    "    public ResponseData save(@RequestBody [ModelClass]DTO container) {\n" +
                    "        return FormatUtil.success(service.save(container));\n" +
                    "    }\n" +
                    "\n" +
                    "    @ApiOperation(value = \"删除xxxx\", notes = \"根据Id删除信息\")\n" +
                    "    @RequestMapping(value = \"/del\", method = { RequestMethod.POST })\n" +
                    "    public ResponseData delete(@RequestBody [ModelClass]DTO container) {\n" +
                    "        if (service.delete(container.getId()) > 0) {\n" +
                    "            return FormatUtil.success();\n" +
                    "        }\n" +
                    "        return FormatUtil.fail();\n" +
                    "    }\n" +
                    "}";
}
