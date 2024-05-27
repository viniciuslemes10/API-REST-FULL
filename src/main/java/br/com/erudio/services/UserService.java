package br.com.erudio.services;

import br.com.erudio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {
    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        logger.info("Finding one user by name " + username);
        var user = repository.findByUsername(username);
        if(user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }
}
