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
	void Example1aDifferentMandE2E() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("example1a.txt", 0.75, 0.99, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(11, 2);
		Double expected = 0.5625;
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
	void StressTest4DifferentMandE2E() {
		ReliabilityAnalysis reliabilityAnalysis = createReliabilityAnalysis("stresstest4.txt", 0.9, 0.99, 16);
		ReliabilityTable reliabilityTable = reliabilityAnalysis.getReliabilities();
		Double actual = reliabilityTable.get(56, 9);
		Double expected = 0.8503056;
		assertEquals(expected, actual);
	}
	
	

	@Test
	void testReliabilityNode() {

	}

	@Test
	void testVerifyReliabilities() {

	}
}