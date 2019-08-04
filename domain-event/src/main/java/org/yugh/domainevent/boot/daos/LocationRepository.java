package org.yugh.domainevent.boot.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yugh.domainevent.boot.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
