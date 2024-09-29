package net.patrickbulle.gestionnairedeperiodes.recurrence.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import net.patrickbulle.gestionnairedeperiodes.recurrence.Jour;
import net.patrickbulle.gestionnairedeperiodes.recurrence.exception.RecurrenceException;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.UUID;

import static java.lang.Math.pow;
import static net.patrickbulle.gestionnairedeperiodes.recurrence.exception.RecurrenceException.CLE_VALEUR_INCONNUE;
import static net.patrickbulle.gestionnairedeperiodes.recurrence.exception.RecurrenceException.ERREUR_PARSE_RECURRENCE;
import static net.patrickbulle.gestionnairedeperiodes.recurrence.models.RecurrenceTousLesJours.CLE_TOUS_LES_N_JOURS;
import static net.patrickbulle.gestionnairedeperiodes.recurrence.models.RecurrenceToutesLesSemaines.CLE_MASQUE_JOURS;
import static net.patrickbulle.gestionnairedeperiodes.recurrence.models.RecurrenceToutesLesSemaines.CLE_TOUTES_LES_N_SEMAINES;

@Getter
@Setter
public abstract class Recurrence {
    protected static final int TOUS_LES_JOURS = 1;
    protected static final int TOUTES_LES_SEMAINES = 2;
    private static final String CLE_TYPE = "type";
    private static final String CLE_ID = "id";
    private static final String CLE_DEBUT = "dateDebut";
    private static final String CLE_FIN = "fin";
    protected Integer type;
    protected UUID id;
    protected LocalDate dateDebut;
    protected RecurrenceFin fin;

    protected Recurrence() {
        super();
        id = UUID.randomUUID();
    }

    protected Recurrence(UUID id, LocalDate dateDebut, RecurrenceFin fin) {
        super();
        this.id = id;
        this.dateDebut = dateDebut;
        this.fin = fin;
    }


    protected void doToJson(ObjectNode noeud) {
        // Ne rien faire
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode noeudRacine = mapper.createObjectNode();

        noeudRacine.put(CLE_TYPE, type);
        if (id == null) {
            noeudRacine.putNull(CLE_ID);
        } else {
            noeudRacine.put(CLE_ID, id.toString());
        }
        if (dateDebut == null) {
            noeudRacine.putNull(CLE_DEBUT);
        } else {
            noeudRacine.put(CLE_DEBUT, dateDebut.format(DateTimeFormatter.ISO_DATE));
        }
        if (fin == null) {
            noeudRacine.putNull(CLE_FIN);
        } else {
            noeudRacine.set(CLE_FIN, fin.toJson());
        }
        doToJson(noeudRacine);
        return noeudRacine.toPrettyString();
    }

    public static Recurrence fromJson(String json) throws RecurrenceException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode noeudRacine;
        Recurrence recurrence = null;

        try {
            noeudRacine = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RecurrenceException(ERREUR_PARSE_RECURRENCE, e);
        }

        if (noeudRacine != null) {
            JsonNode typeNode = noeudRacine.get(CLE_TYPE);
            if (typeNode != null) {
                recurrence = switch (typeNode.asInt()) {
                    case TOUS_LES_JOURS -> getRecurrenceTousLesJours(noeudRacine);
                    case TOUTES_LES_SEMAINES -> getRecurrenceToutesLesSemaines(noeudRacine);
                    default -> throw new RecurrenceException(String.format(CLE_VALEUR_INCONNUE, typeNode.asInt(), CLE_TYPE));
                };
            }

            JsonNode id = noeudRacine.get(CLE_ID);
            if (id != null) {
                try {
                    recurrence.setId(UUID.fromString(id.asText()));
                } catch (Exception e) {
                    throw new RecurrenceException(ERREUR_PARSE_RECURRENCE, e);
                }
            } else {
                throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_ID));
            }

            JsonNode debut = noeudRacine.get(CLE_DEBUT);
            if (debut != null) {
                try {
                    String[] date = debut.asText().split("-");

                    recurrence.setDateDebut(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
                } catch (Exception e) {
                    throw new RecurrenceException(ERREUR_PARSE_RECURRENCE, e);
                }
            } else {
                throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_DEBUT));
            }

            JsonNode fin = noeudRacine.get(CLE_FIN);
            if (fin != null) {
                recurrence.setFin(RecurrenceFin.fromJson(fin));
            } else {
                throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_FIN));
            }

        }
        return recurrence;
    }

    private static RecurrenceTousLesJours getRecurrenceTousLesJours(JsonNode json) throws RecurrenceException {
        RecurrenceTousLesJours recurrenceTousLesJours = new RecurrenceTousLesJours();
        JsonNode noeud = json.get(CLE_TOUS_LES_N_JOURS);


        if (noeud != null) {
            recurrenceTousLesJours.setTousLesNJours(noeud.asInt());
            return recurrenceTousLesJours;
        }
        throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_TOUS_LES_N_JOURS));
    }

    private static RecurrenceToutesLesSemaines getRecurrenceToutesLesSemaines(JsonNode json) throws RecurrenceException {
        RecurrenceToutesLesSemaines recurrenceToutesLesSemaines = new RecurrenceToutesLesSemaines();
        JsonNode noeud = json.get(CLE_TOUTES_LES_N_SEMAINES);

        if (noeud != null) {
            recurrenceToutesLesSemaines.setToutesLesNSemaines(noeud.asInt());
        } else {
            throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_TOUTES_LES_N_SEMAINES));
        }
        noeud = json.get(CLE_MASQUE_JOURS);
        if (noeud != null) {
            Integer masque = noeud.asInt();

            recurrenceToutesLesSemaines.setRepeterLe(new HashSet<>());
            for (Jour jour : Jour.values()) {
                if ((masque & jour.indice) > 0) {
                    recurrenceToutesLesSemaines.getRepeterLe().add(jour);
                }
            }
        } else {
            throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_MASQUE_JOURS));
        }
        return recurrenceToutesLesSemaines;
    }

}
