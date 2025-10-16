package U5_W2_D3.services;

import U5_W2_D3.entities.Author;
import U5_W2_D3.exceptions.NotFoundException;
import U5_W2_D3.payloads.NewAuthorPayload;
import U5_W2_D3.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorsService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author save(NewAuthorPayload payload) {
        Author newAuthor = new Author(payload.getName(), payload.getSurname(),
                payload.getEmail(), payload.getDateOfBirth());
        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" +
                payload.getName() + "+" + payload.getSurname());

        Author savedAuthor = authorRepository.save(newAuthor);
        System.out.println("Il nuovo autore " + savedAuthor.getName() + " " +
                savedAuthor.getSurname() + " è stato aggiunto al database!");
        return savedAuthor;
    }

    public Page<Author> getAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(Long id) {
        Author found = findById(id);
        authorRepository.delete(found);
    }

    public Author findByIdAndUpdate(Long id, NewAuthorPayload payload) {
        Author found = findById(id);
        found.setName(payload.getName());
        found.setSurname(payload.getSurname());
        found.setEmail(payload.getEmail());
        found.setDateOfBirth(payload.getDateOfBirth());
        return authorRepository.save(found);
    }
}
