package com.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.entities.Groupe;
import com.entities.Organization;
import com.entities.User;
import com.repositories.RepoGroupe;
import com.repositories.RepoOrganization;
import com.repositories.RepoUser;

@Controller
@RequestMapping("/orgas/")
public class OrgasController {
	
	@Autowired
	private RepoOrganization repoOrga;
	
	@Autowired
	private RepoGroupe repoGroupe;
	
	@Autowired
	private RepoUser repoUser;
	
	
	@RequestMapping("groupes")
	@ResponseBody
	public String createGroupe() {
		
		Groupe groupe = new Groupe();
		groupe.setName("TD 1000");
		
		repoGroupe.save(groupe);
		
		groupe = new Groupe();
		groupe.setName("???");
		
		repoGroupe.save(groupe);
		
		return "Groupes créés <a href=\"index\">Retour</a>";
	}
	
	@RequestMapping("users")
	@ResponseBody
	public String createUser() {
		
		
		
		List<Organization> orgas = repoOrga.findAll();
		
		for(Organization o : orgas) {
			User user = new User();
			user.setFirstName("Romain");
			user.setLastName("OZDEN");
			o.addUser(user);
			
			user = new User();
			user.setFirstName("Jérémy");
			user.setLastName("HUET");
			o.addUser(user);
			
			repoOrga.save(o);
		}
		
		return "Users créés <a href=\"index\">Retour</a>";

	}
	
	@RequestMapping("create/{id}")
	@ResponseBody
	public String createOrgaGroupe(@PathVariable int id) {
				
		Optional<Organization> orgaOpt = repoOrga.findById(id);
		
		if(orgaOpt.isPresent()) {
			Organization orga = orgaOpt.get();
			
			Groupe gr = new Groupe();
			gr.setName("TD1");
			orga.addGroupe(gr);
			
			repoOrga.save(orga);
			
			return "Oui";
		}
		else {
			return "Non";
		}
		
	}
	
	
	
	
	
	
	
	@GetMapping({"", "index"})
	public String index(ModelMap model) {
		
		List<Organization> orgas = repoOrga.findAll();
		
		model.addAttribute("orgas", orgas);
		
		return "index";
	}
	
	@GetMapping("delete/{id}")
	public String deleteId(ModelMap model, @PathVariable int id) {
		
		List<Organization> orgas = repoOrga.findAll();
		
		model.addAttribute("orgas", orgas);
		
		Optional<Organization> opt = repoOrga.findById(id);
		
		if(opt.isPresent()) {
			Organization orgaSuppr = opt.get();
			model.addAttribute("orgaSuppr", orgaSuppr);
			return "delete";
		}
		else
			return "index";
	}
	
	@GetMapping("delete:{id}")
	public RedirectView delete(@PathVariable int id) {
		
		
		Optional<Organization> opt = repoOrga.findById(id);
		
		if(opt.isPresent()) {
			Organization orgaSuppr = opt.get();
			repoOrga.delete(orgaSuppr);
		}

		return new RedirectView("index");
	}
	
	
	@GetMapping("new")
	public String new_(ModelMap model) {
				
		return "new";
	}
	
	
	@GetMapping("edit/{id}")
	public String editId(@PathVariable int id, ModelMap model) {
		Optional<Organization> opt = repoOrga.findById(id);
		
		if(opt.isPresent()) {
			Organization orga = opt.get();
			model.addAttribute("orga", orga);
			
			return "edit";
		}
		else {
			return "index";
		}
	}
	
	
	
	@GetMapping("display/{id}")
	public String displayId(@PathVariable int id, ModelMap model) {
		Optional<Organization> opt = repoOrga.findById(id);
		
		if(opt.isPresent()) {
			Organization orga = opt.get();
			model.addAttribute("orga", orga);
			
			return "display";
		}
		else {
			return "index";
		}
	}
	
	
	@PostMapping("createOrga")
	public RedirectView createOrga(Organization orga) {
		
		Groupe groupe = new Groupe();
		groupe.setName("TD 1000");
		
		repoGroupe.save(groupe);
		
		groupe = new Groupe();
		groupe.setName("???");
		
		repoGroupe.save(groupe);

		User user = new User();
		user.setFirstName("Romain");
		user.setLastName("OZDEN");
		orga.addUser(user);
		
		user = new User();
		user.setFirstName("Jérémy");
		user.setLastName("HUET");
		orga.addUser(user);
		
		
		repoOrga.save(orga);
				
		List<Groupe> groupes = repoGroupe.findAll();
		for(Groupe g : groupes) {
			orga.addGroupe(g);
			repoOrga.save(orga);
		}


		
		return new RedirectView("index");
	}
	
	@PostMapping("editOrga")
	public RedirectView editOrga(Organization orga) {
			
		repoOrga.save(orga);
		
		return new RedirectView("index");
	}
	

}
