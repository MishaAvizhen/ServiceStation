package repository;

import entity.RepairRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("record")
public interface RepairRecordRepository extends JpaRepository<RepairRecord, Long> {
}
