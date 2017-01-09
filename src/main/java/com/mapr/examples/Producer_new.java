package com.mapr.examples;

import com.google.common.io.Resources;
import org.apache.commons.io.FilenameUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This producer will send a bunch of messages to topic "fast-messages". Every so often,
 * it will send a message to "slow-messages". This shows how messages can be sent to
 * multiple topics. On the receiving end, we will see both kinds of messages but will
 * also see how the two topics aren't really synchronized.
 */
public class Producer_new {

    private static List<Path> files = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // set up the producer
        KafkaProducer<String, String> producer;
        try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<>(properties);
        }

        try {
            Path dir = Paths.get("/home/osboxes/data_orig");
            listFiles(dir);
            BufferedReader br = null;

            int lineNmb = 0;
            long cycleNmb = 1l;
            //files.size()
            while (true) {
                System.out.println("Cycle: " + cycleNmb);
                for (int i = 0; i < files.size(); i++) {
                    Path pltFile = files.get(i);
                    br = new BufferedReader(new FileReader(pltFile.toFile()));
                    int internalLineNmb = 0;

                    String line = br.readLine();
                    while (line != null) {
                        if (!line.contains("lat") && !line.contains("id")) {
                            Thread.sleep(1);
                            ProducerRecord<String, String> data = new ProducerRecord<String, String>("plt-input", lineNmb + "", line);
                            producer.send(data);
                            //System.out.println(data.key() + ": " + data.value());
                            lineNmb++;
                        }
                        line = br.readLine();
                    }

                }
                br.close();
                cycleNmb++;
            }



            /*for (int i = 0; i < 1000000; i++) {
                // send lots of messages
                producer.send(new ProducerRecord<String, String>(
                        "fast-messages",
                        String.format("{\"type\":\"test\", \"t\":%.3f, \"k\":%d}", System.nanoTime() * 1e-9, i)));

                // every so often send to a different topic
                if (i % 1000 == 0) {
                    producer.send(new ProducerRecord<String, String>(
                            "fast-messages",
                            String.format("{\"type\":\"marker\", \"t\":%.3f, \"k\":%d}", System.nanoTime() * 1e-9, i)));
                    producer.send(new ProducerRecord<String, String>(
                            "summary-markers",
                            String.format("{\"type\":\"other\", \"t\":%.3f, \"k\":%d}", System.nanoTime() * 1e-9, i)));
                    producer.flush();
                    System.out.println("Sent msg number " + i);
                }
            }*/
        } catch (Throwable throwable) {
            System.out.printf("%s", throwable.getStackTrace());
        } finally {
            producer.close();
        }

    }

    static void listFiles(Path path) throws IOException {

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {

                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        listFiles(entry);
                    }
                    if (entry.getFileName().toString().contains(".csv")) {
                        files.add(entry);
                    }
                }

        }
    }
}
