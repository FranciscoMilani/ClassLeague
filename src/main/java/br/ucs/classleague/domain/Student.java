package br.ucs.classleague.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student")
public class Student extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private SchoolClass schoolClass;
    private String fatherName;
    private String motherName;
    private Integer pontos;
    
    public Student(){
    }

    public Student(SchoolClass schoolClass, String fatherName, String motherName, Integer pontos, String name, String surname, LocalDate birthDate, String gender, String telephone, String cpf) {
        super(name, surname, birthDate, gender, telephone, cpf);
        this.schoolClass = schoolClass;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.pontos = pontos;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", schoolClass=" + schoolClass + ", fatherName=" + fatherName + ", motherName=" + motherName + ", pontos=" + pontos + '}';
    }
    
    
}
