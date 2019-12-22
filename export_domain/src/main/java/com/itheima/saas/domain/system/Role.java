package com.itheima.saas.domain.system;

public class Role {
    /**
     * 主键，角色的id
     */ 
    private String id;

    /**
     * 角色的名称
     */ 
    private String name;

    /**
     * 角色的备注，说明
     */ 
    private String remark;

    /**
     * 省略getter，setter
     * 排序
     */ 
    private Long orderNo;

    /**
     * 企业id，企业名称
     */
	private String companyId;
	private String companyName;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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