package coop.tecso.examen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import coop.tecso.examen.model.AccountMovement;

public interface IAccountMovementRepository extends JpaRepository<AccountMovement, Long> {

	@Query("select case when count(c)> 0 then true else false end from AccountMovement c where c.accountNumber = :accountNumber")
	boolean existsAccountMovements(@Param("accountNumber") String accountNumber);

	@Query("select c from AccountMovement c where c.accountNumber = :accountNumber order by c.movementDate desc")
	List<AccountMovement> movementsPerAccount(@Param("accountNumber") String accountNumber);

}
