package br.com.erudio.services;

import br.com.erudio.data.vo.v1.AccountCredentialsVO;
import br.com.erudio.data.vo.v1.TokenVO;
import br.com.erudio.model.User;
import br.com.erudio.repositories.UserRepository;
import br.com.erudio.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthServices {
    private Logger logger = Logger.getLogger(AuthServices.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO data) {
        try {
            logger.info("Authenticating user: " + data.getUsername());

            var username = data.getUsername();
            var password = data.getPassword();

            // Busca pelo usuário no banco de dados
            User user = userRepository.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            // Autentica o usuário
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // Gera o token de acesso
            TokenVO tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());

            logger.info("User authenticated successfully: " + username);

            return ResponseEntity.ok(tokenResponse);
        } catch (BadCredentialsException e) {
            logger.warning("Invalid username/password supplied for user: " + data.getUsername());
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
}
