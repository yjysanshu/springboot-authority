package com.temp.generator.service.generator;

import com.temp.generator.consts.CharacterConst;
import com.temp.generator.consts.ModelConst;
import com.temp.generator.models.TableExtend;
import com.temp.generator.util.StringUtil;

public class ModelService extends BaseService {

    @Override
    protected void generator() {
        sb.append(ModelConst.TXT_IMPORT_LINE_DATE);
        sb.append(ModelConst.TXT_CLASS_LINE.replaceAll(ModelConst.REPLACE_CLASS, this.getClassName()));
        //private行
        for (TableExtend tableExtend : list) {
            sb.append(ModelConst.TXT_FIELD_NAME.replaceAll(ModelConst.REPLACE_TYPE, tableExtend.getDataTypeModel())
                    .replaceAll(ModelConst.REPLACE_COLUMN_NAME, tableExtend.getColumnNameModel()));
        }
        sb.append(CharacterConst.ESCAPE_WARP);

        //GETTER & SETTER
        for(TableExtend tableExtend : list) {
            sb.append(ModelConst.TXT_FIELD_GETTER.replaceAll(ModelConst.REPLACE_TYPE, tableExtend.getDataTypeModel())
                    .replaceAll(ModelConst.REPLACE_UPPER_COLUMN_NAME, tableExtend.getColumnNameGSet())
                    .replaceAll(ModelConst.REPLACE_COLUMN_NAME, tableExtend.getColumnNameModel()));
            sb.append(ModelConst.TXT_FIELD_SETTER.replaceAll(ModelConst.REPLACE_TYPE, tableExtend.getDataTypeModel())
                    .replaceAll(ModelConst.REPLACE_UPPER_COLUMN_NAME, tableExtend.getColumnNameGSet())
                    .replaceAll(ModelConst.REPLACE_COLUMN_NAME, tableExtend.getColumnNameModel()));
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(ModelConst.TXT_END_LINE);
    }

    @Override
    protected String getClassName() {
        if (this.className == null) {
            this.className = StringUtil.firstCharacterToUpper(StringUtil.formatField(tableName));
        }
        return this.className;
    }

    @Override
    protected String getPath() {
        if (this.path == null) {
            this.path = System.getProperty("user.dir") + ModelConst.PATH_INNER_MODELS;
        }
        return this.path;
    }

    @Override
    protected String getFullPackageName() {
        return packageName + ModelConst.COMMON_MODEL_PACKAGE;
    }
}
