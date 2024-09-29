package net.patrickbulle.gestionnairedeperiodes.recurrence.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFin;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinApres;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinJamais;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinLe;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RecurrenceTousLesJours extends Recurrence {
    protected static final String CLE_TOUS_LES_N_JOURS = "tousLesNJours";
    private Integer tousLesNJours;

    public RecurrenceTousLesJours() {
        super();
        type = TOUS_LES_JOURS;
        tousLesNJours = 1;
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

    @Override
    protected boolean doIsDateValide(LocalDate date) {
        return switch (fin) {
            case RecurrenceFinJamais finJamais -> getDateValideFinJamais(date);
            case RecurrenceFinLe finLe -> getDateValideFinLe(date, finLe.getDate());
            case RecurrenceFinApres finApres -> getDateValideFinApres(date, finApres.getNbOccurrences());
            default -> false;
        };
    }

    private boolean getDateValideFinJamais(LocalDate date) {
        LocalDate dateTest = dateDebut;

        while (true) {
            if (dateTest.equals(date)) {
                return true;
            }
            if (dateTest.isAfter(date)) {
                return false;
            }
            dateTest = dateTest.plusDays(tousLesNJours);
        }
    }

    private boolean getDateValideFinLe(LocalDate date, LocalDate dateFin) {
        LocalDate dateTest = dateDebut;

        while (true) {
            if (dateTest.equals(date)) {
                return true;
            }
            if (dateTest.isAfter(date) || dateTest.isAfter(dateFin)) {
                return false;
            }
        }
    }

    private boolean getDateValideFinApres(LocalDate date, int nbOccurrences) {
        LocalDate dateTest = dateDebut;
        int compteur = 0;

        while (true) {
            compteur++;
            if (dateTest.equals(date)) {
                return true;
            }
            if (dateTest.isAfter(date) || compteur > nbOccurrences) {
                return false;
            }
        }
    }

    @Override
    public List<LocalDate> getDates(LocalDate depart, Integer nbDates) {
        return List.of();
    }
}
