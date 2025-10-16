package U5_W2_D3.controllers;

import U5_W2_D3.entities.Author;
import U5_W2_D3.payloads.NewAuthorPayload;
import U5_W2_D3.services.AuthorsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorsService authorsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author saveAuthor(@RequestBody @Valid NewAuthorPayload body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String messages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new U5_W2_D3.exceptions.BadRequestException("Errori nel payload: " + messages);
        }
        return authorsService.save(body);
    }

    @GetMapping
    public Page<Author> getAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return authorsService.getAuthors(pageable);
    }

    @GetMapping("/{authorId}")
    public Author findById(@PathVariable Long authorId) {
        return authorsService.findById(authorId);
    }

    @PutMapping("/{authorId}")
    public Author findAndUpdate(@PathVariable Long authorId, @RequestBody @Valid NewAuthorPayload body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String messages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new U5_W2_D3.exceptions.BadRequestException("Errori nel payload: " + messages);
        }
        return authorsService.findByIdAndUpdate(authorId, body);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable Long authorId) {
        authorsService.findByIdAndDelete(authorId);
    }

    @PatchMapping("/{authorId}/avatar")
    public Author uploadAvatar(@PathVariable Long authorId, @RequestParam("avatar") MultipartFile file) throws IOException {
        return authorsService.uploadAvatar(authorId, file);
    }
}
