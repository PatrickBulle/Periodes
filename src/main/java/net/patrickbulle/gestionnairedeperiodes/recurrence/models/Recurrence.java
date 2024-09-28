package net.patrickbulle.gestionnairedeperiodes.recurrence.models;

import lombok.Getter;
import lombok.Setter;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFin;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public abstract class Recurrence {
    private UUID id;
    private LocalDate dateDebut;
    private RecurrenceFin fin;
}
