package net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecurrenceFinApres extends RecurrenceFin {
    protected static final String CLE_NB_OCURRENCES = "nbOccurrences";
    private Integer nbOccurrences;

    public RecurrenceFinApres() {
        super();
        type = FIN_APRES;
    }

    public RecurrenceFinApres(Integer nbOccurrences) {
        super();
        type = FIN_APRES;
        this.nbOccurrences = nbOccurrences;
    }

    @Override
    protected void doToJson(ObjectNode noeud) {
        if (nbOccurrences == null) {
            noeud.putNull(CLE_NB_OCURRENCES);
        } else {
            noeud.put(CLE_NB_OCURRENCES, nbOccurrences);
        }
    }
}
