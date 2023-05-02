package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * ReliabilityVisualization creates the visualizations for
 * the reliability analysis of the WARP program.
 * <p>
 *
 * CS2820 Spring 2023 Project: Implement this class to create
 * the file visualization that is requested in Warp.
 *
 * @author sgoddard
 *
 */
public class ReliabilityVisualization extends VisualizationObject {

	/**
	 * Sets the suffix of the file to be only .ra
	 */
	private static final String SOURCE_SUFFIX = ".ra";
	/**
	 * The name of the file in the GUI
	 */
	private static final String OBJECT_NAME = "Reliability Analysis";
	/**
	 * The WARP source code to be referenced to get the program and workload.
	 */
	private WarpInterface warp;
	/**
	 * The reliability analysis attached to the workload.
	 */
	private ReliabilityAnalysis ra;

	ReliabilityVisualization(WarpInterface warp) {
		super(new FileManager(), warp, SOURCE_SUFFIX);
		this.warp = warp;
		this.ra = warp.toReliabilityAnalysis();
	}

	/**
	 * createTitle creates the title for the reliability analysis file which is the
	 * object name and the graph name
	 */
	private String createTitle() {
		return String.format(
				OBJECT_NAME + " for graph " + warp.getName() + " created with the following parameters:" + "\n");
	}

	/**
	 * displayVisualization creates the gui visualization for the reliability
	 * analysis file
	 * 
	 * @return GuiVisualization Object
	 */
	@Override
	public GuiVisualization displayVisualization() {
		return new GuiVisualization(createTitle(), createColumnHeader(), createVisualizationData());
	}

	/**
	 * createColumnHeader creates the column header for the reliability analysis
	 * file.
	 * It gets the flow names in priority order from the workload then loops through
	 * them and gets the nodes in each flow.
	 * It then adds the flow name and node name to the output arraylist in the
	 * format "flowName:node[i]"
	 */
	@Override
	protected String[] createColumnHeader() {
		// access the workload from the warp program
		WorkLoad wl = this.warp.toProgram().toWorkLoad();
		// get the flow names in priority order from the workload
		ArrayList<String> flows = wl.getFlowNamesInPriorityOrder();
		ArrayList<String> output = new ArrayList<String>();

		// loop through each flow and get each flows nodes from getNodesInFlow and add
		// them to output in the format "flowName:node[i]"
		for (int i = 0; i < flows.size(); i++) {
			String[] nodes = wl.getNodesInFlow(flows.get(i));
			for (int j = 0; j < nodes.length; j++) {
				output.add(flows.get(i) + ":" + nodes[j]);
			}
		}
		// cast output to a string array
		String[] columnNames = output.toArray(new String[0]);
		return columnNames;
	}

	/**
	 * createHeader creates the header for the reliability analysis file. It sets
	 * the title, M, E2E, and nChannels
	 * 
	 * @return header the header for the reliability analysis file
	 */
	@Override
	protected Description createHeader() {
		Description header = new Description();
		header.add(createTitle());
		header.add("Scheduler Name: " + warp.getSchedulerName() + "\n");
		header.add("M: " + warp.getMinPacketReceptionRate() + "\n");
		header.add("E2E: " + warp.getE2e() + "\n");
		header.add("nChannels: " + warp.getNumChannels() + "\n");
		return header;
	}

	/**
	 * createVisualizationData accesses the reliability table from the reliability
	 * analysis then formats it into a 2d string array
	 * 
	 * @return data the 2d string array of the reliability table
	 */
	@Override
	protected String[][] createVisualizationData() {
		// get the reliability table from the reliability analysis
		ReliabilityTable reliabilities = this.ra.getReliabilities();
		// turn the reliability table into 2d a string array
		String[][] data = new String[reliabilities.getNumRows()][reliabilities.getNumColumns()];
		for (int i = 0; i < reliabilities.getNumRows(); i++) {
			for (int j = 0; j < reliabilities.getNumColumns(); j++) {
				data[i][j] = reliabilities.get(i, j).toString();
			}
		}
		return data;
	}

}

/*
 * File Visualization for workload defined in Example.txt follows. Note
 * that your Authentication tag will be different from this example. The
 * rest of your output in the file ExamplePriority-0.9M-0.99E2E.ra
 * should match this output, where \tab characters are used a column
 * delimiters.
 * // Course CS2820 Authentication Tag:
 * r3XWfL9ywZO36jnWMZcKC2KTB2hUCm3AQCGxREWbZRoSn4/XdrQ/
 * QuNQvtzAxeSSw55bWTXwbI9VI0Om+mEhNd4JC2UzrBBrXnHmsbPxbZ8=
 * Reliability Analysis for graph Example created with the following parameters:
 * Scheduler Name: Priority
 * M: 0.9
 * E2E: 0.99
 * nChannels: 16
 * F0:A F0:B F0:C F1:C F1:B F1:A
 * 1.0 0.9 0.0 1.0 0.0 0.0
 * 1.0 0.99 0.81 1.0 0.0 0.0
 * 1.0 0.999 0.972 1.0 0.0 0.0
 * 1.0 0.999 0.9963 1.0 0.0 0.0
 * 1.0 0.999 0.9963 1.0 0.9 0.0
 * 1.0 0.999 0.9963 1.0 0.99 0.81
 * 1.0 0.999 0.9963 1.0 0.999 0.972
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 * 1.0 0.999 0.9963 1.0 0.999 0.9963
 */
