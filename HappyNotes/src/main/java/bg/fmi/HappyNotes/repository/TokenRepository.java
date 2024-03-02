package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

  List<Token> findByToken(String token);

  @Query("select t from Token t inner join User u on t.user.id = u.id " +
          "where t.expiredAt IS NOT NULL AND t.user.id = :userId")
  List<Token> findByUserId(Integer userId);

  void deleteAllByExpiredAtIsNotNull();

  List<Token> findByUserIdAndExpiredAtIsNull(Integer userId);
}
