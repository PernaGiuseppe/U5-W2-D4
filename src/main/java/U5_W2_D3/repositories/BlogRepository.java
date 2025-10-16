package U5_W2_D3.repositories;

import U5_W2_D3.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    
    List<Blog> findByAuthorId(Long authorId);
}
