package br.ucs.classleague.domain;

/**
 *
 * @author Francisco
 */

public class Class {
    private String name;
    private Integer schoolShift;
    private EducationalCycle educationalCycle;
    
    public static enum EducationalCycle{
        ELEMENTARY_SCHOOL("Ensino Fundamental"),
        HIGH_SCHOOL("Ensino Médio");
        
        private String name;
    
        private EducationalCycle(String name) {
            this.name = name;
        } 
    }
}
