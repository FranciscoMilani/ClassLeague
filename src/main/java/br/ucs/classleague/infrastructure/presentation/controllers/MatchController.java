package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.UIManager;

public class MatchController {
    
    private GUI frame;
    private Timer timer;
    private final Color defaultColor;
    
    public MatchController(GUI frame){
        this.frame = frame;
        defaultColor = UIManager.getColor("timerCurrentTimeLabel.background"); 
    }
    
    public void initTimer(int maxTimeSeconds){     
        timer = new Timer(1000, new ActionListener() {
            int currSeconds = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {      
                if (currSeconds == maxTimeSeconds) {
                    frame.timerCurrentTimeLabel.setForeground(Color.red);
                }
                
                int minutes = Math.floorDiv(currSeconds, 60);
                int seconds = Math.floorMod(currSeconds, 60);
                
                String timeText = String.format("%02d:%02d", minutes, seconds);   

                frame.timerCurrentTimeLabel.setText(timeText);
                frame.timerProgressBar.setValue(currSeconds++);   
            }
        });
        
        timer.setInitialDelay(0);
    }
    
    public void startTimer(int roundTimeSeconds){
        if (timer == null){
            frame.timerProgressBar.setMaximum(roundTimeSeconds);
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
            frame.timerPlayButton.setSelected(false);
            frame.timerProgressBar.setValue(0);
            frame.timerCurrentTimeLabel.setForeground(defaultColor);
            frame.timerCurrentTimeLabel.setText("00:00");
            frame.timerEndTimeLabel.setText("00:00");
            timer = null;
        }
    }
}
