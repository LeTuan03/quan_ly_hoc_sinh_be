package com.example.demo.controller;

import com.example.demo.entity.ErrorMessage;
import com.example.demo.entity.LOP12;
import com.example.demo.repo.LOP12Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lop12")
public class LOP12Controller {
    @Autowired
    private LOP12Repo lop12Repo;

    @GetMapping("/getAll")
    public ResponseEntity<List<LOP12>> getAllProjects() {
        try {
            List<LOP12> projectsList = new ArrayList<>();
            lop12Repo.findAll().forEach(projectsList::add);
            if(projectsList.isEmpty()) {
                return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(projectsList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<LOP12> getProjectById(@PathVariable Long id) {
        Optional<LOP12> projectData = lop12Repo.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?>  addProject(@RequestBody LOP12 lop10) {
        if (lop10.getAccountId() == null) {
            ErrorMessage errorMessage = new ErrorMessage("AccountId cannot be null");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
        }

        LOP12 projectObj = lop12Repo.save(lop10);
        return new ResponseEntity<>(projectObj, HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateProjectById(@PathVariable Long id, @RequestBody LOP12 newProjectData) {
        Optional<LOP12> oldProjectDataOptional = lop12Repo.findById(id);

        if (oldProjectDataOptional.isPresent()) {
            LOP12 oldProjectData = oldProjectDataOptional.get();

            if (newProjectData.getAccountId() == null) {
                ErrorMessage errorMessage = new ErrorMessage("AccountId cannot be null");
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
            }

            if (newProjectData.getAccountId() != null) {
                oldProjectData.setAccountId(newProjectData.getAccountId());
            }

            if (newProjectData.getChemistry() != null) {
                oldProjectData.setChemistry(newProjectData.getChemistry());
            }

            if (newProjectData.getBiology() != null) {
                oldProjectData.setBiology(newProjectData.getBiology());
            }

            if (newProjectData.getConduct() != null) {
                oldProjectData.setConduct(newProjectData.getConduct());
            }

            if (newProjectData.getGeography() != null) {
                oldProjectData.setGeography(newProjectData.getGeography());
            }

            if (newProjectData.getFineArt() != null) {
                oldProjectData.setFineArt(newProjectData.getFineArt());
            }

            if (newProjectData.getClassification() != null) {
                oldProjectData.setClassification(newProjectData.getClassification());
            }

            if (newProjectData.getLiterature() != null) {
                oldProjectData.setLiterature(newProjectData.getLiterature());
            }

            if (newProjectData.getMaths() != null) {
                oldProjectData.setMaths(newProjectData.getMaths());
            }

            if (newProjectData.getPhysics() != null) {
                oldProjectData.setPhysics(newProjectData.getPhysics());
            }

            if (newProjectData.getEnglish() != null) {
                oldProjectData.setEnglish(newProjectData.getEnglish());
            }

            if (newProjectData.getCivicEducation() != null) {
                oldProjectData.setCivicEducation(newProjectData.getCivicEducation());
            }

            if (newProjectData.getHistory() != null) {
                oldProjectData.setHistory(newProjectData.getHistory());
            }

            LOP12 updatedProjectData = lop12Repo.save(oldProjectData);

            return new ResponseEntity<>(updatedProjectData, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteProjectById(@PathVariable Long id) {
        lop12Repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getByAccountId/{accountId}")
    public ResponseEntity<?> getProjectsByAccountId(@PathVariable Integer accountId) {
        List<LOP12> projectsData = lop12Repo.findByAccountId(accountId);

        if (!projectsData.isEmpty()) {
            return new ResponseEntity<>(projectsData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are currently no projects", HttpStatus.NO_CONTENT);
        }
    }

}
