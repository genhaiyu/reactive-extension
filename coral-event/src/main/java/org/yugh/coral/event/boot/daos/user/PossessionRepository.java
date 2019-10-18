package org.yugh.coral.event.boot.daos.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yugh.coral.event.boot.domain.Possession;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

}
