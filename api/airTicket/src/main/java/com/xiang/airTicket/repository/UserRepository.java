package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.User;
import com.xiang.airTicket.enumeration.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findAllByUserNameAndRole(String userName, Role role);

    User findAllByUserName(String userName);

}
