package net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin;

public class RecurrenceFinJamais extends RecurrenceFin {

    public RecurrenceFinJamais() {
        super();
        type = FIN_JAMAIS;
    }

    @Override
    public String toString() {
        return "Pas de fin";
    }
}
