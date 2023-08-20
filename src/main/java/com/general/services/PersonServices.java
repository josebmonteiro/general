package com.general.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.general.controllers.PersonController;
import com.general.data.vo.v1.PersonVO;
import com.general.data.vo.v2.PersonVOV2;
import com.general.exceptions.RequiredObjectIsNullException;
import com.general.exceptions.ResourceNotFoundException;
import com.general.mapper.DozerMapper;
import com.general.mapper.custom.PersonMapper;
import com.general.model.Person;
import com.general.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		
		logger.info("Finding all people!");
		
		List<PersonVO> vos = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		
		vos.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		return vos;
	}
	
	public PersonVO findById(Long id) {
		
		logger.info("Finding one PersonVO!");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return vo;
	}
	
	public PersonVO create(PersonVO person) {
		
		if (person == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Creating one PersonVO!");
		
		Person entity = DozerMapper.parseObject(person, Person.class);
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		
		logger.info("Creating one person with V2!");
		
		Person entity = mapper.convertVoToEntity(person);
		
		PersonVOV2 vo = mapper.convertEntityToVo(repository.save(entity));
		
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		if (person == null) {
			throw new RequiredObjectIsNullException();
		}
		
		logger.info("Updating one PersonVO!");
		
		Person entity = repository.findById(person.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}

	public void delete(Long id) {
		
		logger.info("Deleting one PersonVO!");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		repository.delete(entity);
	}
}