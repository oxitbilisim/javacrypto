package com.svn.app.core.repository;

import com.svn.app.core.entity.LanguageKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LanguageKeysRepository extends JpaRepository<LanguageKeys, Integer> {

    List<LanguageKeys> findAllByLanguageCode(@Param("languageCode") String languageCode);

    @Query("SELECT u FROM LanguageKeys u where u.parentCode is null and u.languageCode = :languageCode")
    List<LanguageKeys> findRoots(@Param("languageCode") String languageCode);

    @Query("SELECT u FROM LanguageKeys u where u.parentCode= :parentCode and u.languageCode = :languageCode")
    List<LanguageKeys> findByParent(@Param("languageCode") String languageCode, @Param("parentCode") Integer parentCode);

    List<LanguageKeys> findByParentCodeIsNullAndLanguageCodeOrderByKeyCodeAsc(@Param("languageCode") String languageCode);

    List<LanguageKeys> findByParentCodeOrderByKeyCodeAsc(@Param("parentCode") Integer parentCode);

    @Transactional
    @Modifying
    @Query(value = "call add_language(:languageCode,:key,:defaultText)", nativeQuery = true)
    void saveFullPath(@Param("languageCode") String languageCode, @Param("key") String key, @Param("defaultText") String defaultText);
}

