package com.mbank.bank;

import com.mbank.bank.domain.CustomerEntity;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private CustomerEntity customerEntity;
	private String appUrl;

	public OnRegistrationCompleteEvent(CustomerEntity customerEntity, String appUrl) {
		super(customerEntity);
		this.customerEntity = customerEntity;
		this.appUrl = appUrl;
	}

	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}

	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
}
