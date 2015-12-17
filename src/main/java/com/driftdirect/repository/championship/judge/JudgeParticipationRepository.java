package com.driftdirect.repository.championship.judge;

import com.driftdirect.domain.championship.judge.JudgeParticipation;
import com.driftdirect.domain.championship.judge.JudgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Paul on 12/1/2015.
 */
public interface JudgeParticipationRepository extends JpaRepository<JudgeParticipation, Long> {
    @Query("Select jp FROM JudgeParticipation jp where jp.judgeType=:judgeType")
    public JudgeParticipation findByType(@Param("judgeType") JudgeType judgeType);
}
