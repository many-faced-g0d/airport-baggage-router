package com.jaqen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to encapsulate collection of all domain objects pertaining to the
 * AirPortBaggage router system. Contains collections of conveyor systems,
 * departures, and bags. Also contains a helper method used to compute travel
 * time across a conveyor system route.
 * 
 * @author Kent Fletcher
 * @date 7/12/2018
 *
 */
public class AirPortBaggageRouter
{

	/**
	 * list of conveyor systems
	 */
	private List<ConveyorSystem> conveyorSystems;

	/**
	 * list of departures
	 */
	private List<Departure> departures;

	/**
	 * list of bags
	 */
	private List<Bag> bags;

	/**
	 * public constructor
	 */
	public AirPortBaggageRouter()
	{
		this.conveyorSystems = new ArrayList<ConveyorSystem>();
		this.departures = new ArrayList<Departure>();
		this.bags = new ArrayList<Bag>();
	}

	/**
	 * method to add conveyor system stored in internal collection ->
	 * conveyorSystems
	 * 
	 * @param cs
	 */
	public void addConveyorSystem(ConveyorSystem cs)
	{
		this.conveyorSystems.add(cs);
	}

	/**
	 * Get conveyor system list
	 * 
	 * @return
	 */
	public List<ConveyorSystem> getConveyorSystems()
	{
		return this.conveyorSystems;
	}

	/**
	 * Get list of departures
	 * 
	 * @return
	 */
	public List<Departure> getDepartures()
	{
		return departures;
	}

	/**
	 * For a given bag, get the departure object
	 * 
	 * @param bag Bag
	 * @return Departure
	 */
	public Departure getDeparture(Bag bag)
	{
		Departure rtnDeparture = null;

		for (Departure d : this.getDepartures())
		{
			if (bag.getFlightId().equalsIgnoreCase(d.getFlightId()))
			{
				rtnDeparture = d;
				break;
			}
		}
		return rtnDeparture;
	}

	/**
	 * Set list of departures
	 * 
	 * @param departure
	 */
	public void setDepartures(List<Departure> departure)
	{
		this.departures = departure;
	}

	/**
	 * Add a departure to internal collection object -> departures
	 * 
	 * @param departure
	 */
	public void addDeparture(Departure departure)
	{
		this.departures.add(departure);
	}

	/**
	 * Get list of bags
	 * 
	 * @return List<Bag>
	 */
	public List<Bag> getBags()
	{
		return bags;
	}

	/**
	 * Set list of bags
	 * 
	 * @param bags List<Bag>
	 */
	public void setBags(List<Bag> bags)
	{
		this.bags = bags;
	}

	/**
	 * Add a bag to intaernal collection object -> bags
	 * 
	 * @param bag Bag
	 */
	public void addBag(Bag bag)
	{
		this.bags.add(bag);
	}

	/**
	 * Helper method to compute the travel time for the given route passed as the
	 * argument to the method
	 * 
	 * @return
	 */

	public Long computeTravelTime(List<Integer> route)
	{
		Long tt = 0L;

		Iterator<Integer> itr = route.iterator();
		int ctr = 0;
		int previous = 0;
		while (itr.hasNext())
		{
			int start = 0;
			int end = 0;

			if (ctr == 0)
			{
				start = itr.next();
				end = itr.next();
			} else
			{
				start = previous;
				end = itr.next();
			}

			Node startNode = Node.getNodeById(start);
			Node endNode = Node.getNodeById(end);
			for (ConveyorSystem cs : this.getConveyorSystems())
			{
				// System.out.println(cs+" "+n1+" "+n2);
				if (cs.getStartNode().getName().equals(startNode.getName())
						&& cs.getEndNode().getName().equals(endNode.getName()))
				{
					tt += cs.getTravelTime();
					break;
				}

			}

			previous = end;

			ctr++;
		}

		return tt;
	}

	/**
	 * Get the optimum route based on minimal travel time
	 * 
	 * @return List<Integer>
	 */

	public List<Integer> getOptimalRoute(List<List<Integer>> possibleRoutes)
	{
		long min = 999;
		int ctr = 0;
		int index = 0;
		for (List<Integer> l : possibleRoutes)
		{

			long tt = computeTravelTime(l);
			if (min > tt)
			{
				min = tt;
				index = ctr;
			}
			ctr++;
		}
		return possibleRoutes.get(index);
	}

}
