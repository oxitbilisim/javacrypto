package com.svn.app.core.repository;

import com.svn.app.core.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {

    public PasswordResetToken findByToken(String token);

}
