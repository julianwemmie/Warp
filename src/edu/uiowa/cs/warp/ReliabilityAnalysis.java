package edu.uiowa.cs.warp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

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
	
	private Double e2e = 0.99;
	private  Double minPacketReceptionRate = 0.9;
	private Integer numFaults = 0;
	private Program program = null;
	
	public ArrayList<Integer> numTxAttemptsPerLinkAndTotalTxAttempts(Flow flow, Double e2e, Double M,
		      boolean optimizationRequested) {
		    var nodesInFlow = flow.nodes;
		    var nNodesInFlow = nodesInFlow.size(); // The last entry will contain the worst-case cost of
		                                           // transmitting E2E in isolation
		    // ArrayList<Integer> nPushes = Array(repeating: 0, count: nNodesInFlow+1);
		    ArrayList<Integer> nPushes = new ArrayList<Integer>();// Array to track nPushes for each node in this
		    for (int i = 0; i < nNodesInFlow + 1; i++) {
		    	// initialize to all 0 values
		    	nPushes.add(0);    }
		                                                 // flow (same as nTx per link)
		    
		    var nHops = nNodesInFlow - 1;
		    // minLinkReliablityNeded is the minimum reliability needed per link in a flow to hit E2E
		    // reliability for the flow
		    Double minLinkReliablityNeded = Math.max(e2e, Math.pow(e2e, (1.0 / (double) nHops))); // use max
		                                                                                          // to
		                                                                                          // handle
		                                                                                          // rounding
		                                                                                          // error
		                                                                                          // when
		                                                                                          // e2e ==
		                                                                                          // 1.0
		    // Now compute reliability of packet reaching each node in the given time slot
		    // Start with a 2-D reliability window that is a 2-D matrix of no size
		    // each row is a time slot, stating at time 0
		    // each column represents the reliability of the packet reaching that node at the
		    // current time slot (i.e., the row it is in)
		    // will add rows as we compute reliabilities until the final reliability is reached
		    // for all nodes.
		    var reliabilityWindow = new Vector<Vector<Double>>();
		    var newReliabilityRow = new Vector<Double>();
		    for (int i = 0; i < nNodesInFlow; i++) {
		      newReliabilityRow.add(0.0); // create the the row initialized with 0.0 values
		    }
		    reliabilityWindow.add(newReliabilityRow); // now add row to the reliability window, Time 0
		    Vector<Double> tmpVector = reliabilityWindow.get(0);
		    var currentReliabilityRow = tmpVector.toArray(new Double[tmpVector.size()]);
		    // var currentReliabilityRow = (Double[]) reliabilityWindow.get(0).toArray();
		    // Want reliabilityWindow[0][0] = 1.0 (i.e., P(packet@FlowSrc) = 1
		    // but I din't want to mess with the newReliablityRow vector I use below
		    // So, we initialize this first entry to 1.0, which is reliabilityWindow[0][0]
		    // We will then update this row with computed values for each node and put it
		    // back in the matrix
		    currentReliabilityRow[0] = 1.0; // initialize (i.e., P(packet@FlowSrc) = 1
		    Double e2eReliabilityState = currentReliabilityRow[nNodesInFlow - 1]; // the analysis will end
		                                                                          // when the 2e2
		                                                                          // reliability metrix is
		                                                                          // met, initially the
		                                                                          // state is not met and
		                                                                          // will be 0 with this
		                                                                          // statement
		    var timeSlot = 0; // start time at 0
		    while (e2eReliabilityState < e2e) { // change to while and increment increment timeSlot becuase
		                                        // we don't know how long this schedule window will last
		      var prevReliabilityRow = currentReliabilityRow;
		      currentReliabilityRow = newReliabilityRow.toArray(new Double[newReliabilityRow.size()]); // would
		                                                                                               // be
		                                                                                               // reliabilityWindow[timeSlot]
		                                                                                               // if
		                                                                                               // working
		                                                                                               // through
		                                                                                               // a
		                                                                                               // schedule
		      // Now use each flow:src->sink to update reliability computations
		      // this is the update formula for the state probabilities
		      // nextState = (1 - M) * prevState + M*NextHighestFlowState
		      // use MinLQ for M in above equation
		      // NewSinkNodeState = (1-M)*PrevSnkNodeState + M*PrevSrcNodeState

		      for (int nodeIndex = 0; nodeIndex < (nNodesInFlow - 1); nodeIndex++) { // loop through each
		                                                                             // node in the flow and
		                                                                             // update the sates for
		                                                                             // each link (i.e.,
		                                                                             // sink->src pair)
		        var flowSrcNodeindex = nodeIndex;
		        var flowSnkNodeindex = nodeIndex + 1;
		        var prevSrcNodeState = prevReliabilityRow[flowSrcNodeindex];
		        var prevSnkNodeState = prevReliabilityRow[flowSnkNodeindex];
		        Double nextSnkState;
		        if ((prevSnkNodeState < minLinkReliablityNeded) && prevSrcNodeState > 0) { // do a push
		                                                                                   // until PrevSnk
		                                                                                   // state > e2e to
		                                                                                   // ensure next
		                                                                                   // node reaches
		                                                                                   // target E2E BUT
		                                                                                   // skip if no
		                                                                                   // chance of
		                                                                                   // success (i.e.,
		                                                                                   // source doesn't
		                                                                                   // have packet)
		          nextSnkState = ((1.0 - M) * prevSnkNodeState) + (M * prevSrcNodeState); // need to
		                                                                                  // continue
		                                                                                  // attempting to
		                                                                                  // Tx, so update
		                                                                                  // current state
		          nPushes.set(nodeIndex, nPushes.get(nodeIndex) + 1); // increment the number of pushes for for this node to snk node
		        } else {
		          nextSnkState = prevSnkNodeState; // snkNode has met its reliability. Thus move on to the
		                                           // next node and record the reliability met
		        }

		        if (currentReliabilityRow[flowSrcNodeindex] < prevReliabilityRow[flowSrcNodeindex]) { // probabilities
		                                                                                              // are
		                                                                                              // non-decreasing
		                                                                                              // so
		                                                                                              // update
		                                                                                              // if
		                                                                                              // we
		                                                                                              // were
		                                                                                              // higher
		                                                                                              // by
		                                                                                              // carring
		                                                                                              // old
		                                                                                              // value
		                                                                                              // forward
		          currentReliabilityRow[flowSrcNodeindex] = prevReliabilityRow[flowSrcNodeindex]; // carry
		                                                                                          // forward
		                                                                                          // the
		                                                                                          // previous
		                                                                                          // state
		                                                                                          // for the
		                                                                                          // src
		                                                                                          // node,
		                                                                                          // which
		                                                                                          // may get
		                                                                                          // over
		                                                                                          // written
		                                                                                          // later
		                                                                                          // by
		                                                                                          // another
		                                                                                          // instruction
		                                                                                          // in this
		                                                                                          // slot
		        }
		        currentReliabilityRow[flowSnkNodeindex] = nextSnkState;
		      }

		      e2eReliabilityState = currentReliabilityRow[nNodesInFlow - 1];
		      Vector<Double> currentReliabilityVector = new Vector<Double>();
		      // convert the row to a vector so we can add it to the reliability window
		      Collections.addAll(currentReliabilityVector, currentReliabilityRow);
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
		                                  // reliabilityWindow
		    return nPushes;
	}
	 public ArrayList<Integer> getFixedTxPerLinkAndTotalTxCost(Flow flow) {
		    var nodesInFlow = flow.nodes;
		    var nNodesInFlow = nodesInFlow.size();
		    ArrayList<Integer> txArrayList = new ArrayList<Integer>();
		    /*
		     * Each node will have at most numFaults+1 transmissions. Because we don't know which nodes will
		     * send the message over an edge, we give the cost to each node.
		     */
		    for (int i = 0; i < nNodesInFlow; i++) {
		      txArrayList.add(numFaults + 1);
		    }
		    /*
		     * now compute the maximum # of TX, assuming at most numFaults occur on an edge per period, and
		     * each edge requires at least one successful TX.
		     */
		    var numEdgesInFlow = nNodesInFlow - 1;
		    var maxFaultsInFlow = numEdgesInFlow * numFaults;
		    txArrayList.add(numEdgesInFlow + maxFaultsInFlow);
		    return txArrayList;
		  }
	
	public ArrayList<Integer> numTxPerLinkAndTotalTxCost(Flow flow) {
		
		if()
		
	}

	public ReliabilityAnalysis(Double e2e, Double minPacketReceptionRate) {		// TODO implement this operation
		this.e2e = e2e;
		this.minPacketReceptionRate = minPacketReceptionRate;
	}

	public ReliabilityAnalysis(Integer numFaults) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public ReliabilityAnalysis(Program program) {
		// TODO Auto-generated constructor stub
	}

	public ReliabilityTable getReliabilities() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Boolean verifyReliabilities() {
		// TODO Auto-generated method stub
		return true;
	}

}
