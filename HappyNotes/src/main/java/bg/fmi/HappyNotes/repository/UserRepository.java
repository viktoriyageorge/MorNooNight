package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.Role;
import bg.fmi.HappyNotes.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

  Optional<User> findByUsername(String username);

  List<User> findByRoleIn(List<Role> roles);

}
