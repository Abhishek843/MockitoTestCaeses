package com.revature.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.entity.Allergy;
import com.revature.exception.ResourceNotFoundException;
import com.revature.repository.AllergyRepository;
import com.revature.service.AllergyService;

@Service
public class AllergyServiceImpl implements AllergyService {
	
	@Autowired
	private AllergyRepository allergyRepository;
	
	
	@Override
	public Allergy saveAllergy(Allergy allergy) {
		return allergyRepository.save(allergy);
	}
	
//	public List<Allergy> saveAllergies(List<Allergy> allergies) {
//		return allergyRepository.saveAll(allergies);
//	}

	@Override
	public List<Allergy> getAllergies() {
		// TODO Auto-generated method stub
		return allergyRepository.findAll();
	}

	@Override
	public Allergy getAllergyById(int id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return allergyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Allergy with given id is not found on server"));
	}
	
	public void deleteAllergy(int id) {
		allergyRepository.deleteById(id);
	}
	
	public Allergy updateAllergy(Allergy allergy,int id) {
		Allergy existingAllergy = allergyRepository.findById(id).orElse(null);
		existingAllergy.setAllergyName(allergy.getAllergyName());
		existingAllergy.setNotes(allergy.getNotes());
		return allergyRepository.save(existingAllergy);
	}

}
