package net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class RecurrenceFinLe extends RecurrenceFin {
    protected static final String CLE_DATE = "date";

    private LocalDate date;

    public RecurrenceFinLe() {
        super();
        type = FIN_LE;
    }

    public RecurrenceFinLe(LocalDate date) {
        super();
        type = FIN_LE;
        this.date = date;
    }

    @Override
    protected void doToJson(ObjectNode noeud) {
        if (date == null) {
            noeud.putNull(CLE_DATE);
        } else {
            noeud.put(CLE_DATE, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }
}
