package sk.pivarci.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.pivarci.todo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByLogin(String login);
}
