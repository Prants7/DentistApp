package com.cgi.dentistapp.repositories;

import com.cgi.dentistapp.entity.DentistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Dentists
 */
@Repository
public interface DentistRepository extends JpaRepository<DentistEntity, Long> {

    /**
     * For finding dentists by name
     * @param name name of the dentists
     * @return all entries that have input name
     */
    List<DentistEntity> findByName(String name);
}
