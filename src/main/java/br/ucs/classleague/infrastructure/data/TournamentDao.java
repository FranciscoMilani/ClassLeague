/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ucs.classleague.infrastructure.data;

import br.ucs.classleague.domain.Tournament;

public class TournamentDao extends GenericDAO<Tournament, Long>{
        public TournamentDao() {
        super(Tournament.class);
    }
}
