package org.cedacri.batch.vaadintutorial.core.models.repo;

import org.cedacri.batch.vaadintutorial.core.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "select * from USERS u where u.login = :login")
    User findByLogin(@Param("login") String login);
}
