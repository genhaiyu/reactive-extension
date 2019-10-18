package org.yugh.coral.event.boot.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yugh.coral.event.boot.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
