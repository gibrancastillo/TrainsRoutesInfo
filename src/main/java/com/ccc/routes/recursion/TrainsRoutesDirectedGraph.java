package com.ccc.routes.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The trains routes directed graph class uses a Map with Towns as a key and a list of Route, those Route have 
 * target Town with distance weight. The main strategy to resolve each problem was using recursion algorithms.
 * 
 * @author gibrancastillo
 *
 */
public class TrainsRoutesDirectedGraph {
	private Map<Town<String>, List<Route>> trainsRoutesMap;
	private static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";
	private static final Logger logger = LogManager.getLogger(TrainsRoutesDirectedGraph.class);
	
	/**
	 * Constructs a TrainsRoutesDirectedGraph class
	 * 
	 * @param input_routes - AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
	 */
	public TrainsRoutesDirectedGraph(String input_routes) {
		//A map contains unique keys
		trainsRoutesMap = new HashMap<>();
		createTrainsRoutes(input_routes);
	}
	
	/**
	 * Create and build the directed graph
	 * @param input_routes - TrainsRoutesDirectedGraph
	 */
	public void createTrainsRoutes(String input_routes) {
		Route route = null;
		int edgeDistance = 0;
		Town<String> endingTown = null;
		Town<String> startingTown = null;
		/*
		 * Regular Expression [\\s]*,[\\s]*
		 * Split string into a substring when separated by whitespace follow by any number
		 * of characters then a comma follow by whitespace follow by any number of characters
		 * <> \\s splits the string based on whitespace 
		 * <> '*' wild card or any character
		 */
		List<String> routes = Arrays.asList(input_routes.split("[\\s]*,[\\s]*"));
		
		for(String str_route : routes) {
			startingTown = new Town<String>(str_route.substring(0, 1));
			endingTown = new Town<String>(str_route.substring(1, 2));
			edgeDistance = Integer.parseInt(str_route.substring(2));
			
			logger.debug("str_route: " + str_route);
			logger.debug("Starting Town: " + startingTown.getTownName());
			logger.debug("Ending Town: " + endingTown.getTownName());
			logger.debug("edgeDistance: " + edgeDistance + "\n");
			
			route = new Route(endingTown, edgeDistance);
			
			trainsRoutesMap.putIfAbsent(startingTown, new ArrayList<>());
			trainsRoutesMap.get(startingTown).add(route);
		}
		
		logger.debug("Trains Routes Directed Graph in a HashMap: \n" + trainsRoutesMap.toString() + "\n");
	}
	
	/**
	 * Handles and answer questions 1-5.
     * Calculate distance of the given route.
     * Assume list of towns has more than one element.
     * 
	 * @param towns
	 * @return The distance of the route
	 */
	public String getRouteDistance(List<Town<String>> towns) {
		int distance = 0;
		int townIndex = 0;
		Route route = null;
		List<Route> routes = null;
		
		logger.debug("+++++++++++++++++++++++++++++++++++");
		logger.debug("Towns in train route (display in traveling order): " + towns.toString());
		
		for(Town<String> town : towns) {
			townIndex = towns.indexOf(town);
			
			if(townIndex < towns.size() - 1) {
				final Town<String> nextTown = towns.get(townIndex + 1);
				routes = trainsRoutesMap.get(town);
				route = routes.stream()
						.filter(r -> r.getTown().equals(nextTown))
						.findFirst()
						.orElse(null);
				
				logger.debug("Available train routes from town '" + town.getTownName() + "': " + routes.toString());
				logger.debug("Is there a train route from town '" + town.getTownName() + "' to town '" + nextTown.getTownName() + "'?");
				
				if(route != null) {
					logger.debug("Yes, with a distance equal to " + route.getDistance());
					distance += route.getDistance();
				} else {
					logger.debug("No");
					logger.debug("+++++++++++++++++++++++++++++++++++\n");
					return NO_SUCH_ROUTE;
				}
				
				logger.debug("The train travel distance so far is " + distance);
				logger.debug("--- --- --- --- --- ---");
			}
		}
		
		logger.debug("The train's total travel distance from starting town '" + towns.get(0).getTownName() + "' to ending town '" + towns.get(towns.size() - 1).getTownName() + "' was " + distance);
		
		logger.debug("+++++++++++++++++++++++++++++++++++");
		
		return String.valueOf(distance);
	}
	
	/**
	 * Handles and answer question 6.
	 * 
	 * @param startingTown
	 * @param endingTown
	 * @param maxStops
	 * @return The number of trips from starting to ending town with a maximum number of stops.
	 */
	public String getNumberOfTripsWithMaxStops(Town<String> startingTown, Town<String> endingTown, int maxStops) {
		boolean isCalculatingMaxStops = true;
		Deque<Route> queue = new LinkedList<>();
		AtomicInteger tripsCounter = new AtomicInteger();
		AtomicInteger numberOfRecursions = new AtomicInteger();
		
		countTripsMaxStops(startingTown, endingTown, maxStops, queue, tripsCounter, isCalculatingMaxStops, numberOfRecursions);
		
		return tripsCounter.toString();
	}
	
	/**
	 * Handles and answer question 7.
	 * 
	 * @param startingTown
	 * @param endingTown
	 * @param exactStops
	 * @return The number of trips from starting to ending town with an exactly number of stops.
	 */
	public String getNumberOfTripsWithExactStops(Town<String> startingTown, Town<String> endingTown, int exactStops) {
		boolean isCalculatingMaxStops = false;
		Deque<Route> queue = new LinkedList<>();
		AtomicInteger tripsCounter = new AtomicInteger();
		AtomicInteger numberOfRecursions = new AtomicInteger();
		
		countTripsMaxStops(startingTown, endingTown, exactStops, queue, tripsCounter, isCalculatingMaxStops, numberOfRecursions);
		
		return tripsCounter.toString();
	}
	
	/**
	 * Handles and answer questions 6-7.
	 * 
	 * @param startingTown
	 * @param endingTown
	 * @param numberOfStops
	 * @param isCalculatingMaxStops
	 * @return The number of trips from starting to ending town with maximum or an exactly number of stops.
	 */
	public String getNumberOfTripsWithNumberOfStops(Town<String> startingTown, Town<String> endingTown, int numberOfStops, boolean isCalculatingMaxStops) {
		Deque<Route> queue = new LinkedList<>();
		AtomicInteger tripsCounter = new AtomicInteger();
		AtomicInteger numberOfRecursions = new AtomicInteger();
		
		countTripsMaxStops(startingTown, endingTown, numberOfStops, queue, tripsCounter, isCalculatingMaxStops, numberOfRecursions);
		
		return tripsCounter.toString();
	}
	
	/**
	 * Calculates the number of trips from starting to ending town with the provided number of stops (maximum or exact).
	 * 
	 * @param startingTown
	 * @param endingTown
	 * @param numberOfStops
	 * @param queue
	 * @param tripsCounter
	 * @param isCalculatingMaxStops
	 * @param numberOfRecursions
	 */
	private void countTripsMaxStops(Town<String> startingTown, Town<String> endingTown, int numberOfStops, 
			Deque<Route> queue, AtomicInteger tripsCounter, boolean isCalculatingMaxStops, AtomicInteger numberOfRecursions) { 
		logger.debug("+++++++++++++++++++++++++++++++++++");
		logger.debug("Starting or current town " + startingTown.getTownName());
		logger.debug("Ending town " + endingTown.getTownName());
		logger.debug("Queue with routes size " + queue.size() + " with " + numberOfStops + " number of stops");
		
		if(queue.size() < numberOfStops) {
			Town<String> nextTown = null;
			List<Route> routes = trainsRoutesMap.get(startingTown);
			
			logger.debug("Available train routes from starting or current town '" + startingTown.getTownName() + "': " + routes.toString());
			
			for(Route route : routes) {
				nextTown = route.getTown();
				
				logger.debug("Is the next town '" + nextTown.getTownName() + "' equal to ending town '" + endingTown.getTownName() + "'?");
				
				if(nextTown.equals(endingTown)) {
					if(isCalculatingMaxStops) {
						tripsCounter.incrementAndGet();
						logger.debug("Yes, increment the trips counter to '" + tripsCounter.toString() + "' ^^^ for maximum stops ^^^");
					} else if(queue.size() == numberOfStops - 1) {
						tripsCounter.incrementAndGet();
						logger.debug("Yes, increment the trips counter to '" + tripsCounter.toString() + "' ^^^ for exact stops ^^^");
					} else {
						logger.debug("No, therefore, do not increment the trips counter");
					}
				} else {
					logger.debug("No, therefore, do not increment counter");
				}
				
				queue.addLast(route);
				numberOfRecursions.incrementAndGet();
				logger.debug("######## Start recursion " + numberOfRecursions + " ########");
				countTripsMaxStops(nextTown, endingTown, numberOfStops, queue, tripsCounter, isCalculatingMaxStops, numberOfRecursions);
				logger.debug("######### End recursion " + numberOfRecursions + " #########");
				numberOfRecursions.decrementAndGet();
				queue.removeLast();
			}
		}
	}
	
	/**
	 * Handles and answer questions 8-9.
	 * 
	 * @param startingTown
	 * @param endingTown
	 * @return The distance of the shortest route (in terms of distance to travel) from start to end.
	 */
	public String getShortestRouteDistance(Town<String> startingTown, Town<String> endingTown) {
		AtomicInteger minDistance = new AtomicInteger(999999);
        //ThreadLocal<Integer> minDistance = new ThreadLocal<>();
        //minDistance.set(999999);
        int sumDistance = 0;
        Deque<Town<String>> queue = new LinkedList<>();
        AtomicInteger numberOfRecursions = new AtomicInteger();
        
        calculateShortestRouteDistance(startingTown, endingTown, queue, minDistance, sumDistance, numberOfRecursions);
        
        return minDistance.toString();
	}
	
	/**
	 * Calculates the length of the shortest route (in terms of distance 
	 * to travel) from the starting town to the ending town.
	 * 
	 * @param startingTown
	 * @param endingTown
	 * @param queue
	 * @param minDistance
	 * @param sumDistance
	 * @param numberOfRecursions
	 */
	private void calculateShortestRouteDistance(Town<String> startingTown, Town<String> endingTown, Deque<Town<String>> queue, 
			AtomicInteger minDistance, int sumDistance, AtomicInteger numberOfRecursions) {
		logger.debug("+++++++++++++++++++++++++++++++++++");
		logger.debug("Starting or current town " + startingTown.getTownName());
		logger.debug("Ending town " + endingTown.getTownName());
		logger.debug("Queue with routes size " + queue.size());
		logger.debug("*** Minimum distance " + minDistance.toString() + " ***");
		logger.debug("Sum distance " + sumDistance);
		
        Town<String> nextTown = null;
        List<Route> routes = trainsRoutesMap.get(startingTown);
        
        logger.debug("Available train routes from starting or current town '" + startingTown.getTownName() + "': " + routes.toString());
        
        for(Route route : routes) {
            nextTown = route.getTown();
            
            logger.debug("Is the next town '" + nextTown.getTownName() + "' equal to ending town '" + endingTown.getTownName() + "'?");
            
            if(nextTown.equals(endingTown)) {
            	logger.debug("Yes, is sumDistance + route.getDistance() '" + (sumDistance + route.getDistance()) + "' < minDistance.get() '" + minDistance.get() + "'?");
            	
                if(sumDistance + route.getDistance() < minDistance.get()) {
                    minDistance.set(sumDistance + route.getDistance());
                    logger.debug("Yes, new the shortest route distance is " + minDistance.toString());
                    continue;
                } else {
    				logger.debug("No, therefore, keep current shortest route distance");
    			}
            } else {
				logger.debug("No, therefore, no need to attempt to calculate the shortest route distance");
			}
            
            if(!queue.contains(nextTown)) {
            	logger.debug("Queue does not contain the next town " + nextTown.getTownName());
                queue.addLast(nextTown);
                sumDistance += route.getDistance();
                numberOfRecursions.incrementAndGet();
				logger.debug("######## Start recursion " + numberOfRecursions + " ########");
                calculateShortestRouteDistance(nextTown, endingTown, queue, minDistance, sumDistance, numberOfRecursions);
                logger.debug("######### End recursion " + numberOfRecursions + " #########");
				numberOfRecursions.decrementAndGet();
                sumDistance -= route.getDistance();
                queue.removeLast();
            }
        }
    }
	
	/**
	 * Handles and answer question 10.
	 * 
	 * @param startingTown
	 * @param endingTown
	 * @param maxDistance
	 * @return The number of different routes from starting to ending town with a distance of less than or equal to a given number
	 */
	public String getNumberOfDifferentRoutesWithMaxDistance(Town<String> startingTown, Town<String> endingTown, int maxDistance) {
		int sumDistance = 0;
		AtomicInteger routesCounter = new AtomicInteger();
		AtomicInteger numberOfRecursions = new AtomicInteger();
		
		calculateNumberOfDifferentRoutesWithMaxDistance(startingTown, endingTown, maxDistance, sumDistance, routesCounter, numberOfRecursions);
		
		return routesCounter.toString();
	}
	
	/**
	 * Calculates the number of different routes from starting to ending
	 * town with a distance of less than or equal to a given number
	 * 
	 * @param startingTown
	 * @param endingTown
	 * @param maxDistance
	 * @param sumDistance
	 * @param routesCounter
	 * @param numberOfRecursions
	 */
	private void calculateNumberOfDifferentRoutesWithMaxDistance(Town<String> startingTown, Town<String> endingTown, int maxDistance,
			int sumDistance, AtomicInteger routesCounter, AtomicInteger numberOfRecursions) {
		logger.debug("+++++++++++++++++++++++++++++++++++");
		logger.debug("Starting or current town " + startingTown.getTownName());
		logger.debug("Ending town " + endingTown.getTownName());
		logger.debug("Maximum route distance " + maxDistance);
		logger.debug("*** Different Routes counter " + routesCounter.toString() + " ***");
		logger.debug("Sum distance " + sumDistance);
		
        Town<String> nextTown = null;
        List<Route> routes = trainsRoutesMap.get(startingTown);
        
        logger.debug("Available train routes from starting or current town '" + startingTown.getTownName() + "': " + routes.toString());
        
        for(Route route : routes) {
            nextTown = route.getTown();
            
            logger.debug("Yes, is sumDistance + route.getDistance() '" + (sumDistance + route.getDistance()) + "' >= maxDistance '" + maxDistance + "'?");
        	
            if(sumDistance + route.getDistance() >= maxDistance) {
                logger.debug("Yes, continue (go back to beginning of for loop) with maxDistance equal to " + maxDistance);
                continue;
            } else {
				logger.debug("No, therefore, execute below lines of code within the for loop");
			}
            
            logger.debug("Is the next town '" + nextTown.getTownName() + "' equal to ending town '" + endingTown.getTownName() + "'?");
            
            if(nextTown.equals(endingTown)) {
            	routesCounter.getAndIncrement();
            	logger.debug("Yes, increment the routes counter to '" + routesCounter.toString() + "' ^^^ for maximum distance ^^^");
            } else {
				logger.debug("No, therefore, no need to attempt to calculate the shortest route distance");
			}
            
            sumDistance += route.getDistance();
            numberOfRecursions.incrementAndGet();
			logger.debug("######## Start recursion " + numberOfRecursions + " ########");
			calculateNumberOfDifferentRoutesWithMaxDistance(nextTown, endingTown, maxDistance, sumDistance, routesCounter, numberOfRecursions);
            logger.debug("######### End recursion " + numberOfRecursions + " #########");
			numberOfRecursions.decrementAndGet();
            sumDistance -= route.getDistance();
        }
	}
	
	public Map<Town<String>, List<Route>> getTrainsMap() {
		return trainsRoutesMap;
	}
	
	public void setTrainsMap(Map<Town<String>, List<Route>> trainsMap) {
		this.trainsRoutesMap = trainsMap;
	}
}