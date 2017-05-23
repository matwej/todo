package sk.pivarci.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.pivarci.todo.model.Item;

import java.util.Collection;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Collection<Item> findByUserId(Long user_id);
    Collection<Item> findByUserIdAndDone(Long user_id, boolean done);
}
