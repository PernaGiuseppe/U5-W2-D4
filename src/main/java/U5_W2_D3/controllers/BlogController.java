package U5_W2_D3.controllers;

import U5_W2_D3.entities.Blog;
import U5_W2_D3.payloads.NewBlogPayload;
import U5_W2_D3.services.BlogService;
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
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public Page<Blog> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return blogService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog createBlog(@RequestBody @Valid NewBlogPayload body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String messages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new U5_W2_D3.exceptions.BadRequestException("Errori nel payload: " + messages);
        }
        return blogService.saveBlog(body);
    }

    @GetMapping("/{blogId}")
    public Blog getBlogById(@PathVariable Long blogId) {
        return blogService.findById(blogId);
    }

    @PutMapping("/{blogId}")
    public Blog getByIdAndUpdate(@PathVariable Long blogId, @RequestBody @Valid NewBlogPayload body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String messages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new U5_W2_D3.exceptions.BadRequestException("Errori nel payload: " + messages);
        }
        return blogService.findByIdAndUpdate(blogId, body);
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getBlogByIDAndDelete(@PathVariable Long blogId) {
        blogService.findByIdAndDelete(blogId);
    }

    @PatchMapping("/{blogId}/cover")
    public Blog uploadCover(@PathVariable Long blogId, @RequestParam("cover") MultipartFile file) throws IOException {
        return blogService.uploadCover(blogId, file);
    }
}
