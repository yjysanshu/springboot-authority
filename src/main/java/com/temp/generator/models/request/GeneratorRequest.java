package com.temp.generator.models.request;

import java.util.Arrays;

public class GeneratorRequest {
    private String[] actions;
    private String tableName;
    private String packageName;
    private boolean deletePrefix;

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isDeletePrefix() {
        return deletePrefix;
    }

    public void setDeletePrefix(boolean deletePrefix) {
        this.deletePrefix = deletePrefix;
    }

    @Override
    public String toString() {
        return "GeneratorRequest{" +
                "actions=" + Arrays.toString(actions) +
                ", tableName='" + tableName + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
