package com.temp.generator.service.generator;

import com.temp.generator.consts.CommonConst;
import com.temp.generator.models.Table;
import com.temp.generator.models.TableExtend;
import com.temp.generator.util.FileUtil;
import com.temp.generator.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseService {

    String path;
    String tableName;
    String realTableName;
    String className;
    String packageName;
    private String upperModelClass;
    private String lowerModelClass;
    StringBuilder sb = new StringBuilder();
    List<TableExtend> list = new ArrayList<>();

    /**
     * 初始化table数据
     * @param listTable 源数据
     * @param packageName 包名
     * @param tableName 表名
     * @return BaseService
     */
    public BaseService init(List<Table> listTable, String packageName, String tableName, String realTableName) {
        this.tableName = tableName;
        this.realTableName = realTableName;
        this.packageName = packageName;
        TableExtend tableExtend;
        for (Table table : listTable) {
            System.out.println(table.toString());
            tableExtend = new TableExtend();
            tableExtend.setColumnName(table.getColumnName());
            tableExtend.setColumnDefault(table.getColumnDefault());
            tableExtend.setIsNullable(table.getIsNullable());
            tableExtend.setDataType(table.getDataType());
            tableExtend.setColumnKey(table.getColumnKey());
            tableExtend.setColumnComment(table.getColumnComment());
            this.list.add(tableExtend);
        }
        return this;
    }

    /**
     * 开始生成文件
     * @return true-文件生成成功
     */
    public boolean handle() {
        if (this.getFileType().equals(CommonConst.FILE_TYPE_JAVA)) {
            sb.append(CommonConst.TXT_PACKAGE_LINE.replaceAll(CommonConst.REPLACE_PACKAGE, this.getFullPackageName()));
        }
        this.generator();
        return FileUtil.fileWrite(sb.toString(), this.getPath(), this.getClassName(), this.getFileType());
    }

    protected abstract void generator();

    protected abstract String getClassName();

    protected abstract String getPath();

    protected abstract String getFullPackageName();

    String formatPackageDir(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }

    protected String getFileType() {
        return CommonConst.FILE_TYPE_JAVA;
    }

    String getUpperModelClass() {
        if (this.upperModelClass == null) {
            this.upperModelClass = StringUtil.firstCharacterToUpper(StringUtil.formatField(tableName));
        }
        return this.upperModelClass;
    }

    String getLowerModelClass() {
        if (this.lowerModelClass == null) {
            this.lowerModelClass = StringUtil.firstCharacterToLower(StringUtil.formatField(tableName));
        }
        return this.lowerModelClass;
    }

    String getModelAfterReplace(String replaceStr) {
        return replaceStr.replaceAll(CommonConst.REPLACE_MODEL_CLASS, this.getLowerModelClass())
                .replaceAll(CommonConst.REPLACE_UPPER_MODEL_CLASS, this.getUpperModelClass())
                .replaceAll(CommonConst.REPLACE_PACKAGE, packageName);
    }
}
