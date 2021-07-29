package com.ccc.routes.recursion;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Test NG is a testing framework designed to simplify testing
 * This is a TestNG class that contains all of the Trains Routes Directed Graph test cases.
 * 
 * @author gibrancastillo
 *
 */
public class TrainsRoutesDirectedGraphTestNg {
	private TrainsRoutesDirectedGraph trainsRoutesDirectedGraph;
	private static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";
	private static final Logger logger = LogManager.getLogger(TrainsRoutesDirectedGraphTestNg.class);
	
	@BeforeSuite
	public void loadValidTrainsRoutesInDirectedGraph() throws Exception {
		final String VALID_TRAINS_ROUTES = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		logger.info("Setting up test ng suite with valid trains routes: " + VALID_TRAINS_ROUTES);
		trainsRoutesDirectedGraph = new TrainsRoutesDirectedGraph(VALID_TRAINS_ROUTES);
	}
	
	@AfterSuite
	public void tearDownTestSuite() throws Exception {
		trainsRoutesDirectedGraph = null;
		logger.info("Tear down test ng suite by setting the trainsRoutesDirectedGraph to " + trainsRoutesDirectedGraph);
	}
	
	@Test(priority = 1, description = "Test the creation of the trains routes", groups = {"shakeout", "regression", "MC"}, enabled = true)
	public void testCreateTrainsRoutes() {
		logger.info("Test create or build trains routes");
		
		Town<String> town = new Town<String>("A");
        List<Route> routes = trainsRoutesDirectedGraph.getTrainsMap().get(town);
        assertEquals(routes.size(), 3);
        
        town = new Town<String>("C");
        routes = trainsRoutesDirectedGraph.getTrainsMap().get(town);
        assertEquals(routes.size(), 2); 
	}
	
	@Test(priority = 2, description = "Test getting route distance for five different routes", groups = {"shakeout", "regression", "MC"}, enabled = true)
	public void testGetRouteDistance() {
		logger.info("Test question 1, the distance of the route A-B-C");
        List<Town<String>> towns = new ArrayList<>();
        towns.add(new Town<String>("A"));
		towns.add(new Town<String>("B"));
		towns.add(new Town<String>("C"));        
        assertEquals(trainsRoutesDirectedGraph.getRouteDistance(towns), "9");
        
        logger.info("Test question 2, the distance of the route A-D");
        towns.clear();
        towns.add(new Town<String>("A"));
		towns.add(new Town<String>("D"));     
        assertEquals(trainsRoutesDirectedGraph.getRouteDistance(towns), "5");
        
        logger.info("Test question 3, the distance of the route A-D-C");
        towns.add(new Town<String>("C"));     
        assertEquals(trainsRoutesDirectedGraph.getRouteDistance(towns), "13");
        
        logger.info("Test question 4, the distance of the route A-E-B-C-D");
        towns.clear();
		towns.add(new Town<String>("A"));
		towns.add(new Town<String>("E"));
		towns.add(new Town<String>("B"));
		towns.add(new Town<String>("C"));
		towns.add(new Town<String>("D"));
		assertEquals(trainsRoutesDirectedGraph.getRouteDistance(towns), "22");
        
		logger.info("Test question 5, the distance of the route A-E-D");
        towns.clear();
        towns.add(new Town<String>("A"));
        towns.add(new Town<String>("E"));
        towns.add(new Town<String>("D"));        
        assertEquals(trainsRoutesDirectedGraph.getRouteDistance(towns), NO_SUCH_ROUTE);
	}
	
	/**
	 * This test method, can test question 6.
	 */
	@Test(priority = 3, description = "Test getting the number of trips with a given maximum number of stops", groups = {"shakeout", "regression", "MC"}, enabled = true)
	public void testGetNumberOfTripsWithMaxStops() {
		logger.info("Test question 6, the number of trips starting at C and ending at C with a maximum of 3 stops.");
		Town<String> startingTown = new Town<String>("C");
		Town<String> endingTown = new Town<String>("C");
		assertEquals(trainsRoutesDirectedGraph.getNumberOfTripsWithMaxStops(startingTown, endingTown, 3), "2");
	}
	
	/**
	 * This test method, can test question 7.
	 */
	@Test(priority = 4, description = "Test getting the number of trips with an exact number of stops", groups = {"shakeout", "regression", "MC"}, enabled = true)
	public void testGetNumberOfTripsWithExactStops() {
		logger.info("Test question 7, the number of trips starting at A and ending at C with exactly 4 stops.");
		Town<String> startingTown = new Town<String>("A");
		Town<String> endingTown = new Town<String>("C");
		assertEquals(trainsRoutesDirectedGraph.getNumberOfTripsWithExactStops(startingTown, endingTown, 4), "3");
	}
	
	/**
	 * This test method, can test question 6-7.
	 */
	@Test(priority = 5, description = "Test getting the number of trips with a given number of stops", groups = {"shakeout", "regression", "MC"}, enabled = true)
	public void testGetNumberOfTripsWithNumberOfStops() {
		logger.info("Test question 6, the number of trips starting at C and ending at C with a maximum of 3 stops.");
		Town<String> startingTown = new Town<String>("C");
		Town<String> endingTown = new Town<String>("C");
		assertEquals(trainsRoutesDirectedGraph.getNumberOfTripsWithNumberOfStops(startingTown, endingTown, 3, true), "2");
		
		logger.info("Test question 7, the number of trips starting at A and ending at C with exactly 4 stops.");
		startingTown.setTownName("A");
		assertEquals(trainsRoutesDirectedGraph.getNumberOfTripsWithNumberOfStops(startingTown, endingTown, 4, false), "3");
	}
	
	/**
	 * This test method, can test question 8-9.
	 */
	@Test(priority = 6, description = "Test getting the shortest route distance", groups = {"shakeout", "regression", "MC"}, enabled = true)
	public void testGetShortestRouteDistance() {
		logger.info("Test question 8, the length of the shortest route (in terms of distance to travel) from A to C.");
		Town<String> startingTown = new Town<String>("A");
		Town<String> endingTown = new Town<String>("C");
		assertEquals(trainsRoutesDirectedGraph.getShortestRouteDistance(startingTown, endingTown), "9");
		
		logger.info("Test question 9, the length of the shortest route (in terms of distance to travel) from B to B.");
		startingTown.setTownName("B");
		endingTown.setTownName("B");
		assertEquals(trainsRoutesDirectedGraph.getShortestRouteDistance(startingTown, endingTown), "9");
	}
	
	/**
	 * This test method, can test question 10.
	 */
	@Test(priority = 7, description = "Test getting number of different routes with a given maximum distance", groups = {"shakeout", "regression", "MC"}, enabled = true)
	public void testGetNumberOfDifferentRoutesWithMaxDistance() {
		logger.info("Test question 6, the number of different routes from C to C with a distance of less than 30.");
		Town<String> startingTown = new Town<String>("C");
		Town<String> endingTown = new Town<String>("C");
		assertEquals(trainsRoutesDirectedGraph.getNumberOfDifferentRoutesWithMaxDistance(startingTown, endingTown, 30), "7");
	}
}