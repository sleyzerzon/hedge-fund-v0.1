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

	
	public Greeks() {

	}
	
	public Greeks(Underlying under) {
		
	}
	
	public void getValues() {
		// TODO: issue query to broker
		setTheta(1.0);
		setDelta(1.0);
		setGamma(1.0);
		setVega(1.0);
		setRho(1.0);
	}
	
	// DTRRR: lower risk per unit of return, the more negative it is
	// Use to establish: universal set of objective entry, adjustment, exit rules 
	public Double getDeltaThetaRiskReturnRatio() { 
		Double ratio = getDeltaEffect() + getGammaEffect() / getThetaEffect();
		return ratio;
	}
	

	// VTRRR
	public Double getVegaThetaRiskReturnRatio() { 
		Double ratio = 0.0;
		ratio = getVegaEffect() / getThetaEffect();
		return ratio;
	}
	
	// RTRRR
	public Double getRhoThetaRiskReturnRatio() { 
		Double ratio=0.0;
		ratio = getRhoEffect() / getThetaEffect();
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
		deltaEffect = Math.abs(getDelta()) * getExpectedPriceChange(); // fix
		return deltaEffect;
	}
	
	private Double getGammaEffect() {
		Double gammaEffect=0.0;
		gammaEffect = getGamma() * Math.pow(getExpectedPriceChange(), 2) / 2; // fix
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
	
	// PRIVATE EXTERNAL
	private Double getHoldingPeriodCalendarDays() {
		Double HPD=0.0;
		// TODO
		return HPD;
	}
	
	private Double getExpectedPriceChange() {
		Double EPC=0.0;
		EPC = getUnderlyingPrice() * getImpliedVolatilityAnnualized() * getThetaEffect(); // TODO 
		return EPC;
	}

	private Double getImpliedVolatilityAnnualized() {
		Double IV=0.0;
		// TODO
		return IV;
	}

	private Double getUnderlyingPrice() {
		Double UP=0.0;
		// TODO: price = getBroker().getPriceAsk(stockPromise)
		return UP;
	}
	
	private Double getDailyVolatilityOfImpliedVolatility() {
		Double DVIV=0.0;
		// TODO
		return DVIV;
	}
	
	private Double getDailyVolatilityOfImpliedVolatilitySingleDay() { // TODO pg 53
		Double DVRF=0.0;
		// TODO DVRF = ...
		return DVRF;
	}

	private Double getExpectedChangeInImpliedVolatilityOverTheHoldingPeriod() {
		Double ECIV=0.0;
		Double weekdays = getHoldingPeriodCalendarDays() * 5/7;
		ECIV = getDailyVolatilityOfImpliedVolatility() * Math.pow(weekdays, 0.5);
		return ECIV;
	}
	
	private Double getExpectedChangeInImpliedVolatility() {
		Double ECRF = 0.0;
		Double weekdays = getHoldingPeriodCalendarDays() * 5/7;
		ECRF = getDailyVolatilityOfImpliedVolatilitySingleDay() * Math.pow(weekdays, 0.5);
		return ECRF;
	}
	

	
	// PRINT
	
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

}
