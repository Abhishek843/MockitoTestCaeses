package com.revature;



import static org.hamcrest.CoreMatchers.is;

//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.revature.controller.AllergyController;
//import com.revature.entity.Allergy;
//import com.revature.service.impl.AllergyServiceImpl;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class AllergyServiceControllerTests {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private AllergyServiceImpl allergyService;
//
//    @InjectMocks
//    private AllergyController allergyController;
//
//    private List<Allergy> allergies;
//    private Allergy allergy;
//    
//
//
//  
//    @Test
//    public void testFindAllAllergies() throws Exception {
//        Allergy allergy1 = new Allergy(1, "Peanut allergy", "hi");
//        Allergy allergy2 = new Allergy(2, "Gluten allergy", "hi");
//        List<Allergy> allergies = Arrays.asList(allergy1, allergy2);
//        when(allergyService.getAllergies()).thenReturn(allergies);
//
//        MvcResult result = mockMvc.perform(get("/api/v1/allergies")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseBody = result.getResponse().getContentAsString();
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<Allergy> responseAllergies = objectMapper.readValue(responseBody, new TypeReference<List<Allergy>>(){});
//
//        assert(responseAllergies.size() == 2);
//        assert(responseAllergies.get(0).getId() == allergy.getId());
//        assert(responseAllergies.get(0).getAllergyName().equals(allergy.getAllergyName()));
//    }
//
//    @Test
//    public void testFindAllergyById() throws Exception {
//    	Allergy allergy = new Allergy(1, "Peanut allergy","hi");
//        when(allergyService.getAllergyById(1)).thenReturn(allergy);
//
//        MvcResult result = mockMvc.perform(get("/api/v1/allergies/1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseBody = result.getResponse().getContentAsString();
//        ObjectMapper objectMapper = new ObjectMapper();
//        Allergy responseAllergy = objectMapper.readValue(responseBody, Allergy.class);
//
//        assert(responseAllergy.getId() == allergy.getId());
//        assert(responseAllergy.getAllergyName().equals(allergy.getAllergyName()));
//    }
//
//}




import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.AllergyController;
import com.revature.entity.Allergy;
import com.revature.exception.ResourceNotFoundException;
import com.revature.service.impl.AllergyServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(AllergyController.class)
public class AllergyServiceControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AllergyServiceImpl allergyService;
	
	@Test
	public void testFindAllAllergies() throws Exception {
		List<Allergy> allergies = Arrays.asList(new Allergy(1, "Pollen", "hi"), new Allergy(2, "Peanuts", "hi"));
		Mockito.when(allergyService.getAllergies()).thenReturn(allergies);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/allergies"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].allergyName", is("Pollen")))
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].allergyName", is("Peanuts")));
	}
	
	@Test
	public void testFindAllergyById_NotFound() throws Exception {
		Mockito.when(allergyService.getAllergyById(1)).thenThrow(new ResourceNotFoundException("Allergy not found for id: 1"));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/allergies/1"))
			.andExpect(status().isNotFound());
	}
	
	
}
