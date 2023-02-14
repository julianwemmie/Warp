package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * Flow is a java class in which the user creates a flow object based on the graph file provided.
 * The flows, edges, link transmission and total cost are all added to separate ArrayLists which are stored and can
 * be accessed if needed. Methods inside the class allow the user to manipulate certain aspects of the flow and return
 * certain values.
 * 
 * @author sgoddard
 */
public class Flow extends SchedulableObject implements Comparable<Flow>{

	private static final Integer UNDEFINED = -1;
	private static final Integer DEFAULT_FAULTS_TOLERATED = 0; 
	private static final Integer DEFAULT_INDEX = 0;
	private static final Integer DEFAULT_PERIOD = 100; 
	private static final Integer DEFAULT_DEADLINE = 100;
	private static final Integer DEFAULT_PHASE = 0;
	

    Integer initialPriority = UNDEFINED;
    Integer index;  // order in which the node was read from the Graph file
    Integer numTxPerLink; //  determined by fault model
    ArrayList<Node> nodes; // Flow src is 1st element and flow snk is last element in array
    /*
     *  nTx needed for each link to reach E2E reliability target. Indexed by src node of the link. 
     *  Last entry is total worst-case E2E Tx cost for schedulability analysis
     */
    ArrayList<Integer> linkTxAndTotalCost; 
    ArrayList<Edge> edges; //used in Partition and scheduling
    Node nodePredecessor;
    Edge edgePredecessor;
    
    /*
     * Constructor that sets name, priority, and index of the flow
     */
    Flow (String name, Integer priority, Integer index){
    	super(name, priority, DEFAULT_PERIOD, DEFAULT_DEADLINE, DEFAULT_PHASE);
    	this.index = index;
        /*
         *  Default numTxPerLink is 1 transmission per link. Will be updated based
         *  on flow updated based on flow length and reliability parameters
         */
        this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
        this.nodes = new ArrayList<>();
        this.edges  = new ArrayList<>();
        this.linkTxAndTotalCost = new ArrayList<>();
        this.edges = new ArrayList<>();	
        this.nodePredecessor = null;
        this.edgePredecessor = null;
    }
    
    /*
     * Constructor
     */
    Flow () {
    	super();
    	this.index = DEFAULT_INDEX;
    	/*
    	 *  Default numTxPerLink is 1 transmission per link. Will be updated based
    	 *  on flow updated based on flow length and reliability parameters
    	 */
    	this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
    	this.nodes = new ArrayList<>();
    	this.linkTxAndTotalCost = new ArrayList<>();
    	this.edges = new ArrayList<>();
    	this.nodePredecessor = null;
        this.edgePredecessor = null;
    }

	/**
	 * Gets the initial priority of the flow.
	 * 
	 * 
	 */
	public Integer getInitialPriority() {
		return initialPriority;
	}

	/**
	 * Gets the index of the flow. The order in which the node was read from the 
	 * graph file.
	 *  
	 * 
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * Gets the number of transmissions per link of the flow.
	 * Defaulted to 1.
	 * 
	 * 
	 */
	public Integer getNumTxPerLink() {
		return numTxPerLink;
	}

	/**
	 * Gets an ArrayList of nodes in the flow.
	 * 
	 * 
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	/**
	 * Gets and ArrayList of edges in the flow.
	 * 
	 * 
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * Add an edge to the flow by first setting the edge predecessor, adding the edge, then
	 * updating the predecessor for the next node.
	 * 
	 * @param edge the edge to added
	 */
	public void addEdge(Edge edge) {
		/* set predecessor and add edge to flow */
		edge.setPredecessor(edgePredecessor);
		edges.add(edge);
		/* update predecessor for next edge added */
		edgePredecessor = edge;
	}
	
	/**
	 * Add a node to the flow by first setting its predecessor, adding the node, then 
	 * updating the predecessor for the next node.
	 * 
	 * @param node the node to be added
	 */
	public void addNode(Node node) {
		/* set predecessor and add edge to flow */
		node.setPredecessor(nodePredecessor);
		nodes.add(node);
		/* update predecessor for next edge added */
		nodePredecessor = node;
	}
	/**
	 * Gets the link transmission and total cost of the flow.
	 * 
	 * @return ArrayList of link transmission and total cost values.
	 */
	public ArrayList<Integer> getLinkTxAndTotalCost() {
		return linkTxAndTotalCost;
	}

	/**
	 * Sets the initial priority of the flow. Default is -1.
	 * 
	 * @param initialPriority the initial priority to be set
	 */
	public void setInitialPriority(Integer initialPriority) {
		this.initialPriority = initialPriority;
	}

	/**
	 * Sets the index of a node in the flow. Default is 0.
	 * 
	 * @param index the index to be set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * Sets the number of transmissions per link. Determined by the fault model.
	 * 
	 * @param numTxPerLink the number of transmissions per link to be set.
	 */
	public void setNumTxPerLink(Integer numTxPerLink) {
		this.numTxPerLink = numTxPerLink;
	}

	/**
	 * Sets a list of nodes to the flow.
	 * 
	 * @param nodes the ArrayList of nodes to be set.
	 */
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Sets a list of link transmission and total cost to the flow.
	 * 
	 * @param linkTxAndTotalCost the ArrayList of values to set.
	 */
	public void setLinkTxAndTotalCost(ArrayList<Integer> linkTxAndTotalCost) {
		this.linkTxAndTotalCost = linkTxAndTotalCost;
	}
	/**
	 * Compares the flow based on priority. Ordered in ascending order with 0 being the highest priority.
	 * 
	 * @param flow the flow to be compared.
	 */
	@Override
    public int compareTo(Flow flow) {
    	// ascending order (0 is highest priority)
        return flow.getPriority() > this.getPriority() ? -1 : 1;
    }
    /**
     * Gets the name of the flow.
     * 
     * @return The name of the flow.
     */
    @Override
    public String toString() {
        return getName();
    }
    
}
