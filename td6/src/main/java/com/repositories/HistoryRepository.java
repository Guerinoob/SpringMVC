package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {

}
