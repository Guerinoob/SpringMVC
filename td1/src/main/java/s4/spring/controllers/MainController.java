package s4.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import s4.spring.models.Element;

@Controller
@SessionAttributes("elements")
public class MainController {
	
	private List<Element> elements;
	
	@ModelAttribute("elements")
	public List<Element> getElements(){
		if(elements == null) elements = new ArrayList<Element>();
		
		return elements;
	}
	
	@GetMapping("/items")
	public String index(ModelMap model) {
		model.addAttribute("elements", elements);
		return "index";
	}
	
	@GetMapping("new")
	public String new_(ModelMap model) {
		model.addAttribute("elements", elements);

		return "formItem";
	}
	
	@PostMapping("addNew")
	public RedirectView addNew(@RequestParam("nom") String nom, @RequestParam int evaluation) {
		Element el = new Element();
		el.setNom(nom);
		el.setEvaluation(evaluation);
		getElements().add(el);
		return new RedirectView("/items");
	}
	
	@GetMapping("inc/{{nom}}")
	public RedirectView inc(@PathVariable String nom) {
		for(Element e : elements) {
			if(e.getNom().equals(nom)) {
				e.setEvaluation(e.getEvaluation()+1);
				break;
			}
		}
		
		return new RedirectView("items");
	}
	
	@GetMapping("desc/{{nom}}")
	public RedirectView desc(@PathVariable String nom) {
		for(Element e : elements) {
			if(e.getNom().equals(nom)) {
				e.setEvaluation(e.getEvaluation()-1);
				break;
			}
		}
		
		return new RedirectView("items");
	}
	
}
