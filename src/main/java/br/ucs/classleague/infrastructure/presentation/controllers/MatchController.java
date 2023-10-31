package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.MatchTimer;
import br.ucs.classleague.domain.MatchTimer.MatchState;
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
import javax.swing.Timer;
import javax.swing.UIManager;

public class MatchController {
    
    private final GUI view;
    private final Color defaultColor;
    private final MatchDao dao;
    
    // Classe Timer do java swing. Roda eventos mesma thread dos componentes swing
    private Timer timer;
    
    private MatchModel matchModel;
    private TournamentModel tournamentModel;
    
    public MatchController(GUI view, MatchModel matchModel, TournamentModel tournamentModel){
        this.view = view;
        this.matchModel = matchModel;
        this.tournamentModel = tournamentModel;
        dao = DaoFactory.getMatchDao();
        
        matchModel.setTimer(new MatchTimer());
        defaultColor = UIManager.getColor("timerCurrentTimeLabel.background"); 
    }
    
    public void setMatchInfo(String matchId) {
        Long mId = Long.parseLong(matchId);
        matchModel.setMatch(dao.findById(mId).get());
        //matchModel.setInfo();
        fillMatchInfo();
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
            MatchTimer.setState(MatchState.WAITING);
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
    
    public void fillMatchInfo() {
        Match match = matchModel.getMatch();
        
        LocalDate date = match.getDateTime().toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        String dateText = date.format(formatter);
        
        view.matchStartTimeDataLabel.setText(dateText);
        view.matchEndTimeDataLabel.setText("---");
        view.matchPhaseDataLabel.setText(
                match.getTournament()
                        .getPhase()
                        .getName()
        );
    }
    
    public void endMatch() {
        Match match = matchModel.getMatch();
        match.setEnded(true);
        dao.update(match);
    }
}
