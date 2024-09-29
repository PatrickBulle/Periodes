package net.patrickbulle.gestionnairedeperiodes.recurrence.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import net.patrickbulle.gestionnairedeperiodes.recurrence.Jour;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFin;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class RecurrenceToutesLesSemaines extends Recurrence {
    protected static final String CLE_TOUTES_LES_N_SEMAINES = "toutesLesNSemaines";
    protected static final String CLE_MASQUE_JOURS = "jours";
    private Integer toutesLesNSemaines;
    private Set<Jour> repeterLe;

    public RecurrenceToutesLesSemaines() {
        super();
        type = TOUTES_LES_SEMAINES;
    }

    public RecurrenceToutesLesSemaines(UUID id, LocalDate debut, Integer toutesLesNSemaines, Set<Jour> repeterLe, RecurrenceFin fin) {
        super(id, debut, fin);
        type = TOUTES_LES_SEMAINES;
        this.toutesLesNSemaines = toutesLesNSemaines;
        this.repeterLe = repeterLe;
    }

    protected Integer getMasqueSemaine() {
        Integer masqueSemaine = 0;
        if (repeterLe != null && !repeterLe.isEmpty()) {
            for (Jour jour : repeterLe) {
                if (jour != null) {
                    masqueSemaine += jour.indice;
                }
            }
        }
        return masqueSemaine;
    }

    @Override
    protected void doToJson(ObjectNode noeud) {
        if (toutesLesNSemaines == null) {
            noeud.putNull(CLE_TOUTES_LES_N_SEMAINES);
        } else {
            noeud.put(CLE_TOUTES_LES_N_SEMAINES, toutesLesNSemaines);
        }
        noeud.put(CLE_MASQUE_JOURS, getMasqueSemaine());
    }
}
