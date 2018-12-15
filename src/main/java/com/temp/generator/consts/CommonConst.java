package com.temp.generator.consts;

public class CommonConst {

    public static final String[] TIME_FIELD = {"create_at", "update_at"};
    public static final String[] SYSTEM_DATABASE = {"information_schema", "performance_schema", "mysql"};

    public static final String SYSTEM_DATABASE_INFORMATION_SCHEMA = "information_schema";
    public static final String SYSTEM_DATABASE_PERFORMANCE_SCHEMA = "performance_schema";
    public static final String SYSTEM_DATABASE_MYSQL = "mysql";


    //文件路径
    public static final String PATH_INNER_MODELS = "/src/main/resources/code/models";
    public static final String PATH_INNER_CONTROLLER = "/src/main/resources/code/controllers";
    public static final String PATH_INNER_REQUEST_MODELS = "/src/main/resources/code/models/request";
    public static final String PATH_INNER_RESPONSE_MODELS = "/src/main/resources/code/models/response";
    public static final String PATH_INNER_SERVICE = "/src/main/resources/code/services";
    public static final String PATH_INNER_MAPPER = "/src/main/resources/code/mapper";
    public static final String PATH_INNER_MAPPER_XML = "/src/main/resources/code/mappers";
    public static final String PATH_INNER_MAPPER_VUE = "/src/main/resources/code/vue";

    //文件类型
    public static final String FILE_TYPE_JAVA = "java";
    public static final String FILE_TYPE_XML = "xml";
    public static final String FILE_TYPE_VUE = "vue";

    //错误类型
    public static final String TYPE_ERROR = "ERROR";

    //数据库字段类型
    public static final String DATA_TYPE_INT = "int";
    public static final String DATA_TYPE_FLOAT = "float";
    public static final String DATA_TYPE_DOUBLE = "double";
    public static final String DATA_TYPE_ENUM = "enum";
    public static final String DATA_TYPE_CHAR = "char";
    public static final String DATA_TYPE_VARCHAR = "varchar";
    public static final String DATA_TYPE_TEXT = "text";
    public static final String DATA_TYPE_TINYINT = "tinyint";
    public static final String DATA_TYPE_DATETIME = "datetime";
    public static final String DATA_TYPE_TIMESTAMP = "timestamp";

    //数据库KEY
    public static final String DATA_KEY_PRI = "PRI";

    //JAVA字段类型
    public static final String JAVA_TYPE_DATE = "Date";
    public static final String JAVA_TYPE_STRING = "String";
    public static final String JAVA_TYPE_INTEGER = "Integer";
    public static final String JAVA_TYPE_DOUBLE = "Double";

    //JDBC字段类型
    public static final String JDBC_TYPE_TIMESTAMP = "TIMESTAMP";
    public static final String JDBC_TYPE_VARCHAR = "VARCHAR";
    public static final String JDBC_TYPE_INTEGER = "INTEGER";
    public static final String JDBC_TYPE_FLOAT = "FLOAT";
    public static final String JDBC_TYPE_DOUBLE = "DOUBLE";

    //待替换的文本
    public static final String REPLACE_PACKAGE = "\\[package\\]";
    public static final String REPLACE_CLASS = "\\[class\\]";
    public static final String REPLACE_PARENT = "\\[parent\\]";
    public static final String REPLACE_TYPE = "\\[type\\]";
    public static final String REPLACE_COLUMN_NAME = "\\[columnName\\]";
    public static final String REPLACE_COLUMN_NAME_RE = "\\[columnNameRe\\]";
    public static final String REPLACE_UPPER_COLUMN_NAME = "\\[ColumnName\\]";
    public static final String REPLACE_UPPER_COLUMN_NAME_RE = "\\[ColumnNameRe\\]";
    public static final String REPLACE_MODEL_CLASS = "\\[modelClass\\]";
    public static final String REPLACE_UPPER_MODEL_CLASS = "\\[ModelClass\\]";
    public static final String REPLACE_TABLE_NAME = "\\[tableName\\]";
    public static final String REPLACE_REAL_TABLE_NAME = "\\[realTableName\\]";

    //替换的文本
    public static final String TXT_PACKAGE_LINE = "package [package];\n\n";
    public static final String TXT_IMPORT_LINE_DATE = "import java.util.Date;\n\n";
    public static final String TXT_CLASS_LINE = "public class [class] {\n";
    public static final String TXT_CLASS_EXTEND_LINE = "public class [class] extends [parent] {\n";
    public static final String TXT_END_LINE = "}";
}
