package com.weshopify.platform.features.customers.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weshopify.platform.features.customers.bean.CustomerBean;
import com.weshopify.platform.features.customers.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/view-customerBoard")
	public String viewCustomerDashBoard(Model model) {
		log.info("i am inn viewCustomerDashBoard page");
		List<CustomerBean> customerList = customerService.getAllCustomers();
		model.addAttribute("currentPage", 1);
		model.addAttribute("customerData", customerList);
		return "customer-dashboard";
	}
	
	@RequestMapping("/view-customerBoard/{currentPage}/{noOfRecPerPage}")
	public String viewCustomerDashBoardWithPagination(@PathVariable("currentPage") String currentPage,@PathVariable("noOfRecPerPage") String noOfRecPerPage,Model model) {
		log.info("i am inn viewCustomerDashBoard page");
		log.info("curent page is:\t"+currentPage);
		log.info("no.Of Rec Per Page is:\t"+noOfRecPerPage);
		
		if(currentPage != null && !currentPage.isEmpty() && !currentPage.equals("null")) {
			int presentPage = Integer.valueOf(currentPage)-1;
			List<CustomerBean> customerList = customerService.getAllCustomers(presentPage,Integer.valueOf(noOfRecPerPage));
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("noOfRecPerPage", noOfRecPerPage);
			if(!CollectionUtils.isEmpty(customerList)) {
				model.addAttribute("customerData", customerList);
				model.addAttribute("nextPage", Integer.valueOf(currentPage)+1);
				if(Integer.valueOf(currentPage) > 1) {
					model.addAttribute("previousPage", Integer.valueOf(currentPage)-1);
				}else {
					model.addAttribute("previousPage", 1);
				}
			}else {
				model.addAttribute("message", "No Further Records Found");
			}
		}else {
			model.addAttribute("previousPage", 1);
			model.addAttribute("message", "No Further Records Found");
		}
		List<Integer> pagesList = new ArrayList<>();
		int totalRecords = customerService.getAllCustomers().size();
		System.out.println("total records from db are:\t"+totalRecords);
		/**
		 * taken float and rounded the value to get the heiest value of the fraction
		 * that means, if the total o.of rec are 5,no.of rec per page is 2
		 * the last record i.e. 5th record will come on 3 rd page, so to take
		 * that 3 rd page , take the round value 
		 */
		double noOfPages = Math.round(Float.valueOf(totalRecords)/Float.valueOf(noOfRecPerPage));
		
		System.out.println("noOfPages after calculation are:\t"+noOfPages);
		if(noOfPages == 0) {
			noOfPages = 1;
		}
		for(int i=1;i<=noOfPages;i++) {
			pagesList.add(i);
		}
		model.addAttribute("totalNoOfRecords", pagesList);
		
		return "customer-dashboard-pagination";
	}
	
	@RequestMapping("/add-customer-view")
	public String addCustomerViewPage(Model model) {
		log.info("i am inn addCustomerViewPage page");
		model.addAttribute("customerFormBean", new CustomerBean());
		return "add-customer";
	}
	
	@RequestMapping(value = "/save-customer",method = RequestMethod.POST)
	public String addCustomer(@ModelAttribute("customerFormBean") CustomerBean customerBean, Model model) {
		log.info("i am inn addCustomerViewPage page");
		log.info(customerBean.toString());
		
		if(customerBean.getIsSelfReg() != null && Boolean.valueOf(customerBean.getIsSelfReg())) {
			customerService.saveCustomer(customerBean);
			if(customerBean != null && customerBean.getId() >0) {
				
				String isReg="true";
				model.addAttribute("regMessage", isReg);
				
				return "customer-sefReg";
				
			}
		}else {
			customerService.saveCustomer(customerBean);
			
		}
		return "redirect:/view-customerBoard";
	}
	
	@RequestMapping(value = "/delete-customer/{customerId}",method = RequestMethod.GET)
	public String deleteCustomer(@PathVariable("customerId") String customerId, Model model) {
		log.info("i am inn deleteCustomerPage:\t"+customerId);
		
		customerService.deleteCustomerById(Integer.valueOf(customerId));
		return "redirect:/view-customerBoard";
	}
	
	@RequestMapping(value = "/edit-customer/{customerId}",method = RequestMethod.GET)
	public String editCustomer(@PathVariable("customerId") String customerId, Model model) {
		log.info("i am inn deleteCustomerPage:\t"+customerId);
		
		CustomerBean customerBean = customerService.getCustomerById(Integer.valueOf(customerId));
		model.addAttribute("customerFormBean", customerBean);
		return "edit-customer";
	}
	
	public static void main(String[] args) {
		int noOfPages = 7/2;
		
		System.out.println(noOfPages);
	}
	
}
