package com.aarthy.myapp.repository;

import com.aarthy.myapp.domain.Contest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Contest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {}
