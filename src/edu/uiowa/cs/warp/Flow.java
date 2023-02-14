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
	 * @return the initialPriority
	 */
	public Integer getInitialPriority() {
		return initialPriority;
	}

	/**
	 * Gets the index of the flow.
	 *  
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * Gets the number of transmissions per link of the flow.
	 * 
	 * @return the numTxPerLink
	 */
	public Integer getNumTxPerLink() {
		return numTxPerLink;
	}

	/**
	 * Gets and ArrayList of nodes in the flow.
	 * 
	 * @return nodes
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	/**
	 * Gets and ArrayList of edges in the flow.
	 * 
	 * @return edges
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * Add an edge to the flow.
	 * 
	 * @param edge
	 */
	public void addEdge(Edge edge) {
		/* set predecessor and add edge to flow */
		edge.setPredecessor(edgePredecessor);
		edges.add(edge);
		/* update predecessor for next edge added */
		edgePredecessor = edge;
	}
	
	/**
	 * Add a node to the flow.
	 * 
	 * @param node
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
	 * @return linkTxAndTotalCost
	 */
	public ArrayList<Integer> getLinkTxAndTotalCost() {
		return linkTxAndTotalCost;
	}

	/**
	 * Sets the initial priority of the flow.
	 * 
	 * @param initialPriority 
	 */
	public void setInitialPriority(Integer initialPriority) {
		this.initialPriority = initialPriority;
	}

	/**
	 * Sets the index of a node in the flow.
	 * 
	 * @param index 
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * Sets the number of transmissions per link.
	 * 
	 * @param numTxPerLink 
	 */
	public void setNumTxPerLink(Integer numTxPerLink) {
		this.numTxPerLink = numTxPerLink;
	}

	/**
	 * Sets and ArrayList of nodes to the flow.
	 * 
	 * @param nodes 
	 */
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Sets an ArrayList of link transmission and total cost to the flow.
	 * 
	 * @param linkTxAndTotalCost 
	 */
	public void setLinkTxAndTotalCost(ArrayList<Integer> linkTxAndTotalCost) {
		this.linkTxAndTotalCost = linkTxAndTotalCost;
	}
	/**
	 * Compares the flow based on priority. Ordered in ascending order with 0 being the highest priority.
	 * 
	 * @param flow 
	 */
	@Override
    public int compareTo(Flow flow) {
    	// ascending order (0 is highest priority)
        return flow.getPriority() > this.getPriority() ? -1 : 1;
    }
    /**
     * Gets the name of the flow.
     * 
     * return getName();
     */
    @Override
    public String toString() {
        return getName();
    }
    
}
