package com.example.clientapp.services;

import com.example.clientapp.dao.UserDAO;
import com.example.clientapp.domain.Rol;
import com.example.clientapp.domain.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        for (Rol rol : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getName()));
        }

        return new User(user.getUsername(), user.getPassword(), roles);
    }

    @Transactional
    public void save(UserApp user) {
        userDao.save(user);
    }
}
