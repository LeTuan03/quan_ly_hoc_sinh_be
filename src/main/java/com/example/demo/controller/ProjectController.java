package com.example.demo.controller;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.entity.ErrorMessage;
import com.example.demo.entity.Project;
import com.example.demo.repo.ProjectRepo;
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
public class ProjectController {
    @Autowired
    private ProjectRepo projectsRepo;

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projectsList = new ArrayList<>();
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
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> projectData = projectsRepo.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addProject")
    public ResponseEntity<?>  addProject(@RequestBody Project projects) {
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
        Project projectObj = projectsRepo.save(projects);
        return new ResponseEntity<>(projectObj, HttpStatus.OK);
    }

    @PutMapping("/updateProjectById/{id}")
    public ResponseEntity<?> updateProjectById(@PathVariable Long id, @RequestBody Project newProjectData) {
        Optional<Project> oldProjectDataOptional = projectsRepo.findById(id);

        if (oldProjectDataOptional.isPresent()) {
            Project oldProjectData = oldProjectDataOptional.get();

            if (newProjectData.getAccountId() == null) {
                ErrorMessage errorMessage = new ErrorMessage("AccountId cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
            }

            if (newProjectData.getName() == null || newProjectData.getName().trim().isEmpty()) {
                ErrorMessage errorMessage = new ErrorMessage("Name cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
            }

            if (newProjectData.getStatus() == null || newProjectData.getStatus().isBlank()) {
                ErrorMessage errorMessage = new ErrorMessage("Status cannot be null or empty");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
            }

            if (newProjectData.getName() != null) {
                oldProjectData.setName(newProjectData.getName());
            }

            if (newProjectData.getStartDate() != null) {
                oldProjectData.setStartDate(newProjectData.getStartDate());
            }

            if (newProjectData.getEndDate() != null) {
                oldProjectData.setEndDate(newProjectData.getEndDate());
            }

            if (newProjectData.getNote() != null) {
                oldProjectData.setNote(newProjectData.getNote());
            }

            if (newProjectData.getStatus() != null) {
                oldProjectData.setStatus(newProjectData.getStatus());
            }

            if (newProjectData.getDescription() != null) {
                oldProjectData.setDescription(newProjectData.getDescription());
            }

            if (newProjectData.getCreatedBy() != null) {
                oldProjectData.setCreatedBy(newProjectData.getCreatedBy());
            }

            if (newProjectData.getAccountId() != null) {
                oldProjectData.setAccountId(newProjectData.getAccountId());
            }

            oldProjectData.setUpdatedAt(new Date());

            Project updatedProjectData = projectsRepo.save(oldProjectData);

            return new ResponseEntity<>(updatedProjectData, HttpStatus.OK);
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
        List<Project> projectsData = projectsRepo.findByAccountId(accountId);

        if (!projectsData.isEmpty()) {
            return new ResponseEntity<>(projectsData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are currently no projects", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/search")
    public List<Project> search(@RequestBody ProjectDTO dto) {
        return projectsRepo.searchProjects(dto.getAccountId(), dto.getName());
    }
}
