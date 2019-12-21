package com.itheima.saas.domain.system;

import java.io.Serializable;

public class Dept implements Serializable {

    private String id;
    private String deptName;
    private Dept parent;   //父部门类型也是一个实体类
    private Integer state;
    private String companyId;
    private String companyName;

    public Dept() {
    }
    //查找部门的父部门名称： parent.deptName,   parent.companyId

    //返回Dept实体类：{id:2, deptName:北京分公司, state:1, companyId:1, companyName:传智播客
    //                  parent:{id:1, deptName:传智播客, state:1, companyId:1, companyName:传智播客, parent:null}
    //               }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Dept getParent() {
        return parent;
    }

    public void setParent(Dept parent) {
        this.parent = parent;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
