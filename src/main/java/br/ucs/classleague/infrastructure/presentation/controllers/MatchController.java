package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.domain.MatchTimer;
import br.ucs.classleague.domain.MatchTimer.MatchState;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.presentation.model.MatchModel;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.UIManager;

public class MatchController {
    
    private final GUI view;
    private final Color defaultColor;
    private final MatchDao dao = DaoFactory.getMatchDao();
    private MatchModel matchModel;
    private Timer timer;
    
    public MatchController(GUI view, MatchModel matchModel){
        this.view = view;
        this.matchModel = matchModel;
        
        matchModel.setTimer(new MatchTimer());
        defaultColor = UIManager.getColor("timerCurrentTimeLabel.background"); 
    }
    
    public void setMatchInfo(String matchId){
        Long mId = Long.parseLong(matchId);
        matchModel.setMatch(dao.findById(mId).get());
        matchModel.setInfo();
    }
    
    public void initTimer(int maxTimeSeconds){   
        MatchTimer.setState(MatchTimer.MatchState.RUNNING);
        
        timer = new Timer(1000, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {      
                if (matchModel.getTimer().getCurrentTime() == maxTimeSeconds) {
                    view.timerDecreaseTimeButton.setEnabled(true);
                    view.timerCurrentTimeLabel.setForeground(Color.red);
                }
                
                int minutes = Math.floorDiv(matchModel.getTimer().getCurrentTime(), 60);
                int seconds = Math.floorMod(matchModel.getTimer().getCurrentTime(), 60);
                
                String timeText = String.format("%02d:%02d", minutes, seconds);   

                view.timerCurrentTimeLabel.setText(timeText);
                view.timerProgressBar.setValue(matchModel.getTimer().addCurrentTime(1));   
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
            view.timerPlayButton.setSelected(false);
            view.timerProgressBar.setValue(0);
            view.timerCurrentTimeLabel.setForeground(defaultColor);
            view.timerCurrentTimeLabel.setText("00:00");
            view.timerEndTimeLabel.setText("00:00");
            MatchTimer.setState(MatchState.WAITING);
            timer = null;
        }
    }
    
    public void startNextPeriod() {
        MatchTimer.setState(MatchState.RUNNING);
        matchModel.getTimer().addPeriod();
    }
}
