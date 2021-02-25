package com.jacaranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietFriendRequest;

@Repository
public interface DietFriendRequestRepository extends JpaRepository<DietFriendRequest, Long>{

}
