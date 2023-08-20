package com.general.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.general.controllers.BookController;
import com.general.data.vo.v1.BookVO;
import com.general.exceptions.RequiredObjectIsNullException;
import com.general.exceptions.ResourceNotFoundException;
import com.general.mapper.DozerMapper;
import com.general.mapper.custom.PersonMapper;
import com.general.model.Book;
import com.general.repositories.BookRepository;

@Service
public class BookServices {
	
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	public List<BookVO> findAll(){
		logger.info("Finding all books!");
		
		List<BookVO> vos = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		
		vos.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
		
		return vos;
	}
	
	public BookVO findById(Long id) {
		logger.info("Finding a book!");
		
		Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return vo;
	}
	
	public BookVO create(BookVO book) {
		if (book == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Creating one BookVO!");
		
		Book entity = DozerMapper.parseObject(book, Book.class);
		BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public BookVO update(BookVO book) {
		if (book == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Updating one BookVO!");
		
		Book entity = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one BookVO!");
		
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		repository.delete(entity);
	}
}