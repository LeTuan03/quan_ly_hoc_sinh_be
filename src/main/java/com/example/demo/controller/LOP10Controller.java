package com.example.demo.controller;

import com.example.demo.config.Constants;
import com.example.demo.entity.ErrorMessage;
import com.example.demo.entity.LOP10;
import com.example.demo.repo.LOP10Repo;
//đánh dấu một dependency trong Spring để Container có thể tự động tiêm giá trị vào nó
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lop10")
public class LOP10Controller {
    @Autowired
    private LOP10Repo lop10Repo;

    @GetMapping("/getAll")
    public ResponseEntity<List<LOP10>> getAllProjects() {
        try {
            List<LOP10> projectsList = new ArrayList<>();
            lop10Repo.findAll().forEach(projectsList::add);
            if(projectsList.isEmpty()) {
                return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(projectsList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<LOP10> getProjectById(@PathVariable Long id) {
        Optional<LOP10> projectData = lop10Repo.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?>  addProject(@RequestBody LOP10 lop10) {
            if (lop10.getAccountId() == null) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.ID_MUST_NOT_EMPTY);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
        }

        LOP10 projectObj = lop10Repo.save(lop10);
        return new ResponseEntity<>(projectObj, HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateProjectById(@PathVariable Long id, @RequestBody LOP10 newProjectData) {
        Optional<LOP10> oldProjectDataOptional = lop10Repo.findById(id);

        if (oldProjectDataOptional.isPresent()) {
            LOP10 oldProjectData = oldProjectDataOptional.get();

            if (newProjectData.getAccountId() == null) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.ID_MUST_NOT_EMPTY);
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

            LOP10 updatedProjectData = lop10Repo.save(oldProjectData);

            return new ResponseEntity<>(updatedProjectData, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteProjectById(@PathVariable Long id) {
        lop10Repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getByAccountId/{accountId}")
    public ResponseEntity<?> getProjectsByAccountId(@PathVariable Integer accountId) {
        List<LOP10> projectsData = lop10Repo.findByAccountId(accountId);

        if (!projectsData.isEmpty()) {
            return new ResponseEntity<>(projectsData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Constants.NO_INFO_POINT_10, HttpStatus.NO_CONTENT);
        }
    }

}
