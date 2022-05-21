package com.kbaje.eshop;

import com.kbaje.eshop.dto.CreateUserDto;
import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AfterStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired    
    private UserService userService;

    @Autowired
    private Environment env;
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String username = env.getProperty("kbaje.admin.username");
		String password = env.getProperty("kbaje.admin.password");
		String email = env.getProperty("kbaje.admin.email");

		if (userService.getUserByEmail(email) == null) {
            UserDto admin = userService.createUser(new CreateUserDto(username, email, password));
            userService.makeUserAdmin(admin.id);
		}
    }
        
}
