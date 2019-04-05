package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.entities.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {
	
	@Transactional
	@Modifying
	@Query("DELETE FROM History WHERE script_id = :scriptId")
	void deleteAllByScriptId(@Param("scriptId") int scriptId);

}
