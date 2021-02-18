package com.dongdl.springboot1.bean;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 16:11 UTC+8
 * @description
 **/
public class TaskConfigBean {

    private String id;
    private String name;
    private String description;
    private String open;
    private Integer limit;
    private String execTime;

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
