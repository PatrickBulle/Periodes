package net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.patrickbulle.gestionnairedeperiodes.recurrence.exception.RecurrenceException;

import java.time.LocalDate;

import static net.patrickbulle.gestionnairedeperiodes.recurrence.exception.RecurrenceException.CLE_VALEUR_INCONNUE;
import static net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinLe.CLE_DATE;
import static net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinApres.CLE_NB_OCURRENCES;

public abstract class RecurrenceFin {
    protected static final int FIN_JAMAIS = 1;
    protected static final int FIN_LE = 2;
    protected static final int FIN_APRES = 4;

    private static final String CLE_TYPE = "type";

    protected Integer type;

    protected void doToJson(ObjectNode noeud) {
        // Ne rien faire
    }

    public JsonNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode noeudRacine = mapper.createObjectNode();

        noeudRacine.put(CLE_TYPE, type);
        doToJson(noeudRacine);
        return noeudRacine;
    }

    public static RecurrenceFin fromJson(String json) throws RecurrenceException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode noeudRacine = null;

        try {
            noeudRacine = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RecurrenceException(RecurrenceException.ERREUR_PARSE_RECURRENCE_FIN, e);
        }

        if (noeudRacine != null) {
            JsonNode typeNode = noeudRacine.get(CLE_TYPE);
            if (typeNode != null) {
                return switch (typeNode.asInt()) {
                    case FIN_JAMAIS -> new RecurrenceFinJamais();
                    case FIN_LE -> getRecurrenceFinLe(noeudRacine);
                    case FIN_APRES -> getRecurrenceFinApres(noeudRacine);
                    default ->
                            throw new RecurrenceException(String.format(CLE_VALEUR_INCONNUE, typeNode.asInt(), CLE_TYPE));
                };
            }
            throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_TYPE));
        }
        return null;
    }

    private static RecurrenceFinLe getRecurrenceFinLe(JsonNode json) throws RecurrenceException {
        JsonNode dateNode = json.get(CLE_DATE);

        if (dateNode != null) {
            try {
                RecurrenceFinLe recurrenceFinLe = new RecurrenceFinLe();
                String[] valeurs = dateNode.asText().split("-");

                recurrenceFinLe.setDate(LocalDate.of(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]), Integer.parseInt(valeurs[2])));
                return recurrenceFinLe;
            } catch (Exception e) {
                throw new RecurrenceException(RecurrenceException.ERREUR_PARSE_RECURRENCE_FIN_LE, e);
            }
        }
        throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_DATE));
    }

    private static RecurrenceFinApres getRecurrenceFinApres(JsonNode json) throws RecurrenceException {
        JsonNode nbOccurrencesNode = json.get(RecurrenceFinApres.CLE_NB_OCURRENCES);

        if (nbOccurrencesNode != null) {
            try {
                RecurrenceFinApres recurrenceFinApres = new RecurrenceFinApres();

                recurrenceFinApres.setNbOccurrences(Integer.parseInt(nbOccurrencesNode.asText()));
                return recurrenceFinApres;
            } catch (Exception e) {
                throw new RecurrenceException(RecurrenceException.ERREUR_PARSE_RECURRENCE_FIN_APRES, e);
            }
        }
        throw new RecurrenceException(String.format(RecurrenceException.CLE_NON_TROUVEE, CLE_NB_OCURRENCES));
    }

}
