package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

class ReliabilityAnalysisTest {

	private ReliabilityAnalysis createReliabilityAnalysis(String filename, Double m, Double e2e, int numChanells) {

		SystemAttributes.ScheduleChoices choice = SystemAttributes.ScheduleChoices.PRIORITY;
		WorkLoad workload = new WorkLoad(m, e2e, filename);
		Program program = new Program(workload, numChanells, choice);
		ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program);
		return reliabilityAnalysis;
		
	}

	@Test
	void testBuildReliabiltyTableExample1a() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.8, 0.99, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(12, 2);
		Double expected = 0.896;
		assertEquals(expected, actual);
	}
	
	@Test
	void testBuildReliabilityTableExample1aDifferentM() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.75, 0.99, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(11, 2);
		Double expected = 0.5625;
		assertEquals(expected, actual);
	}
	
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