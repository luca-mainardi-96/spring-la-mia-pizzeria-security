package pizzeria.spring_la_mia_pizzeria_security.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pizzeria.spring_la_mia_pizzeria_security.model.Role;
import pizzeria.spring_la_mia_pizzeria_security.model.User;

public class DatabaseUserDetail implements UserDetails{

    private String username;

    private String password;

    private Set<GrantedAuthority> authorities;

    public DatabaseUserDetail(User user){
        this.username = user.getName();
        this.password = user.getPassword();
        this.authorities = new HashSet<>();
        for(Role role : user.getRoles()){
            SimpleGrantedAuthority sGA = new SimpleGrantedAuthority(role.getName());
            this.authorities.add(sGA);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
