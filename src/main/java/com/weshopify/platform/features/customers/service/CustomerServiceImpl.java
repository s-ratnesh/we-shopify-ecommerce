package com.weshopify.platform.features.customers.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.weshopify.platform.features.customers.bean.CustomerBean;
import com.weshopify.platform.features.customers.domain.Customer;
import com.weshopify.platform.features.customers.repo.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;
	
	@Override
	public CustomerBean saveCustomer(CustomerBean customerBean) {
		Customer customerDomain = new Customer();
		/**
		 * Convert the bean to domain as per the repository 
		 * design, it will only accesspts the domains which are 
		 * etities.
		 */
		BeanUtils.copyProperties(customerBean, customerDomain);
		customerRepo.save(customerDomain);
		BeanUtils.copyProperties(customerDomain, customerBean);
		
		return customerBean;
	}

	@Override
	public CustomerBean updateCustomer(CustomerBean customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerBean> getAllCustomers() {
		
		Iterator<Customer> customerIt = customerRepo.findAll().iterator();
		List<CustomerBean> custBeanList = new ArrayList<>();
		while(customerIt.hasNext()) {
			Customer customer = customerIt.next();
			CustomerBean custBean = new CustomerBean();
			BeanUtils.copyProperties(customer, custBean);
			custBeanList.add(custBean);
		}
		return custBeanList;
	}

	@Override
	public void deleteCustomerById(int id) {
		customerRepo.deleteById(id);
	}

	@Override
	public List<CustomerBean> deleteCustomer(CustomerBean customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerBean getCustomerById(int id) {
		Customer customerDomain = customerRepo.findById(id).get();
		CustomerBean custBean = new CustomerBean();
		BeanUtils.copyProperties(customerDomain, custBean);
		return custBean;
	}

	@Override
	public List<CustomerBean> getAllCustomers(int curret_page,int noOfRecPerPage) {
		PageRequest pageReq = PageRequest.of(curret_page, noOfRecPerPage);
		Iterator<Customer> customerIt = customerRepo.findAll(pageReq).iterator();
		List<CustomerBean> custBeanList = new ArrayList<>();
		while(customerIt.hasNext()) {
			Customer customer = customerIt.next();
			CustomerBean custBean = new CustomerBean();
			BeanUtils.copyProperties(customer, custBean);
			custBeanList.add(custBean);
		}
		return custBeanList;
	}

}
