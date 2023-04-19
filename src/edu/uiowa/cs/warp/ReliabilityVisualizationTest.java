package edu.uiowa.cs.warp;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.BeforeAll.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class ReliabilityVisualizationTest {
	/**
	 * createReliabilityVisualization is a method that creates and returns an instance of the 
	 * ReliabilityVisualization class, given a filename, m, e2e, and numChannels.
	 * 
	 * @param filename
	 * @param m Minimum packet reception rate
	 * @param e2e End-to-End reliability 
	 * @param numChannels
	 * @return ReliabilityVisualization object
	 */
    private ReliabilityVisualization createReliabilityVisualization(String filename, Double m, Double e2e,
            int numChannels) {
        SystemAttributes.ScheduleChoices choice = SystemAttributes.ScheduleChoices.PRIORITY;
        WorkLoad workload = new WorkLoad(m, e2e, filename);
        WarpInterface warp = new WarpSystem(workload, numChannels, choice);
        ReliabilityVisualization viz = new ReliabilityVisualization(warp);
        return viz;
    }
    /** testCreateHeader_Example tests the createHeader() method and createTitle() of a ReliabilityVisualization object.
     *  The test creates a new ReliabilityVisualization object by calling the createReliabilityVisualization() 
     *  method with Example.txt as the input file. It then tests the header created with createHeader() and createTitle()
     *  and compares it with the expected output.
     *  
     */
    @Test
    void testCreateHeader_Example() {
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
    /**
     * testCreateHeader_Example1a tests the createHeader() method and createTitle() of a ReliabilityVisualization object.
     *  The test creates a new ReliabilityVisualization object by calling the createReliabilityVisualization() 
     *  method with Example1a.txt as the input file. It then tests the header created with createHeader() and createTitle() 
     *  and compares it with the expected output.
     *  
     */
    @Test
    void testCreateHeader_Example1a() {
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
    /**
     *  testCreateHeader_StressTest4 tests the createHeader() method and createTitle() of a ReliabilityVisualization object.
     *  The test creates a new ReliabilityVisualization object by calling the createReliabilityVisualization() 
     *  method with StressTest4.txt as the input file. It then tests the header created with createHeader() and createTitle() 
     *  and compares it with the expected output.
     */
    @Test
    void testCreateHeader_StressTest4() {
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
    /**
     * testCreateVisualizationData_Example() tests the createVisualizationData() method of a ReliabilityVisualization object. 
     * The test creates a new ReliabilityVisualization object with Example1.txt as the input file.
     * It then initializes the expected output String array expected to be a 2D array with height rows and width columns,
     * and fills it with the value "0.0".
     *  
     */
    @Test
    void testCreateVisualizationData_Example() {
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
    /**
     * testCreateVisualizationData_Example1a() tests the createVisualizationData() method of a ReliabilityVisualization object. 
     * The test creates a new ReliabilityVisualization object with Example1a.txt as the input file.
     * It then initializes the expected output String array expected to be a 2D array with height rows and width columns,
     * and fills it with the value "0.0".
     */
    @Test
    void testCreateVisualizationData_Example1a() {
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
    /**
     * testCreateVisualizationData_StressTest4() tests the createVisualizationData() method of a ReliabilityVisualization object. 
     * The test creates a new ReliabilityVisualization object with StressTest4.txt as the input file.
     * It then initializes the expected output String array expected to be a 2D array with height rows and width columns,
     * and fills it with the value "0.0".
     */
    @Test
    void testCreateVisualizationData_StressTest4() {
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
    /**
     * testCreateColumnHeader_Example() tests the createColumnHeader() method of a ReliabiltyVisualization object. 
     * The test creates a new ReliabilityVisualization object with Example.txt as the input file.
     * The test creates a column header using the createColumnHeader() method then compares it to the desired output
     * which is and array of the strings "Flow:Node" for all the flows and nodes in the file.
     */
    @Test
    void testCreateColumnHeader_Example() {
        String filename = "example.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);

        String[] columnHeader = viz.createColumnHeader();

        String[] expected = { "F0:A", "F0:B", "F0:C", "F1:C", "F1:B", "F1:A" };

        assertArrayEquals(expected, columnHeader);
    }
    /**
     * testCreateColumnHeader_Example1a() tests the createColumnHeader() method of a ReliabiltyVisualization object. 
     * The test creates a new ReliabilityVisualization object with Example1a.txt as the input file.
     * The test creates a column header using the createColumnHeader() method then compares it to the desired output
     * which is and array of the strings "Flow:Node" for all the flows and nodes in the file.
     */
    @Test
    void testCreateColumnHeader_Example1a() {
        String filename = "example1a.txt";
        Double m = 0.9;
        Double e2e = 0.99;
        int numChannels = 16;
        ReliabilityVisualization viz = createReliabilityVisualization(filename, m, e2e, numChannels);

        String[] columnHeader = viz.createColumnHeader();

        String[] expected = { "F0:A", "F0:B", "F0:C", "F1:C", "F1:B", "F1:A" };

        assertArrayEquals(expected, columnHeader);

    }
    /**
     * testCreateColumnHeader_StressTest4() tests the createColumnHeader() method of a ReliabiltyVisualization object. 
     * The test creates a new ReliabilityVisualization object with StressTest4.txt as the input file.
     * The test creates a column header using the createColumnHeader() method then compares it to the desired output
     * which is and array of the strings "Flow:Node" for all the flows and nodes in the file.
     */
    @Test
    void testCreateColumnHeader_StressTest4() {
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
    
   @Test
	void testCreateColumnHeader_EmptyFlows() {
		
		String filename = "EmptyFlows.txt";
		Double m = 0.9;
		Double e2e = 0.99;
		int numChannels = 16;
		
		IndexOutOfBoundsException thrown = assertThrows(
				IndexOutOfBoundsException.class,
		           () -> createReliabilityVisualization(filename, m, e2e, numChannels)
		    );
		
	    String expectedMessage = "Index -1 out of bounds for length 0";
	    String actualMessage = thrown.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
    
}
  

    


