package com.mapr.examples;

import org.apache.flume.node.Application;

import java.io.IOException;

/**
 * Pick whether we want to run as producer or consumer. This lets us
 * have a single executable as a build target.
 */
public class RunFlow {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {

        }
        //1. run flume
        String[] flumeArgs = new String[] { "agent", "-nflume1",
                "-fc:\\apache-flume-1.7.0-bin\\conf\\flume-conf.properties" };

        Application.main(flumeArgs);
        //2. run producer
        Producer.main(args);

        /*switch (args[0]) {
            case "producer":
                Producer.main(args);
                break;
            case "consumer":
                Consumer.main(args);
                break;
            default:
                throw new IllegalArgumentException("Don't know how to do " + args[0]);
        }*/
    }
}
