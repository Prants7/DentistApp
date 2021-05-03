package com.cgi.dentistapp.repositories;

import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for saving dentist visits
 */
@Repository
public interface DentistVisitRepository extends JpaRepository<DentistVisitEntity, Long> {

    /**
     * For finding entities that have specific dateTime
     * @param dateTime searched for dateTime string
     * @return all matching visit entities
     */
    List<DentistVisitEntity> findByDateTime(String dateTime);

    /**
     * For finding all entries that have specific dentist id
     * @param dentistId id of the dentist that the visit is registered to
     * @return list of all found entries
     */
    List<DentistVisitEntity> findByDentist_id(long dentistId);

    /**
     * For doing searches where the string matches part or full dentist name
     * @param name part of full string of a dentist name
     * @return list of visits registered to the searched for dentist
     */
    List<DentistVisitEntity> findByDentistNameContains(String name);

    /**
     * For searching entries with part or full dateTime string
     * @param dateTime searched string
     * @return list of matching entries
     */
    List<DentistVisitEntity> findByDateTimeContains(String dateTime);
}
