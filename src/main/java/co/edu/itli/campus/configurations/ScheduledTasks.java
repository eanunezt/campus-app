package co.edu.itli.campus.configurations;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1790000)
    public void reportCurrentTime() {
    	RestTemplate restTemplate = new RestTemplate();
        String quote = restTemplate.getForObject("https://campus-itli.herokuapp.com/users/online", String.class);
        log.info(""+quote+" The time is now {}", dateFormat.format(new Date()));
    }
}