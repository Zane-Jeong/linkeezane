package com.zane.linkee_platform.user.repository;

import com.zane.linkee_platform.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
