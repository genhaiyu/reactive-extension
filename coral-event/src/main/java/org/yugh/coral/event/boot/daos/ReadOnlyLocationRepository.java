package org.yugh.coral.event.boot.daos;

import org.springframework.data.repository.Repository;
import org.yugh.coral.event.boot.domain.Location;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ReadOnlyLocationRepository extends Repository<Location, Long> {

    Optional<Location> findById(Long id);

    Location save(Location location);
}
