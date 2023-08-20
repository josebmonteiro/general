package com.general.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.general.data.vo.v2.PersonVOV2;
import com.general.model.Person;

@Service
public class PersonMapper {
	
	public PersonVOV2 convertEntityToVo(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setAddress(person.getAddress());
		vo.setGender(person.getGender());
		vo.setBithDay(new Date());
		return vo;
	}
	
	public Person convertVoToEntity(PersonVOV2 vo) {
		Person entity = new Person();
		entity.setId(vo.getId());
		entity.setFirstName(vo.getFirstName());
		entity.setLastName(vo.getLastName());
		entity.setAddress(vo.getAddress());
		entity.setGender(vo.getGender());
		//entity.setBithDay(new Date());
		return entity;
	}
}
