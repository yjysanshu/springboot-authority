package com.temp.generator.service.generator;

import com.temp.generator.consts.ControllerConst;
import com.temp.generator.util.StringUtil;

public class ControllerService extends BaseService {
    @Override
    protected void generator() {
        sb.append(this.getModelAfterReplace(ControllerConst.TXT_IMPORT_CUSTOM_INFO));
        sb.append(this.getModelAfterReplace(ControllerConst.TXT_IMPORT_FRAMEWORK_INFO));
        sb.append(this.getModelAfterReplace(ControllerConst.TXT_IMPORT_SYSTEM_INFO));
        sb.append(this.getModelAfterReplace(ControllerConst.TXT_CONTROLLER));
    }

    @Override
    protected String getClassName() {
        if (this.className == null) {
            this.className = StringUtil.firstCharacterToUpper(StringUtil.formatField(tableName)) + ControllerConst.CLASS_NAME_CONTROLLER;
        }
        return this.className;
    }

    @Override
    protected String getPath() {
        if (this.path == null) {
            this.path = System.getProperty("user.dir") + ControllerConst.PATH_INNER_CONTROLLER;
        }
        return this.path;
    }

    @Override
    protected String getFullPackageName() {
        return packageName + ControllerConst.ADMIN_CONTROLLER_PACKAGE;
    }
}
