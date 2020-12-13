package com.vantaihanhkhach.model;

import java.util.Date;

import lombok.Data;

@Data

public class ChuyenXe {

	private long id;

	private int soLuongKhach;

	private double giaVe;
	
	private Date ngayChay;

	private XeKhach xeKhach;

	private TuyenXe tuyenxe;

	private TaiXe taixe;

	private TaiXe taixe1;
}
