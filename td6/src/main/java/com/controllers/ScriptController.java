package com.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.entities.Category;
import com.entities.Language;
import com.entities.Script;
import com.entities.User;
import com.repositories.CategoryRepository;
import com.repositories.HistoryRepository;
import com.repositories.LanguageRepository;
import com.repositories.ScriptRepository;
import com.repositories.UserRepository;

@Controller
public class ScriptController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private LanguageRepository languageRepository;
	
	@Autowired
	private ScriptRepository scriptRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private User user;
	
	
	@RequestMapping("/createAll")
	@ResponseBody
	public String createAll() {
		User u = new User();
		u.setLogin("21703883");
		u.setPassword("mdp");
		u.setIdentity("Jérémy HUET");
		u.setEmail("21703883@etu.unicaen.fr");
		userRepository.save(u);
		
		User u2 = new User();
		u2.setLogin("Hazyl14");
		u2.setPassword("mdp");
		u2.setIdentity("Sulian MARIE");
		u2.setEmail("21709776@etu.unicaen.fr");
		userRepository.save(u2);
		
		Category cat = new Category();
		cat.setName("Script SQL");
		categoryRepository.save(cat);
		
		Language sql = new Language();
		sql.setName("SQL");
		
		Language java = new Language();
		java.setName("JAVA");
		languageRepository.save(sql);
		languageRepository.save(java);
		
		return "Creation complete !";
	}
	
	
	
	
	
	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	
	@PostMapping("loginPost")
	public RedirectView loginPost(HttpServletRequest request) {
		
		List<User> users = userRepository.findAll();
	
		User connection = new User();
		connection.setLogin(request.getParameter("login"));
		connection.setPassword(request.getParameter("password"));
				
		for(User u : users) {
			if(u.getLogin().equals(connection.getLogin()) && u.getPassword().equals(connection.getPassword())) {
				user = u;

				List<Script> allScripts = scriptRepository.findAll();
				List<Script> usersScript = new ArrayList<>();
				
				for(Script s : allScripts) {
					if(s.getUser().getId() == user.getId())
					usersScript.add(s);
				}
				user.setScripts(usersScript);
				
				return new RedirectView("index");
			}
		}
		
		return new RedirectView("login");
	}
	
	
	@RequestMapping("/logout")
	public RedirectView logout() {
		user = null;
		return new RedirectView("index");
	}
	
	
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		if(user != null) {
			model.addAttribute("user", user);
			return "index";
		}
		return "login";
	}
	
	
	
	
	
	@RequestMapping("/script/new")
	public String scriptNew(ModelMap model) {
		if(user != null) {
			List<Category> categories = categoryRepository.findAll();
			
			List<Language> languages = languageRepository.findAll();
			
			model.addAttribute("categories", categories);
			model.addAttribute("languages", languages);
			return "script_new";
		}
		return "non_connected";
	}
	
	@PostMapping("/script/submit")
	public RedirectView addScript(Script script) {
		if(user != null) {
			
			Optional<Script> opt = scriptRepository.findById(script.getId());
			if(opt.isPresent()) {
				
				script.setUser(user);
				scriptRepository.save(script);

				List<Script> allScripts = scriptRepository.findAll();
				List<Script> usersScript = new ArrayList<>();
				
				for(Script s : allScripts) {
					if(s.getUser().getId() == user.getId())
					usersScript.add(s);
				}
				user.setScripts(usersScript);
			}
			else {
				script.setUser(user);
				user.getScripts().add(script);
				
				scriptRepository.save(script);
			}
						
			return new RedirectView("../index");
		}
		return new RedirectView("../non_connected");
	}
	
	
	@RequestMapping("/script/{id}")
	@GetMapping
	public String scriptEdit(@PathVariable("id") int id, ModelMap model) {
		
		if(user == null)
			return "non_connected";
		
		Optional<Script> opt = scriptRepository.findById(id);
		
		if(opt.isPresent()) {
			Script s = opt.get();
			if(s.getUser().getId() == user.getId()) {
				model.addAttribute("script", s);
				
				List<Category> categories = categoryRepository.findAll();
				
				List<Language> languages = languageRepository.findAll();
				
				Category selectedCat = s.getCategory();
				categories.remove(selectedCat);
				model.addAttribute("selectedCategory", selectedCat);
				
				Language selectedLanguage = s.getLanguage();
				languages.remove(selectedLanguage);
				model.addAttribute("selectedLanguage", selectedLanguage);
				
				model.addAttribute("categories", categories);
				model.addAttribute("languages", languages);
				
				return "script_edit";
			}
		}
		
		model.addAttribute("user", user);
		return "index";
		
	}

	
	@RequestMapping("non_connected")
	public String nonConnected() {
		return "non_connected";
	}
	
	
	
	
	
	@RequestMapping("search")
	@ResponseBody
	public String search() {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Script>> response = restTemplate.exchange("http://localhost:8080/rest/"+user.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Script>>(){});
 
		List<Script> scripts = response.getBody();
		
		String chaine = "";
		for(Script s : scripts){
			chaine += s.getTitle()+"<br />";
		}
		
		return chaine;
	}

}
