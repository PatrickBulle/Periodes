package net.patrickbulle.gestionnairedeperiodes;

import net.patrickbulle.gestionnairedeperiodes.recurrence.models.Recurrence;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.RecurrenceTousLesJours;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinJamais;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecurrenceTousLesJoursSansDateDeFinTest {
    private Recurrence recurrence;

    @Test
    void testRecurrenceDateValideQuandLaDateDeDebutEstNull() {
        // Given
        // Une récurrence tous les jours sans date de fin
        recurrence = new RecurrenceTousLesJours();

        // When
        // La date de début n'est pas initialisée
        recurrence.setDateDebut(null);
        boolean dateValide = recurrence.isDateValide(LocalDate.now());

        // Then
        // La validité ne doit pas être vérifiée
        assertFalse(dateValide);
    }

    @Test
    void testRecurrenceDateValideQuandLaFinEstNull() {
        // Given
        // Une récurrence tous les jours où la fin n'est pas définie
        recurrence = new RecurrenceTousLesJours();

        // When
        // La date de début n'est pas initialisée
        recurrence.setDateDebut(LocalDate.now());
        recurrence.setFin(null);
        boolean dateValide = recurrence.isDateValide(LocalDate.now());

        // Then
        // La validité ne doit pas être vérifiée
        assertFalse(dateValide);
    }

    @Test
    void testRecurrenceDateValideAnterieureALaDateDeDebut() {
        // Given
        // Une récurrence tous les jours démarrant à la date du jour et n'ayant pas de fin
        recurrence = new RecurrenceTousLesJours(UUID.randomUUID(), LocalDate.now(), 1, new RecurrenceFinJamais());

        // When
        // Vérifier la validité d'une date antérieure d'un jour à la date du jour
        LocalDate dateTest = LocalDate.now().minusDays(1);
        boolean dateValide = recurrence.isDateValide(dateTest);

        // Then
        // La date testée ne doit pas être valide
        assertFalse(dateValide);
    }

    @Test
    void testRecurrenceDateValideEgaleALaDateDeDebut() {
        // Given
        // Une récurrence tous les jours démarrant à la date du jour et n'ayant pas de fin
        recurrence = new RecurrenceTousLesJours(UUID.randomUUID(), LocalDate.now(), 1, new RecurrenceFinJamais());

        // When
        // Vérifier la validité d'une date égale d'un jour à la date du jour
        LocalDate dateTest = LocalDate.now();
        boolean dateValide = recurrence.isDateValide(dateTest);

        // Then
        // La date testée doit être valide
        assertTrue(dateValide);
    }
}
