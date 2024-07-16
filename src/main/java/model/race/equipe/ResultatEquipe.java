package model.race.equipe;

import model.race.equipe.RankEquipe;

public class ResultatEquipe {

    RankEquipe[] equipes;

    public RankEquipe[] getEquipes() {
        return equipes;
    }

    public void setEquipes(RankEquipe[] equipes) {
        for (RankEquipe equipe : equipes) {
            equipe.setResultatEquipe(this);
        }
        this.equipes = equipes;
    }
    
    public boolean checkDoublon(RankEquipe equipe) {
        for (RankEquipe rank : this.getEquipes()) {
            if (!rank.getId().equals(equipe.getId()) && rank.getRang() == equipe.getRang()) {
                return true;
            }
        }
        return false;
    }
    
}
