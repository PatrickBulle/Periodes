package net.patrickbulle.gestionnairedeperiodes;

import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFin;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinApres;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinJamais;
import net.patrickbulle.gestionnairedeperiodes.recurrence.models.fin.RecurrenceFinLe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GestionnaireDePeriodesApplication implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(GestionnaireDePeriodesApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(GestionnaireDePeriodesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       LOGGER.info("GestionnaireDePeriodesApplication started");

       List<RecurrenceFin> fins = new ArrayList<>();
       List<String> jsons = new ArrayList<>();
        RecurrenceFinJamais finJamais = new RecurrenceFinJamais();
        RecurrenceFinLe finLe = new RecurrenceFinLe();
        RecurrenceFinApres finApres = new RecurrenceFinApres();

        finLe.setDate(LocalDate.of(1975, 6, 17));
        finApres.setNbOccurrences(12);

        fins.add(finJamais);
        fins.add(finLe);
        fins.add(finApres);

        for (RecurrenceFin fin : fins) {
            jsons.add(fin.toJson().toString());
            LOGGER.info(fin.toJson().toString());
        }

        for (String json : jsons) {
            RecurrenceFin fin = RecurrenceFin.fromJson(json);
            LOGGER.info(fin.toString());
        }



    }
}
