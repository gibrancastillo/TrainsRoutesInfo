package com.ccc.routes.recursion;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The driver class to execute the train routes directed graph application.
 * 
 * @author gibrancastillo
 *
 */
public class TrainsRoutesDriver {
	private static final String VALID_TRAINS_ROUTES = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
	private static final Logger logger = LogManager.getLogger(TrainsRoutesDriver.class);

	public static void main(String[] args) {
		logger.debug("------ Help the railroad company provide its customers with debugrmation about the train routes");
		logger.debug("------ Start Loading Valid Trains Routes ");
		
		TrainsRoutesDirectedGraph trainsRoutes = new TrainsRoutesDirectedGraph(VALID_TRAINS_ROUTES);
		
		logger.debug("------ Done Loading Valid Trains Routes ");
		
		List<Town<String>> towns = new ArrayList<>();
		towns.add(new Town<String>("A"));
		towns.add(new Town<String>("B"));
		towns.add(new Town<String>("C"));
		logger.debug("------ The distance of the route A-B-C");
		logger.info("Output #1: " + trainsRoutes.getRouteDistance(towns));
		//logger.debug("------ Output #1: " + trainsRoutes.getRouteDistance(towns) + "\n");
		
		towns.clear();
		towns.add(new Town<String>("A"));
		towns.add(new Town<String>("D"));
		logger.debug("------ The distance of the route A-D");
		logger.info("Output #2: " + trainsRoutes.getRouteDistance(towns));
		//logger.debug("------ Output #2: " + trainsRoutes.getRouteDistance(towns) + "\n");
		
		towns.add(new Town<String>("C"));
		logger.debug("------ The distance of the route A-D-C");
		logger.info("Output #3: " + trainsRoutes.getRouteDistance(towns));
		//logger.debug("------ Output #3: " + trainsRoutes.getRouteDistance(towns) + "\n");
		
		towns.clear();
		towns.add(new Town<String>("A"));
		towns.add(new Town<String>("E"));
		towns.add(new Town<String>("B"));
		towns.add(new Town<String>("C"));
		towns.add(new Town<String>("D"));
		logger.debug("------ The distance of the route A-E-B-C-D");
		logger.info("Output #4: " + trainsRoutes.getRouteDistance(towns));
		//logger.debug("------ Output #4: " + trainsRoutes.getRouteDistance(towns) + "\n");
		
		towns.clear();
		towns.add(new Town<String>("A"));
		towns.add(new Town<String>("E"));
		towns.add(new Town<String>("D"));
		logger.debug("------ The distance of the route A-E-D");
		logger.info("Output #5: " + trainsRoutes.getRouteDistance(towns));
		//logger.debug("------ Output #5: " + trainsRoutes.getRouteDistance(towns) + "\n");
		
		logger.debug("The number of trips starting at C and ending at C with a maximum of 3 stops.");
		logger.debug("With the given data, there are two such trips: C-D-C (2 stops) and C-E-B-C (3 stops).");
		logger.info("Output #6: " + trainsRoutes.getNumberOfTripsWithMaxStops(new Town<String>("C"), new Town<String>("C"), 3));
		//logger.debug("Output #6: " + trainsRoutes.getNumberOfTripsWithMaxStops(new Town<String>("C"), new Town<String>("C"), 3) + "\n");
		logger.debug("Output #6: " + trainsRoutes.getNumberOfTripsWithNumberOfStops(new Town<String>("C"), new Town<String>("C"), 3, true) + "\n");
		
		logger.debug("The number of trips starting at A and ending at C with exactly 4 stops.");
		logger.debug("With the data, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).");
		logger.info("Output #7: " + trainsRoutes.getNumberOfTripsWithExactStops(new Town<String>("A"), new Town<String>("C"), 4));
		//logger.debug("Output #7: " + trainsRoutes.getNumberOfTripsWithExactStops(new Town<String>("A"), new Town<String>("C"), 4) + "\n");
		//logger.debug("Output #7: " + trainsRoutes.getNumberOfTripsWithNumberOfStops(new Town<String>("A"), new Town<String>("C"), 4, false) + "\n");
		
		logger.debug("The length of the shortest route (in terms of distance to travel) from A to C.");
		logger.info("Output #8: " + trainsRoutes.getShortestRouteDistance(new Town<String>("A"), new Town<String>("C")));
		//logger.debug("Output #8: " + trainsRoutes.getShortestRouteDistance(new Town<String>("A"), new Town<String>("C")) + "\n");
		
		logger.debug("The length of the shortest route (in terms of distance to travel) from B to B.");
		logger.info("Output #9: " + trainsRoutes.getShortestRouteDistance(new Town<String>("B"), new Town<String>("B")));
		//logger.debug("Output #9: " + trainsRoutes.getShortestRouteDistance(new Town<String>("B"), new Town<String>("B")) + "\n");
		
		logger.debug("The number of different routes from C to C with a distance of less than 30.");
		logger.debug("With the given data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC");
		logger.info("Output #10: " + trainsRoutes.getNumberOfDifferentRoutesWithMaxDistance(new Town<String>("C"), new Town<String>("C"), 30));
		//logger.debug("Output #10: " + trainsRoutes.getNumberOfDifferentRoutesWithMaxDistance(new Town<String>("C"), new Town<String>("C"), 30) + "\n");
	}
}