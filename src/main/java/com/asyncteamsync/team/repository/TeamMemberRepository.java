package com.asyncteamsync.team.repository;

import com.asyncteamsync.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository
        extends JpaRepository<TeamMember, Long> {
}
