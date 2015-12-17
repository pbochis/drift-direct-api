package com.driftdirect.repository.championship.judge;

import com.driftdirect.domain.championship.judge.PointsAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 12/17/2015.
 */
public interface PointsAllocationRepository extends JpaRepository<PointsAllocation, Long> {
}
