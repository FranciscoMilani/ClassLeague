package br.ucs.classleague.application.services;

import br.ucs.classleague.domain.Coach;
import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.domain.Sport;
import br.ucs.classleague.domain.Sport.SportsEnum;
import br.ucs.classleague.domain.Student;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.domain.TournamentTeamKey;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.StudentDao;
import br.ucs.classleague.infrastructure.data.TeamDao;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegisterService {
    
    private ClassDao classDao;
    private TeamDao teamDao;
    private ClassService classService;
    private TournamentDao tournamentDao;
    private StudentDao studentDao;
    private TournamentService tournamentService;
    private TeamRegisterService teamRegisterService;
    
    public RegisterService() {
        classDao = DaoProvider.getClassDao();
        teamDao = DaoProvider.getTeamDao();
        tournamentDao = DaoProvider.getTournamentDao();
        studentDao = DaoProvider.getStudentDao();
        classService = new ClassService();
        teamRegisterService = new TeamRegisterService();
        tournamentService = new TournamentService();
    }
    
    public Map<String, Object> registerClass(SchoolClass schoolClass) {
        Map<String, Object> response = new HashMap<>();  
        SchoolClass sc = classDao.create(schoolClass);
        
        response.put("isValid", true);
        response.put("message", "Cadastro realizado");
        
        if (sc == null) {
            response.put("isValid", false);
            response.put("message", "Erro ao cadastrar.");
        }
        
        return response;
    }
    
    public Map<String, Object> validateClassFields(String name, String number) {
        
        Map<String, Object> response = new HashMap<>();
        
        response.put("isValid", true);
        response.put("message", "");

        if (name.isBlank()) {
            response.put("isValid", false);
            response.put("message",  "Campo \"Nome\" não pode estar vazio.");
            
            return response;
        }

        try {
            int intNumber = Integer.parseInt(number);
            if (!classService.checkForValidNumber(intNumber)) {
                response.put("isValid", false);
                response.put("message", "Campo \"Número\" da turma já existe, insira outro.");
                
                return response;
            }
        } catch (NumberFormatException nfe) {
            response.put("isValid", false);
            response.put("message", "Campo \"Número\" precisa conter um número inteiro.");
            
            return response;
        }
        
        return response;
    }
    
    public Map<String, Object> registerTeam(Team team, List<Long> ids, int sportTypeIndex, String acronym) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("isValid", true);
        response.put("message", "Cadastro realizado.");
        
        boolean availableAcronym = teamRegisterService.verifyAvailableAcronym(acronym);
        if (!availableAcronym) {
            response.put("isValid", false);
            response.put("message", "Acrônimo já existe, tente outro.");
            return response;
        }
        
        for (Long id : ids) {
            SportsEnum sportTypeEnum = Sport.SportsEnum.values()[sportTypeIndex];
            boolean availableStudent = teamRegisterService.verifyAvailableStudent(id, sportTypeEnum);
            if (!availableStudent) {
                response.put("isValid", false);
                response.put("message", "Estudantes marcados já estão cadastrados.");
                return response;
            }
        }

        Team persistedTeam = teamDao.create(team);
        boolean success = teamRegisterService.assignTeamMembers(ids, persistedTeam.getId());
        if (!success) {
            response.put("isValid", false);
            response.put("message", "Erro ao cadastrar aluno no time.");
            return response;
        }
        
        return response;
    }
    
    public Map<String, Object> validateTeamFields(String name, String acronym, Coach coach, Object classField) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("isValid", true);
        response.put("message", "");
        
        if (name.equals("") || acronym.equals("")) {
            response.put("isValid", false);
            response.put("message", "Preencha todos os campos de texto.");
            return response;
        }
        
        if (coach == null) {
            response.put("isValid", false);
            response.put("message", "Um treinador precisa ser selecionado.");
            return response;
        }
        
        if (classField == null || classField == "") {
            response.put("isValid", false);
            response.put("message", "Uma turma precisa ser selecionada.");
            return response;
        }
        
        return response;
    }
    
    public Map<String, Object> registerTournament(List<Long> teamIds, Tournament tournament) {
        Map<String, Object> response = new HashMap<>();
        response.put("isValid", true);
        response.put("message", "");
        
        try {
            Long tournamentId = tournamentDao.create(tournament).getId();
            assignTeamsToTournament(teamIds, tournamentId);
            tournamentService.createInitialSimpleClashes(tournament);
            response.put("isValid", false);
            response.put("message", "Torneio cadastrado com sucesso.");
            return response;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            response.put("isValid", false);
            response.put("message", "Erro ao cadastrar torneio.");
            return response;
            
        }   
    }
    
    public Map<String, Object> registerStudent(Student student) {
        Map<String, Object> response = new HashMap<>();
        response.put("isValid", true);
        response.put("message", "");
        
        boolean existsCpf = studentDao.existsStudentCPF(student.getCpf());
        if (!existsCpf) {
            response.put("isValid", true);
            response.put("message", "CPF já existe.");
            return response;
        }
        
        studentDao.create(student); 
        return response;
    }
    
    public Map<String, Object> verifyTournamentFields(Integer count, Integer phaseIndex) {
        Map<String, Object> response = new HashMap<>();
        response.put("isValid", true);
        response.put("message", "");
        
        if (!isPowerOfTwo(count) || count < 4) {
            response.put("isValid", false);
            response.put("message", "Quantidade de times incorreta para realização do chaveamento. Selecione potências de 2 maiores ou igual a 4.");
            return response;
        }
        
        if (count > 16) {
            response.put("isValid", false);
            response.put("message", "Quantidade de times excedida, limite: 16 times.");
            return response;
        }
        
        return response;
    }
    
    private void assignTeamsToTournament(List<Long> teamIds, Long tournamentId) {
        Tournament tournament = tournamentDao.findById(tournamentId).get();
        Set<TournamentTeam> ttSet = new HashSet<>();
        
        for (Long teamId : teamIds) {
            Team team = teamDao.findById(teamId).get();

            // Cria chave composta para criar a entidade de junção Tournament-Team
            TournamentTeam tt = new TournamentTeam();
            tt.setTournamentTeamKey(new TournamentTeamKey(tournamentId, teamId));
            tt.setTournament(tournament);
            tt.setTeam(team);

            ttSet.add(tt);
        }
        
        // Atribui ao torneio o conjunto das entidades de junção criadas
        tournament.setTournamentTeam(ttSet);
        
        tournamentDao.update(tournament);
        tournamentDao.refresh(tournament);
    }
    
    public LocalDate parseStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate;
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
    
    public static boolean isPowerOfTwo(int number) {
        if (number <= 0) {
            return false;
        }
        
        // Verifica se o número é uma potência de 2.
        return (number & (number - 1)) == 0 && (number > 1);
    }
}
