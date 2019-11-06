package coop.tecso.examen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import coop.tecso.examen.model.Account;

public interface IAccountRepository extends JpaRepository<Account, Long> {

	@Query("select case when count(c)> 0 then true else false end from Account c where c.accountNumber = :accountNumber")
	boolean existsAccount(@Param("accountNumber") String accountNumber);

	@Query("select c from Account c where c.accountNumber = :accountNumber")
	Account findByAccountNumber(@Param("accountNumber") String accountNumber);

}
