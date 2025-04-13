package com.vrtkarim.music.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;

@Configuration
@EnableScheduling
public class SchudelerConfiguration {
    @Scheduled(fixedDelay = 1800000)//30 d9i9a
    public void deleteFiles() {
        File dir = new File("dir");
        Stream.of(dir.listFiles()).forEach(f-> {
            try {
                FileTime fileTime = Files.getLastModifiedTime(Path.of(f.getPath()));
                LocalDateTime fileLastModification =LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
                LocalDateTime now = LocalDateTime.now();
                if(Duration.between(fileLastModification, now).toMinutes() > 7) {
                    Files.delete(Path.of("dir"+File.separator+f.getPath()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
