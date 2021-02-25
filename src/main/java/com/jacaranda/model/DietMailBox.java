package com.jacaranda.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class DietMailBox implements Serializable{

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private List<DietFriendRequest> friendRequests;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "mailBox_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the friendRequests
	 */
	@OneToMany
	@JoinColumn(name = "friend_id")
	public List<DietFriendRequest> getFriendRequests() {
		return friendRequests;
	}

	/**
	 * @param friendRequests the friendRequests to set
	 */
	public void setFriendRequests(List<DietFriendRequest> friendRequests) {
		this.friendRequests = friendRequests;
	}

}
