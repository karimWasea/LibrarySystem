package Librar.Library.Servess;

import Librar.Library.Servess.IServess.IPatronServess;
import Librar.Library.Modes.Dtos.PatronDto;
import Librar.Library.Servess.Repository.PatronReprastory;
import Librar.Library.Modes.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatronServessImpl implements IPatronServess {

    @Autowired
    private PatronReprastory patronRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "patrons", key = "'all'")
    public List<PatronDto> findAllPatrons() {
        return patronRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "patrons", key = "#id")
    public Optional<PatronDto> findPatronById(Long id) {
        return patronRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatronById(Long id) {
        patronRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "patrons", key = "{#patronDto.id, #patronDto.name}")
    public boolean checkIfPatronExists(PatronDto patronDto) {
        return patronRepository.existsByCriteria(patronDto.getId(), patronDto.getName());
    }

    @Override
    @Transactional
    @CachePut(value = "patrons", key = "#id")
    public PatronDto updatePatron(Long id, PatronDto patronDto) {
        return patronRepository.findById(id)
                .map(existingPatron -> {
                    existingPatron.setName(patronDto.getName());
                    existingPatron.setContactInfo(patronDto.getContactInfo());
                    return convertToDto(patronRepository.save(existingPatron));
                }).orElseThrow(() -> new IllegalArgumentException("Patron not found"));
    }

    @Override
    @Transactional
    @CachePut(value = "patrons", key = "#patronDto.id")
    public PatronDto createPatron(PatronDto patronDto) {
        Patron patron = convertToEntity(patronDto);
        return convertToDto(patronRepository.save(patron));
    }

    // Conversion methods between Patron and PatronDto
    private PatronDto convertToDto(Patron patron) {
        PatronDto dto = new PatronDto();
        dto.setId(patron.getId());
        dto.setName(patron.getName());
        dto.setContactInfo(patron.getContactInfo());
        return dto;
    }

    private Patron convertToEntity(PatronDto dto) {
        Patron patron = new Patron();
        patron.setId(dto.getId());
        patron.setName(dto.getName());
        patron.setContactInfo(dto.getContactInfo());
        return patron;
    }
}
