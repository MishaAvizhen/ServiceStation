package repository;

import entity.RepairRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("boom")
public interface RepairRequestRepository extends JpaRepository<RepairRequest, Long> {
}
