package com.aarthy.myapp.repository;

import com.aarthy.myapp.domain.Participant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Participant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {}
