package com.mbank.bank.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationToken {
	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	@JoinColumn(name = "customer", referencedColumnName = "id", nullable = false)
	private CustomerEntity user;

	private Date expiryDate;

	public VerificationToken(String token, CustomerEntity user) {
		this.token = token;
		this.user = user;
		this.calculateExpiryDate(60);
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public CustomerEntity getUser() {
		return user;
	}

	public void setUser(CustomerEntity user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
}