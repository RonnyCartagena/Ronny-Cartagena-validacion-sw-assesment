package com.viraj.sample.service;

import com.viraj.sample.entity.Employee;
import com.viraj.sample.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Danny", "Software Engineer");
        employee.setEmployeeId(1L);
    }

    @Test
    @DisplayName("Debe guardar un empleado exitosamente")
    void saveEmployeeTest() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);


        Employee saved = employeeService.saveEmployee(employee);

        assertNotNull(saved);
        assertEquals("Danny", saved.getEmployeeName());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    @DisplayName("Debe retornar null cuando el empleado no existe")
    void getEmployeeNotFoundTest() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());
        Employee found = employeeService.getEmployee(2L);
        assertNull(found);
    }

    @Test
    @DisplayName("Debe eliminar un empleado por ID")
    void deleteEmployeeTest() {
        employeeService.deleteEmployee(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}