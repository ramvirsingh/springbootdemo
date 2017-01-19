/**
 *
 */
package com.springbootdemo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.springbootdemo.dao.IUserDao;
import com.springbootdemo.model.User;
import com.springbootdemo.service.IUserService;
import com.springbootdemo.web.dto.UserDTO;

/**
 * @author RAM
 *
 */
@Service
public class UserServiceImpl implements IUserService {

    // private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private IUserDao userDao;

    /**
     * @param userDao the userDao to set
     */
    @Autowired
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userDao.findOne(id);
        return new UserDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userDao.findAll();
        List<UserDTO> userDtos = users.stream().map(this::convert).collect(Collectors.toList());
        return Lists.newArrayList(userDtos);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);

    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();

    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = new User(userDTO);
        userDao.save(user);
        userDTO.setId(user.getId());
        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = userDao.findOne(userDTO.getId());
        if (user != null) {
            user.setDepartment(userDTO.getDepartment());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPassword(userDTO.getPassword());
            userDao.save(user);
        }
        return userDTO;
    }

    private final UserDTO convert(final User user) {
        return new UserDTO(user);
    }
}