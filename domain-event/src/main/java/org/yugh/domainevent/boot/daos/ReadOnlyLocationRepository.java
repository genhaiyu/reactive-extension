package org.yugh.domainevent.boot.daos;

import org.springframework.data.repository.Repository;
import org.yugh.domainevent.boot.domain.Location;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ReadOnlyLocationRepository extends Repository<Location, Long> {

    Optional<Location> findById(Long id);

    Location save(Location location);
}
