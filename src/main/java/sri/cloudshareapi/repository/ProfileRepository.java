package sri.cloudshareapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sri.cloudshareapi.document.ProfileDocument;

import java.util.Optional;
@Repository
public interface ProfileRepository extends MongoRepository<ProfileDocument,String> {

    Optional<ProfileDocument> findByEmail(String email);

    ProfileDocument findByClerkId(String clerkId);

    Boolean existsByClerkId(String clerkId);
}
