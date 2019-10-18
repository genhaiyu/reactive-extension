package org.yugh.coral.event.multipledb.dao.user;

import com.baeldung.multipledb.model.user.UserMultipleDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserMultipleDB, Integer> {
}