package sri.cloudshareapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sri.cloudshareapi.document.UserCredits;

import java.util.Optional;
@Repository
public interface UserCreditsRepository extends MongoRepository<UserCredits, String> {
    Optional<UserCredits> findByClerkId(String clerkId);
}
