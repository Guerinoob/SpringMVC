package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Organization;

public interface RepoOrganization extends JpaRepository<Organization, Integer> {

	
	
}
