package sri.cloudshareapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sri.cloudshareapi.document.FileMetadataDocument;

import java.util.List;
@Repository
public interface FileMetadataRepository extends MongoRepository<FileMetadataDocument, String> {

    List<FileMetadataDocument> findByClerkId(String clerkId);

    Long countByClerkId(String clerkId);
}
