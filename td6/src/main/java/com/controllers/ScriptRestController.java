package com.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Script;
import com.repositories.ScriptRepository;

@RequestMapping("/rest/")
@RestController
public class ScriptRestController {
	
	@Autowired
	private ScriptRepository scriptRepository;
	
	@RequestMapping("{id_user}")
	public ResponseEntity<String> get(@PathVariable("id_user") int userId, HttpServletRequest request) throws JSONException {

		String search = "";
		String searchOptions = "";
		List<String> options;
		
		try {
			search = request.getParameter("search");	
			searchOptions = request.getParameter("searchOptions");
		} catch(NullPointerException e) {
			
		}
		
		options = Arrays.asList(searchOptions.split(","));
		
		
		List<Script> all = scriptRepository.findAll();
		
		List<Script> scripts = new ArrayList<>();
		
		for(Script s : all) {
			if(s.getUser().getId() == userId) {
				
				if(options.contains("title"))
					if(s.getTitle().toLowerCase().contains(search) && !scripts.contains(s))
						scripts.add(s);
				
				if(options.contains("description"))
					if(s.getDescription().toLowerCase().contains(search) && !scripts.contains(s))
						scripts.add(s);
				
				if(options.contains("creationDate"))
					if(s.getCreationDate().toLowerCase().contains(search) && !scripts.contains(s))
						scripts.add(s);
			}
		}
		
		String html = "";
		
		for(Script s : scripts) {
			html += "<tr>";
			
			html += "<td>"+s.getId()+"</td>";
			html += "<td><a href=\"script/"+s.getId()+"\">"+s.getTitle()+"</td>";
			html += "<td>"+s.getDescription()+"</td>";
			html += "<td>"+s.getCreationDate()+"</td>";
			html += "<td><a href=\""+ScriptController.address+"/script/delete/"+s.getId()+"\">\n" + 
					"                            <button class=\"circular red ui icon button\" >\n" + 
					"                                <i class=\"delete icon\"></i>\n" + 
					"                            </button>\n" + 
					"                        </a>\n" + 
					"                    </td>";
			
			html += "</tr>";
		}
		
		return new ResponseEntity<String>(html, HttpStatus.OK);
	}

}
