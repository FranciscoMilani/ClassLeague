package br.ucs.classleague.presentation.controllers;

import br.ucs.classleague.application.services.MatchService;
import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.MatchState;
import br.ucs.classleague.domain.MatchTimer;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.presentation.model.MatchModel;
import br.ucs.classleague.presentation.model.TournamentModel;
import br.ucs.classleague.presentation.views.GUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MatchController {
    
    private final GUI view;
    private final MatchDao dao;
    private MatchModel matchModel;
    private TournamentModel tournamentModel;
    private MatchService matchService;
    
    // Timer do Java Swing
    private Timer timer;
    
    public MatchController(GUI view, MatchModel matchModel, TournamentModel tournamentModel){
        this.view = view;
        this.matchModel = matchModel;
        this.tournamentModel = tournamentModel;
        dao = DaoProvider.getMatchDao();
        matchService = new MatchService();
        matchModel.setTimer(new MatchTimer());
    }
    
    public void beginMatch() {
        Match match = matchModel.getMatch();
        MatchTimer.setState(MatchState.RUNNING);
        LocalDateTime nowTime = LocalDateTime.now();
        
        match.setDateTime(nowTime);
        dao.update(match);
        
        String date = ControllerUtilities.getFormattedDate(nowTime);
        String time = ControllerUtilities.getFormattedTime(nowTime);
            
        view.matchStartDateDataLabel.setText(date);
        view.matchStartTimeDataLabel.setText(time);
        view.addPointButton.setEnabled(true);
        view.removePointButton.setEnabled(true);
    }
    
    public void finishMatch() {
        MatchTimer.setState(MatchState.ENDED);
        view.addPointButton.setEnabled(false);
        view.removePointButton.setEnabled(false);
        view.endMatchButton.setEnabled(true);
        endTimer();
    }
    
    public void endMatch() {
        int option = JOptionPane.showConfirmDialog(
                view.tournamentDialog, 
                "Confirmar encerramento da partida?", 
                "Aviso",
                0
        );
        
        // Marcou "Sim" para encerrar partida
        if (option == 0) {
            Match match = matchModel.getMatch();
            Team winner = matchService.determineMatchWinner(match);
            
            if (winner != null) {
                JOptionPane.showMessageDialog(
                    view.tournamentDialog, 
                    "Partida encerrada", 
                    "",
                    1
                );
            } else {
                Integer opt = showCustomDrawOptionPane(
                        match.getFirst_team().getName(),
                        match.getSecond_team().getName()
                );
                
                if (opt != -1) {
                    matchService.setDrawedMatchWinner(match, opt);   
                } else {
                    return;
                }
            }
            
            view.setAndShowActiveTournamentDialogCard("card1");
        }
    }
    
    public void setMatchInfo() {
        Long id = matchModel.getMatchId();
        Match match = dao.findById(id).get();
        matchModel.setMatch(match);
        
        view.tournamentMatchesTable.clearSelection();
        view.startNewMatchButton.setEnabled(false);
        view.endMatchButton.setEnabled(false);
        view.addPointButton.setEnabled(false);
        view.removePointButton.setEnabled(false);
        view.matchTitle.setText("Partida");
        
        if (!match.getEnded()) {
            fillInfoForMatch(match);
            matchModel.getTimer().resetTimer();
        }
    }
    
    private void initTimer(int maxTimeSeconds) {
        MatchTimer.setState(MatchState.RUNNING);
        
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
        timer.start();
    }
    
    public void startTimer() {
        if (timer == null) {  
            System.out.println(MatchTimer.getState());
            if (!MatchTimer.getState().equals(MatchState.INTERVAL)) {
                int confirmStart = JOptionPane.showConfirmDialog(
                        view.tournamentDialog, 
                        "Deseja iniciar a partida agora?",
                        "Aviso!", 
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmStart != 0) {
                    view.timerPlayButton.setSelected(false);
                    return;
                }
            }
            
            beginMatch();
            Integer roundTimeSeconds = matchModel.getMatch()
                    .getTournament()
                    .getSport()
                    .getMatchDurationMinutes() * 60;

            view.timerProgressBar.setMaximum(roundTimeSeconds);
            initTimer(roundTimeSeconds);
        } else {
            resumeTimer();
        }
    }
    
    public void freezeTimer() { 
        if (timer.isRunning()) {
            timer.stop();
            MatchTimer.setState(MatchState.STOPPED);
        }
    }
    
    public void resumeTimer() {
        timer.setInitialDelay(1000);
        timer.start();
    }
    
    public void resetTimer() {
        if (timer != null){
            freezeTimer();
            
            view.timerProgressBar.setValue(0);
            view.timerCurrentTimeLabel.setForeground(view.defaultColor);
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
            view.timerCurrentTimeLabel.setForeground(view.defaultColor);
            view.timerCurrentTimeLabel.setText("--:--");
            view.timerPeriodNumberLabel.setText("-");

            view.timerPlayButton.setSelected(false);
            view.timerPlayButton.setEnabled(false);
            view.timerNextPeriodButton.setEnabled(false);
            view.timerResetTimerButton.setEnabled(false);
            
            matchModel.getTimer().endTimer();
            timer.stop();
            timer = null;
        }
    }
    
    public void endPeriod() {
        if (timer != null) {
            Integer periods = matchModel
                    .getMatch()
                    .getTournament()
                    .getSport()
                    .getPeriodAmount();
            
            Integer currentPeriod = matchModel
                    .getTimer()
                    .getCurrentPeriod();
            
            if (periods == currentPeriod) {
                finishMatch();
                return;
            }
            
            currentPeriod++;
            freezeTimer();
            
            view.timerPlayButton.setSelected(false);
            view.timerProgressBar.setValue(0);
            view.timerCurrentTimeLabel.setForeground(view.defaultColor);
            view.timerCurrentTimeLabel.setText("00:00");
            
            matchModel.getTimer().prepareNextPeriod();

            view.timerPeriodNumberLabel.setText(currentPeriod.toString());
            view.timerNextPeriodButton.setEnabled(false);
            
            timer = null;
        }
    }
    
    public void fillInfoForMatch(Match match) {
        Tournament tournament = matchModel.getMatch().getTournament();
        LocalDateTime ldt = match.getDateTime();
        String dateText = "aguardando início...";
        String timeText = "aguardando início...";
        
        if (ldt != null) {
            dateText = ControllerUtilities.getFormattedDate(ldt);
            timeText = ControllerUtilities.getFormattedTime(ldt);
        }
        
        // buttons
        view.timerPlayButton.setSelected(false);
        view.timerPlayButton.setEnabled(true);
        view.endMatchButton.setEnabled(false);
        view.timerNextPeriodButton.setEnabled(false);
        
        // labels
        
        view.matchTitle.setText("Partida do torneio " + tournament.getName());
        view.matchStartDateDataLabel.setText(dateText);
        view.matchStartTimeDataLabel.setText(timeText);
        view.matchPhaseDataLabel.setText(match.getTournament().getPhase().getName());
        view.firstTeamScoreLabel.setText("0");
        view.secondTeamScoreLabel.setText("0");
        view.timerCurrentTimeLabel.setText("00:00");
        view.timerPeriodNumberLabel.setText("1");
        view.firstTeamNameLabel.setText(String.format(
                match.getFirst_team().getName() + " [%s]", 
                match.getFirst_team().getAcronym())
        );
        view.secondTeamNameLabel.setText(String.format(
                match.getSecond_team().getName() + " [%s]", 
                match.getSecond_team().getAcronym())
        );
        
        // outros
        view.timerProgressBar.setValue(0);
        
        String periodTimeMins = tournament.getSport().getMatchDurationMinutes().toString(); 
        view.timerEndTimeLabel.setText(periodTimeMins + ":00");
    }
    
    public Integer showCustomDrawOptionPane(String teamOneName, String teamTwoName) {
        String message = "Partida empatada. Defina o vencedor:\n"
                + "Time 1: " + teamOneName
                + "\nTime 2 : " + teamTwoName;
        String title = "Empate!";

        Object[] options = {"Time 1", "Time 2"};

        int result = JOptionPane.showOptionDialog(
                view.tournamentDialog,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null
        );

        return result;
    }
}
