package com.example.backend;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReadingListService {

    private final ReadingListRepository readingListRepository;

    public ReadingListService(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    public List<Book> findAll() {
        return readingListRepository.findAll();
    }

    public Book save(Book book) {
        return readingListRepository.save(book);
    }

    public Optional<Book> findById(Long id) {
        return readingListRepository.findById(id);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return readingListRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setDescription(updatedBook.getDescription());
                    book.setIsbn(updatedBook.getIsbn());
                    book.setReader(updatedBook.getReader());
                    return readingListRepository.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
    }

    public void deleteById(Long id) {
        if (!readingListRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id " + id);
        }
        readingListRepository.deleteById(id);
    }
}

