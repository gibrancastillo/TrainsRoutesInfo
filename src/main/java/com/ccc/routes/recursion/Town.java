package com.ccc.routes.recursion;

/**
 * A class that represents a town (node).
 * 
 * @author gibrancastillo
 *
 * @param <E>
 */
public class Town<E> {
	private String townName;
	
	public Town(String town) {
		super();
		this.townName = town;
	}
	
	public String getTownName() {
		return townName;
	}
	
	public void setTownName(String town) {
		this.townName = town;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getTownName().equals(((Town<?>)obj).getTownName());
	}
	
	@Override
	public int hashCode() {
		return this.getTownName().hashCode();
	}
	
	@Override
	public String toString() {
		return this.getTownName();
	}
}