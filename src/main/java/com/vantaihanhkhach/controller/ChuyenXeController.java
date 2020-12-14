package com.vantaihanhkhach.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.vantaihanhkhach.model.TaiXe;
import com.vantaihanhkhach.model.TuyenXe;
import com.vantaihanhkhach.model.XeKhach;

@Controller
@RequestMapping(value = "/home/chuyenxe")
public class ChuyenXeController {
	private RestTemplate rest = new RestTemplate();
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping()
	public String homedriver(Model model) {
		List<ChuyenXe> listTaiXe = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/chuyenxe", ChuyenXe[].class));
		model.addAttribute("listchuyenxe", listTaiXe);
		return "chuyenxe/listChuyenXe";
	}

	@GetMapping("/edit/{id}")
	public String editdriver(Model model, @PathVariable("id") long id) {
		ChuyenXe taixe = rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/chuyenxe/{id}", ChuyenXe.class, id);
		model.addAttribute("chuyenxe", taixe);
		
		List<TaiXe> listTaiXe = Arrays.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/taixe", TaiXe[].class));
		model.addAttribute("listtaixe", listTaiXe);

		List<TuyenXe> listTuyenXe = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/tuyenxe", TuyenXe[].class));

		model.addAttribute("listtuyenxe", listTuyenXe);

		List<XeKhach> listXeKhach = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach", XeKhach[].class));

		model.addAttribute("listxekhach", listXeKhach);
		return "chuyenxe/editChuyenXe";
		
	}

	@PostMapping("/edit/{id}")
	public String editdriver2(@ModelAttribute ChuyenXe taixe, @RequestParam("ngaychay") String ngaychay,
			@RequestParam("laixe") long laixe, @RequestParam("phuxe") long phuxe, @RequestParam("tuyen") long tuyenxe,
			@RequestParam("xekhach") long xekhach) {
		Date date;
		try {
			date = formatter.parse(ngaychay);
			taixe.setNgayChay(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		taixe.setTaixe(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/taixe/{id}", TaiXe.class, laixe));
		taixe.setTaixe1(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/taixe/{id}", TaiXe.class, phuxe));
		taixe.setTuyenxe(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/tuyenxe/{id}", TuyenXe.class, tuyenxe));
		taixe.setXeKhach(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach/{id}", XeKhach.class, xekhach));
		rest.postForObject("http://localhost:8080/api/chuyenxe", taixe, ChuyenXe.class);
		return "redirect:/home/chuyenxe";
	}

	@GetMapping("/add")
	public String adddriver(Model model) {
		model.addAttribute("chuyenxe", new ChuyenXe());

		List<TaiXe> listTaiXe = Arrays.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/taixe", TaiXe[].class));
		model.addAttribute("listtaixe", listTaiXe);

		List<TuyenXe> listTuyenXe = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/tuyenxe", TuyenXe[].class));

		model.addAttribute("listtuyenxe", listTuyenXe);

		List<XeKhach> listXeKhach = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach", XeKhach[].class));

		model.addAttribute("listxekhach", listXeKhach);
		return "chuyenxe/addChuyenXe";

	}

	@PostMapping("/add")
	public String adddriver2(@ModelAttribute ChuyenXe taixe, @RequestParam("ngaychay") String ngaychay,
			@RequestParam("laixe") long laixe, @RequestParam("phuxe") long phuxe, @RequestParam("tuyen") long tuyenxe,
			@RequestParam("xekhach") long xekhach) {
		Date date;
		try {
			date = formatter.parse(ngaychay);
			taixe.setNgayChay(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		taixe.setTaixe(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/taixe/{id}", TaiXe.class, laixe));
		taixe.setTaixe1(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/taixe/{id}", TaiXe.class, phuxe));
		taixe.setTuyenxe(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/tuyenxe/{id}", TuyenXe.class, tuyenxe));
		taixe.setXeKhach(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/xekhach/{id}", XeKhach.class, xekhach));
		rest.postForObject("http://localhost:8080/api/chuyenxe", taixe, ChuyenXe.class);
		return "redirect:/home/chuyenxe";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteTuyenXe(@PathVariable("id") long id) {
		rest.delete("https://vantaihanhkhach.herokuapp.com/api/chuyenxe/{id}", id);
		return "redirect:/home/chuyenxe";
	}

}
