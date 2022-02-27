package com.svn.app.wlt.repository;

import com.svn.app.wlt.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter,Long> {

    @Query("SELECT p FROM Parameter p WHERE p.category = 'APIURL' AND p.code = :exchangeCode")
    List<Parameter> getApiUrls(@Param("exchangeCode") String exchangeCode);

    @Query("SELECT p FROM Parameter p WHERE p.category = 'APIURI' AND p.code = :api")
    Optional<Parameter> getApiUri(@Param("api") String api);
}
