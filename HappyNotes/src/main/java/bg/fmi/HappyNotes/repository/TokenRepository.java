package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

  Optional<Token> findByToken(String token);

  /// TODO: LIST
  @Query("select t from Token t inner join User u on t.user.id = u.id where t.expiredAt IS NOT NULL AND t.user.id = :userId")
  Optional<Token> findByUserId(Integer userId);

  Integer deleteAllByExpiredAtIsNotNull();

  Optional<Token> findByUserIdAndExpiredAtIsNull(Integer userId);
}
