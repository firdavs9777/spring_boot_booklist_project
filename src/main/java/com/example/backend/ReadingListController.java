package com.example.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/readingList")
public class ReadingListController {

    private final ReadingListService readingListService;

    public ReadingListController(ReadingListService readingListService) {
        this.readingListService = readingListService;
    }

    @GetMapping
    public String getReadingList(Model model) {
        model.addAttribute("books", readingListService.findAll());
        model.addAttribute("book", new Book());
        return "readingList";
    }

    @PostMapping
    public String addToReadingList(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("books", readingListService.findAll());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "readingList";
        }
        readingListService.save(book);
        return "redirect:/readingList";
    }

    // NEW: Show edit form
    @GetMapping("/{id}/edit")
    public String editBookForm(@PathVariable Long id, Model model) {
        try {
            Book book = readingListService.findById(id)
                    .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
            model.addAttribute("book", book);
            return "editBook";
        } catch (BookNotFoundException e) {
            return "redirect:/readingList";
        }
    }

    // NEW: Handle edit form submission
    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable Long id, @Valid Book book, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "editBook";
        }

        try {
            readingListService.updateBook(id, book);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/readingList";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            readingListService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the book");
            e.printStackTrace();
        }
        return "redirect:/readingList";
    }
}