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
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("create")
	@ResponseBody
	public String createOrga() {
		/*repoOrga.deleteAll();
		repoGroupe.deleteAll();
		repoUser.deleteAll();*/
		
		
		
		Organization orga = new Organization();
		orga.setName("IUT Ifs");
		orga.setDomain("unicaen.fr");
		orga.setAliases("iutc3.unicaen.fr");
		
			
		
		
		repoOrga.save(orga);
						
		return orga+" ajoutée dans la base";
	}
	
	@RequestMapping("groupes")
	@ResponseBody
	public String createGroupe() {
		
		Groupe groupe = new Groupe();
		groupe.setName("TD 1000");
		groupe.setOrganization(repoOrga.findById(22).get());
		
		repoGroupe.save(groupe);
		
		return "Groupe créé";
	}
	
	@RequestMapping("users")
	@ResponseBody
	public String createUser() {
		
		User user = new User();
		user.setFirstName("Romain");
		user.setLastName("OZDEN");
		
		Optional<Groupe> groupeOpt = repoGroupe.findById(40);
		
		if(groupeOpt.isPresent()) {
			Groupe groupe = groupeOpt.get();
			groupe.addUser(user);
	
			repoGroupe.save(groupe);
			
			return "User créé";
		}
		else {
			return "Ça marche pas";
		}
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
	
	
	
	
	
	
	
	@GetMapping("index")
	public String index(ModelMap model) {
		
		List<Organization> orgas = repoOrga.findAll();
		
		model.addAttribute("orgas", orgas);
		
		return "index";
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
	

}
