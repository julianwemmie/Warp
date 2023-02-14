
package edu.uiowa.cs.warp;

/**
 * Allows the user to generate a GUI to visualize WARP data given by .dsl files.
 * If applicable, the GUI will contain minimum packet reception rate, End-to-End transmissions, 
 * and number of channels.
 *
 * @author sgoddard
 * @version 1.5
 * 
 */
public class ProgramVisualization extends VisualizationObject {
/**
 * Sets the suffix of the file to be only .dsl
 * 
 */
  private static final String SOURCE_SUFFIX = ".dsl";
  /**
   * The WARP source code to be used.
   */
  private ProgramSchedule sourceCode;
  /**
   * The program to draw data from.
   * 
   */
  private Program program;
  /**
   * Deadlines met flag.
   */
  private Boolean deadlinesMet;
  /**
   * Constructor for new ProgramVisualization. Used WARP file as input changes it to
   * a program and gets the schedule and deadlines met.
   * 
   * @param warp
   */
  ProgramVisualization(WarpInterface warp) {
    super(new FileManager(), warp, SOURCE_SUFFIX);
    this.program = warp.toProgram();
    this.sourceCode = program.getSchedule();
    this.deadlinesMet = warp.deadlinesMet();
  }
  /**
   * Creates a Java Swing GUI window containing the title, column headers and the visualization data.
   * 
   * @return new GuiVisualization
   */
  @Override
  public GuiVisualization displayVisualization() {
    return new GuiVisualization(createTitle(), createColumnHeader(), createVisualizationData());
  }
  /**
   * Creates a header for the GUI visualization, takes the tile from the program's scheduler name. If applicable
   *  adds number of faults (if greater than 0), minimum packet reception rate, E2E, and number of
   *  channels.
   *  
   *  @return header
   */
  @Override
  protected Description createHeader() {
    Description header = new Description();

    header.add(createTitle());
    header.add(String.format("Scheduler Name: %s\n", program.getSchedulerName()));

    /* The following parameters are output based on a special schedule or the fault model */
    if (program.getNumFaults() > 0) { // only specify when deterministic fault model is assumed
      header.add(String.format("numFaults: %d\n", program.getNumFaults()));
    }
    header.add(String.format("M: %s\n", String.valueOf(program.getMinPacketReceptionRate())));
    header.add(String.format("E2E: %s\n", String.valueOf(program.getE2e())));
    header.add(String.format("nChannels: %d\n", program.getNumChannels()));
    return header;
  }
  /**
   * Creates a footer. The message is determined by if all the flows met their deadlines.
   * 
   * @return footer
   */
  @Override
  protected Description createFooter() {
    Description footer = new Description();
    String deadlineMsg = null;

    if (deadlinesMet) {
      deadlineMsg = "All flows meet their deadlines\n";
    } else {
      deadlineMsg = "WARNING: NOT all flows meet their deadlines. See deadline analysis report.\n";
    }
    footer.add(String.format("// %s", deadlineMsg));
    return footer;
  }
  /**
   * Creates a string array of column headers ordered alphabetically.
   * 
   * @return columnNames
   */
  
  @Override
  protected String[] createColumnHeader() {
    var orderedNodes = program.toWorkLoad().getNodeNamesOrderedAlphabetically();
    String[] columnNames = new String[orderedNodes.length + 1];
    columnNames[0] = "Time Slot"; // add the Time Slot column header first
    /* loop through the node names, adding each to the header */
    for (int i = 0; i < orderedNodes.length; i++) {
      columnNames[i + 1] = orderedNodes[i];
    }
    return columnNames;
  }
  /**
   * Creates a string matrix using the data collected from visualizationData.
   * 
   * @return visualizationData
   */
  @Override
  protected String[][] createVisualizationData() {
    if (visualizationData == null) {
      int numRows = sourceCode.getNumRows();
      int numColumns = sourceCode.getNumColumns();
      visualizationData = new String[numRows][numColumns + 1];

      for (int row = 0; row < numRows; row++) {
        visualizationData[row][0] = String.format("%s", row);
        for (int column = 0; column < numColumns; column++) {
          visualizationData[row][column + 1] = sourceCode.get(row, column);
        }
      }
    }
    return visualizationData;
  }
  /**
   * Creates a title for the GUI using the name from the program.
   * 
   * 
   */
  private String createTitle() {
    return String.format("WARP program for graph %s\n", program.getName());
  }
}
