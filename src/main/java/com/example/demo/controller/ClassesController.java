package com.example.demo.controller;

import com.example.demo.dto.ClassesDTO;
import com.example.demo.entity.ErrorMessage;
import com.example.demo.entity.Classes;
import com.example.demo.repo.ClassesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ClassesController {
    @Autowired
    private ClassesRepo projectsRepo;

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Classes>> getAllProjects() {
        try {
            List<Classes> projectsList = new ArrayList<>();
            projectsRepo.findAll().forEach(projectsList::add);
            if(projectsList.isEmpty()) {
                return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(projectsList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProjectById/{id}")
    public ResponseEntity<Classes> getProjectById(@PathVariable Long id) {
        Optional<Classes> projectData = projectsRepo.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addProject")
    public ResponseEntity<?>  addProject(@RequestBody Classes projects) {
            if (projects.getAccountId() == null) {
            ErrorMessage errorMessage = new ErrorMessage("AccountId cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
        }
        if (projects.getName() == null || projects.getName().trim().isEmpty() ) {
            ErrorMessage errorMessage = new ErrorMessage("Name cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
        }
        if (projects.getStatus() == null || projects.getStatus().isEmpty() ) {
            ErrorMessage errorMessage = new ErrorMessage("Status cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
        }
        Classes classesObj = projectsRepo.save(projects);
        return new ResponseEntity<>(classesObj, HttpStatus.OK);
    }

    @PutMapping("/updateProjectById/{id}")
    public ResponseEntity<?> updateProjectById(@PathVariable Long id, @RequestBody Classes newClassesData) {
        Optional<Classes> oldProjectDataOptional = projectsRepo.findById(id);

        if (oldProjectDataOptional.isPresent()) {
            Classes oldClassesData = oldProjectDataOptional.get();

            if (newClassesData.getAccountId() == null) {
                ErrorMessage errorMessage = new ErrorMessage("AccountId cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
            }

            if (newClassesData.getName() == null || newClassesData.getName().trim().isEmpty()) {
                ErrorMessage errorMessage = new ErrorMessage("Name cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
            }

            if (newClassesData.getStatus() == null || newClassesData.getStatus().isBlank()) {
                ErrorMessage errorMessage = new ErrorMessage("Status cannot be null or empty");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
            }

            if (newClassesData.getName() != null) {
                oldClassesData.setName(newClassesData.getName());
            }

            if (newClassesData.getStartDate() != null) {
                oldClassesData.setStartDate(newClassesData.getStartDate());
            }

            if (newClassesData.getEndDate() != null) {
                oldClassesData.setEndDate(newClassesData.getEndDate());
            }

            if (newClassesData.getNote() != null) {
                oldClassesData.setNote(newClassesData.getNote());
            }

            if (newClassesData.getStatus() != null) {
                oldClassesData.setStatus(newClassesData.getStatus());
            }

            if (newClassesData.getDescription() != null) {
                oldClassesData.setDescription(newClassesData.getDescription());
            }

            if (newClassesData.getCreatedBy() != null) {
                oldClassesData.setCreatedBy(newClassesData.getCreatedBy());
            }

            if (newClassesData.getAccountId() != null) {
                oldClassesData.setAccountId(newClassesData.getAccountId());
            }

            oldClassesData.setUpdatedAt(new Date());

            Classes updatedClassesData = projectsRepo.save(oldClassesData);

            return new ResponseEntity<>(updatedClassesData, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/deleteProjectById/{id}")
    public ResponseEntity<HttpStatus> deleteProjectById(@PathVariable Long id) {
        projectsRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getProjectsByAccountId/{accountId}")
    public ResponseEntity<?> getProjectsByAccountId(@PathVariable Integer accountId) {
        List<Classes> projectsData = projectsRepo.findByAccountId(accountId);

        if (!projectsData.isEmpty()) {
            return new ResponseEntity<>(projectsData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are currently no projects", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/search")
    public List<Classes> search(@RequestBody ClassesDTO dto) {
        return projectsRepo.searchProjects(dto.getAccountId(), dto.getName());
    }
}
