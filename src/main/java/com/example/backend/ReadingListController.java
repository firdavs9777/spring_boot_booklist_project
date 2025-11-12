package com.example.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/readingList")
public class ReadingListController {

    private final ReadingListRepository readingListRepository;

    public ReadingListController(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    // Display all books
    @GetMapping
    public String getReadingList(Model model) {
        model.addAttribute("books", readingListRepository.findAll());
        return "readingList"; // Maps to readingList.html in templates
    }

    // Add a new book
    @PostMapping
    public String addToReadingList(Book book) {
        readingListRepository.save(book);
        return "redirect:/readingList"; // Redirect back to the list after saving
    }

    @PutMapping("readingList/{id}")
    @ResponseBody
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return readingListRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setDescription(updatedBook.getDescription());
                    book.setIsbn(updatedBook.getIsbn());
                    book.setReader(updatedBook.getReader());
                    return readingListRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }
}
