package com.merak.lzptc.controller;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 编号ID 编号ID
	 */
	private Long id;
	/**
	 * 手机号 手机号
	 */
	private String mobile;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}