package com.sbs.das.dto;

import java.io.Serializable;

import kr.co.d2net.commons.utils.BeanUtils;

public abstract class BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 신규등록[true], 변경[false]
	private boolean addClip;

	public boolean isAddClip() {
		return addClip;
	}

	public void setAddClip(boolean addClip) {
		this.addClip = addClip;
	}

	@Override
    public boolean equals(Object obj) {
        return BeanUtils.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return BeanUtils.hashCode(this);
    }

    @Override
    public String toString() {
        return BeanUtils.toString(this);
    }
}
