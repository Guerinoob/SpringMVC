package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Script;

public interface ScriptRepository extends JpaRepository<Script, Integer>{
	

}
