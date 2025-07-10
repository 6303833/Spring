package com.example.DTOs;

public class WeeklyTrendDTO { 
	    private int week;
	    private double totalAmount;
		public WeeklyTrendDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		public WeeklyTrendDTO(int week, double totalAmount) {
			super();
			this.week = week;
			this.totalAmount = totalAmount;
		}
		public int getWeek() {
			return week;
		}
		public void setWeek(int week) {
			this.week = week;
		}
		public double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}
	    
	 
}
