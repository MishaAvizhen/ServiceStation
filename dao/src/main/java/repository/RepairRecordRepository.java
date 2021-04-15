package repository;

import entity.RepairRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRecordRepository extends JpaRepository<RepairRecord, Long> {
    @Query("SELECT rr FROM RepairRecord rr INNER JOIN rr.repairRequest rreq INNER JOIN rreq.user u WHERE u.username = :username")
    List<RepairRecord> findByUsername(@Param("username") String username);

}

