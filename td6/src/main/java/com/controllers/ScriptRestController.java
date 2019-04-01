package com.controllers;

import java.util.ArrayList;
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
		
		try {
			search = request.getParameter("search");		
		} catch(NullPointerException e) {
			
		}
		
		
		List<Script> all = scriptRepository.findAll();
		
		List<Script> scripts = new ArrayList<>();
		
		for(Script s : all) {
			if(s.getUser().getId() == userId) {
				
				if(s.getTitle().contains(search))
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
			
			html += "</tr>";
		}
		
		return new ResponseEntity<String>(html, HttpStatus.OK);
	}

}
