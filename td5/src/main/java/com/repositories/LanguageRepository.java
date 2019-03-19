package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>{

}
