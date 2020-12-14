package com.vantaihanhkhach.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

@Controller
@RequestMapping(value = "/home/taixe")
public class TaiXeController {
	private RestTemplate rest = new RestTemplate();
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping()
	public String homedriver(Model model) {
		List<TaiXe> listTaiXe = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/taixe", TaiXe[].class));
		model.addAttribute("listtaixe", listTaiXe);
		return "taixe/homedriver";
	}

	@GetMapping("/luongtaixe")
	public String salary(Model model) {
		List<ChuyenXe> listChuyenXe = Arrays
				.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/chuyenxe/get", ChuyenXe[].class));
		HashMap<String, Double> salary = new HashMap<>();
		for (ChuyenXe chuyenxe : listChuyenXe) {
			if (salary.containsKey(chuyenxe.getTaixe().getTen())) {
				salary.put(chuyenxe.getTaixe().getTen(),
						salary.get(chuyenxe.getTaixe().getTen()) + chuyenxe.getTuyenxe().getDoPhucTap() * 200000.0);
			} else {
				salary.put(chuyenxe.getTaixe().getTen(), chuyenxe.getTuyenxe().getDoPhucTap() * 200000.0);
			}
		}
		for (ChuyenXe chuyenxe : listChuyenXe) {
			if (salary.containsKey(chuyenxe.getTaixe1().getTen())) {
				salary.put(chuyenxe.getTaixe1().getTen(),
						salary.get(chuyenxe.getTaixe1().getTen()) + chuyenxe.getTuyenxe().getDoPhucTap() * 100000.0);
			} else {
				salary.put(chuyenxe.getTaixe1().getTen(), chuyenxe.getTuyenxe().getDoPhucTap() * 100000.0);
			}
		}
		model.addAttribute("salary", salary);
		return "taixe/salary";
	}

	@GetMapping("/edit/{id}")
	public String editdriver(Model model, @PathVariable("id") long id) {
		TaiXe taixe = rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/taixe/{id}", TaiXe.class, id);
		model.addAttribute("taixe", taixe);
		return "taixe/editTaiXe";
	}

	@PostMapping("/edit/{id}")
	public String editdriver2(@ModelAttribute TaiXe taixe, @RequestParam("ngaysinh") String ngaysinh, Model model) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = formatter.parse(ngaysinh);
			taixe.setNgaySinh(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (rest.postForObject("https://vantaihanhkhach.herokuapp.com/api/taixe", taixe, TaiXe.class) == null) {
			model.addAttribute("err", "nv is exists");
			// TaiXe taixe = rest.getForObject("http://localhost:8080/api/taixe/{id}",
			// TaiXe.class, taixe.getId());
			model.addAttribute("taixe", taixe);
			return "taixe/editTaiXe";
		} else {
			return "redirect:/home/taixe";
		}

	}

	@GetMapping("/add")
	public String adddriver(Model model) {
		model.addAttribute("taixe", new TaiXe());
		return "taixe/addTaiXe";
	}

	@PostMapping("/add")
	public String adddriver2(@ModelAttribute TaiXe taixe, @RequestParam("ngaysinh") String ngaysinh, Model model) {
		Date date;
		try {
			date = formatter.parse(ngaysinh);
			taixe.setNgaySinh(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rest.postForObject("https://vantaihanhkhach.herokuapp.com/api/taixe", taixe, TaiXe.class) == null) {
			model.addAttribute("err", "nv is exists");
			// TaiXe taixe = rest.getForObject("http://localhost:8080/api/taixe/{id}",
			// TaiXe.class, taixe.getId());
			model.addAttribute("taixe", taixe);
			return "taixe/addTaiXe";
		} else {
			return "redirect:/home/taixe";
		}
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteTuyenXe(@PathVariable("id") long id) {
		rest.delete("https://vantaihanhkhach.herokuapp.com/api/taixe/{id}", id);
		return "redirect:/home/taixe";
	}

}
