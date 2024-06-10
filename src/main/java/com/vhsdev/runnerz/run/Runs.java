package com.vhsdev.runnerz.run;

import java.util.List;

/**
 * Created at 1:59:00 into the video for loading JSON data using streams via a new data loader
 * class. The previous method using @Bean CommandLineRunner runner() was commented out.
 */
public record Runs(List<Run> runs) {

}
