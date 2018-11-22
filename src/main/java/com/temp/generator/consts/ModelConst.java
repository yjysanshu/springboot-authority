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

    public static final String COMMON_MODEL_PACKAGE = ".permission.entity";

    public static final String PACKAGE_ADMIN_REQUEST = ".permission.model.request";
    public static final String PACKAGE_ADMIN_RESPONSE = ".permission.model.response";

    public static final String CLASS_REQUEST = "Request";
    public static final String CLASS_RESPONSE = "Response";

    public static final String PARENT_CLASS_REQUEST = "BaseRequest";
    public static final String PARENT_CLASS_RESPONSE = "BaseResponse";
}
