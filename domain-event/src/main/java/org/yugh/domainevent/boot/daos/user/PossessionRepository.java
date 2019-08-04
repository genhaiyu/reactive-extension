package org.yugh.domainevent.boot.daos.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yugh.domainevent.boot.domain.Possession;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

}
