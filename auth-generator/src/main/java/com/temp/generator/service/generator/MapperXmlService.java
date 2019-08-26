package com.temp.generator.service.generator;

import com.temp.generator.consts.MapperConst;
import com.temp.generator.models.TableExtend;
import com.temp.generator.util.StringUtil;

public class MapperXmlService extends BaseService {
    private String primaryKey;
    private String primaryKeyColumn;

    @Override
    protected void generator() {
        StringBuilder sbResult = new StringBuilder();
        StringBuilder sbConditionList = new StringBuilder();
        StringBuilder sbConditionValue = new StringBuilder();
        StringBuilder sbConditionSet = new StringBuilder();
        StringBuilder sbConditionWhere = new StringBuilder();
        StringBuilder sbConditionWhereWithModel = new StringBuilder();
        StringBuilder sbBaseLine = new StringBuilder();
        StringBuilder sbBaseValue = new StringBuilder();

        for (TableExtend tableExtend : list) {
            if (!tableExtend.getColumnKey().equals(MapperConst.DATA_KEY_PRI)) {
                sbConditionSet.append(this.getConditionAfterReplace(MapperConst.TXT_CONDITION_SET, tableExtend));
            } else {
                this.primaryKey = tableExtend.getColumnName();
                this.primaryKeyColumn = tableExtend.getColumnNameModel();
            }
            sbResult.append(this.getConditionAfterReplace(MapperConst.TXT_RESULT_LINE, tableExtend));
            sbConditionList.append(this.getConditionAfterReplace(MapperConst.TXT_CONDITION_LIST, tableExtend));
            sbConditionValue.append(this.getConditionAfterReplace(MapperConst.TXT_CONDITION_VALUE, tableExtend));
            sbConditionWhere.append(this.getConditionAfterReplace(MapperConst.TXT_CONDITION_WHERE, tableExtend));
            sbConditionWhereWithModel.append(this.getConditionAfterReplace(MapperConst.TXT_CONDITION_WHERE_WITH_MODEL, tableExtend));
            sbBaseLine.append(this.getConditionAfterReplace(MapperConst.TXT_COLUMN_LINE, tableExtend));
            sbBaseValue.append(this.getConditionAfterReplace(MapperConst.TXT_VALUE_LINE, tableExtend));

        }
        sb.append(MapperConst.TXT_XML.replaceAll(MapperConst.REPLACE_RESULT_MAP_LINE, sbResult.toString())
                .replaceAll(MapperConst.REPLACE_CONDITION_COLUMN_LIST, sbConditionList.toString())
                .replaceAll(MapperConst.REPLACE_CONDITION_COLUMN_VALUE, sbConditionValue.toString())
                .replaceAll(MapperConst.REPLACE_CONDITION_COLUMN_SET, sbConditionSet.toString())
                .replaceAll(MapperConst.REPLACE_CONDITION_COLUMN_WHERE, sbConditionWhere.toString())
                .replaceAll(MapperConst.REPLACE_CONDITION_COLUMN_WHERE_WITH_CLASS, sbConditionWhereWithModel.toString()
                        .replaceAll(MapperConst.REPLACE_MODEL_CLASS, this.getLowerModelClass())
                )
                .replaceAll(MapperConst.REPLACE_PRIMARY_KEY, this.primaryKey)
                .replaceAll(MapperConst.REPLACE_PRIMARY_KEY_COLUMN, this.primaryKeyColumn)
                .replaceAll(MapperConst.REPLACE_BASE_COLUMN_LINE, sbBaseLine.deleteCharAt(sbBaseLine.length() - 1).deleteCharAt(sbBaseLine.length() - 1).toString())
                .replaceAll(MapperConst.REPLACE_BASE_COLUMN_VALUE, sbBaseValue.deleteCharAt(sbBaseValue.length() - 1).deleteCharAt(sbBaseValue.length() - 1).toString())
                .replaceAll(MapperConst.REPLACE_UPPER_MODEL_CLASS, this.getUpperModelClass())
                .replaceAll(MapperConst.REPLACE_PACKAGE, packageName)
                .replaceAll(MapperConst.REPLACE_REAL_TABLE_NAME, realTableName));
    }

    @Override
    protected String getClassName() {
        if (this.className == null) {
            this.className = StringUtil.firstCharacterToUpper(StringUtil.formatField(tableName)) + MapperConst.CLASS_NAME_MAPPER;
        }
        return this.className;
    }

    @Override
    protected String getPath() {
        if (this.path == null) {
            this.path = System.getProperty("user.dir") +  MapperConst.PATH_INNER_MAPPER_XML;
        }
        return this.path;
    }

    @Override
    protected String getFullPackageName() {
        return "";
    }

    @Override
    protected String getFileType() {
        return MapperConst.FILE_TYPE_XML;
    }

    private String getConditionAfterReplace(String replaceStr, TableExtend tableExtend) {
        return replaceStr.replaceAll(MapperConst.REPLACE_COLUMN_LINE, tableExtend.getColumnName())
                .replaceAll(MapperConst.REPLACE_COLUMN_NAME, tableExtend.getColumnNameModel())
                .replaceAll(MapperConst.REPLACE_JDBC_TYPE, tableExtend.getDataTypeJdbc());
    }
}
