package net.patrickbulle.gestionnairedeperiodes.recurrence.exception;

public class RecurrenceException extends RuntimeException {
    public static final String CLE_NON_TROUVEE = "Clé '%s' non trouvée pour la fin de récurrence";
    public static final String CLE_VALEUR_INCONNUE = "Valeur '%s' non reconnue pour la clé '%s'";
    public static final String ERREUR_PARSE_RECURRENCE = "Une erreur est survenue lors de la désérialisation de la récurrence";
    public static final String ERREUR_PARSE_RECURRENCE_FIN = "Une erreur est survenue lors de la désérialisation de la fin de récurrence";
    public static final String ERREUR_PARSE_RECURRENCE_FIN_LE = "Une erreur est survenue lors de la désérialisation de la fin de récurrence après le";
    public static final String ERREUR_PARSE_RECURRENCE_FIN_APRES = "Une erreur est survenue lors de la désérialisation de la fin de récurrence après n occurrences";

    public RecurrenceException(String message) {
        super(message);
    }

    public RecurrenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
