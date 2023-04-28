package edu.uiowa.cs.warp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import edu.uiowa.cs.warp.WarpDSL.InstructionParameters;

/**
 * ReliabilityAnalysis analyzes the end-to-end reliability of messages
 * transmitted in flows for the WARP system.
 * <p>
 * 
 * Let M represent the Minimum Packet Reception Rate on an edge in a flow. The
 * end-to-end reliability for each flow, flow:src->sink, is computed iteratively
 * as follows:<br>
 * (1)The flow:src node has an initial probability of 1.0 when it is released.
 * All other initial probabilities are 0.0. (That is, the reset of the nodes in
 * the flow have an initial probability value of 0.0.) <br>
 * (2) each src->sink pair probability is computed as NewSinkNodeState =
 * (1-M)*PrevSnkNodeState + M*PrevSrcNodeState <br>
 * This value represents the probability that the message as been received by
 * the node SinkNode. Thus, the NewSinkNodeState probability will increase each
 * time a push or pull is executed with SinkNode as a listener.
 * <p>
 * 
 * The last probability state value for any node is the reliability of the
 * message reaching that node, and the end-to-end reliability of a flow is the
 * value of the last Flow:SinkNode probability.
 * <p>
 * 
 * CS2820 Spring 2023 Project: Implement this class to compute the probabilities
 * the comprise the ReliablityMatrix, which is the core of the file
 * visualization that is requested in Warp.
 * <p>
 * 
 * To do this, you will need to retrieve the program source, parse the
 * instructions for each node, in each time slot, to extract the src and snk
 * nodes in the instruction and then apply the message success probability
 * equation defined above.
 * <p>
 * 
 * I recommend using the getInstructionParameters method of the WarpDSL class to
 * extract the src and snk nodes from the instruction string in a program
 * schedule time slot.
 * 
 * @author sgoddard
 * @version 1.5
 *
 */
public class ReliabilityAnalysis {
	/**
	 * Constant variable to store a constructor that sets the e2e and m
	 */
	private static final String E2E_M_CONSTRUCTOR = "E2E_M_CONSTRUCTOR";
	/**
	 * Constant variable to store a constructor that sets the numFaults
	 */
	private static final String NUM_FAULTS_CONSTRUCTOR = "NUM_FAULTS_CONSTRUCTOR";

	/**
	 * default end-to-end reliability for all flows
	 */
	private Double e2e = 0.99;
	/**
	 * the minimum packet reception rate which will be changed by the
	 * E2E_M_CONSTUCTOR
	 */
	private Double minPacketReceptionRate = 0.9;
	/**
	 * number of faults tolerated per edge
	 */
	private Integer numFaults = 0;
	private Program program = null;
	/**
	 * Stores which constructor was used to create the reliability analysis.
	 */
	private String constructorUsed = null;
	
	private ReliabilityTable reliabilityTable = null;
	
	private HashMap<String, ReliabilityNode> nodeMap = new HashMap<String, ReliabilityNode>();
	
	class ReliabilityNode {
		String nodeName;
		String flowName;
		Boolean isSourceNode;
		Integer columnIndex;
		Integer period;
		Double prevR = 0.0;

		ReliabilityNode(String nodeName, String flowName, Boolean isSourceNode, 
				Integer columnIndex, Integer period){
			this.nodeName = nodeName;
			this.flowName = flowName;
			this.isSourceNode = isSourceNode;
			this.columnIndex = columnIndex;
			this.period = period;
		}
	}
	

	/**
	 * Creates a ReliabilityAnalysis object and sets the current constructor used to
	 * E2E_M_CONSTRUCTOR.
	 * 
	 * @param e2e
	 * @param minPacketReceptionRate
	 */
	public ReliabilityAnalysis(Double e2e, Double minPacketReceptionRate) {
		this.e2e = e2e;
		this.minPacketReceptionRate = minPacketReceptionRate;
		this.constructorUsed = E2E_M_CONSTRUCTOR;
	}

	/**
	 * Creates a ReliabilityAnalysis object and sets the current constructor used to
	 * NUM_FAULTS_CONSTRUCTOR.
	 * 
	 * @param numFaults
	 * 
	 */
	public ReliabilityAnalysis(Integer numFaults) {
		this.numFaults = numFaults;
		this.constructorUsed = NUM_FAULTS_CONSTRUCTOR;
	}

	/**
	 * Creates an array list of the number of transmission attempts per link and the
	 * total
	 * transmission attempts.
	 * 
	 * @param flow
	 * @param e2e
	 * @param M
	 * @param optimizationRequested
	 * @return nPushesArrayList
	 */
	public ArrayList<Integer> numTxAttemptsPerLinkAndTotalTxAttempts(Flow flow, Double e2e, Double M,
			boolean optimizationRequested) {

		var nodesInFlow = flow.nodes;
		var nNodesInFlow = nodesInFlow.size(); // The last entry will contain the worst-case cost of
												// transmitting E2E in isolation
		// ArrayList<Integer> nPushes = Array(repeating: 0, count: nNodesInFlow+1);
		ArrayList<Integer> nPushes = new ArrayList<Integer>();// Array to track nPushes for each node in this
		for (int i = 0; i < nNodesInFlow + 1; i++) {
			// initialize to all 0 values
			nPushes.add(0);
		}
		// flow (same as nTx per link)

		var nHops = nNodesInFlow - 1;
		// minLinkReliablityNeded is the minimum reliability needed per link in a flow
		// to hit E2E
		// reliability for the flow
		Double minLinkReliablityNeded = Math.max(e2e, Math.pow(e2e, (1.0 / (double) nHops))); 
		// use max to handle rounding error when e2e == 1.0
		// Now compute reliability of packet reaching each node in the given time slot
		// Start with a 2-D reliability window that is a 2-D matrix of no size
		// each row is a time slot, stating at time 0
		// each column represents the reliability of the packet reaching that node at
		// the
		// current time slot (i.e., the row it is in)
		// will add rows as we compute reliabilities until the final reliability is
		// reached
		// for all nodes.
		ReliabilityTable reliabilityWindow = new ReliabilityTable();
		ReliabilityRow newReliabilityRow = new ReliabilityRow();
		for (int i = 0; i < nNodesInFlow; i++) {
			newReliabilityRow.add(0.0); // create the the row initialized with 0.0 values
		}
		reliabilityWindow.add(newReliabilityRow); // now add row to the reliability window, Time 0
		ReliabilityRow tmpVector = reliabilityWindow.get(0);
		ArrayList<Double> currentReliabilityRow = new ArrayList<Double>();
		for (int i = 0; i < tmpVector.size(); i++) {
			currentReliabilityRow.add(tmpVector.get(i));
		}
		// var currentReliabilityRow = (Double[]) reliabilityWindow.get(0).toArray();
		// Want reliabilityWindow[0][0] = 1.0 (i.e., P(packet@FlowSrc) = 1
		// but I din't want to mess with the newReliablityRow vector I use below
		// So, we initialize this first entry to 1.0, which is reliabilityWindow[0][0]
		// We will then update this row with computed values for each node and put it
		// back in the matrix
		currentReliabilityRow.set(0, 1.0); // initialize (i.e., P(packet@FlowSrc) = 1
		Double e2eReliabilityState = currentReliabilityRow.get(nNodesInFlow - 1); // the analysis will end
		// when the 2e2 reliability metrix is met, initially the state is not met and will be 0 with this statement
		var timeSlot = 0; // start time at 0
		while (e2eReliabilityState < e2e) { // change to while and increment increment timeSlot becuase
											// we don't know how long this schedule window will last
			ArrayList<Double> prevReliabilityRow = new ArrayList<Double>(currentReliabilityRow);
			currentReliabilityRow.clear(); // would
			for (int i = 0; i < newReliabilityRow.size(); i++) {
				currentReliabilityRow.add(newReliabilityRow.get(i));
			}

			// be reliabilityWindow[timeSlot] if working through a schedule
			// Now use each flow:src->sink to update reliability computations
			// this is the update formula for the state probabilities
			// nextState = (1 - M) * prevState + M*NextHighestFlowState
			// use MinLQ for M in above equation
			// NewSinkNodeState = (1-M)*PrevSnkNodeState + M*PrevSrcNodeState

			for (int nodeIndex = 0; nodeIndex < (nNodesInFlow - 1); nodeIndex++) { 
				// loop through each node in the flow and update the sates for each link (i.e., sink->src pair)
				var flowSrcNodeindex = nodeIndex;
				var flowSnkNodeindex = nodeIndex + 1;
				var prevSrcNodeState = prevReliabilityRow.get(flowSrcNodeindex);
				var prevSnkNodeState = prevReliabilityRow.get(flowSnkNodeindex);
				Double nextSnkState;
				if ((prevSnkNodeState < minLinkReliablityNeded) && prevSrcNodeState > 0) { 
					// do a push until PrevSnk state > e2e to ensure next node reaches target E2E BUT skip if no
					// chance of success (i.e., source doesn't have packet)
					nextSnkState = ((1.0 - M) * prevSnkNodeState) + (M * prevSrcNodeState); 
					// need to continue attempting to Tx, so update current state
					nPushes.set(nodeIndex, nPushes.get(nodeIndex) + 1); // increment the number of pushes for for this
																		// node to snk node
				} else {
					nextSnkState = prevSnkNodeState; // snkNode has met its reliability. Thus move on to the
														// next node and record the reliability met
				}

				if (currentReliabilityRow.get(flowSrcNodeindex) < prevReliabilityRow.get(flowSrcNodeindex)) { // probabilities
					// are non-decreasing so update if we were higher by carring old value forward
					currentReliabilityRow.set(flowSrcNodeindex, prevReliabilityRow.get(flowSrcNodeindex)); // carry
					// forward the previous state for the src node, which 
					// may get over written later by another instruction in this slot
				}
				currentReliabilityRow.set(flowSnkNodeindex, nextSnkState);
			}

			e2eReliabilityState = currentReliabilityRow.get(nNodesInFlow - 1);
			ReliabilityRow currentReliabilityVector = new ReliabilityRow();
			// convert the row to a vector so we can add it to the reliability window
			Collections.addAll(currentReliabilityVector, currentReliabilityRow.toArray(new Double[0]));
			if (timeSlot < reliabilityWindow.size()) {
				reliabilityWindow.set(timeSlot, (currentReliabilityVector));
			} else {
				reliabilityWindow.add(currentReliabilityVector);
			}
			timeSlot += 1; // increase to next time slot
		}
		var size = reliabilityWindow.size();
		nPushes.set(nNodesInFlow, size); // The total (worst-case) cost to transmit E2E in isolation with
											// specified reliability target is the number of rows in the
		for (Object row : reliabilityWindow.toArray()) {
			System.out.println(row);
		} // reliabilityWindow
		return nPushes;
	}

	/**
	 * Calculates the fixed transmission per link, the total transmission cost, and
	 * returns it.
	 * 
	 * @param flow
	 * @return txArrayList
	 */
	public ArrayList<Integer> getFixedTxPerLinkAndTotalTxCost(Flow flow) {

		var nodesInFlow = flow.nodes;
		var nNodesInFlow = nodesInFlow.size();
		ArrayList<Integer> txArrayList = new ArrayList<Integer>();
		/*
		 * Each node will have at most numFaults+1 transmissions. Because we don't know
		 * which nodes will
		 * send the message over an edge, we give the cost to each node.
		 */
		for (int i = 0; i < nNodesInFlow; i++) {
			txArrayList.add(numFaults + 1);
		}
		/*
		 * now compute the maximum # of TX, assuming at most numFaults occur on an edge
		 * per period, and
		 * each edge requires at least one successful TX.
		 */
		var numEdgesInFlow = nNodesInFlow - 1;
		var maxFaultsInFlow = numEdgesInFlow * numFaults;
		txArrayList.add(numEdgesInFlow + maxFaultsInFlow);
		return txArrayList;
	}

	/**
	 * Calculates the number of transmissions per link and total cost based on which
	 * constructor was used.
	 * 
	 * @param flow
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> numTxPerLinkAndTotalTxCost(Flow flow) {
		if (this.constructorUsed.equals(E2E_M_CONSTRUCTOR)) {
			return this.numTxAttemptsPerLinkAndTotalTxAttempts(flow, this.e2e, this.minPacketReceptionRate, true);
		}
		return this.getFixedTxPerLinkAndTotalTxCost(flow);
	}

	public ReliabilityAnalysis(Program program) {
		this.program = program;
		buildReliabilityTable();

	}
	
	public void setReliabilityHeaderRow(ArrayList<String> reliabiltyHeaderRow) {
		
	}
	
	private void buildReliabilityTable() {
		WorkLoad wl = program.toWorkLoad();
		ArrayList<String> flowNames = wl.getFlowNamesInPriorityOrder();
		FlowMap flows = wl.getFlows();
		ArrayList<ReliabilityNode> reliabilityNodes = new ArrayList<ReliabilityNode>();
		int numCols = 0;
		for (String flowName : flowNames) {
			Flow flow = flows.get(flowName);
			Integer flowPeriod = flow.getPeriod();
			ArrayList<Node> nodes = flow.getNodes();
			for (int i = 0; i<nodes.size(); i++) {
				String nodeName = nodes.get(i).toString();
				ReliabilityNode reliabilityNode;
				if (i == 0) {
					reliabilityNode = new ReliabilityNode(nodeName, flowName, true, i, flowPeriod);
					reliabilityNode.prevR = 1.0;
				}
				else {
					reliabilityNode = new ReliabilityNode(nodeName, flowName, false, i, flowPeriod);
				}
				reliabilityNodes.add(reliabilityNode);
				numCols++;
				
				nodeMap.put(flowName + ":" + nodeName, reliabilityNode);
				
			}
		}
		
		int numRows = program.getSchedule().getNumRows();
		reliabilityTable = new ReliabilityTable(numRows, numCols);
		
		// set source nodes to 1.0
		for (int i = 0; i<reliabilityNodes.size(); i++) {
			ReliabilityNode rNode = reliabilityNodes.get(i);
			if (rNode.isSourceNode == true) {
				for (int j = 0; j<numRows; j++) {
					reliabilityTable.get(j).set(i, 1.0);
				}
			}
		}
		WarpDSL dsl = new WarpDSL();
		ProgramSchedule schedule = program.getSchedule();
		for (int i = 0; i<numRows; i++) {
			InstructionTimeSlot timeSlot = schedule.get(i);
			for (String instruction : timeSlot) {
				InstructionParameters parameters = dsl.getInstructionParameters(instruction).get(0);
				String flow = parameters.getFlow();
				if (flow.equals("unused")) {
					// TODO: copy previous value
					continue;
				}
				String src = parameters.getSrc();
				String snk = parameters.getSnk();
				
				ReliabilityNode rNodeSrc = nodeMap.get(flow + ":" + src);
				ReliabilityNode rNodeSnk = nodeMap.get(flow + ":" + snk);
				
				int src_index = reliabilityNodes.indexOf(rNodeSrc);
				int snk_index = reliabilityNodes.indexOf(rNodeSnk);
				
				Double src_r = reliabilityTable.get(i, src_index);
				Double snk_r = reliabilityTable.get(i, snk_index);
				
				// check for period, clear out if new period
				if (i % rNodeSrc.period == 0 && rNodeSrc.isSourceNode == true) {
					Flow flowToClear = flows.get(flow);
					int flowToClear_len = flowToClear.getNodes().size();
					for (int index = 0; index < flowToClear_len; index++) {
						if (index == 0) {
							continue;
						}
						for (int k = i; k < numRows; k++) {
							reliabilityTable.get(k).set(src_index + index, 0.0);
						}
						reliabilityNodes.get(src_index + index).prevR = 0.0;
					}
				}
				
				// src node reliability calculation
				// TODO: this part does not work. is meant to fix src calc.
				if (rNodeSrc.isSourceNode == false && rNodeSrc.prevR <= e2e) {
					Double src_r_prev = reliabilityNodes.get(src_index - 1).prevR;
					Double next_r = calcNextNodeReliability(src_r, src_r_prev);
					rNodeSrc.prevR = next_r;
					for (int k = i; k < numRows; k++) {
						reliabilityTable.get(k).set(src_index, next_r);
					}
				}
				
				
				Double next_r = calcNextNodeReliability(rNodeSnk.prevR, src_r);
				rNodeSnk.prevR = next_r;
				// copy values down
				for (int k = i; k < numRows; k++) {
					reliabilityTable.get(k).set(snk_index, next_r);
				}
			}
		}
	}
	
	private Double calcNextNodeReliability(Double prevSnk, Double prevSrc) {
		
		if (prevSnk == null || prevSrc == null) {
			return 0.0;
		}
		
		double M = program.getMinPacketReceptionRate();
		return (1-M) * prevSnk + M * prevSrc;
	}
	
	private void carryForwardReliabilities(Integer timeSlot, NodeMap
			nodeMap, ReliabilityTable reliabilities) {
		
	}

	private void printRaTable(ReliabilityTable reliabilities, Integer timeSlot) {
		
	}
	
	private void setReliabilities(ReliabilityTable rm) {
		
	}
	
	private void setInitialStateForReleasedFlows(NodeMap nodeMap,
			ReliabilityTable reliabilities) {
		
	}
	
	public ReliabilityTable getReliabilities() {
//		ProgramSchedule schedule = program.getSchedule();
//		int numrow = schedule.getNumRows();
//		WorkLoad workload = program.workLoad;
//		FlowMap flows = workload.getFlows();
//		String[] flownames = workload.getFlowNames();
//		int numcol = 0;
//		for (int i=0; i < flownames.length;i++) {
//			String flowname = flownames[i];
//			Flow flow = flows.get(flowname);
//			int numNodes = flow.getNodes().size();
//			numcol = numcol + numNodes;
//		}
//		
//		
//		ReliabilityTable rt = new ReliabilityTable(numrow, numcol);
//		return rt;
		return this.reliabilityTable;
		// TODO implement this operation
	}

	public Boolean verifyReliabilities() {
		// TODO Auto-generated method stub
		return true;
	}

}
