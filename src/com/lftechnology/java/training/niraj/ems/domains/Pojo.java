package com.lftechnology.java.training.niraj.ems.domains;

import java.util.List;
import java.util.Map;

public abstract class Pojo {
    public String table;
    public String primaryKey;
    public String id = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public abstract List<String> getAttributes();

    public abstract Map<String, String> getInfo();

}