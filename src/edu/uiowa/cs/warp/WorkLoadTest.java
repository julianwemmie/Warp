package edu.uiowa.cs.warp;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WorkLoadTest {
	
	/**
	 * Helper method to setup workload object depending on filename.
	 * @param inputFileName name of file to use.
	 * @return
	 */
	private WorkLoad createDefaultWorkload(String inputFileName) {
		int numFaults = 0;
		double minLQ = 0.9;
		double e2e = 0.99;
		WorkLoad workload = new WorkLoad(numFaults, minLQ, e2e, inputFileName);
		return workload;
	}
	/**
	 * Tests the addFlow method in the WorkLoad.java class. The test adds new flows 
	 * to "Example4.txt" or whichever test file you choose. It compares the actual values and 
	 * expected values by iterating through both using a for loop.
	 */
	@Test
	void testAddFlow() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		workload.addFlow("F2");
		workload.addFlow("F3");
		workload.addFlow("F4");
		workload.addFlow("F5");
		workload.addFlow("F6");
		var expected = new String[] {"F0", "F1", "F2", "F3", "F4", "F5", "F6"};
		var flows = workload.getFlowNamesInOriginalOrder();
		for(int i = 0; i < flows.size(); i++) {
			var msg = String.format("At index %d: Flow expected: %s, was: %s.", i, expected[i], flows.get(i));
			assertEquals(msg, flows.get(i), expected[i] );
		}
		
			
		}
	/**
	 * Tests the addFlow method in the WorkLoad.java class. The test adds new flows of just numbers
	 * to "Example4.txt" or whichever test file you choose. It compares the actual values and 
	 * expected values by iterating through both using a for loop.
	 */
	@Test
	void testAddFlowJustNumbers() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		workload.addFlow("2");
		workload.addFlow("3");
		workload.addFlow("4");
		workload.addFlow("5");
		workload.addFlow("6");
		workload.addFlow("7");
		var expected = new String[] {"F0", "F1", "2", "3", "4", "5", "6", "7"};
		var flows = workload.getFlowNamesInOriginalOrder();
		for(int i = 0; i < flows.size(); i++) {
			var msg = String.format("At index %d: Flow expected: %s, was: %s.", i, expected[i], flows.get(i));
			assertEquals(msg, flows.get(i), expected[i] );
		}	
		}
	
	/**
	 * Tests the addNodeToFlow method in the WorkLoad.java class. The test firsts adds Nodes to
	 * and existing flow in the workload object, then compares the expected nodes to the actual nodes
	 * in the flow.
	 */
	@Test
	void testAddNodeToFlow() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		workload.addNodeToFlow("F1", "D");
		workload.addNodeToFlow("F1", "E");
		workload.addNodeToFlow("F1", "F");
		var expected = new String[] {"C", "B", "A", "D", "E", "F"};
		var nodes = workload.getNodesInFlow("F1");
		for(int i = 0; i < nodes.length; i++) {
			var msg = String.format("At index %d: Node expected: %s was: %s", i, expected[i], nodes[i]);
			assertEquals(msg, expected[i], nodes[i]);
		}
	}
	
	/**
	 * Tests the addNodeToFlow method in the WorkLoad.java class. The test firsts adds flow "testFlow" to
	 * the workload object, then adds nodes to that flow. 
	 */
	@Test
	void testAddNodeToFlowAddingToNewFlow() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		workload.addFlow("testFlow");
		workload.addNodeToFlow("testFlow", "A");
		workload.addNodeToFlow("testFlow", "B");
		workload.addNodeToFlow("testFlow", "C");
		var expected = new String[] {"A", "B", "C"};
		var nodes = workload.getNodesInFlow("testFlow");
		for(int i = 0; i < nodes.length; i++) {
			var msg = String.format("At index %d: Node expected: %s was: %s", i, expected[i], nodes[i]);
			assertEquals(msg, expected[i], nodes[i]);
		}
	}
	
		/**
		 * Tests the getTotalTxAttemptsInFlow method in the WorkLoad.java class. Iterates through the 
		 * list of flow names grabbing the total tx attempts of each, comparing them to the expected.
		 */
	@Test
	void testGetTotalTxAttemptsInFlow() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		var expected = new int[] {5, 4};
		var flows = workload.getFlowNames();
		for(int i = 0; i < flows.length; i++) {
			int attempts = workload.getTotalTxAttemptsInFlow(flows[i]);
			var msg = String.format("expected Tx Attempts: %d was: %d", expected[i], attempts);

			assertEquals(msg, expected[i], attempts); 
		}
		
		
	}
	
	/**
	 * Test the GetFlowPriority method in WorkLoad.java. First gets a list of the nodes in priority order,
	 * then iterates through both the actual list and expected comparing the values. 	
	 */
	@Test
	void testGetFlowPriority() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		var flows = workload.getFlowNamesInPriorityOrder();
		var expectedP = new int[] {0, 1};
		for(int i = 0; i < flows.size(); i++) {
			int flowPriority = workload.getFlowPriority(flows.get(i));
			var msg = String.format("Expected Priority: %d Actual Priority: %d ", expectedP[i], flowPriority);
			assertEquals(msg, expectedP[i], flowPriority);	
		}	
	}
	/**
	 * Tests the setFlowPriority method in WorkLoad.java. Takes the flows in priority order then 
	 * puts the priorities in descending order (first flow having the priority of the last and vice versa),
	 * checking to make sure it is successfully changed.
	 */
	@Test
	void testSetFlowPriority() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		//set integer to set the priority
		Integer j = 0;
		var flows = workload.getFlowNamesInPriorityOrder();
		//for loops starts at the end of the list
		for(int i = flows.size(); i > 0; i-- ) {
			//sets the priority using j then increases j by 1
			workload.setFlowPriority(flows.get(i), j );
			j++;
			var priority = workload.getFlowPriority(flows.get(i));
			var msg = String.format("Expected Priority: %d Actual Priority: %d ", j, priority);
			assertEquals(msg, j, priority);
		}
		
		
		
	}
	/**
	 * Tests getFlowTxAttemptsPerLink method from the WorkLoad.java file. Adds a flow to the workload,
	 * gets the flow names, and compares the No. of transmission attempts per link to the expected output.
	 * 
	 */
	@Test
	void testGetFlowTxAttemptsPerLink(){
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		workload.addFlow("test");
		var flows = workload.getFlowNames();
		var expected = new int[] {3, 3, 1};
		for(int i = 0; i < flows.length; i++) {
		int attempts = workload.getFlowTxAttemptsPerLink(flows[i]);
		String msg = String.format("Expected No. of Tx: %d Actual No. of Tx: %d ", expected[i], attempts);
		assertEquals(msg, expected[i], attempts);
		}
		}
		
		
		
	/**
	 * Tests the setFlowsInRMorder method in the WorkLoad.java class. Test first calls the method then
	 * compares the output with the expected values.
	 */
	@Test
	void testSetFlowsInRMOrder() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
	    workload.setFlowsInRMorder();
	    var flows = workload.getFlowNamesInPriorityOrder();
		var expected = new String[] {"F0", "F1" };
		for(int i = 0; i < flows.size(); i++ ) {
			var msg = String.format("Expected Flow at index %d: %s Actual Flow: %s ",i, expected[i], flows.get(i));
			
			assertEquals(msg, expected[i], flows.get(i).toString());
		}
	}
	/**
	 * Tests the setFlowsInRMorder method in the WorkLoad.java class. Test first changes the period and priority
	 * of the second flow "F1" to make sure it appears before "F0". 
	 * 
	 */
	@Test
	void testSetFlowsInRMOrderChangePriorityandPeriod() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		workload.setFlowPriority("F1", 0);
		workload.setFlowPeriod("F1", 5);
		workload.setFlowsInRMorder();
		var flows = workload.getFlowNamesInPriorityOrder();
		var expected = new String[] {"F1", "F0" };
		  for(int i = 0; i < flows.size(); i++ ) {
			var msg = String.format("Expected Flow at index %d: %s Actual Flow: %s ",i, expected[i], flows.get(i));
				
			assertEquals(msg, expected[i], flows.get(i).toString());
	}
		}
	
	/**
	 * Tests getNodeNamesOrderedAlphabetically method in WorkLoad.java class. Takes the names
	 * of all the nodes in the Example4.txt file and orders them alphabetically, taking account if they are integers.
	 * 
	 * 
	 *  
	 */
	@Test
	void testGetNodeNamesInAlphabeticalOrder() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		var expected = new String[] {"A", "B", "C", "D",};
		var actual = workload.getNodeNamesOrderedAlphabetically();
		for(int i = 0; i < actual.length; i++) {
			var node = actual[i];
			var msg = String.format("At index: %d Node expected: %s Actual Node: %s", i, expected[i], node);
			assertEquals(msg, expected[i], node);
		}
	}
	/**
	 * tests the getNodeNamesOrderedAlphabetically method in WorkLoad.java class. Adds extra Nodes
	 * with names not present in the original file, then tests to see if they are sorted properly.
	 */
	
	void testGetNodeNamesInAlphabeticalOrderExtraNodes() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		workload.addNodeToFlow("F0", "1");
		workload.addNodeToFlow("F1", "O");
		workload.addNodeToFlow("F0", "N");
		workload.addNodeToFlow("F1", "2");
		var expected = new String[] {"1", "2", "A", "B", "C", "D", "N", "O"};
		var actual = workload.getNodeNamesOrderedAlphabetically();
		for(int i = 0; i < actual.length; i++) {
			var node = actual[i];
			var msg = String.format("At index: %d Node expected: %s Actual Node: %s", i, expected[i], node);
			assertEquals(msg, expected[i], node);
		}
	}
	
	/**
	 * Getter test for flowNames.
	 * Tests file: Example4.txt
	 */
	@Test
	void getFlowNames() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		String[] actual = workload.getFlowNames();
		String[] expected = {"F0", "F1"};
		assertArrayEquals(actual, expected, "Testing File: " + fileName);
	}
	
	/**
	 * Getter test for node index when the node exists.
	 * Tests file: Example4.txt
	 */
	@Test
	void getNodeIndex_exists() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		int actual = workload.getNodeIndex("D");
		int expected = 3;
		assertEquals(expected, actual, "Testing File: " + fileName);
	}
	
	/**
	 * Getter test for node index when the node does not exist.
	 * Tests file: Example4.txt
	 */
	@Test
	void getNodeIndex_doesNotExist() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		int actual = workload.getNodeIndex("");
		int expected = 0;
		assertEquals(expected, actual, "Testing File: " + fileName);
	}
	
	/**
	 * Getter test for all nodes in a flow.
	 * Tests file: Example4.txt
	 */
	@Test
	void getNodesInFlow() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		String[] actual = workload.getNodesInFlow("F0");
		String[] expected = {"A", "B", "C", "D"};
		assertArrayEquals(expected, actual, "Testing File: " + fileName);
	}
	
	/**
	 * Getter test for hyper period for a short example.
	 * Tests file: Example4.txt
	 */
	@Test
	void getHyperPeriod_Short() {
		String fileName = "Example4.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		int actual = workload.getHyperPeriod();
		int expected = 20;
		assertEquals(expected, actual, "Testing File: " + fileName);
	}
	
	/**
	 * Getter test for hyper period for a longer example.
	 * Tests file: StressTest.txt
	 */
	@Test
	void getHyperPeriod_Long() {
		String fileName = "StressTest.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		int actual = workload.getHyperPeriod();
		int expected = 300;
		assertEquals(expected, actual, "Testing File: " + fileName);
	}
	
	/**
	 * Getter test for number of transmissions per link
	 * Tests file: StressTest.txt
	 */
	@Test
	void getNumTxAttemptsPerLink() {
		String fileName = "StressTest.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		Integer[] actual = workload.getNumTxAttemptsPerLink("F1");
		Integer[] expected = {3, 3, 0};
		assertArrayEquals(expected, actual, "Testing File: " + fileName);
	}
	
	/**
	 * Max flow length for many flows.
	 * Tests file: StressTest.txt
	 */
	@Test 
	void maxFlowLength_StressTest() {
		String fileName = "StressTest.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		int expected = 8;
		int actual = workload.maxFlowLength();
		assertEquals(expected, actual, "Testing File: " + fileName);
	}
	
	/**
	 * Max flow length test for long flow.
	 * Tests file: LongChain.txt
	 */
	@Test 
	void maxFlowLength_LongChain() {
		String fileName = "LongChain.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		int expected = 26;
		int actual = workload.maxFlowLength();
		assertEquals(expected, actual, "Testing File: " + fileName);
	}
	
	/**
	 * Getter test for flow deadline
	 * Tests file: Example.txt
	 */
	@Test
	void getFlowDeadline() {
		String fileName = "Example.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		
		String flow = "F0";
		int expected = 10;
		
		workload.setFlowDeadline(flow, expected);
		int actual = workload.getFlowDeadline(flow);
		assertEquals(expected, actual, "Testing File: " + fileName);
	}

	/**
	 * Setter test for flow deadline
	 * Tests file: Example.txt
	 */
	@Test
	void setFlowDeadline() {
		String fileName = "Example.txt";
		WorkLoad workload = createDefaultWorkload(fileName);
		
		String flow = "F0";
		int expected = 10;
		
		workload.setFlowDeadline(flow, expected);
		int actual = workload.getFlowDeadline(flow);
		assertEquals(expected, actual, "Testing File: " + fileName);
	}
}
