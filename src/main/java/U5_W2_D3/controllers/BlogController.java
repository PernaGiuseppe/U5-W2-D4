package U5_W2_D3.controllers;

import U5_W2_D3.entities.Blog;
import U5_W2_D3.payloads.NewBlogPayload;
import U5_W2_D3.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Blog createBlog(@RequestBody NewBlogPayload body) {
        return blogService.saveBlog(body);
    }

    @GetMapping("/{blogId}")
    public Blog getBlogById(@PathVariable Long blogId) {
        return blogService.findById(blogId);
    }

    @PutMapping("/{blogId}")
    public Blog getByIdAndUpdate(@PathVariable Long blogId, @RequestBody NewBlogPayload body) {
        return blogService.findByIdAndUpdate(blogId, body);
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getBlogByIDAndDelete(@PathVariable Long blogId) {
        blogService.findByIdAndDelete(blogId);
    }
}
