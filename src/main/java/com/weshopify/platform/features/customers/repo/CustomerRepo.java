package com.weshopify.platform.features.customers.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.weshopify.platform.features.customers.domain.Customer;


/*public interface CustomerRepo extends CrudRepository<Customer, Integer>*/ 
public interface CustomerRepo extends PagingAndSortingRepository<Customer, Integer>{

	
}
