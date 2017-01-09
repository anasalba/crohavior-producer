package com.mapr.examples;

import com.google.common.io.Resources;
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
public class Producer_newtemp {

    private static List<Path> files = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // set up the producer


        try {
            Path dir = Paths.get("/home/osboxes/data_orig/new/200000.csv");
            BufferedReader br = null;

            int lineNmb = 1;
            //files.size()

            Path pltFile = dir;
            br = new BufferedReader(new FileReader(pltFile.toFile()));
            int internalLineNmb = 0;

            String line = br.readLine();
            while (line != null) {
                if (lineNmb > 1 && lineNmb < 37) {

                } else if (lineNmb >= 37 && lineNmb < 77) {

                } else if (lineNmb >= 77 && lineNmb < 121) {

                } else if (lineNmb >= 121) {

                }

                lineNmb++;
                line = br.readLine();
            }


        } catch (Throwable throwable) {
            System.out.printf("%s", throwable.getStackTrace());
        } finally {
        }

    }

}
