package sk.pivarci.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sk.pivarci.todo.model.Item;
import sk.pivarci.todo.model.User;
import sk.pivarci.todo.repo.ItemRepository;
import sk.pivarci.todo.service.UserService;

import java.util.Arrays;

@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(final UserService userService, final ItemRepository itemRepository) {
        return (evt) -> Arrays.asList(new String[]{"marvin", "zaphod"})
                .forEach(
                        a -> {
                            if(userService.findUserByLogin(a) == null) {
                                User user = userService.save(new User(a, "12345"));
                                itemRepository.save(new Item(user, "example item", false));
                            }
                        });
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}
