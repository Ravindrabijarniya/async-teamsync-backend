package com.asyncteamsync.standup.entity;

import com.asyncteamsync.common.BaseEntity;
import com.asyncteamsync.team.entity.Team;
import com.asyncteamsync.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "standups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Standup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String yesterdayWork;

    private String todayPlan;

    private String blockers;

    @Enumerated(EnumType.STRING)
    private StandupStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
