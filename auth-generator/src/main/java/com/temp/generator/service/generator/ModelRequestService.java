package com.temp.generator.service.generator;

import com.temp.generator.consts.CharacterConst;
import com.temp.generator.consts.ModelConst;
import com.temp.generator.models.TableExtend;
import com.temp.generator.util.StringUtil;

import java.util.Arrays;

public class ModelRequestService extends BaseService {

    @Override
    protected void generator() {
        sb.append(ModelConst.TXT_IMPORT_LINE_DTO);
        sb.append(ModelConst.TXT_CLASS_EXTEND_LINE.replaceAll(ModelConst.REPLACE_CLASS, this.getClassName())
                .replaceAll(ModelConst.REPLACE_PARENT, ModelConst.PARENT_CLASS_REQUEST));
        //private行
        for (TableExtend tableExtend : list) {
            if (Arrays.asList(ModelConst.TIME_FIELD).contains(tableExtend.getColumnName()
                    .replaceAll(tableName + CharacterConst.CHARACTER_UNDERLINE, CharacterConst.CHARACTER_NULL))) {
                continue;
            }
            sb.append(ModelConst.TXT_FIELD_NAME
                    .replaceAll(ModelConst.REPLACE_TYPE, tableExtend.getDataTypeModel())
                    .replaceAll(ModelConst.REPLACE_COLUMN_NAME, tableExtend.getColumnNameRe(this.getLowerModelClass())));
        }
        sb.append(CharacterConst.ESCAPE_WARP);

        //GETTER & SETTER
        for(TableExtend tableExtend : list) {
            if (Arrays.asList(ModelConst.TIME_FIELD).contains(tableExtend.getColumnName()
                    .replaceAll(tableName + CharacterConst.CHARACTER_UNDERLINE, CharacterConst.CHARACTER_NULL))) {
                continue;
            }
            //GETTER
            sb.append(ModelConst.TXT_FIELD_GETTER.replaceAll(ModelConst.REPLACE_TYPE, tableExtend.getDataTypeModel())
                    .replaceAll(ModelConst.REPLACE_UPPER_COLUMN_NAME, tableExtend.getColumnNameReGSet(this.getLowerModelClass()))
                    .replaceAll(ModelConst.REPLACE_COLUMN_NAME, tableExtend.getColumnNameRe(this.getLowerModelClass())));
            //SETTER
            sb.append(ModelConst.TXT_FIELD_SETTER.replaceAll(ModelConst.REPLACE_TYPE, tableExtend.getDataTypeModel())
                    .replaceAll(ModelConst.REPLACE_UPPER_COLUMN_NAME, tableExtend.getColumnNameReGSet(this.getLowerModelClass()))
                    .replaceAll(ModelConst.REPLACE_COLUMN_NAME, tableExtend.getColumnNameRe(this.getLowerModelClass())));
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(ModelConst.TXT_END_LINE);
    }

    @Override
    protected String getClassName() {
        if (this.className == null) {
            this.className = StringUtil.firstCharacterToUpper(StringUtil.formatField(tableName)) + ModelConst.CLASS_REQUEST;
        }
        return this.className;
    }

    @Override
    protected String getPath() {
        if (this.path == null) {
            this.path = System.getProperty("user.dir") + ModelConst.PATH_INNER_REQUEST_MODELS;
        }
        return this.path;
    }

    @Override
    protected String getFullPackageName() {
        return packageName + ModelConst.PACKAGE_ADMIN_REQUEST;
    }
}
