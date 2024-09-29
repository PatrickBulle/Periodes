package net.patrickbulle.gestionnairedeperiodes;

import net.patrickbulle.gestionnairedeperiodes.recurrence.Jour;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.Recurrence;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.RecurrenceTousLesJours;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.RecurrenceToutesLesSemaines;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinApres;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinJamais;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinLe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class GestionnaireDePeriodesApplication implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(GestionnaireDePeriodesApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(GestionnaireDePeriodesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       LOGGER.info("GestionnaireDePeriodesApplication started");

        List<Recurrence> recurrences = new ArrayList<>();
        List<String> json = new ArrayList<>();

        recurrences.add(new RecurrenceTousLesJours(UUID.randomUUID(), LocalDate.of(2024, 10, 9), 2, new RecurrenceFinJamais()));
        recurrences.add(new RecurrenceTousLesJours(UUID.randomUUID(), LocalDate.of(2023, 9, 8),1, new RecurrenceFinLe(LocalDate.of(2024, 12, 25))));
        recurrences.add(new RecurrenceTousLesJours(UUID.randomUUID(), LocalDate.of(2022, 8, 7), 3, new RecurrenceFinApres(5)));

        recurrences.add(new RecurrenceToutesLesSemaines(UUID.randomUUID(), LocalDate.of(2021, 7, 6), 1, new HashSet<>(Arrays.asList(Jour.LUNDI, Jour.SAMEDI)), new RecurrenceFinJamais()));
        recurrences.add(new RecurrenceToutesLesSemaines(UUID.randomUUID(), LocalDate.of(2020, 6, 5), 1, new HashSet<>(Arrays.asList(Jour.LUNDI, Jour.MARDI, Jour.MERCREDI, Jour.JEUDI, Jour.VENDREDI)), new RecurrenceFinLe(LocalDate.of(2024, 11, 12))));
        recurrences.add(new RecurrenceToutesLesSemaines(UUID.randomUUID(), LocalDate.of(2019, 5, 4), 1, new HashSet<>(Arrays.asList(Jour.DIMANCHE)), new RecurrenceFinApres(52)));

        for (Recurrence recurrence : recurrences) {
            json.add(recurrence.toJson());
        }

        for (String jsonString : json) {
            Recurrence recurrence = Recurrence.fromJson(jsonString);
            System.out.println("hello");
        }

    }
}
