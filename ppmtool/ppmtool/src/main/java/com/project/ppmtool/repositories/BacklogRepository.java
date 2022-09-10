package com.project.ppmtool.repositories;

import com.project.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog,Long> {

}
