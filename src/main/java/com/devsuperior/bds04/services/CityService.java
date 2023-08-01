package com.devsuperior.bds04.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.DatabaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

    @Autowired
    CityRepository repository;

	@Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        List<City> list = repository.findAll(Sort.by("name").ascending());

        return  list.stream().map(obj -> new CityDTO(obj)).collect(Collectors.toList());
    }

	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setName(dto.getName());
		entity = repository.save(entity);

		return new CityDTO(entity);
	}
	
//    public void delete(Long id) {
//		try {
//			repository.deleteById(id);
//			
//		}
//		catch (EmptyResultDataAccessException e) {
//			throw new ResourceNotFoundException("CV - Id not found " + id);
//		}
//		catch (DataIntegrityViolationException e) {
//			throw new DatabaseException("CV - Integrity violation");
//		}
//    }


	

}
