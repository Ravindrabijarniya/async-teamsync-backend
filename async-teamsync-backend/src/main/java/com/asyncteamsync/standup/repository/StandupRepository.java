package com.asyncteamsync.standup.repository;

import com.asyncteamsync.standup.entity.Standup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandupRepository
        extends JpaRepository<Standup, Long> {
}
