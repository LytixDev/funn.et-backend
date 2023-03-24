package edu.ntnu.idatt2105.funn.startup;

import edu.ntnu.idatt2105.funn.dto.user.RegisterDTO;
import edu.ntnu.idatt2105.funn.mapper.user.RegisterMapper;
import edu.ntnu.idatt2105.funn.mapper.user.UserMapper;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.repository.user.UserRepository;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

  @Autowired
  UserService userService;

  @Override
  public void run(String... args) throws Exception {
    if (!userService.usernameExists("admin")) {
      RegisterDTO admin = new RegisterDTO("admin", "admin", "admin", "admin", "admin");
      User user = RegisterMapper.INSTANCE.registerDTOtoUser(admin);
      user.setRole(Role.ADMIN);
      userService.saveUser(user);
    }
  }
}
