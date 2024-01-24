package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.InspirationalQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspirationalQuoteRepository extends JpaRepository<InspirationalQuote, Integer> {

}
