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

    @Override
    protected void doToJson(ObjectNode noeud) {
        if (nbOccurrences == null) {
            noeud.putNull(CLE_NB_OCURRENCES);
        } else {
            noeud.put(CLE_NB_OCURRENCES, nbOccurrences);
        }
    }

    @Override
    public String toString() {
        return nbOccurrences == null ? "Nombre d'occurrences non renseigné" : String.format("Fin après %s occurrence%s", nbOccurrences, nbOccurrences > 1 ? "s" : "");
    }
}
