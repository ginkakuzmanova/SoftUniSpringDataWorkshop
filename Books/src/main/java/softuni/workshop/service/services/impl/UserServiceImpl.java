package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Role;
import softuni.workshop.data.entities.User;
import softuni.workshop.data.repositories.UserRepository;
import softuni.workshop.error.Constants;
import softuni.workshop.service.models.UserServiceModel;
import softuni.workshop.service.services.RoleService;
import softuni.workshop.service.services.UserService;
import softuni.workshop.web.models.UserRegisterModel;

import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private RoleService roleService;
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper, RoleService roleService, BCryptPasswordEncoder cryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleService = roleService;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserRegisterModel userRegisterModel) {
        User user = this.mapper.map(userRegisterModel, User.class);
        if (this.userRepository.count() == 0) {
            this.roleService.seedRolesInDb();
            user.setAuthorities(
                    this.roleService.findAllRoles().stream()
                            .map(r -> this.mapper.map(r, Role.class))
                            .collect(Collectors.toSet())
            );
        } else {
            user.getAuthorities().add(
                    this.mapper.map(this.roleService.
                            findByAuthority("ROLE_USER"), Role.class)

            );
        }
        user.setPassword(this.cryptPasswordEncoder.encode(userRegisterModel.getPassword()));
        this.userRepository.saveAndFlush(user);
        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() ->
                new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND)
        );
    }

    @Override
    public boolean userAlreadyExists(String username) {
        return this.userRepository.existsUserByUsername(username);
    }
}
