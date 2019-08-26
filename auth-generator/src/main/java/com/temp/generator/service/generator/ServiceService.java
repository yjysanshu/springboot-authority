package com.temp.generator.service.generator;

import com.temp.generator.consts.CharacterConst;
import com.temp.generator.consts.ServiceConst;
import com.temp.generator.models.TableExtend;
import com.temp.generator.util.StringUtil;

import java.util.Arrays;

public class ServiceService extends BaseService {
    @Override
    protected void generator() {
        StringBuilder sbReqToModel = new StringBuilder();
        StringBuilder sbModelToRes = new StringBuilder();
        StringBuilder sbReqToSaveModel = new StringBuilder();

        for (TableExtend tableExtend : list) {
            if (Arrays.asList(ServiceConst.TIME_FIELD).contains(tableExtend.getColumnName()
                    .replaceAll(tableName + CharacterConst.CHARACTER_UNDERLINE, CharacterConst.CHARACTER_NULL))) {
                sbReqToModel.append(this.getFieldAfterReplace(ServiceConst.TXT_FIELD_REQUEST_TO_MODEL, tableExtend));
                sbModelToRes.append(this.getFieldAfterReplace(ServiceConst.TXT_FIELD_MODEL_TO_RESPONSE_TIME, tableExtend));
                continue;
            }
            if (!tableExtend.getColumnKey().equals(ServiceConst.DATA_KEY_PRI)) {
                sbReqToSaveModel.append(this.getFieldAfterReplace(ServiceConst.TXT_FIELD_REQUEST_TO_SAVE_MODEL, tableExtend));
            }
            sbReqToModel.append(this.getFieldAfterReplace(ServiceConst.TXT_FIELD_REQUEST_TO_MODEL, tableExtend));
            sbModelToRes.append(this.getFieldAfterReplace(ServiceConst.TXT_FIELD_MODEL_TO_RESPONSE, tableExtend));
        }

        sb.append(this.getModelAfterReplace(ServiceConst.TXT_IMPORT_CUSTOM_INFO));
        sb.append(this.getModelAfterReplace(ServiceConst.TXT_IMPORT_FRAMEWORK_INFO));
        sb.append(this.getModelAfterReplace(ServiceConst.TXT_IMPORT_SYSTEM_INFO));
        sb.append(this.getModelAfterReplace(ServiceConst.TXT_SERVICE)
                .replaceAll(ServiceConst.REPLACE_REQUEST_TO_SAVE_MODEL, sbReqToSaveModel.toString())
                .replaceAll(ServiceConst.REPLACE_MODEL_TO_RESPONSE, sbModelToRes.toString())
                .replaceAll(ServiceConst.REPLACE_REQUEST_TO_MODEL, sbReqToModel.toString()));
    }

    @Override
    protected String getClassName() {
        if (this.className == null) {
            this.className = StringUtil.firstCharacterToUpper(StringUtil.formatField(tableName)) + ServiceConst.CLASS_NAME_SERVICE;
        }
        return this.className;
    }

    @Override
    protected String getPath() {
        if (this.path == null) {
            this.path = System.getProperty("user.dir") + ServiceConst.PATH_INNER_SERVICE;
        }
        return this.path;
    }

    @Override
    protected String getFullPackageName() {
        return packageName + ServiceConst.COMMON_SERVICE_PACKAGE;
    }

    private String getFieldAfterReplace(String replaceStr, TableExtend tableExtend) {
        return replaceStr.replaceAll(ServiceConst.REPLACE_UPPER_COLUMN_NAME, tableExtend.getColumnNameGSet())
                .replaceAll(ServiceConst.REPLACE_UPPER_COLUMN_NAME_RE, tableExtend.getColumnNameReGSet(this.getLowerModelClass()))
                .replaceAll(ServiceConst.REPLACE_MODEL_CLASS, this.getLowerModelClass());
    }
}
