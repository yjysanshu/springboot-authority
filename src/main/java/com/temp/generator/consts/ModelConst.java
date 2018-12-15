package com.temp.generator.consts;

public class ModelConst extends CommonConst {
    //待替换的文本
    public static final String TXT_FIELD_NAME = "\tprivate [type] [columnName];\n";
    public static final String TXT_FIELD_GETTER = "\tpublic [type] get[ColumnName]() {" +
            "\n\t\treturn this.[columnName];\n\t}\n\n";

    public static final String TXT_FIELD_SETTER = "\tpublic void set[ColumnName]([type] [columnName]) " +
            "{\n\t\tthis.[columnName] = [columnName];\n\t}\n\n";
    public static final String TXT_FIELD_TOSTRING = "\t@Override\n\tpublic String toString() {" +
            "\n\treturn \"[modelName]{\" + \n";

    public static final String COMMON_MODEL_PACKAGE = ".common.model.entity";

    public static final String PACKAGE_ADMIN_REQUEST = ".admin.dto";
    public static final String PACKAGE_ADMIN_RESPONSE = ".admin.dto";

    public static final String CLASS_REQUEST = "DTO";
    public static final String CLASS_RESPONSE = "ListDTO";

    public static final String PARENT_CLASS_REQUEST = "BaseDTO";
    public static final String PARENT_CLASS_RESPONSE = "BaseListDTO";

    public static final String TXT_IMPORT_LINE_DTO = "import com.temp.common.model.BaseDTO;\n\n";
    public static final String TXT_IMPORT_LINE_LIST_DTO = "import com.temp.common.model.BaseListDTO;\n\n";
}
