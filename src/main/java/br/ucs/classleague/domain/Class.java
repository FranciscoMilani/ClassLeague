package br.ucs.classleague.domain;

import java.io.Serializable;

/**
 *
 * @author Francisco
 */
public class Class implements Serializable{

    private Long id;
    private String name;
    private Integer schoolShift;
    private Integer number;
    private EducationalCycle educationalCycle;

    public static enum EducationalCycle {
        ELEMENTARY_SCHOOL("Ensino Fundamental"),
        HIGH_SCHOOL("Ensino Médio");

        private String name;

        private EducationalCycle(String name) {
            this.name = name;
        }
    }

    public Class() {
    }

    public Class(Long id, String name, Integer schoolShift, Integer number, EducationalCycle educationalCycle) {
        this.id = id;
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

    public Integer getSchoolShift() {
        return schoolShift;
    }

    public void setSchoolShift(Integer schoolShift) {
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
