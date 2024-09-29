package net.patrickbulle.gestionnairedeperiodes.recurrence.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFin;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class RecurrenceTousLesJours extends Recurrence {
    protected static final String CLE_TOUS_LES_N_JOURS = "tousLesNJours";
    private Integer tousLesNJours;

    public RecurrenceTousLesJours() {
        super();
        type = TOUS_LES_JOURS;
    }

    public RecurrenceTousLesJours(UUID id, LocalDate debut, Integer tousLesNJours, RecurrenceFin fin) {
        super(id, debut, fin);
        type = TOUS_LES_JOURS;
        this.tousLesNJours = tousLesNJours;
    }

    @Override
    protected void doToJson(ObjectNode noeud) {
        if (tousLesNJours == null) {
            noeud.putNull(CLE_TOUS_LES_N_JOURS);
        } else {
            noeud.put(CLE_TOUS_LES_N_JOURS, tousLesNJours);
        }
    }
}
