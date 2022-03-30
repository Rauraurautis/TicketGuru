package fi.triforce.TicketGuru.Web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fi.triforce.TicketGuru.Domain.Role;
import fi.triforce.TicketGuru.Domain.RoleRepository;
import fi.triforce.TicketGuru.Domain.User;
import fi.triforce.TicketGuru.Domain.UserRepository;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.utils.ReturnMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements UserDetailsService {

    private final UserRepository ur;
    private final RoleRepository rr;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = ur.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ur.save(user);
    }

    public HashMap<String, String> deleteUser(Long id) {
        User user = ur.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find a user with the id " + id));
        log.info("Deleting user {} from the database", user.getName());
        ur.delete(user);
        return new ReturnMsg("Deleted a user with the id " + id).getReturnMsg();
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return rr.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = ur.findByUsername(username);
        Role role = rr.findByName(roleName);
        user.getRoles().add(role);
        ur.save(user);
        // Transactional-annotaation takia ei tarvitse tallentaa useria erikseen vaan se
        // tallentuu automaattisesti
    }

    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return ur.findByUsername(username);
    }

    public List<User> getUsers() {
        log.info("Fetching users");
        return ur.findAll();
    }

}
