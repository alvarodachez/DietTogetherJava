package com.jacaranda.security.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.jacaranda.model.DietReport;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jacaranda.model.DietAthlete;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class DietUser implements UserDetails, Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	@OneToOne
	@JoinColumn(name="athlete_id")
	private DietAthlete athleteId;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<DietRole> roles;

	@CreatedDate
	private LocalDateTime createTime;

	@UpdateTimestamp
	private LocalDateTime updateTime;

	private LocalDateTime deleteTime;

	private LocalDateTime lastPasswordChange;

	private boolean locked;

	private boolean enabled;

	private Integer authenticationAttempts;

	private LocalDateTime passwordPolicyExpDate;

	private List<DietReport> reports;

	private static final long serialVersionUID = 2046866248113544418L;

	private static final int MAX_AUTH_ATTEMPTS = 3;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.name())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.getDeleteTime() == null;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.getAuthenticationAttempts() < MAX_AUTH_ATTEMPTS;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.getLastPasswordChange().isBefore(this.passwordPolicyExpDate);
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public Long getId() {
		return id;
	}

	/**
	 * @return the athleteId
	 */
	public DietAthlete getAthleteId() {
		return athleteId;
	}

	/**
	 * @param athleteId the athleteId to set
	 */
	public void setAthleteId(DietAthlete athleteId) {
		this.athleteId = athleteId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<DietRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<DietRole> roles) {
		this.roles = roles;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public LocalDateTime getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(LocalDateTime deleteTime) {
		this.deleteTime = deleteTime;
	}

	public LocalDateTime getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Integer getAuthenticationAttempts() {
		return authenticationAttempts;
	}

	public void setAuthenticationAttempts(Integer authenticationAttempts) {
		this.authenticationAttempts = authenticationAttempts;
	}

	public LocalDateTime getPasswordPolicyExpDate() {
		return passwordPolicyExpDate;
	}

	public void setPasswordPolicyExpDate(LocalDateTime passwordPolicyExpDate) {
		this.passwordPolicyExpDate = passwordPolicyExpDate;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany
	@JoinColumn(name = "report_id")
	@ElementCollection
	public List<DietReport> getReports() {
		return reports;
	}

	public void setReports(List<DietReport> reports) {
		this.reports = reports;
	}
}
