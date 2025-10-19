package pizzeria.spring_la_mia_pizzeria_security.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pizzeria.spring_la_mia_pizzeria_security.model.User;
import pizzeria.spring_la_mia_pizzeria_security.repository.UserRepository;

@Service
public class DatabaseUserdetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepo.findByUsername(username);
        if(userOpt.isPresent()){
            return new DatabaseUserDetail(userOpt.get());
        } else {
            throw new UsernameNotFoundException ("Username not found.");
        }
    }

    

}
