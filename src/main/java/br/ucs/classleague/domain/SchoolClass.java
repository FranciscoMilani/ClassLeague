package br.ucs.classleague.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
public class SchoolClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private Integer number;
    private SchoolShift schoolShift;
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

    public SchoolClass(String name, Integer number, SchoolShift schoolShift,  EducationalCycle educationalCycle) {
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public SchoolShift getSchoolShift() {
        return schoolShift;
    }

    public void setSchoolShift(SchoolShift schoolShift) {
        this.schoolShift = schoolShift;
    }

    public EducationalCycle getEducationalCycle() {
        return educationalCycle;
    }

    public void setEducationalCycle(EducationalCycle educationalCycle) {
        this.educationalCycle = educationalCycle;
    }
    
    @Override
    public String toString() {
        return "SchoolClass{" + "id=" + id + ", name=" + name + ", number=" + number + ", schoolShift=" + schoolShift + ", educationalCycle=" + educationalCycle + '}';
    }
}
