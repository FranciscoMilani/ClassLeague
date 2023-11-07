package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.MatchService;
import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.MatchTimer;
import br.ucs.classleague.domain.MatchTimer.MatchState;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.presentation.model.MatchModel;
import br.ucs.classleague.infrastructure.presentation.model.TournamentModel;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;

public class MatchController {
    
    private final GUI view;
    private final Color defaultColor;
    private final MatchDao dao;
    private MatchModel matchModel;
    private TournamentModel tournamentModel;
    private MatchService matchService;
    
    // Classe Timer do java swing. Roda eventos mesma thread dos componentes swing
    private Timer timer;
    
    public MatchController(GUI view, MatchModel matchModel, TournamentModel tournamentModel){
        this.view = view;
        this.matchModel = matchModel;
        this.tournamentModel = tournamentModel;
        dao = DaoFactory.getMatchDao();
        matchService = new MatchService();
        
        matchModel.setTimer(new MatchTimer());
        defaultColor = UIManager.getColor("timerCurrentTimeLabel.background"); 
    }
    
    public void setMatchInfo() {
        Long id = matchModel.getMatchId();
        Match match = dao.findById(id).get();
        matchModel.setMatch(match);
        
        view.startNewMatchButton.setEnabled(false);
        view.tournamentMatchesTable.clearSelection();
        
        if (match.getEnded()){
            fillInfoForEndedMatch(match);
        } else {
            fillInfoForMatchToBePlayed(match);
        }
    }
    
    public void initTimer(int maxTimeSeconds) {
        MatchTimer.setState(MatchTimer.MatchState.RUNNING);
        
        timer = new Timer(1000, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {   
                if (matchModel.getTimer().getCurrentTime() == maxTimeSeconds) {
                    view.timerNextPeriodButton.setEnabled(true);
                    view.timerCurrentTimeLabel.setForeground(Color.red);
                }
                
                int minutes = Math.floorDiv(matchModel.getTimer().getCurrentTime(), 60);
                int seconds = Math.floorMod(matchModel.getTimer().getCurrentTime(), 60);
                
                String timeText = String.format("%02d:%02d", minutes, seconds);   
                view.timerCurrentTimeLabel.setText(timeText);
                view.timerProgressBar.setValue(matchModel.getTimer().getCurrentTime());
                
                matchModel.getTimer().addCurrentTime(1);
            }
        });
        
        timer.setInitialDelay(0);
    }
    
    public void startTimer() {
        if (timer == null){
            Integer roundTimeSeconds = matchModel.getMatch()
                    .getTournament()
                    .getSport()
                    .getMatchDurationMinutes() * 60;
            
            view.timerProgressBar.setMaximum(roundTimeSeconds);
            initTimer(roundTimeSeconds);
            timer.start();
        } else {
            resumeTimer();
        }
    }
    
    public void freezeTimer(){        
        if(timer.isRunning()){
            MatchTimer.setState(MatchState.STOPPED);
            timer.stop();
        }
    }
    
    public void resumeTimer(){
        timer.setInitialDelay(1000);
        timer.start();
    }
    
    public void resetTimer(){
        if (timer != null){
            freezeTimer();
            
            view.timerProgressBar.setValue(0);
            view.timerCurrentTimeLabel.setForeground(defaultColor);
            view.timerCurrentTimeLabel.setText("00:00");
            view.timerPeriodNumberLabel.setText("1");
            view.timerProgressBar.setValue(0);
           
            view.timerPlayButton.setSelected(false);
            view.timerNextPeriodButton.setEnabled(false);
            
            matchModel.getTimer().resetTimer();
            timer = null;
        }
    }
    
    public void endTimer() {
        if (timer != null) {
            freezeTimer();
            view.timerCurrentTimeLabel.setForeground(defaultColor);
            view.timerCurrentTimeLabel.setText("--:--");
            view.timerPeriodNumberLabel.setText("-");

            view.timerPlayButton.setSelected(false);
            view.timerPlayButton.setEnabled(false);
            view.timerNextPeriodButton.setEnabled(false);
            view.timerResetTimerButton.setEnabled(false);
            
            matchModel.getTimer().resetTimer();
            timer = null;
        }
    }
    
    public void endPeriod() {
        if (timer != null){
            Integer periods = matchModel
                    .getMatch()
                    .getTournament()
                    .getSport()
                    .getPeriodAmount();
            
            Integer currentPeriod = matchModel
                    .getTimer()
                    .getCurrentPeriod();
            
            if (periods == currentPeriod) {
                MatchTimer.setState(MatchState.ENDED);
                view.endMatchButton.setEnabled(true);
                endTimer();
                return;
            }
            
            currentPeriod++;
            freezeTimer();
            
            view.timerPlayButton.setSelected(false);
            view.timerProgressBar.setValue(0);
            view.timerCurrentTimeLabel.setForeground(defaultColor);
            view.timerCurrentTimeLabel.setText("00:00");
            view.timerEndTimeLabel.setText("00:00");
            
            matchModel.getTimer().prepareNextPeriod();

            view.timerPeriodNumberLabel.setText(currentPeriod.toString());
            view.timerNextPeriodButton.setEnabled(false);
            
            timer = null;
        }
    }
    
    public void fillInfoForMatchToBePlayed(Match match) {  
        LocalDate date = match.getDateTime().toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(
                FormatStyle.LONG
        );
        String dateText = date.format(formatter);
        
        view.matchStartTimeDataLabel.setText(dateText);
        view.matchEndTimeDataLabel.setText("---");
        view.matchPhaseDataLabel.setText(
                match.getTournament()
                        .getPhase()
                        .getName()
        );
        view.firstTeamNameLabel.setText(match.getFirst_team().getName());
        view.secondTeamNameLabel.setText(match.getSecond_team().getName());
        
        String periodTimeMins = matchModel.getMatch()
            .getTournament()
            .getSport()
            .getMatchDurationMinutes()
        .toString();
        
        view.timerEndTimeLabel.setText(periodTimeMins + ":00");
    }
    
    public void fillInfoForEndedMatch(Match match) {  
        LocalDate date = match.getDateTime().toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(
                FormatStyle.LONG
        );
        String dateText = date.format(formatter);
        
        // buttons
        view.endMatchButton.setEnabled(false);
        view.timerPlayButton.setEnabled(false);
        view.timerNextPeriodButton.setEnabled(false);
        
        // labels
        view.matchStartTimeDataLabel.setText(dateText);
        view.matchEndTimeDataLabel.setText("---");
        view.matchPhaseDataLabel.setText(match.getTournament().getPhase().getName());
        view.firstTeamNameLabel.setText(match.getFirst_team().getName());
        view.secondTeamNameLabel.setText(match.getSecond_team().getName());
        
        // outros
        view.timerProgressBar.setValue(0);
        
        String periodTimeMins = match
            .getTournament()
            .getSport()
            .getMatchDurationMinutes()
        .toString();
        
        view.timerEndTimeLabel.setText(periodTimeMins + ":00");
    }
    
    public void endMatch() {
        int option = JOptionPane.showConfirmDialog(
                view.tournamentDialog, 
                "Confirmar encerramento da partida?", 
                "Encerrando partida",
                0
        );
        
        // Marcou "Sim" para encerrar partida
        if (option == 0) {
            Match match = matchModel.getMatch();
            Team winner = matchService.determineMatchWinner(match);
            
            if (winner != null) {
                match.setEnded(true);
                match.setWinner(winner);
                dao.update(match);
            } else {
                // implementar logica de empate
                JOptionPane.showMessageDialog(view.tournamentDialog, "Partida empatada...");
            }
        }
    }
}
