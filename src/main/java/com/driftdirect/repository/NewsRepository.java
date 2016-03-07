package com.driftdirect.repository;

import com.driftdirect.domain.news.ImageLink;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 12/6/2015.
 */
public interface NewsRepository extends JpaRepository<ImageLink, Long> {
}
