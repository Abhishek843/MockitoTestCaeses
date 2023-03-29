package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entity.Allergy;
import com.revature.exception.ResourceNotFoundException;
import com.revature.service.impl.AllergyServiceImpl;

@RestController
@RequestMapping("/api/v1/allergies")

public class AllergyController {
	
	@Autowired
	private AllergyServiceImpl allergyService;
	
	

	
	
	@GetMapping
	public ResponseEntity<List<Allergy>> findAllAllergies(){
		List<Allergy> allergies = allergyService.getAllergies();
		return ResponseEntity.ok(allergies);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Allergy> findAllergyById(@PathVariable int id) throws ResourceNotFoundException {
		Allergy allergy = allergyService.getAllergyById(id);
		return ResponseEntity.ok(allergy);
	}
	


}
