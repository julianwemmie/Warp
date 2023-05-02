package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReliabilityAnalysisTest {

	/**
	 * Helper method to create a ReliabilityAnalysis object.
	 *
	 * @param filename    Name of the file containing the workload.
	 * @param m           The mean time to failure.
	 * @param e2e         The end-to-end reliability.
	 * @param numChannels The number of channels.
	 * @return A ReliabilityAnalysis object.
	 */
	private ReliabilityAnalysis createReliabilityAnalysis(String filename, Double m, Double e2e, int numChanells) {

		SystemAttributes.ScheduleChoices choice = SystemAttributes.ScheduleChoices.PRIORITY;
		WorkLoad workload = new WorkLoad(m, e2e, filename);
		Program program = new Program(workload, numChanells, choice);
		ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program);
		return reliabilityAnalysis;

	}

	/**
	 * Test case for building a reliability table using example1a.txt.
	 * Parameters: m = 0.8, e2e = 0.99, numChannels = 16, row = 12, column = 2
	 */
	@Test
	void testBuildReliabiltyTableExample1a() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.8, 0.99, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(12, 2);
		Double expected = 0.896;
		assertEquals(expected, actual);
	}

	/**
	 * Test case for building a reliability table using example1a.txt with different
	 * m and e2e values.
	 * Parameters: m = 0.75, e2e = 0.99, numChannels = 16, row = 11, column = 2
	 */
	@Test
	void testBuildReliabilityTableExample1aDifferentM() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.75, 0.99, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(11, 2);
		Double expected = 0.5625;
		assertEquals(expected, actual);
	}

	/**
	 * Test case for building a reliability table using stresstest4.txt.
	 * Parameters: m = 0.9, e2e = 0.9, numChannels = 16, row = 56, column = 9
	 */
	@Test
	void testBuildReliabilityTableExample1aDifferentE2E() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.8, 0.9, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(12, 2);
		Double expected = 0.896;
		assertEquals(expected, actual);
	}
	
	@Test
	void testBuildReliabiltyTableStressTest4() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("stresstest4.txt", 0.9, 0.9, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(56, 9);
		Double expected = 0.8503056;
		assertEquals(expected, actual);
	}

	/**
	 * Test case for building a reliability table using stresstest4.txt with
	 * different m and e2e values.
	 * Parameters: m = 0.9, e2e = 0.99, numChannels = 16, row = 56, column = 9
	 */
	@Test
	void testBuildReliabiltyTableStressTest4DifferentM() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("stresstest4.txt", 0.8, 0.9, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(7,4);
		Double expected = 0.992;
		assertEquals(expected, actual);
	}
	
	@Test
	void testReliabilityTableStressTest4DifferentE2E() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("stresstest4.txt", 0.9, 0.99, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(56, 9);
		Double expected = 0.8503056;
		assertEquals(expected, actual);
	}


	@Test
	void testVerifyReliabilitiesExample1a() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.8, 0.99, 16);
		Boolean actual = reliabilityAnalysis.verifyReliabilities();
		Boolean expected = true;
		assertEquals(expected,actual);
	}
	
	@Test
	void testVerifyReliabilitiesExample1aExample1DifferentM() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.75, 0.99, 16);
		Boolean actual = reliabilityAnalysis.verifyReliabilities();
		Boolean expected = true;
		assertEquals(expected,actual);
	}
	
	@Test
	void testVerifyReliabilitiesExample1aExample1aDifferentE2E() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.75, 0.98, 16);
		Boolean actual = reliabilityAnalysis.verifyReliabilities();
		Boolean expected = false;
		assertEquals(expected,actual);
	}
	
	@Test
	void testVerifyReliabilitiesExample1aStressTest4() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("stresstest4.txt", 0.9, 0.99, 16);
		Boolean actual = reliabilityAnalysis.verifyReliabilities();
		Boolean expected = true;
		assertEquals(expected,actual);
	}
	
	@Test
	void testVerifyReliabilitiesExample1aStressTest4DifferentM() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.75, 0.99, 16);
		Boolean actual = reliabilityAnalysis.verifyReliabilities();
		Boolean expected = true;
		assertEquals(expected,actual);
	}
	
	@Test
	void StressTest4DifferentE2E() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.9, 0.98, 16);
		Boolean actual = reliabilityAnalysis.verifyReliabilities();
		Boolean expected = false;
		assertEquals(expected,actual);
	}
	
}