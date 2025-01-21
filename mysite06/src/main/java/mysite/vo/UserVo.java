package mysite.vo;


import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;

public class UserVo {
	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Length(min=4,max=8)
	private String password;
	
	@NotEmpty
	private String gender;
	
	private String joinDate;
	private String role;
	
	@NotEmpty
	private String agreeProv;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
	public String getAgreeProv() {
		return agreeProv;
	}
	public void setAgreeProv(String agreeProv) {
		this.agreeProv = agreeProv;
	}
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + ", joinDate=" + joinDate + ", role=" + role + "]";
	}
}
