package com.vantaihanhkhach.model;

import java.util.Date;

import lombok.Data;

@Data
public class XeKhach {

	private long id;

	private String bienSo;

	private String mauXe;

	private String hsx;

	private String kieuXe;
	
	private int soGhe;

	private int soNamSD;

	private Date ngayBaoDuongCuoi;
}
