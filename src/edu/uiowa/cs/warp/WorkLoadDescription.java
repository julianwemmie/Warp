/**
 * 
 */
package edu.uiowa.cs.warp;

import java.util.*;
/**
 * Reads the input file, whose name is passed as input parameter to the constructor, and builds a
 * Description object based on the contents. Each line of the file is an entry (string) in the
 * Description object.
 * 
 * @author sgoddard
 * @version 1.4 Fall 2022
 */
public class WorkLoadDescription extends VisualizationObject {

  private static final String EMPTY = "";
  private static final String INPUT_FILE_SUFFIX = ".wld";
  //adding a final variable for easy file changing
  private static final String FILE = "StressTest.txt";

  private Description description;
  private String inputGraphString;
  private FileManager fm;
  private String inputFileName;

  WorkLoadDescription(String inputFileName) {
    super(new FileManager(), EMPTY, INPUT_FILE_SUFFIX); // VisualizationObject constructor
    this.fm = this.getFileManager();
    initialize(inputFileName);
  }

  @Override
  public Description visualization() {
    return description;
  }

  @Override
  public Description fileVisualization() {
    return description;
  }

  // @Override
  // public Description displayVisualization() {
  // return description;
  // }

  @Override
  public String toString() {
    return inputGraphString;
  }

  public String getInputFileName() {
    return inputFileName;
  }

  private void initialize(String inputFile) {
    // Get the input graph file name and read its contents
    InputGraphFile gf = new InputGraphFile(fm);
    inputGraphString = gf.readGraphFile(inputFile);
    this.inputFileName = gf.getGraphFileName();
    description = new Description(inputGraphString);
   
  }
  
  public static void main(String[] args) {
	  //Initiating new WorkLoadDescription w/ FILE
	  //FILE is a static final variable initialized at the beginning of the file
	  //allowing for quick swap of files
	   WorkLoadDescription wldTest = new WorkLoadDescription(FILE);
	   //Initializing new array list for which elements of the file will be stored
	   ArrayList<String> testFile = new ArrayList<String>();
	   //for loop to add elements from wldTest to Array list skipping the title line
	   for(int i = 1 ; i < wldTest.visualization().size(); i++) {
		   testFile.add(wldTest.visualization().get(i));
	   }
	   //Sorting the new ArrayList
	   Collections.sort(testFile);
	   //Print out the input file name without suffix
	   System.out.println(wldTest.getInputFileName().replace(".txt", ""));
	   //for loop goes through the new ArrayList skipping the final "}"
	   for(int i = 0 ; i < testFile.size() - 1; i++ ) {
		   //if condition skips blank lines
		   if (testFile.get(i).isBlank()) {
			   continue;
		   }
		   //Printing out results formatted to example, trimming leading or ending blank space
		   System.out.println("Flow " + (i + 1) +": " + testFile.get(i).trim() );
		   
	   }
	   
	   
	  
	   
	   
	  

	   	   
	   
	   } 
  }
