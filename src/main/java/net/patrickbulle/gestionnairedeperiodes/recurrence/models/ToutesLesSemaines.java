package net.patrickbulle.gestionnairedeperiodes.recurrence.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.patrickbulle.gestionnairedeperiodes.recurrence.Jour;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToutesLesSemaines extends Recurrence {

    private Integer toutesLesNSemaines;
    private List<Jour> repeterLe;
}
