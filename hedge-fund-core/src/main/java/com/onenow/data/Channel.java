package com.onenow.data;

import com.ib.client.Types.SecType;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;
import com.onenow.util.Stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class Channel {

	private Investment investment;
	private Contract contract;
	private ContractFactory contractFactory;
	
	private HashMap resistanceDayMap = new HashMap();
	private HashMap supportDayMap = new HashMap();
	private HashMap recentDayMap = new HashMap();
	// open, close...
	
	private List<String> resistanceDayList = new ArrayList<String>();
	private List<String> supportDayList = new ArrayList<String>();
	private List<String> recentDayList = new ArrayList<String>();
	
	TimeParser parser = new TimeParser();
//	Stats stats = new Stats();
	
	List<Double> rangeToResistance = new ArrayList<Double>();
	List<Double> rangeToSupport = new ArrayList<Double>();
	List<Double> halfCycleToResistance = new ArrayList<Double>();		
	List<Double> halfCycleToSupport = new ArrayList<Double>();		

	Stats statsHalfCycleToSupport = new Stats(halfCycleToSupport);
	Stats statsHalfCycleToResistance = new Stats(halfCycleToResistance);
	Stats statsRangeToSupport = new Stats(rangeToSupport);
	Stats statsRangeToResistance = new Stats(rangeToResistance);
		
	public Channel() {
		
	}
	
	public Channel(Investment inv) {
		setInvestment(inv);
		setContractFactory(new ContractFactory());
		setContract(getContractFactory().getContract(inv));
	}
	
//	public Channel(Contract contract) {
//		setContract(contract);
//		
//	}

	public void addResistance(String date) {
		if(!getResistanceDayMap().containsKey(date)) {
			getResistanceDayList().add(date);
		}
		getResistanceDayMap().put(date, -999999.0);
	}

	public void addResistance(String date, Double price) {
		addResistance(date);
		getResistanceDayMap().put(date, price);
	}

	public void addSupport(String date) {
		if(!getSupportDayMap().containsKey(date)) {
			getSupportDayList().add(date);
		}
		getSupportDayMap().put(date, 999999.0);
	}

	public void addSupport(String date, Double price) {
		addSupport(date);
		getSupportDayMap().put(date, price);		
	}

	public void addRecent(String date) {
		if(!getRecentDayMap().containsKey(date)) {
//			System.out.println("ADDED RECENT " + date);
			getRecentDayList().add(date);
			getRecentDayMap().put(date, 999999.0);
		}
	}
	
	public void addRecent(String date, Double price) {
		addRecent(date);
		getRecentDayMap().put(date, price);
//		System.out.println("ADDED RECENT " + date + " " + price);

	}
	
	public String getForecastSupportDate() { // based on opposite
		String date="";


		Integer halfSupportCycle = (int) Math.round(statsHalfCycleToSupport.getMean());
		Integer halfResistanceCycle = (int) Math.round(statsHalfCycleToResistance.getMean());

		String lastResistanceDate = getLastResistanceDate();
		String lastSupportDate = getLastSupportDate();
		if(parser.isLaterDate(lastSupportDate, lastResistanceDate)) {
			date = parser.getDatePlusDashed(lastResistanceDate, halfSupportCycle); 
		} else {
			date = parser.getDatePlusDashed(lastSupportDate, halfSupportCycle+halfResistanceCycle);
		}

//		System.out.println("f- " + lastDate + " " + halfCycle + " " + date);
		return date;
	}

	public String getForecastResistanceDate() { // based on opposite
		String date="";

		Integer halfSupportCycle = (int) Math.round(statsHalfCycleToSupport.getMean());
		Integer halfResistanceCycle = (int) Math.round(statsHalfCycleToResistance.getMean());

		String lastResistanceDate = getLastResistanceDate();
		String lastSupportDate = getLastSupportDate();
		if(parser.isLaterDate(lastSupportDate, lastResistanceDate)) {
			date = parser.getDatePlusDashed(lastResistanceDate, halfSupportCycle+halfResistanceCycle); 
		} else {
			date = parser.getDatePlusDashed(lastSupportDate, halfResistanceCycle);
		}

		return date;		
	}

	public Double getForecastResistancePrice(String newDay) { 
		Double newPrice=0.0;
		
//		Collections.sort(getResistanceDayList());
//		String oldDay = getResistanceDayList().get(0);
//		Double oldPrice = (Double) getResistanceDayMap().get(oldDay);
//		
//		newPrice = oldPrice + getResistanceSlope()*parser.getElapsedDays(oldDay, newDay);  
				
		return newPrice;
	}
	
	public Double getForecastSupportPrice(String newDay) { 
		Double p=0.0;
//		String oldDay = getSupportDayList().get(0);
//		Double oldPrice = (Double) getSupportDayMap().get(oldDay);
//		newPrice = oldPrice + getResistanceSlope()*parser.getElapsedDays(oldDay, newDay);  FUNDAMENTAL
		// +getSupportSlope()
		return p;
	}	

	public Double getForecastResistancePrice() { 
		Double newPrice=0.0;
		
		Collections.sort(getSupportDayList());
		Collections.sort(getResistanceDayList());

		if(parser.isLaterDate(getLastSupportDate(), getLastResistanceDate())) {
			newPrice = getLastResitancePrice() - statsRangeToSupport.getMean() + statsRangeToResistance.getMean();
		} else {
			newPrice = getLastSupportPrice() + statsRangeToResistance.getMean();			
		}

		return newPrice;
	}

	
	public Double getForecastSupportPrice() { 
		Double newPrice=0.0;
		
		Collections.sort(getSupportDayList());
		Collections.sort(getResistanceDayList());

		if(parser.isLaterDate(getLastSupportDate(), getLastResistanceDate())) {
			newPrice = getLastResitancePrice() - statsRangeToSupport.getMean();
		} else {
			newPrice = getLastSupportPrice() + statsRangeToResistance.getMean() - statsRangeToSupport.getMean();			
		}

		return newPrice;
	}

	private Double getLastResitancePrice() {
		Double oldResistancePrice = (Double) getResistanceDayMap().get(getLastResistanceDate());
		return oldResistancePrice;
	}

	private Double getLastSupportPrice() {
		Double oldSupportPrice = (Double) getSupportDayMap().get(getLastSupportDate());
		return oldSupportPrice;
	}

	private String getLastDate() {
		String lastDate="";
		if(parser.isLaterDate(getLastSupportDate(), getLastResistanceDate())) {
			lastDate = getLastResistanceDate(); 
		} else {
			lastDate = getLastSupportDate();
		}
		return lastDate;
	}
	private String getLastResistanceDate() {
		String lastResistanceDate = getResistanceDayList().get(getResistanceDayList().size()-1);
		return lastResistanceDate;
	}

	private String getLastSupportDate() {
		String lastSupportDate = getSupportDayList().get(getSupportDayList().size()-1);
		return lastSupportDate;
	}
	
	
	private Double getResistanceSlope() {
		Collections.sort(getResistanceDayList());
		int max = getResistanceDayList().size()-1;
		String newDate = getResistanceDayList().get(max);
		Double newPrice = (Double) getResistanceDayMap().get(newDate);
		String oldDate = getResistanceDayList().get(0);
		Double oldPrice = (Double) getResistanceDayMap().get(oldDate);
		
		Double range = parser.getElapsedDaysDashed(oldDate, newDate)*1.0;	
		Double slope = (newPrice-oldPrice)/range; 		
//		System.out.println("RES SLOPE " + newPrice + " " + oldPrice + " " + range + " " + slope);

		return slope;

	}

	private Double getSupportSlope() {
		Collections.sort(getSupportDayList());
		int max = getSupportDayList().size()-1;
		String newDate = getSupportDayList().get(max);
		Double newPrice = (Double) getSupportDayMap().get(newDate);
		String oldDate = getSupportDayList().get(0);
		Double oldPrice = (Double) getSupportDayMap().get(oldDate);
		
		Double range = parser.getElapsedDaysDashed(oldDate, newDate)*1.0;	
		Double slope = (newPrice-oldPrice)/range;	
//		System.out.println("SUP SLOPE " + newPrice + " " + oldPrice + " " + range + " " + slope);
		
		return slope;
	}
	
	public void addChannel(List<Channel> channels, Contract contract) {
//
//		String today = new ParseDate().getToday();
//
//		if(contract.symbol().equals("SPX")) { 	// for SecType.IND and SecType.OPT
//			
//			Channel spx = new Channel(contract);
//			channels.add(spx);
//			
//			
//			// LOOKING FOR OVER-REACTION of 30% of range within 3 days
//			// AFTER HI/LO that subsides next day, with lower closing		
//			// SPX
//
//			// RECENT
//			spx.addRecent(today);  
//			spx.addRecent("2015-02-12");  
//			
//			// SLOW CHANNEL
//			spx.addResistance("2015-02-06"); 
//			spx.addSupport("2015-02-02");
//			spx.addResistance("2015-01-22");
//			spx.addSupport("2015-01-15");
//			spx.addResistance("2015-01-08");
//			spx.addSupport("2015-01-06");
//			// *** 30-day trend change
//			spx.addResistance("2014-12-29");  
//			spx.addSupport("2014-12-16"); // fundamentals t2 low 
//			spx.addResistance("2014-12-05"); 
//			// November: mild market 
//			spx.addSupport("2014-10-15"); // CRASH
//			spx.addResistance("2014-09-18"); 
//			spx.addSupport("2014-08-07"); // fundamentals t1 low
//			spx.addResistance("2014-07-24");				
//		}
//		
//		if(contract.symbol().equals("RUT")) { 	// for SecType.IND and SecType.OPT
//			
//			Channel rut = new Channel(contract);
//			channels.add(rut);
//
//			// RECENT
//			rut.addRecent(today);  
//			rut.addRecent("2015-02-12");  
//
//			// MIDDLEPATH CHANNEL
//			// repeated confrontation resistance/support over 3 months
//			rut.addSupport("2015-02-09"); 				// determines channel 
//			rut.addResistance("2015-02-06"); 			// determines channel  			
//			rut.addSupport("2015-02-02"); 				// determines channel 
//			rut.addResistance("2015-01-28"); 			
//			rut.addSupport("2015-01-15"); 			
//			rut.addSupport("2015-01-06"); 
//			rut.addResistance("2014-12-31"); 			
//			rut.addSupport("2014-12-15"); 			
//			rut.addResistance("2014-11-28"); 			// determines channel 
//			rut.addResistance("2014-09-02"); 			// determines channel 
//
//		}
//		
	}
	
	// PRINT
	public String toString() {
		String s="\n";
		s = s + getContract().toString() + "\n";
		s = s + "DATE" + "\t\t" + "SPIKE" + "\t\t" + "RANGE" + "\t\t" + "DAYS" + "\n";
		s = outlineChannel(s); 
		
		if(contract.secType().equals(SecType.IND)) {
			s = getKPI(s);	
			s = getForecast(s);	
			s = s + "\n\n";		
			s = getAlert(s);				
		}
		return s;
	}

	private String getAlert(String s) {
		s = s + "alert=> " + "\t*" + Math.round(getForecastSupportPrice()+10) + "*\t*" + 
			     				    Math.round(getForecastResistancePrice()-10) + "*\t" +
			     				    "\n";
		return s;
	}

	private String getForecast(String s) {
		s = s + getForecastSupportDate() + " f-" + "\t" + Math.round(getForecastSupportPrice()) + "\n";				
		s = s + getForecastResistanceDate() + " f+" + "\t    " + Math.round(getForecastResistancePrice()) + "\n";
		return s;
	}

	private String getKPI(String s) {
		s = s + "kpi " + "\t\t  " + Math.round(getSupportSlope()*30) + "/" + Math.round(getResistanceSlope()*30) + "\t\t" + 
					Math.round(statsRangeToSupport.getMean()) + "/" + Math.round(statsRangeToResistance.getMean()) + "\t\t" +
					Math.round(statsHalfCycleToSupport.getMean()) + "/" + Math.round(statsHalfCycleToResistance.getMean()) + "\n";
		return s;
	}

	
	private String outlineChannel(String s)  { 
		
		List<String> allDates = getDayList();
						
		int size = allDates.size()-1;
		for(int i=size; i>=0; i--) {
			String date=allDates.get(i);
			s = s + date;
			try { // SUPPORT
				Double supPrice = (Double) getSupportDayMap().get(date);
				s = s + " -" + "\t" + 	Math.round(supPrice) + "\t\t";
				String prevDate = allDates.get(i-1);
				s = getSupportString(	date, supPrice, prevDate,
										rangeToSupport, halfCycleToSupport, s);
			} catch (Exception e) {  } // it's normal b/c adAll
			
			try { // RESISTANCE
				Double resPrice = (Double) getResistanceDayMap().get(date);
				s = s + " +" + "\t    " + 	Math.round(resPrice) + "\t   ";
				String prevDate = allDates.get(i-1);
				s = getResistanceString(	date, resPrice, prevDate,
											rangeToResistance, halfCycleToResistance, s);
			} catch (Exception e) {  } //	it's normal b/c adAll
			
			try { // RECENT
				Double recentPrice = (Double) getRecentDayMap().get(date);
//				String prevDate = allDates.get(i-1);
				String prevDate = date;		// TODO
				s = getRecentString(	date, recentPrice, prevDate,
										rangeToResistance, halfCycleToResistance, s);
			} catch (Exception e) {  } //	it's normal b/c adAll
			s = s + "\n";
			
//			System.out.println(s);
		}
		return s;
	}

	private List<String> getDayList() {
		List<String> allDates = new ArrayList<String>();
		allDates.addAll(getResistanceDayList());
		allDates.addAll(getSupportDayList());
		allDates.addAll(getRecentDayList());
		Collections.sort(allDates);
		List<String> uniqueDates = new ArrayList<String>();		
		for (String date:allDates) {
			  if (!uniqueDates.contains(date)) {
				  uniqueDates.add(date);
			  }
		}
		return uniqueDates;
	}

	private String getRecentString(	String date, Double recentPrice, 
									String prevDate, List<Double> widthToRecent,
									List<Double> halfCycleToRecent, String s) {
		
		if(recentPrice>0 && recentPrice<9999) {
			s = s + " ?" + "\t  " + Math.round(recentPrice);
		}
		
		return s;
	}
	
	private String getResistanceString(	String date, Double resPrice,
										String prevDate, List<Double> widthToResistance,
										List<Double> halfCycleToResistance, String s) {
		
		Double prevSubprice = (Double) getSupportDayMap().get(prevDate);
		Double range = resPrice-prevSubprice;
		widthToResistance.add(range);
		Integer elapsed = parser.getElapsedDaysDashed(prevDate, date);
		halfCycleToResistance.add(elapsed*1.0);
		
		if(resPrice>0 && resPrice<9999) {
//			s = s + " +" + "\t    " + 	Math.round(resPrice) + "\t   ";
			
			if(contract.secType().equals(SecType.IND)) {
				s = s + 			Math.round(range) + "\t\t  " + 
									elapsed;
			}
		}
		
		return s;
	}

	private String getSupportString(String date, Double supPrice,
									String prevDate, List<Double> widthToSupport,
									List<Double> halfCycleToSupport, String s) {
		
		Double prevResprice = (Double) getResistanceDayMap().get(prevDate);
		Double range = prevResprice-supPrice;
		widthToSupport.add(range);
		Integer elapsed = parser.getElapsedDaysDashed(prevDate, date);
		halfCycleToSupport.add(elapsed*1.0);

		if(supPrice>0 && supPrice<9999) {
//			s = s + " -" + "\t" + 	Math.round(supPrice) + "\t\t";
			
			if(contract.secType().equals(SecType.IND)) {
				s = s +			Math.round(range) + "\t\t" + 
								elapsed;
			}
		}

		return s;
	}
	
	
	// CHANNEL
	public void setChannelPrices(String day, Double highPrice, Double lowPrice, Double recentPrice) {
		if(getResistanceDayMap().containsKey(day)) { // day resistance
			if( highPrice > (Double)getResistanceDayMap().get(day)) { // price
				addResistance(day, highPrice);
//					System.out.println("high " + highPrice);
			}
		}
		if(getSupportDayMap().containsKey(day)) { // day support
			if( lowPrice < (Double)getSupportDayMap().get(day)) { // price
				addSupport(day, lowPrice);
//					System.out.println("low " + lowPrice);
			}
		}
		if(getRecentDayMap().containsKey(day)) { // day support
//			System.out.println("RECENT " + channel.getContract().secType() + " " + day + " " + recentPrice);
			addRecent(day, recentPrice); // last
		} 
	}

	// TEST
	public void test() {
		
	}

	
	// SET GET
	public HashMap getResistanceDayMap() {
		return resistanceDayMap;
	}

	private void setResistanceDayMap(HashMap resistance) {
		this.resistanceDayMap = resistance;
	}

	public HashMap getSupportDayMap() {
		return supportDayMap;
	}

	private void setSupportDayMap(HashMap support) {
		this.supportDayMap = support;
	}

	public List<String> getResDayList() {
		return resistanceDayList;
	}

	private void setResDayList(List<String> resDate) {
		this.resistanceDayList = resDate;
	}

	public List<String> getSupDayList() {
		return supportDayList;
	}

	private void setSupDayList(List<String> supDate) {
		this.supportDayList = supDate;
	}

	public HashMap getRecentDayMap() {
		return recentDayMap;
	}

	private void setRecentDayMap(HashMap recent) {
		this.recentDayMap = recent;
	}

	private List<String> getResistanceDayList() {
		return resistanceDayList;
	}

	private void setResistanceDayList(List<String> date) {
		this.resistanceDayList = date;
	}

	private List<String> getSupportDayList() {
		return supportDayList;
	}

	private void setSupportDayList(List<String> date) {
		this.supportDayList = date;
	}

	public List<String> getRecentDayList() {
		return recentDayList;
	}

	private void setRecentDayList(List<String> date) {
		this.recentDayList = date;
	}

	public Contract getContract() {
		return contract;
	}

	private void setContract(Contract contract) {
		this.contract = contract;
	}

	public ContractFactory getContractFactory() {
		return contractFactory;
	}

	public void setContractFactory(ContractFactory contractFactory) {
		this.contractFactory = contractFactory;
	}

	public Investment getInvestment() {
		return investment;
	}

	public void setInvestment(Investment investment) {
		this.investment = investment;
	}

	

}
