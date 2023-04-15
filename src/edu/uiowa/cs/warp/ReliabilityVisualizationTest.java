package edu.uiowa.cs.warp;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.BeforeAll.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class ReliabilityVisualizationTest {

    private ReliabilityVisualization createReliabilityVisualization(String filename, Double m, Double e2e,
            int numChannels) {
        SystemAttributes.ScheduleChoices choice = SystemAttributes.ScheduleChoices.PRIORITY;
        WorkLoad workload = new WorkLoad(m, e2e, filename);
        WarpInterface warp = new WarpSystem(workload, numChannels, choice);
        ReliabilityVisualization viz = new ReliabilityVisualization(warp);
        return viz;
    }

    @Test
    void testCreateHeader1() {
        String filename = "example.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);
        String expected = "Reliability Analysis for graph Example created with the following parameters:\n" +
                "Scheduler Name: Priority\n" +
                "M: 0.9\n" +
                "E2E: 0.99\n" +
                "nChannels: 16\n";
        String actual = viz.createHeader().toString();
        assertEquals(expected, actual);
    }
    
    @Test
    void testCreateHeader2() {
        String filename = "example1a.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);
        String expected = "Reliability Analysis for graph Example1A created with the following parameters:\n" +
                "Scheduler Name: Priority\n" +
                "M: 0.9\n" +
                "E2E: 0.99\n" +
                "nChannels: 16\n";
        String actual = viz.createHeader().toString();
        assertEquals(expected, actual);
    }
    
    @Test
    void testCreateHeader3() {
        String filename = "StressTest4.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);
        String expected = "Reliability Analysis for graph StressTest4 created with the following parameters:\n" +
                "Scheduler Name: Priority\n" +
                "M: 0.9\n" +
                "E2E: 0.99\n" +
                "nChannels: 16\n";
        String actual = viz.createHeader().toString();
        assertEquals(expected, actual);
    }

    @Test
    void testCreateVisualizationData1() {
        String filename = "example.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);
        int height = 100;
        int width = 6;
        String[][] expected = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                expected[i][j] = "0.0";
            }
        }
        String[][] actual = viz.createVisualizationData();
        assertEquals(expected, actual);
    }
    
    @Test
    void testCreateVisualizationData2() {
        String filename = "example1a.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);
        int height = 20;
        int width = 6;
        String[][] expected = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                expected[i][j] = "0.0";
            }
        }
        String[][] actual = viz.createVisualizationData();
        assertEquals(expected, actual);
    }
    
    @Test
    void testCreateVisualizationData3() {
        String filename = "StressTest4.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);
        int height = 300;
        int width = 58;
        String[][] expected = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                expected[i][j] = "0.0";
            }
        }
        String[][] actual = viz.createVisualizationData();
        assertEquals(expected, actual);
    }

    @Test
    void testCreateColumnHeader1() {
        String filename = "example.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);

        String[] columnHeader = viz.createColumnHeader();

        String[] expected = { "F0:A", "F0:B", "F0:C", "F1:C", "F1:B", "F1:A" };

        assertArrayEquals(expected, columnHeader);
    }

    @Test
    void testCreateColumnHeader2() {
        String filename = "example1a.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);

        String[] columnHeader = viz.createColumnHeader();

        String[] expected = { "F0:A", "F0:B", "F0:C", "F1:C", "F1:B", "F1:A" };

        assertArrayEquals(expected, columnHeader);

    }

    @Test
    void testCreateColumnHeader3() {
        String filename = "StressTest4.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);

        String[] columnHeader = viz.createColumnHeader();

        String[] expected = { "F1:B", "F1:C", "F1:D", "F2:C", "F2:D", "F2:E", "F2:F",
                "F2:G", "F2:H", "F2:I", "F3:C", "F3:D", "F3:E", "F3:J",
                "F3:K", "F3:L", "F4:A", "F4:B", "F4:C", "F4:D", "F4:E",
                "F4:J", "F4:K", "F4:L", "F5:A", "F5:B", "F5:C", "F5:D",
                "F5:E", "F6:B", "F6:C", "F6:D", "F7:A", "F7:B", "F7:C",
                "F7:D", "F7:E", "F8:C", "F8:D", "F8:E", "F8:F", "F8:G",
                "F8:H", "F8:I", "F9:A", "F9:B", "F9:C", "F9:D", "F9:E",
                "F9:J", "F9:K", "F9:L", "F10:C", "F10:D", "F10:E", "F10:J",
                "F10:K", "F10:L" };

        assertArrayEquals(expected, columnHeader);
    }

}
