package com.temp.generator.models;

import com.temp.generator.consts.CharacterConst;
import com.temp.generator.consts.ModelConst;
import com.temp.generator.util.StringUtil;

public class TableExtend extends Table {
    private String dataTypeModel;
    private String dataTypeJdbc;
    private String columnNameModel;
    private String columnNameGSet;
    private String columnNameRe;
    private String columnNameReGSet;

    public String getDataTypeModel() {
        if (this.dataTypeModel == null) {
            this.dataTypeModel = getModelType(this.getDataType());
        }
        return this.dataTypeModel;
    }

    public String getDataTypeJdbc() {
        if (this.dataTypeJdbc == null) {
            this.dataTypeJdbc = getJdbcType(this.getDataType());
        }
        return this.dataTypeJdbc;
    }

    public String getColumnNameModel() {
        if (this.columnNameModel == null) {
            this.columnNameModel = StringUtil.formatField(this.getColumnName());
        }
        return this.columnNameModel;
    }

    public String getColumnNameGSet() {
        if (this.columnNameGSet == null) {
            this.columnNameGSet = StringUtil.firstCharacterToUpper(StringUtil.formatField(this.getColumnName()));
        }
        return columnNameGSet;
    }

    public String getColumnNameRe(String modelClass) {
        if (this.columnNameRe == null) {
            this.columnNameRe = StringUtil.firstCharacterToLower(
                    StringUtil.formatField(this.getColumnName()).replaceAll(modelClass, CharacterConst.CHARACTER_NULL));
        }
        return this.columnNameRe;
    }

    public String getColumnNameReGSet(String modelClass) {
        if (this.columnNameReGSet == null) {
            this.columnNameReGSet = StringUtil.firstCharacterToUpper(
                    StringUtil.formatField(this.getColumnName()).replaceAll(modelClass, CharacterConst.CHARACTER_NULL));
        }
        return columnNameReGSet;
    }

    /**
     * 获取数据库类型对应的JAVA类型
     * @param dataType 数据类型
     * @return JAVA类型
     */
    private static String getModelType(String dataType) {
        switch (dataType) {
            case ModelConst.DATA_TYPE_INT:
            case ModelConst.DATA_TYPE_TINYINT:
                return ModelConst.JAVA_TYPE_INTEGER;    //java的int类型
            case ModelConst.DATA_TYPE_FLOAT:
            case ModelConst.DATA_TYPE_DOUBLE:
                return ModelConst.JAVA_TYPE_DOUBLE; //java的double类型
            case ModelConst.DATA_TYPE_ENUM:
            case ModelConst.DATA_TYPE_CHAR:
            case ModelConst.DATA_TYPE_VARCHAR:
            case ModelConst.DATA_TYPE_TEXT:
                return ModelConst.JAVA_TYPE_STRING; //java的string类型
            case ModelConst.DATA_TYPE_DATETIME:
            case ModelConst.DATA_TYPE_TIMESTAMP:
                return ModelConst.JAVA_TYPE_DATE;   //java的date类型
        }
        return ModelConst.TYPE_ERROR;
    }

    /**
     * 获取数据库类型对应的JDBC类型
     * @param dataType 数据类型
     * @return JDBC类型
     */
    private static String getJdbcType(String dataType) {
        switch (dataType) {
            case ModelConst.DATA_TYPE_INT:
            case ModelConst.DATA_TYPE_TINYINT:
                return ModelConst.JDBC_TYPE_INTEGER;    //jdbc的integer类型
            case ModelConst.DATA_TYPE_FLOAT:
                return ModelConst.JDBC_TYPE_FLOAT;      //jdbc的float类型
            case ModelConst.DATA_TYPE_DOUBLE:
                return ModelConst.JDBC_TYPE_DOUBLE;     //jdbc的double类型
            case ModelConst.DATA_TYPE_ENUM:
            case ModelConst.DATA_TYPE_CHAR:
            case ModelConst.DATA_TYPE_VARCHAR:
            case ModelConst.DATA_TYPE_TEXT:
                return ModelConst.JDBC_TYPE_VARCHAR;    //jdbc的varchar类型
            case ModelConst.DATA_TYPE_DATETIME:
            case ModelConst.DATA_TYPE_TIMESTAMP:
                return ModelConst.JDBC_TYPE_TIMESTAMP;  //jdbc的timestamp类型
        }
        return ModelConst.TYPE_ERROR;
    }
}
