package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entities.Script;

public interface ScriptRepository extends JpaRepository<Script, Integer>{
	
	@Query("SELECT id, title, description, content, creationDate, user FROM Script")
	List<Script> getScripts();

}
