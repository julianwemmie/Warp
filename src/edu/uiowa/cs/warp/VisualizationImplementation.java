/**
 * 
 */
package edu.uiowa.cs.warp;

import java.io.File;

/**
 * Handles visualizations for Warp program outputs.
 * This includes creating either GraphViz files (.gv) or WARP files (.wf) and 
 * displaying graphs in a new window.
 * 
 * @author sgoddard
 * @version 1.5
 */
public class VisualizationImplementation implements Visualization {

  /**
   * Visualization created based on selected choice.
   */
  private Description visualization;
  
  /**
   * Stored visualization to be written to file
   */
  private Description fileContent;
  
  /**
   * Window containing gui visualization.
   * @see GuiVisualization
   */
  private GuiVisualization window;
  
  /**
   * The name of the output file.
   */
  private String fileName;
  
  /**
   * The name of the input file.
   */
  private String inputFileName;
  
  /**
   * Formatted file path.
   */
  private String fileNameTemplate;
  
  /**
   * File Manager to help with I/O
   * @see FileManager
   */
  private FileManager fm = null;
  
  /**
   * Warp output to be visualized.
   */
  private WarpInterface warp = null;
  
  /**
   * Warp workload to be visualized.
   */
  private WorkLoad workLoad = null;
  
  /**
   * Specific implementation of visualization based on option selected.
   */
  private VisualizationObject visualizationObject;


  /**
   * Creates visualization from warp program output.
   * 
   * @param warp WarpInterface object. 
   * @param outputDirectory Where to save the visualization
   * @param choice Type of visualization to create. @see Visualization
   */
  public VisualizationImplementation(WarpInterface warp, String outputDirectory,
      SystemChoices choice) {
    this.fm = new FileManager();
    this.warp = warp;
    inputFileName = warp.toWorkload().getInputFileName();
    this.fileNameTemplate = createFileNameTemplate(outputDirectory);
    visualizationObject = null;
    createVisualization(choice);
  }

  /**
   * Creates visualization from given workload.
   * 
   * @param workLoad WorkLoad object
   * @param outputDirectory Where to save the visualization
   * @param choice Type of visualization to create. @see Visualization
   */
  public VisualizationImplementation(WorkLoad workLoad, String outputDirectory,
      WorkLoadChoices choice) {
    this.fm = new FileManager();
    this.workLoad = workLoad;
    inputFileName = workLoad.getInputFileName();
    this.fileNameTemplate = createFileNameTemplate(outputDirectory);
    visualizationObject = null;
    createVisualization(choice);
  }

  /**
   * Displays visualization in a new window.
   */
  @Override
  public void toDisplay() {
    // System.out.println(displayContent.toString());
    window = visualizationObject.displayVisualization();
    if (window != null) {
      window.setVisible();
    }
  }

  /**
   * Writes visualization to a file.
   */
  @Override
  public void toFile() {
    fm.writeFile(fileName, fileContent.toString());
  }

  
  /**
   * Converts visualization object to string format.
   */
  @Override
  public String toString() {
    return visualization.toString();
  }

  private void createVisualization(SystemChoices choice) {
    switch (choice) { // select the requested visualization
      case SOURCE:
        createVisualization(new ProgramVisualization(warp));
        break;

      case RELIABILITIES:
        // TODO Implement Reliability Analysis Visualization
        createVisualization(new ReliabilityVisualization(warp));
        break;

      case SIMULATOR_INPUT:
        // TODO Implement Simulator Input Visualization
        createVisualization(new NotImplentedVisualization("SimInputNotImplemented"));
        break;

      case LATENCY:
        // TODO Implement Latency Analysis Visualization
        createVisualization(new LatencyVisualization(warp));
        break;

      case CHANNEL:
        // TODO Implement Channel Analysis Visualization
        createVisualization(new ChannelVisualization(warp));
        break;

      case LATENCY_REPORT:
        createVisualization(new ReportVisualization(fm, warp,
            new LatencyAnalysis(warp).latencyReport(), "Latency"));
        break;

      case DEADLINE_REPORT:
        createVisualization(
            new ReportVisualization(fm, warp, warp.toProgram().deadlineMisses(), "DeadlineMisses"));
        break;

      default:
        createVisualization(new NotImplentedVisualization("UnexpectedChoice"));
        break;
    }
  }

  private void createVisualization(WorkLoadChoices choice) {
    switch (choice) { // select the requested visualization
      case COMUNICATION_GRAPH:
        // createWarpVisualization();
        createVisualization(new CommunicationGraph(fm, workLoad));
        break;

      case GRAPHVIZ:
        createVisualization(new GraphViz(fm, workLoad.toString()));
        break;

      case INPUT_GRAPH:
        createVisualization(workLoad);
        break;

      default:
        createVisualization(new NotImplentedVisualization("UnexpectedChoice"));
        break;
    }
  }

  private <T extends VisualizationObject> void createVisualization(T obj) {
    visualization = obj.visualization();
    fileContent = obj.fileVisualization();
    /* display is file content printed to console */
    fileName = obj.createFile(fileNameTemplate); // in output directory
    visualizationObject = obj;
  }

  private String createFileNameTemplate(String outputDirectory) {
    String fileNameTemplate;
    var workingDirectory = fm.getBaseDirectory();
    var newDirectory = fm.createDirectory(workingDirectory, outputDirectory);
    // Now create the fileNameTemplate using full output path and input filename
    if (inputFileName.contains("/")) {
      var index = inputFileName.lastIndexOf("/") + 1;
      fileNameTemplate = newDirectory + File.separator + inputFileName.substring(index);
    } else {
      fileNameTemplate = newDirectory + File.separator + inputFileName;
    }
    return fileNameTemplate;
  }

}
