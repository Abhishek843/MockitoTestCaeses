package com.revature;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.entity.Allergy;
import com.revature.exception.ResourceNotFoundException;
import com.revature.repository.AllergyRepository;
import com.revature.service.impl.AllergyServiceImpl;

public class AllergyServiceImplTest {

    @Mock
    private AllergyRepository allergyRepository;

    @InjectMocks
    private AllergyServiceImpl allergyService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveAllergy() {
        Allergy allergy = new Allergy(0, "Peanut Allergy", "Allergy to peanuts");
        when(allergyRepository.save(allergy)).thenReturn(allergy);
        Allergy savedAllergy = allergyService.saveAllergy(allergy);
        assertEquals("Peanut Allergy", savedAllergy.getAllergyName());
        assertEquals("Allergy to peanuts", savedAllergy.getNotes());
    }

    @Test
    void testGetAllergies() {
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(new Allergy(0, "Peanut Allergy", "Allergy to peanuts"));
        allergies.add(new Allergy(0, "Shellfish Allergy", "Allergy to shellfish"));
        when(allergyRepository.findAll()).thenReturn(allergies);
        List<Allergy> foundAllergies = allergyService.getAllergies();
        assertEquals(2, foundAllergies.size());
    }

    @Test
    void testGetAllergyById() throws ResourceNotFoundException {
        Allergy allergy = new Allergy(0, "Peanut Allergy", "Allergy to peanuts");
        when(allergyRepository.findById(1)).thenReturn(Optional.of(allergy));
        Allergy foundAllergy = allergyService.getAllergyById(1);
        assertEquals("Peanut Allergy", foundAllergy.getAllergyName());
        assertEquals("Allergy to peanuts", foundAllergy.getNotes());
    }

    @Test
    void testDeleteAllergy() {
        allergyService.deleteAllergy(1);
        verify(allergyRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdateAllergy() {
        Allergy allergy = new Allergy(0, "Peanut Allergy", "Allergy to peanuts");
        Allergy existingAllergy = new Allergy(0, "Nut Allergy", "Allergy to nuts");
        when(allergyRepository.findById(1)).thenReturn(Optional.of(existingAllergy));
        when(allergyRepository.save(existingAllergy)).thenReturn(existingAllergy);
        Allergy updatedAllergy = allergyService.updateAllergy(allergy, 1);
        assertEquals("Peanut Allergy", updatedAllergy.getAllergyName());
        assertEquals("Allergy to peanuts", updatedAllergy.getNotes());
    }

}
