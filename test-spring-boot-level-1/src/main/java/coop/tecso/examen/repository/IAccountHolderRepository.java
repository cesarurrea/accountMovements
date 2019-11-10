package coop.tecso.examen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import coop.tecso.examen.model.AccountHolder;

public interface IAccountHolderRepository extends JpaRepository<AccountHolder, Long> {

	@Query("select case when count(c)> 0 then true else false end from AccountHolder c where c.rut = :rut")
	boolean existsRut(@Param("rut") String rut);

	@Query("select case when count(c)> 0 then true else false end from AccountHolder c where c.id != :id and c.rut = :rut")
	boolean rutDuplicado(@Param("id") Long id, @Param("rut") String rut);

	@Query("select c from AccountHolder c where c.rut = :rut")
	AccountHolder findByRut(@Param("rut") String rut);

}
