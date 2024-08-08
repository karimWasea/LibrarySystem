package Librar.Library.Controller;


import Librar.Library.Servess.IServess.IPatronServess;
import Librar.Library.Modes.Dtos.PatronDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import Librar.Library.Api.Controller.PatronController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatronController.class)
class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPatronServess patronService;

    @Autowired
    private ObjectMapper objectMapper;

    private PatronDto patronDto;

    @BeforeEach
    void setUp() {
        patronDto = new PatronDto();
        patronDto.setId(1L);
        patronDto.setName("John Doe");
    }

    @Test
    void getAllPatrons_shouldReturnListOfPatrons() throws Exception {
        List<PatronDto> patrons = new ArrayList<>();
        patrons.add(patronDto);

        when(patronService.findAllPatrons()).thenReturn(patrons);

        mockMvc.perform(get("/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getPatronById_whenFound_shouldReturnPatron() throws Exception {
        when(patronService.findPatronById(1L)).thenReturn(Optional.of(patronDto));

        mockMvc.perform(get("/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getPatronById_whenNotFound_shouldReturnNotFound() throws Exception {
        when(patronService.findPatronById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/patrons/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patron not found with ID: 1"));
    }

    @Test
    void createPatron_whenValid_shouldCreatePatron() throws Exception {
        when(patronService.checkIfPatronExists(any(PatronDto.class))).thenReturn(false);
        when(patronService.createPatron(any(PatronDto.class))).thenReturn(patronDto);

        mockMvc.perform(post("/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void createPatron_whenPatronExists_shouldReturnConflict() throws Exception {
        when(patronService.checkIfPatronExists(any(PatronDto.class))).thenReturn(true);

        mockMvc.perform(post("/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Patron with name 'John Doe' already exists."));
    }

    @Test
    void updatePatron_whenValid_shouldUpdatePatron() throws Exception {
        when(patronService.findPatronById(1L)).thenReturn(Optional.of(patronDto));
        when(patronService.updatePatron(anyLong(), any(PatronDto.class))).thenReturn(patronDto);

        mockMvc.perform(put("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void updatePatron_whenNotFound_shouldReturnNotFound() throws Exception {
        when(patronService.findPatronById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patron with ID 1 does not exist."));
    }

    @Test
    void deletePatron_whenValid_shouldDeletePatron() throws Exception {
        when(patronService.findPatronById(1L)).thenReturn(Optional.of(patronDto));

        mockMvc.perform(delete("/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Patron with ID 1 has been successfully deleted."));
    }

    @Test
    void deletePatron_whenNotFound_shouldReturnNotFound() throws Exception {
        when(patronService.findPatronById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/patrons/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patron with ID 1 does not exist."));
    }
}
