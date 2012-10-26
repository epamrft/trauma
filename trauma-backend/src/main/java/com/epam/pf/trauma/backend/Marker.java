package com.epam.pf.trauma.backend;

import java.io.Serializable;

public class Marker implements Serializable {

	private static final long serialVersionUID = 1L;

	private String desc;

	public Marker() {
		super();
	}

	public Marker(String desc) {
		super();
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
