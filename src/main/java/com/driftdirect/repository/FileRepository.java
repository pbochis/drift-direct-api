package com.driftdirect.repository;

import com.driftdirect.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/28/2015.
 */
public interface FileRepository extends JpaRepository<File, Long> {
}
