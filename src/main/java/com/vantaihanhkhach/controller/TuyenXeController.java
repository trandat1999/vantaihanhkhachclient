package com.vantaihanhkhach.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.vantaihanhkhach.model.TuyenXe;

@Controller
@RequestMapping(value = "/home/tuyenxe")
public class TuyenXeController {
	private RestTemplate rest = new RestTemplate();
	
	@GetMapping()
	public String homedriver(Model model) {
		List<TuyenXe> listTaiXe = Arrays.asList(rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/tuyenxe", TuyenXe[].class));
		model.addAttribute("listtaixe", listTaiXe);
		return "tuyenxe/listTuyenXe";
	}

	@GetMapping("/edit/{id}")
	public String editdriver(Model model, @PathVariable("id") long id) {
		TuyenXe tuyenxe = rest.getForObject("https://vantaihanhkhach.herokuapp.com/api/tuyenxe/{id}", TuyenXe.class, id);
		model.addAttribute("xekhach", tuyenxe);
		return "tuyenxe/editTuyenXe";
	}

	@PostMapping("/edit/{id}")
	public String editdriver2(@ModelAttribute TuyenXe xekhach) {
		rest.postForObject("https://vantaihanhkhach.herokuapp.com/api/tuyenxe", xekhach, TuyenXe.class);
		return "redirect:/home/tuyenxe";
	}

	@GetMapping("/add")
	public String adddriver(Model model) {
		model.addAttribute("xekhach", new TuyenXe());
		return "tuyenxe/addTuyenXe";
	}

	@PostMapping("/add")
	public String adddriver2(@ModelAttribute TuyenXe xekhach) {
		rest.postForObject("https://vantaihanhkhach.herokuapp.com/api/tuyenxe", xekhach, TuyenXe.class);
		return "redirect:/home/tuyenxe";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteTuyenXe(@PathVariable("id") long id) {
		rest.delete("https://vantaihanhkhach.herokuapp.com/api/tuyenxe/{id}", id);
		return "redirect:/home/tuyenxe";
	}
}