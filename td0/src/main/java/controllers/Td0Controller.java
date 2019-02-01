package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import models.Element;

@Controller
@SessionAttributes("elements")
public class Td0Controller {
	
	@ModelAttribute("elements")
	public List<Element> getElements(){
		List<Element> elements = new ArrayList<Element>();
		
		return elements;
	}
	
	@RequestMapping("/")
	public String index(@ModelAttribute("elements") List<Element> elements) {
		
		Element el = new Element();
		el.setNom("FDP");
		elements.add(el);
		
		return "index";
	}

}
