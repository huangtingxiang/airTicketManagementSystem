package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.enumeration.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Page<User> findAllByRole(Role role, Pageable pageable);

    User findAllByUserName(String userName);

    Page<User> findAllByUserNameLike(String userName, Pageable pageable);

}
