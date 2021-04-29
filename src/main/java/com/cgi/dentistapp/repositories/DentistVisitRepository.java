package com.cgi.dentistapp.repositories;

import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DentistVisitRepository extends JpaRepository<DentistVisitEntity, Long> {

    List<DentistVisitEntity> findByDateTime(String dateTime);

    List<DentistVisitEntity> findByDentist_id(long dentistId);

    List<DentistVisitEntity> findByDentistNameContains(String name);

    List<DentistVisitEntity> findByDateTimeContains(String dateTime);
}
