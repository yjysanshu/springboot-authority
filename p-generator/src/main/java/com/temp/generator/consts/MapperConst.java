package com.temp.generator.consts;

public class MapperConst extends CommonConst {
    //待替换的文本
    public static final String REPLACE_COLUMN_LINE = "\\[column\\]";
    public static final String REPLACE_JDBC_TYPE = "\\[jdbcType\\]";
    public static final String REPLACE_RESULT_MAP_LINE = "\\[resultMapLine\\]";
    public static final String REPLACE_BASE_COLUMN_LINE = "\\[baseColumnLine\\]";
    public static final String REPLACE_BASE_COLUMN_VALUE = "\\[baseColumnValue\\]";
    public static final String REPLACE_PRIMARY_KEY = "\\[primaryKey\\]";
    public static final String REPLACE_PRIMARY_KEY_COLUMN = "\\[primaryKeyColumn\\]";
    public static final String REPLACE_CONDITION_COLUMN_LIST = "\\[conditionColumnList\\]";
    public static final String REPLACE_CONDITION_COLUMN_VALUE = "\\[conditionColumnValue\\]";
    public static final String REPLACE_CONDITION_COLUMN_SET = "\\[conditionColumnSet\\]";
    public static final String REPLACE_CONDITION_COLUMN_WHERE = "\\[conditionColumnWhere\\]";
    public static final String REPLACE_CONDITION_COLUMN_WHERE_WITH_CLASS = "\\[conditionColumnWhereWithClass\\]";

    public static final String TXT_IMPORT_CUSTOM_INFO = "import [package].common.model.entity.[ModelClass];\n\n";
    public static final String TXT_MIDDLE_CLASS = "public interface [ModelClass]Mapper extends BaseMapper<[ModelClass]> {\n\n}";

    public static final String COMMON_MAPPER_PACKAGE = ".common.mapper";

    public static final String CLASS_NAME_MAPPER = "Mapper";

    //xml
    public static final String TXT_COLUMN_LINE = "[column], ";
    public static final String TXT_VALUE_LINE = "#{[columnName],jdbcType=[jdbcType]}, ";
    public static final String TXT_RESULT_LINE = "        <result column=\"[column]\" property=\"[columnName]\" jdbcType=\"[jdbcType]\" />\n";
    public static final String TXT_CONDITION_LIST =  "            <if test=\"[columnName] != null\" >\n" +
            "                [column],\n" +
            "            </if>\n";
    public static final String TXT_CONDITION_VALUE =  "            <if test=\"[columnName] != null\" >\n" +
            "                #{[columnName],jdbcType=[jdbcType]},\n" +
            "            </if>\n";
    public static final String TXT_CONDITION_SET = "            <if test=\"[columnName] != null\" >\n" +
            "                [column] = #{[columnName],jdbcType=[jdbcType]},\n" +
            "            </if>\n";
    public static final String TXT_CONDITION_WHERE = "            <if test=\"[columnName] != null\" >\n" +
            "                AND [column] = #{[columnName],jdbcType=[jdbcType]}\n" +
            "            </if>\n";
    public static final String TXT_CONDITION_WHERE_WITH_MODEL = "            <if test=\"[modelClass].[columnName] != null\" >\n" +
            "                AND [column] = #{[modelClass].[columnName],jdbcType=[jdbcType]}\n" +
            "            </if>\n";

    public static final String TXT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
            "<mapper namespace=\"com.temp.common.mapper.[ModelClass]Mapper\" >\n" +
            "\n" +
            "    <resultMap id=\"BaseResultMap\" type=\"[package].common.model.entity.[ModelClass]\" >\n" +
            "[resultMapLine]" +
            "    </resultMap>\n" +
            "\n" +
            "    <sql id=\"Base_Column_List\" >\n" +
            "        [baseColumnLine]\n" +
            "    </sql>\n" +
            "\n" +
            "    <select id=\"queryList\" resultMap=\"BaseResultMap\" parameterType=\"[package].common.model.entity.[ModelClass]\">\n" +
            "        SELECT\n" +
            "        <include refid=\"Base_Column_List\" />\n" +
            "        FROM [realTableName]\n" +
            "        <where>\n" +
            "[conditionColumnWhere]" +
            "        </where>\n" +
            "        ORDER BY [primaryKey] DESC\n" +
            "    </select>\n" +
            "\n" +
            "    <select id=\"queryPageList\" resultMap=\"BaseResultMap\">\n" +
            "        SELECT\n" +
            "        <include refid=\"Base_Column_List\" />\n" +
            "        FROM [realTableName]\n" +
            "        <where>\n" +
            "[conditionColumnWhereWithClass]" +
            "        </where>\n" +
            "        ORDER BY [primaryKey] DESC LIMIT ${(page - 1) * size}, ${size}\n" +
            "    </select>\n" +
            "\n" +
            "    <select id=\"queryCount\" resultType=\"java.lang.Integer\" parameterType=\"[package].common.model.entity.[ModelClass]\">\n" +
            "        SELECT COUNT(*) FROM [realTableName]\n" +
            "        <where>\n" +
            "[conditionColumnWhere]" +
            "        </where>\n" +
            "    </select>\n" +
            "\n" +
            "    <select id=\"queryOne\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Integer\" >\n" +
            "        SELECT\n" +
            "        <include refid=\"Base_Column_List\" />\n" +
            "        FROM [realTableName]\n" +
            "        WHERE [primaryKey] = #{id,jdbcType=INTEGER}\n" +
            "    </select>\n" +
            "\n" +
            "    <delete id=\"delete\" parameterType=\"java.lang.Integer\" >\n" +
            "        DELETE FROM [realTableName]\n" +
            "        WHERE [primaryKey] = #{id,jdbcType=INTEGER}\n" +
            "    </delete>\n" +
            "\n" +
            "    <insert id=\"add\" parameterType=\"[package].common.model.entity.[ModelClass]\" >\n" +
            "        INSERT INTO [realTableName]\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "[conditionColumnList]" +
            "        </trim>\n" +
            "        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "[conditionColumnValue]" +
            "        </trim>\n" +
            "    </insert>\n" +
            "\n" +
            "    <update id=\"update\" parameterType=\"[package].common.model.entity.[ModelClass]\" >\n" +
            "        update [realTableName]\n" +
            "        <set>\n" +
            "[conditionColumnSet]" +
            "        </set>\n" +
            "        where [primaryKey] = #{[primaryKeyColumn],jdbcType=INTEGER}\n" +
            "    </update>\n" +
            "</mapper>";
}
