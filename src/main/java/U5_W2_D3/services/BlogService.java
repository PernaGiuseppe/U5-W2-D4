package U5_W2_D3.services;

import U5_W2_D3.entities.Author;
import U5_W2_D3.entities.Blog;
import U5_W2_D3.exceptions.BadRequestException;
import U5_W2_D3.exceptions.NotFoundException;
import U5_W2_D3.payloads.NewBlogPayload;
import U5_W2_D3.repositories.AuthorRepository;
import U5_W2_D3.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Blog saveBlog(NewBlogPayload payload) {
        Long authorId;
        try {
            authorId = Long.parseLong(payload.getAuthorId());
        } catch (NumberFormatException e) {
            throw new BadRequestException("L'authorId deve essere un numero valido");
        }

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(authorId));

        Blog newBlog = new Blog(payload.getTitle(), payload.getTitle(),
                payload.getContent(), payload.getReadingTime(), author);

        Blog savedBlog = blogRepository.save(newBlog);
        System.out.println("Il nuovo blog '" + savedBlog.getTitolo() +
                "' è stato aggiunto al database!");
        return savedBlog;
    }

    public Blog findById(Long blogId) {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException(blogId));
    }

    public Blog findByIdAndUpdate(Long blogId, NewBlogPayload payload) {
        Blog found = findById(blogId);

        Long authorId;
        try {
            authorId = Long.parseLong(payload.getAuthorId());
        } catch (NumberFormatException e) {
            throw new BadRequestException("L'authorId deve essere un numero valido");
        }

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(authorId));

        found.setCategoria(payload.getTitle());
        found.setTitolo(payload.getTitle());
        found.setContenuto(payload.getContent());
        found.setTempoDiLettura(payload.getReadingTime());
        found.setAuthor(author);

        return blogRepository.save(found);
    }

    public void findByIdAndDelete(Long blogId) {
        Blog found = findById(blogId);
        blogRepository.delete(found);
    }
}