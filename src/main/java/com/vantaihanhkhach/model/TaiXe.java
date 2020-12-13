package com.vantaihanhkhach.model;

import java.util.Date;

import lombok.Data;

@Data

public class TaiXe {

	private long id;

	private String ten;

	private String CMND;

	private String maBangLai;

	private String loaiBangLai;

	private String diaChi;

	private Date ngaySinh;

	private int thamNien;

}
