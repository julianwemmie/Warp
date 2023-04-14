# CS2820 Spring 2023 WARP Project Code
This code base will be used for the University of Iowa CS 2820 Introduction to Software
Development course. The code was developed by Steve Goddard for the WARP sensor network 
research project. It was first written in Swift and rewritten in Java. It was then 
rewritten again in an object-oriented programming style. It was a quick
hack, and it needs a lot of cleanup and refactoring. A perfect code base to teach
the value of software developement fundamentals!

Edited by Ethan Boileau on 1/22/2023

2/7/2023: <br>
Implemented a main() function to <b> WorkLoadDescription.java </b>. The user inputs a graph text file,
and the function outputs the file with the flows alphabetically ordered and numbered in ascending order. 

<br>
2/9/2023: <br>
Julian Wemmie: Added Javadocs to all attributes of Warp.java

2/13/2023: <br>
Ethan Boileau: Added Javadocs to Flow.java, WorkLoad.java, and ProgramVisualization.java

2/13/2023: <br>
Julian Wemmie: Added Javadocs to VisualizationImplementation.java and to the following methods in Program.java:
<ul>
	<li>getSchedule()</li>
	<li>getNodeMapIndex()</li>
	<li>toWorkLoad()</li>
</ul>

2/14/2023: <br>
Ethan Boileau: Added Javadocs to ProgramVisualization.java, added touch-ups to previous Javadocs.

<h3> HW3 Changes 2/15/23 - 2/28/23 </h3> 
Ethan Boileau: added test methods for the following methods in WorkLoad.java:
<li>addFlow()</li>
<li>addNodeToFlow() </li>
<li>getTotalTxAttemptsInFlow()</li>
<li>getFlowPriority</li>
<li>setFlowPriority</li>
<li>getFlowTxAttemptsPerLink() </li>
<li>setFlowsInRMorder() </li>
<li>getNodeNamesOrderedAlphabetically</li>

2/28/23: <br>
Julian Wemmie: added test methods for the following methods in WorkLoad.java
<li>getFlowNames() </li>
<li>getNodeIndex() </li>
<li>getNodesInFlow() </li>
<li>getHyperPeriod() </li>
<li>getNumTxAttemptsPerLink() </li>
<li>maxFlowLength() </li>
<li>getFlowDeadline() </li>
<li>setFlowDeadline()</li>

-Generated javadoc
<h3>HW4 changes 3/6/2023 </h3>
<li>Created UML diagram for ScheduleObject.java</li>
<li>Created UML diagram for WorkLoad.java</li>
<li>Created UML diagram "Reliability.uml" consisting of all
Reliability*.java classes </li>
<li>Added getReliabilities() method to ReliabilityAnalysis.java</li>
<h3> 3/7/2023 </h3>
<li>showed parents for ReliabilityVisualization.java</li>

<h3> HW5 changes 3/24/23 </h3>
<li> Ethan and Patrick, used pair programming to complete the assignment</li>
<li> Refactored two WorkLoad methods and added them to ReliabilityAnalysis</li>
<li> Created ReliabilityAnalysis constructors to be able to access the WorkLoad methods </li>
<li> Generated JavaDocs for the changes made to ReliabilityAnalysis</li><b

<br><br><br><br>

<h2> Final Project </h2>

The final project is focused on creating code to analyze and visualize the reliability of end-to-end message transmissions in a WARP program. This primary goal of the first sprint is to organize and lay out a plan of action to write code, test, and document our changes to the ReliabilityAnalysis.java and ReliabilityVisualization.java classes.

<h3> Final project - Sprint 1 </h3>

This is the first of three sprints dedicated to the final project. This sprint is largely dedicated to planing out the following to Sprints. <br><br>A detailed grasp of the task to be done can be found in **SWD Project Plan.pptx**. The PowerPoint will be used as a method of delivery for further plan documentation. We will include the tasks that need to be completed and by whom, our methods for testing, how we will document any code written, and any other pertinent information.<br><br> An in depth look at the interactions between the objects and there methods can be found in **Sequence_Diagram.png**. The sequence diagram details the steps and methods called in order for the ".ra" files to be processed. <br><br>

We allocated work for this sprint with the following work distribution:
- Ethan: Wrote ReadME documentation and built out the powerpoint to outline the project's duties <br>
- Julian: Searched through the codebase and given instruction to conceptualize the relationship between the two then taught his team members what he found <br>
- Patrick: Took what he learned from Julian and Avi to build out the sequence diagrams </li>
- Avi:Worked with Julian to understand the future changes and how they would change ReliabilityAnalysis and ReliabilityVisualization<br>

<h3> Final project - Sprint 2 </h3>
Sprint 2 begins implementing the plan layed out in Sprint1. This sprint is focused on implementing the changes to the ReliabilityVisualization.java classes. ReliabilityVisualization handles displaying the data in the warp including its ReliabilityTable. Implemented methods are called by VisulaizationImplementation and include:<br>
- createTitle()<br>
- displayVisualization()<br>
- createHeader(String)<br>
- createVisualizationData()<br>
<br>
To ensure sustainability of the codebase, we have implemented Javadocs to all methods and classes. We have also implemented test cases to ensure the code is working as intended. We also built UML diagrams to model the relationship between the objects. We also continued preparing for Sprint3 by conceptulizing ReliabilityAnalysis <br><br>

We allocated work for this sprint with the following work distribution:
- Ethan: Wrote JavaDocs and ReadME for this sprint<br>
- Julian: Updated UML and sequence diagrams<br>
- Patrick: Implemented the methods in Reliability Visualization<br>
- Avi: Continued working on preparing for Sprint 3's implementation of ReliabilityAnalysis<br>
