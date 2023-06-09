package com.messenger.authandprofile.infra.auth.persistence;

import com.messenger.authandprofile.infra.auth.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
    List<RefreshTokenEntity> findAllByUserIdAndInvalid(UUID userId, Boolean isInvalid);

//    @Query(value = "SELECT RefreshTokenEntity.token FROM RefreshTokenEntity WHERE RefreshTokenEntity.token = :id AND RefreshTokenEntity.isInvalid = :invalid LIMIT 1")
    boolean existsByTokenAndInvalid(String id, boolean invalid);
}
