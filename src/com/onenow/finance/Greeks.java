package com.onenow.finance;

public class Greeks {

	// Greeks
	// TODO get true Delta/greeks
	// regular greeks assume that implied volatility is constant for different underlying prices
	private Double theta;
	private Double delta;
	private Double gamma;
	private Double vega;
	private Double rho;

	private Double underlyingPrice; 
	private Double impliedVolatilityAnnualized;
	private Double holdingPeriodCalendarDays;
	private Double DailyVolatilityOfImpliedVolatility;
	private Double dailyVolatilityOfSingleDayImpliedVolatility;
	
	public Greeks() {

	}
	
	public Greeks(	Double theta, Double delta, Double gamma, Double vega, Double rho,
					Double price, Double IV, Double holdingPeriodCalendarDays,
					Double dailyVolatilityOfImpliedVolatility,
					Double dailyVolatilityOfSingleDayImpliedVolatility) {
		setTheta(theta);
		setDelta(delta);
		setGamma(gamma);
		setVega(vega);
		setRho(rho);
		setUnderlyingPrice(price);
		setImpliedVolatilityAnnualized(IV);
		setHoldingPeriodCalendarDays(holdingPeriodCalendarDays);
		setDailyVolatilityOfImpliedVolatility(dailyVolatilityOfImpliedVolatility);
		setDailyVolatilityOfSingleDayImpliedVolatility(dailyVolatilityOfSingleDayImpliedVolatility);
	}
		
	
	// DTRRR: lower risk per unit of return, the more negative it is
	// Use to establish: universal set of objective entry, adjustment, exit rules 
	public Double getDeltaThetaRiskReturnRatio() { 
		Double deltaEffect = getDeltaEffect();
		Double gammaEffect = getGammaEffect();
		Double thetaEffect = getThetaEffect();
//		System.out.println("DTRRRR " + deltaEffect + "\t" + gammaEffect + "\t" + thetaEffect);
		Double ratio = (deltaEffect+gammaEffect) / thetaEffect;
		return ratio;
	}
	

	// VTRRR
	public Double getVegaThetaRiskReturnRatio() { 
		Double ratio = 0.0;
		Double vegaEffect = getVegaEffect();
		Double thetaEffect = getThetaEffect();
//		System.out.println("VTRRR " + vegaEffect + "\t" + thetaEffect);
		ratio = vegaEffect / thetaEffect;
		return ratio;
	}
	
	// RTRRR
	public Double getRhoThetaRiskReturnRatio() { 
		Double ratio=0.0;
		Double rhoEffect = getRhoEffect();
		Double thetaEffect = getThetaEffect();
//		System.out.println("RTRRR " + rhoEffect + "\t" + thetaEffect);
		ratio = rhoEffect / thetaEffect;
		return ratio;
	}


	// GREEK EFFECTS
	private Double getThetaEffect() {
		Double thetaEffect = 0.0;
		thetaEffect = getTheta() * getHoldingPeriodCalendarDays();
		return thetaEffect;
	}
	
	private Double getDeltaEffect() {
		Double deltaEffect = 0.0;
		deltaEffect = -Math.abs(getDelta()) * getExpectedPriceChange(); // TODO: fix
		return deltaEffect;
	}
	
	private Double getGammaEffect() {
		Double gammaEffect=0.0;
		gammaEffect = getGamma() * Math.pow(getExpectedPriceChange(), 2) / 2; // TODO: fix
		return gammaEffect;
	}
	
	private Double getVegaEffect() {
		Double vegaEffect=0.0;
		vegaEffect= -Math.abs(getVega()) * getExpectedChangeInImpliedVolatilityOverTheHoldingPeriod();
		return vegaEffect;
	}

	private Double getRhoEffect() {
		Double rhoEffect=0.0;
		rhoEffect = -Math.abs(getRho())*getExpectedChangeInImpliedVolatility();
		return rhoEffect;
	}
	
	// DERIVED METRICS
	private Double getExpectedPriceChange() {
		Double EPC=0.0;
		Double squareRoot = Math.pow(getHoldingPeriodCalendarDays()/365, 0.5);
		Double exponential = Math.exp( getImpliedVolatilityAnnualized() *  squareRoot) - 1;
		EPC = getUnderlyingPrice() * exponential;
		//System.out.println("EPC " + squareRoot + "\t" + exponential + "\t" + EPC);
		return EPC;
	}
	
	private Double getExpectedChangeInImpliedVolatilityOverTheHoldingPeriod() {
		Double ECIV=0.0;
		Double weekdays = getHoldingPeriodCalendarDays() * 5/7;
		Double volatility = getDailyVolatilityOfImpliedVolatility();
//		System.out.println("ECIV " + volatility + "\t" + weekdays);
		ECIV = volatility * Math.pow(weekdays, 0.5);
		return ECIV;
	}
	
	private Double getExpectedChangeInImpliedVolatility() {
		Double ECRF = 0.0;
		Double weekdays = getHoldingPeriodCalendarDays() * 5/7;
		Double volatility = getDailyVolatilityOfSingleDayImpliedVolatility();
//		System.out.println("ECRF " + volatility + "\t" + weekdays);
		ECRF = volatility * Math.pow(weekdays, 0.5);
		return ECRF;
	}
	

	
	// PRINT
	public String toString() {
		String s = "\n\n";
		
		s = s + "GREEKS:" + "\n";
		s = s + "theta " + getTheta() + "\n";
 		s = s + "delta " + getDelta() + "\n";
		s = s + "gamma " + getGamma() + "\n";
 		s = s + "vega " + getVega() + "\n";
 		s = s + "rho " + getRho() + "\n";
 
 		s = s + "RISK:" + "\n";
 		s = s + "dtrrr " + getDeltaThetaRiskReturnRatio() + "\n";
 		s = s + "vtrrr " + getVegaThetaRiskReturnRatio() + "\n";
 		s = s + "rtrrr " + getRhoThetaRiskReturnRatio() + "\n";
 		
 		s = s + "GREEK EFFECT:" + "\n";
		s = s + "theta effect $" + getThetaEffect() + "\n";
 		s = s + "delta effect $" + getDeltaEffect() + "\n";
		s = s + "gamma effect $" + getGammaEffect() + "\n";
 		s = s + "vega effect $" + getVegaEffect() + "\n";
 		s = s + "rho effect $" + getRhoEffect() + "\n";
 		
 		s = s + "CALCULATED:" + "\n";
 		s = s + "expected price change $" + getExpectedPriceChange() + "\n";
 		s = s + "expected change in implied volatility over holding period " + getExpectedChangeInImpliedVolatilityOverTheHoldingPeriod() + "\n";
 		s = s + "expected change in implied volatility " + getExpectedChangeInImpliedVolatility() + "\n";
 		
 		s = s + "BASED ON:" + "\n";
 		s = s + "underlyign price $" + getUnderlyingPrice() + "\n";
 		s = s + "holding period days " + getHoldingPeriodCalendarDays() + "\n";
 		s = s + "implied volatility annualized " + getImpliedVolatilityAnnualized() + "\n";
		s = s + "daily volatility of implied volatility " + getDailyVolatilityOfImpliedVolatility() + "\n";
 		s = s + "daily volatility of single day implied volatility " + getDailyVolatilityOfSingleDayImpliedVolatility() + "\n";
		return s;
	}

	// TEST
	public boolean test() {
		setTheta(29.26);
		setDelta(0.25);
		setGamma(-0.66);
		setVega(-81.90);
		setRho(1.14);
		setUnderlyingPrice(901.60);
		setHoldingPeriodCalendarDays(3.0);
		setImpliedVolatilityAnnualized(0.2060);
		setDailyVolatilityOfImpliedVolatility(2.57);
		setDailyVolatilityOfSingleDayImpliedVolatility(0.0095);
		
		System.out.println(toString());
		return (
				testDTRRR(-1.134412846893558) &&
				testVTRRR(-3.5100884322355794) && 
				testRTRRR(-1.8060488363008564E-4)
				);
	}
	
	
	public boolean testDTRRR(Double num){
		if(!getDeltaThetaRiskReturnRatio().equals(num)) {
			System.out.println("ERROR DTRRR " + getDeltaThetaRiskReturnRatio());
			return false;
		}
		return true;
	}
	
	public boolean testVTRRR(Double num) {
		if(!getVegaThetaRiskReturnRatio().equals(num)) {
			System.out.println("ERROR VTRRR " + getVegaThetaRiskReturnRatio());
			return false;			
		}
		return true;
	}

	public boolean testRTRRR(Double num) {
		if(!getRhoThetaRiskReturnRatio().equals(num)) {
			System.out.println("ERROR RTRRR " + getRhoThetaRiskReturnRatio());
			return false;			
		}
		return true;
	}

	// SET GET
	public Double getTheta() {
		return theta;
	}

	private void setTheta(Double theta) {
		this.theta = theta;
	}

	public Double getDelta() {
		return delta;
	}

	private void setDelta(Double delta) {
		this.delta = delta;
	}

	public Double getGamma() {
		return gamma;
	}

	private void setGamma(Double gamma) {
		this.gamma = gamma;
	}

	public Double getVega() {
		return vega;
	}

	private void setVega(Double vega) {
		this.vega = vega;
	}

	public Double getRho() {
		return rho;
	}

	private void setRho(Double rho) {
		this.rho = rho;
	}

	private Double getUnderlyingPrice() {
		return underlyingPrice;
	}

	private void setUnderlyingPrice(Double underlyingPrice) {
		this.underlyingPrice = underlyingPrice;
	}

	private Double getImpliedVolatilityAnnualized() {
		return impliedVolatilityAnnualized;
	}

	private void setImpliedVolatilityAnnualized(Double impliedVolatilityAnnualized) {
		this.impliedVolatilityAnnualized = impliedVolatilityAnnualized;
	}

	private Double getHoldingPeriodCalendarDays() {
		return holdingPeriodCalendarDays;
	}

	private void setHoldingPeriodCalendarDays(Double holdingPeriodCalendarDays) {
		this.holdingPeriodCalendarDays = holdingPeriodCalendarDays;
	}

	private void setDailyVolatilityOfSingleDayImpliedVolatility(
			Double dailyVolatilityOfSingleDayImpliedVolatility) {
		this.dailyVolatilityOfSingleDayImpliedVolatility = dailyVolatilityOfSingleDayImpliedVolatility;
	}

	private Double getDailyVolatilityOfSingleDayImpliedVolatility() {
		return dailyVolatilityOfSingleDayImpliedVolatility;
	}

	private void setDailyVolatilityOfImpliedVolatility(
			Double dailyVolatilityOfImpliedVolatility) {
		DailyVolatilityOfImpliedVolatility = dailyVolatilityOfImpliedVolatility;
	}

	private Double getDailyVolatilityOfImpliedVolatility() {
		return DailyVolatilityOfImpliedVolatility;
	}


}
