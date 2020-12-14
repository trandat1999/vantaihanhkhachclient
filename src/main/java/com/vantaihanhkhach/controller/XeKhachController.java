package com.vantaihanhkhach.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.vantaihanhkhach.model.ChuyenXe;
import com.vantaihanhkhach.model.XeKhach;

@Controller
@RequestMapping(value = "/home/xekhach")
public class XeKhachController {
	private RestTemplate rest = new RestTemplate();
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping()
	public String homedriver(Model model) {
		List<XeKhach> listXeKhach = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach", XeKhach[].class));
		model.addAttribute("listxekhach", listXeKhach);
		return "xekhach/listxekhach";
	}

	@GetMapping("/ngaybaoduong")
	public String ngaybaoduong(Model model) {
		Date today = new Date();
		HashMap<String, Long> dayduong = new HashMap<>();
		HashMap<String, Long> dayam = new HashMap<>();
		List<XeKhach> listxekhach = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach", XeKhach[].class));
		for (XeKhach xekhach : listxekhach) {
			long day=360;
			LocalDate a= LocalDate.parse(formatter.format(xekhach.getNgayBaoDuongCuoi()), DateTimeFormatter.ISO_LOCAL_DATE);
	        LocalDate b= LocalDate.parse(formatter.format(today), DateTimeFormatter.ISO_LOCAL_DATE);
	        Duration diff = Duration.between(a.atStartOfDay(), b.atStartOfDay());
	        long diffDays = diff.toDays();
	        day-=diffDays;
			List<ChuyenXe> listchuyenxe = Arrays
					.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/chuyenxe/Greater/{date1}", ChuyenXe[].class,
							formatter.format(xekhach.getNgayBaoDuongCuoi())));
			for(ChuyenXe chuyenxe:listchuyenxe) {
				if(chuyenxe.getXeKhach().getBienSo().equals(xekhach.getBienSo())) {
					day-=(chuyenxe.getTuyenxe().getDoDai()*chuyenxe.getTuyenxe().getDoPhucTap())/100;
				}
			}
			if(day<0) {
				dayam.put(xekhach.getBienSo(), day);
			}else {
				dayduong.put(xekhach.getBienSo(), day);
			}
		}
		model.addAttribute("dayduong", dayduong);
		model.addAttribute("dayam", dayam);
		return "xekhach/baoduongxe";
	}

	@GetMapping("/doanhthuxe")
	public String doanhthu(Model model) {
		return "xekhach/doanhthuxe";
	}

	@PostMapping("/doanhthuxe")
	public String doanhthuxe(Model model, @RequestParam("startdate") String startdate,
			@RequestParam("enddate") String enddate) {
		Map<String, String> map = new HashMap<>();
		map.put("date", startdate);
		map.put("date1", enddate);

		List<ChuyenXe> listchuyenxe = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/chuyenxe/{date}/{date1}", ChuyenXe[].class, map));
		HashMap<String, Double> salary = new HashMap<>();
		for (ChuyenXe chuyenxe : listchuyenxe) {
			if (salary.containsKey(chuyenxe.getXeKhach().getBienSo())) {
				salary.put(chuyenxe.getXeKhach().getBienSo(), salary.get(chuyenxe.getXeKhach().getBienSo())
						+ chuyenxe.getGiaVe() * chuyenxe.getSoLuongKhach());
			} else {
				salary.put(chuyenxe.getXeKhach().getBienSo(), chuyenxe.getGiaVe() * chuyenxe.getSoLuongKhach());
			}
		}
		model.addAttribute("sdate", startdate);
		model.addAttribute("edate", enddate);
		model.addAttribute("salary", salary);
		return "xekhach/doanhthuxe";
	}

	@GetMapping("/edit/{id}")
	public String editdriver(Model model, @PathVariable("id") long id) {
		XeKhach taixe = rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach/{id}", XeKhach.class, id);
		model.addAttribute("xekhach", taixe);
		return "xekhach/editXeKhach";
	}

	@PostMapping("/edit/{id}")
	public String editdriver2(@ModelAttribute XeKhach xekhach, @RequestParam("ngaybaoduong") String ngaysinh, Model model) {
		Date date;
		try {
			date = formatter.parse(ngaysinh);
			xekhach.setNgayBaoDuongCuoi(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(rest.postForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach", xekhach, XeKhach.class)==null) {
			model.addAttribute("err", "xe khach is exists");
			model.addAttribute("xekhach", xekhach);
			return "xekhach/editXeKhach";
		}else {
			return "redirect:/home/xekhach";
		}
	}

	@GetMapping("/add")
	public String adddriver(Model model) {
		model.addAttribute("xekhach", new XeKhach());
		return "xekhach/addXeKhach";
	}

	@PostMapping("/add")
	public String adddriver2(@ModelAttribute XeKhach xekhach, @RequestParam("ngaybaoduong") String ngayBaoDuongCuoi, Model model) {
		Date date;
		try {
			date = formatter.parse(ngayBaoDuongCuoi);
			xekhach.setNgayBaoDuongCuoi(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(rest.postForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach", xekhach, XeKhach.class)==null) {
			model.addAttribute("err", "xe khach is exists");
			model.addAttribute("xekhach", xekhach);
			return "xekhach/addXeKhach";
		}else {
			return "redirect:/home/xekhach";
		}
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteTuyenXe(@PathVariable("id") long id) {
		rest.delete("https://vantaihanhkhach.herokuapp.com/api/xekhach/{id}", id);
		return "redirect:/home/xekhach";
	}
}
