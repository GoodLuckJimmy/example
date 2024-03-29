package com.example.srprs.admin.admin.repository;

import com.example.srprs.admin.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findById(String id);
}
