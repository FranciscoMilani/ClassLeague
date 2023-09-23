package br.ucs.classleague.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "class")
public class SchoolClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private SchoolShift schoolShift;
    private Integer number;
    private EducationalCycle educationalCycle;
    
    public static enum SchoolShift {
        MATUTINE("Matutino"),
        VESPERTINE("Vespertino"),
        FULL_TIME("Tempo Integral");

        private String name;

        private SchoolShift(String name) {
            this.name = name;
        }
    }

    public static enum EducationalCycle {
        ELEMENTARY_SCHOOL("Ensino Fundamental"),
        HIGH_SCHOOL("Ensino Médio");

        private String name;

        private EducationalCycle(String name) {
            this.name = name;
        }
    }
    
    public SchoolClass(){
    }

    public SchoolClass(String name, SchoolShift schoolShift, Integer number, EducationalCycle educationalCycle) {
        this.name = name;
        this.schoolShift = schoolShift;
        this.number = number;
        this.educationalCycle = educationalCycle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchoolShift getSchoolShift() {
        return schoolShift;
    }

    public void setSchoolShift(SchoolShift schoolShift) {
        this.schoolShift = schoolShift;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public EducationalCycle getEducationalCycle() {
        return educationalCycle;
    }

    public void setEducationalCycle(EducationalCycle educationalCycle) {
        this.educationalCycle = educationalCycle;
    }
}
