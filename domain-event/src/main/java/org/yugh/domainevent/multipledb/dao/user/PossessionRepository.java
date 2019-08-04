package org.yugh.domainevent.multipledb.dao.user;

import com.baeldung.multipledb.model.user.PossessionMultipleDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossessionRepository extends JpaRepository<PossessionMultipleDB, Long> {

}
