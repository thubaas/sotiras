package com.thubas.sotiras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.sotiras.model.Checkout;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
	
	List<Checkout> findByBook_Contributor_Id(Long id);

}
