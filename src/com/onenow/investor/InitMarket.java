package com.onenow.investor;

import java.util.ArrayList;
import java.util.List;

import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.InvestmentFuture;
import com.onenow.finance.InvestmentIndex;
import com.onenow.finance.InvestmentOption;
import com.onenow.finance.InvestmentStock;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;


public class InitMarket {

	Portfolio marketPortfolio;
	
	List<String> indices = new ArrayList<String>();
	List<String> SNP500 = new ArrayList<String>();
	
	
	public InitMarket() {
		
	}
	
	public InitMarket(Portfolio portfolio) {
		setMarketPortfolio(portfolio);
		initMarket();
	}
	
	private void initMarket() { // create the investments
		String expDate="20150326";

		initAllIndicesAndOptions("20150326");
		initAllFutures("20150320");
		initAllStocks();

		System.out.println(getMarketPortfolio().toString());		
	}

	private void initAllFutures(String expDate) {
		Underlying under = new Underlying("ES");
		InvestmentFuture future = new InvestmentFuture(under, expDate);
		Trade futureTrade = new Trade(future, TradeType.BUY, 1, 0.0);
		Transaction stockTrans = new Transaction(futureTrade);
		getMarketPortfolio().enterTransaction(stockTrans);		

	}
	
	private void initAllStocks() {
		setSNP500List();
		for (String stock:getSNP500()) {
			setStock(stock);
		}
	}

	private void initAllIndicesAndOptions(String expDate) {
		String spx="SPX";
		Integer seedSPX=2100;		
		setIndexAndOptions(spx, expDate, seedSPX);
		
		String ndx="NDX";
		Integer seedNDX=4450;		
		setIndexAndOptions(ndx, expDate, seedNDX);

		String rut="RUT";
		Integer seedRUT=1350;		
		setIndexAndOptions(rut, expDate, seedRUT);
	}
	
	private void setStock(String name) {
		Underlying under = new Underlying(name);
		InvestmentStock stock = new InvestmentStock(under);
		Trade stockTrade = new Trade(stock, TradeType.BUY, 1, 0.0);
		Transaction stockTrans = new Transaction(stockTrade);
		getMarketPortfolio().enterTransaction(stockTrans);		
	}

	private void setIndexAndOptions(String name, String expDate, Integer seed) {
		Underlying under = new Underlying(name);
		setIndex(under);		
		setOptions(under, expDate, seed);
	}

	private void setIndex(Underlying under) {
		InvestmentIndex index = new InvestmentIndex(under);
		Trade indexTrade = new Trade(index, TradeType.BUY, 1, 0.0);
		Transaction indexTrans = new Transaction(indexTrade);
		getMarketPortfolio().enterTransaction(indexTrans);
	}

	private void setOptions(Underlying under, String expDate, Integer seed) {
		for (Double strike=(double) (seed-200); strike<(seed+200); strike=strike+5) {
			Investment call = new InvestmentOption(under, InvType.CALL, expDate, strike);
			Investment put = new InvestmentOption(under, InvType.PUT, expDate, strike);
			Trade callTrade = new Trade(call, TradeType.BUY, 1, 0.0);
			Trade putTrade = new Trade(put, TradeType.BUY, 1, 0.0);
//			Transaction optTrans = new Transaction(callTrade, putTrade);
			Transaction optTrans = new Transaction(callTrade);
			getMarketPortfolio().enterTransaction(optTrans);
		}
	}

//	private void setIndicesList() {
//		getIndices().add("SPX");
//		getIndices().add("NDX");
//		getIndices().add("RUT");		
//	}
	
	private void setSNP500List() {
		getSNP500().add("A");
		getSNP500().add("AA");
		getSNP500().add("AAPL");
		getSNP500().add("ABC");
		getSNP500().add("ABT");
		getSNP500().add("ACE");
		getSNP500().add("ACN");
		getSNP500().add("ADBE");
		getSNP500().add("ADI");
		getSNP500().add("ADM");
		getSNP500().add("ADP");
		getSNP500().add("ADSK");
		getSNP500().add("ADT");
		getSNP500().add("AEE");
		getSNP500().add("AEP");
		getSNP500().add("AES");
		getSNP500().add("AET");
		getSNP500().add("AFL");
		getSNP500().add("AGN");
		getSNP500().add("AIG");
		getSNP500().add("AIV");
		getSNP500().add("AIZ");
		getSNP500().add("AKAM");
		getSNP500().add("ALL");
		getSNP500().add("ALTR");
		getSNP500().add("ALXN");
		getSNP500().add("AMAT");
		getSNP500().add("AMD");
		getSNP500().add("AMGN");
		getSNP500().add("AMP");
		getSNP500().add("AMT");
		getSNP500().add("AMZN");
		getSNP500().add("AN");
		getSNP500().add("ANF");
		getSNP500().add("AON");
		getSNP500().add("APA");
		getSNP500().add("APC");
		getSNP500().add("APD");
		getSNP500().add("APH");
		getSNP500().add("APOL");
		getSNP500().add("ARG");
		getSNP500().add("ATI");
		getSNP500().add("AVB");
		getSNP500().add("AVP");
		getSNP500().add("AVY");
		getSNP500().add("AXP");
		getSNP500().add("AZO");
		getSNP500().add("BA");
		getSNP500().add("BAC");
		getSNP500().add("BAX");
		getSNP500().add("BBBY");
		getSNP500().add("BBT");
		getSNP500().add("BBY");
		getSNP500().add("BCR");
		getSNP500().add("BDX");
		getSNP500().add("BEAM");
		getSNP500().add("BEN");
		getSNP500().add("BF.B");
		getSNP500().add("BHI");
		getSNP500().add("BIG");
		getSNP500().add("BIIB");
		getSNP500().add("BK");
		getSNP500().add("BLK");
		getSNP500().add("BLL");
		getSNP500().add("BMC");
		getSNP500().add("BMS");
		getSNP500().add("BMY");
		getSNP500().add("BRCM");
		getSNP500().add("BRK.B");
		getSNP500().add("BSX");
		getSNP500().add("BTU");
		getSNP500().add("BWA");
		getSNP500().add("BXP");
		getSNP500().add("C");
		getSNP500().add("CA");
		getSNP500().add("CAG");
		getSNP500().add("CAH");
		getSNP500().add("CAM");
		getSNP500().add("CAT");
		getSNP500().add("CB");
		getSNP500().add("CBG");
		getSNP500().add("CBS");
		getSNP500().add("CCE");
		getSNP500().add("CCI");
		getSNP500().add("CCL");
		getSNP500().add("CELG");
		getSNP500().add("CERN");
		getSNP500().add("CF");
		getSNP500().add("CFN");
		getSNP500().add("CHK");
		getSNP500().add("CHRW");
		getSNP500().add("CI");
		getSNP500().add("CINF");
		getSNP500().add("CL");
		getSNP500().add("CLF");
		getSNP500().add("CLX");
		getSNP500().add("CMA");
		getSNP500().add("CMCSA");
		getSNP500().add("CME");
		getSNP500().add("CMG");
		getSNP500().add("CMI");
		getSNP500().add("CMS");
		getSNP500().add("CNP");
		getSNP500().add("CNX");
		getSNP500().add("COF");
		getSNP500().add("COG");
		getSNP500().add("COH");
		getSNP500().add("COL");
		getSNP500().add("COP");
		getSNP500().add("COST");
		getSNP500().add("COV");
		getSNP500().add("CPB");
		getSNP500().add("CRM");
		getSNP500().add("CSC");
		getSNP500().add("CSCO");
		getSNP500().add("CSX");
		getSNP500().add("CTAS");
		getSNP500().add("CTL");
		getSNP500().add("CTSH");
		getSNP500().add("CTXS");
		getSNP500().add("CVC");
		getSNP500().add("CVH");
		getSNP500().add("CVS");
		getSNP500().add("CVX");
		getSNP500().add("D");
		getSNP500().add("DD");
		getSNP500().add("DE");
		getSNP500().add("DELL");
		getSNP500().add("DF");
		getSNP500().add("DFS");
		getSNP500().add("DG");
		getSNP500().add("DGX");
		getSNP500().add("DHI");
		getSNP500().add("DHR");
		getSNP500().add("DIS");
		getSNP500().add("DISCA");
		getSNP500().add("DLTR");
		getSNP500().add("DNB");
		getSNP500().add("DNR");
		getSNP500().add("DO");
		getSNP500().add("DOV");
		getSNP500().add("DOW");
		getSNP500().add("DPS");
		getSNP500().add("DRI");
		getSNP500().add("DTE");
		getSNP500().add("DTV");
		getSNP500().add("DUK");
		getSNP500().add("DVA");
		getSNP500().add("DVN");
		getSNP500().add("EA");
		getSNP500().add("EBAY");
		getSNP500().add("ECL");
		getSNP500().add("ED");
		getSNP500().add("EFX");
		getSNP500().add("EIX");
		getSNP500().add("EL");
		getSNP500().add("EMC");
		getSNP500().add("EMN");
		getSNP500().add("EMR");
		getSNP500().add("EOG");
		getSNP500().add("EQR");
		getSNP500().add("EQT");
		getSNP500().add("ESRX");
		getSNP500().add("ESV");
		getSNP500().add("ETFC");
		getSNP500().add("ETN");
		getSNP500().add("ETR");
		getSNP500().add("EW");
		getSNP500().add("EXC");
		getSNP500().add("EXPD");
		getSNP500().add("EXPE");
		getSNP500().add("F");
		getSNP500().add("FAST");
		getSNP500().add("FCX");
		getSNP500().add("FDO");
		getSNP500().add("FDX");
		getSNP500().add("FE");
		getSNP500().add("FFIV");
		getSNP500().add("FHN");
		getSNP500().add("FII");
		getSNP500().add("FIS");
		getSNP500().add("FISV");
		getSNP500().add("FITB");
		getSNP500().add("FLIR");
		getSNP500().add("FLR");
		getSNP500().add("FLS");
		getSNP500().add("FMC");
		getSNP500().add("FOSL");
		getSNP500().add("FRX");
		getSNP500().add("FSLR");
		getSNP500().add("FTI");
		getSNP500().add("FTR");
		getSNP500().add("GAS");
		getSNP500().add("GCI");
		getSNP500().add("GD");
		getSNP500().add("GE");
		getSNP500().add("GILD");
		getSNP500().add("GIS");
		getSNP500().add("GLW");
		getSNP500().add("GME");
		getSNP500().add("GNW");
		getSNP500().add("GOOG");
		getSNP500().add("GPC");
		getSNP500().add("GPS");
		getSNP500().add("GS");
		getSNP500().add("GT");
		getSNP500().add("GWW");
		getSNP500().add("HAL");
		getSNP500().add("HAR");
		getSNP500().add("HAS");
		getSNP500().add("HBAN");
		getSNP500().add("HCBK");
		getSNP500().add("HCN");
		getSNP500().add("HCP");
		getSNP500().add("HD");
		getSNP500().add("HES");
		getSNP500().add("HIG");
		getSNP500().add("HNZ");
		getSNP500().add("HOG");
		getSNP500().add("HON");
		getSNP500().add("HOT");
		getSNP500().add("HP");
		getSNP500().add("HPQ");
		getSNP500().add("HRB");
		getSNP500().add("HRL");
		getSNP500().add("HRS");
		getSNP500().add("HSP");
		getSNP500().add("HST");
		getSNP500().add("HSY");
		getSNP500().add("HUM");
		getSNP500().add("IBM");
		getSNP500().add("ICE");
		getSNP500().add("IFF");
		getSNP500().add("IGT");
		getSNP500().add("INTC");
		getSNP500().add("INTU");
		getSNP500().add("IP");
		getSNP500().add("IPG");
		getSNP500().add("IR");
		getSNP500().add("IRM");
		getSNP500().add("ISRG");
		getSNP500().add("ITW");
		getSNP500().add("IVZ");
		getSNP500().add("JBL");
		getSNP500().add("JCI");
		getSNP500().add("JCP");
		getSNP500().add("JDSU");
		getSNP500().add("JEC");
		getSNP500().add("JNJ");
		getSNP500().add("JNPR");
		getSNP500().add("JOY");
		getSNP500().add("JPM");
		getSNP500().add("JWN");
		getSNP500().add("K");
		getSNP500().add("KEY");
		getSNP500().add("KIM");
		getSNP500().add("KLAC");
		getSNP500().add("KMB");
		getSNP500().add("KMI");
		getSNP500().add("KMX");
		getSNP500().add("KO");
		getSNP500().add("KR");
		getSNP500().add("KRFT");
		getSNP500().add("KSS");
		getSNP500().add("L");
		getSNP500().add("LEG");
		getSNP500().add("LEN");
		getSNP500().add("LH");
		getSNP500().add("LIFE");
		getSNP500().add("LLL");
		getSNP500().add("LLTC");
		getSNP500().add("LLY");
		getSNP500().add("LM");
		getSNP500().add("LMT");
		getSNP500().add("LNC");
		getSNP500().add("LO");
		getSNP500().add("LOW");
		getSNP500().add("LRCX");
		getSNP500().add("LSI");
		getSNP500().add("LTD");
		getSNP500().add("LUK");
		getSNP500().add("LUV");
		getSNP500().add("LYB");
		getSNP500().add("M");
		getSNP500().add("MA");
		getSNP500().add("MAR");
		getSNP500().add("MAS");
		getSNP500().add("MAT");
		getSNP500().add("MCD");
		getSNP500().add("MCHP");
		getSNP500().add("MCK");
		getSNP500().add("MCO");
		getSNP500().add("MDLZ");
		getSNP500().add("MDT");
		getSNP500().add("MET");
		getSNP500().add("MHP");
		getSNP500().add("MJN");
		getSNP500().add("MKC");
		getSNP500().add("MMC");
		getSNP500().add("MMM");
		getSNP500().add("MNST");
		getSNP500().add("MO");
		getSNP500().add("MOLX");
		getSNP500().add("MON");
		getSNP500().add("MOS");
		getSNP500().add("MPC");
		getSNP500().add("MRK");
		getSNP500().add("MRO");
		getSNP500().add("MS");
		getSNP500().add("MSFT");
		getSNP500().add("MSI");
		getSNP500().add("MTB");
		getSNP500().add("MU");
		getSNP500().add("MUR");
		getSNP500().add("MWV");
		getSNP500().add("MYL");
		getSNP500().add("NBL");
		getSNP500().add("NBR");
		getSNP500().add("NDAQ");
		getSNP500().add("NE");
		getSNP500().add("NEE");
		getSNP500().add("NEM");
		getSNP500().add("NFLX");
		getSNP500().add("NFX");
		getSNP500().add("NI");
		getSNP500().add("NKE");
		getSNP500().add("NOC");
		getSNP500().add("NOV");
		getSNP500().add("NRG");
		getSNP500().add("NSC");
		getSNP500().add("NTAP");
		getSNP500().add("NTRS");
		getSNP500().add("NU");
		getSNP500().add("NUE");
		getSNP500().add("NVDA");
		getSNP500().add("NWL");
		getSNP500().add("NWSA");
		getSNP500().add("NYX");
		getSNP500().add("OI");
		getSNP500().add("OKE");
		getSNP500().add("OMC");
		getSNP500().add("ORCL");
		getSNP500().add("ORLY");
		getSNP500().add("OXY");
		getSNP500().add("PAYX");
		getSNP500().add("PBCT");
		getSNP500().add("PBI");
		getSNP500().add("PCAR");
		getSNP500().add("PCG");
		getSNP500().add("PCL");
		getSNP500().add("PCLN");
		getSNP500().add("PCP");
		getSNP500().add("PCS");
		getSNP500().add("PDCO");
		getSNP500().add("PEG");
		getSNP500().add("PEP");
		getSNP500().add("PETM");
		getSNP500().add("PFE");
		getSNP500().add("PFG");
		getSNP500().add("PG");
		getSNP500().add("PGR");
		getSNP500().add("PH");
		getSNP500().add("PHM");
		getSNP500().add("PKI");
		getSNP500().add("PLD");
		getSNP500().add("PLL");
		getSNP500().add("PM");
		getSNP500().add("PNC");
		getSNP500().add("PNR");
		getSNP500().add("PNW");
		getSNP500().add("POM");
		getSNP500().add("PPG");
		getSNP500().add("PPL");
		getSNP500().add("PRGO");
		getSNP500().add("PRU");
		getSNP500().add("PSA");
		getSNP500().add("PSX");
		getSNP500().add("PWR");
		getSNP500().add("PX");
		getSNP500().add("PXD");
		getSNP500().add("QCOM");
		getSNP500().add("QEP");
		getSNP500().add("R");
		getSNP500().add("RAI");
		getSNP500().add("RDC");
		getSNP500().add("RF");
		getSNP500().add("RHI");
		getSNP500().add("RHT");
		getSNP500().add("RL");
		getSNP500().add("ROK");
		getSNP500().add("ROP");
		getSNP500().add("ROST");
		getSNP500().add("RRC");
		getSNP500().add("RRD");
		getSNP500().add("RSG");
		getSNP500().add("RTN");
		getSNP500().add("S");
		getSNP500().add("SAI");
		getSNP500().add("SBUX");
		getSNP500().add("SCG");
		getSNP500().add("SCHW");
		getSNP500().add("SE");
		getSNP500().add("SEE");
		getSNP500().add("SHW");
		getSNP500().add("SIAL");
		getSNP500().add("SJM");
		getSNP500().add("SLB");
		getSNP500().add("SLM");
		getSNP500().add("SNA");
		getSNP500().add("SNDK");
		getSNP500().add("SNI");
		getSNP500().add("SO");
		getSNP500().add("SPG");
		getSNP500().add("SPLS");
		getSNP500().add("SRCL");
		getSNP500().add("SRE");
		getSNP500().add("STI");
		getSNP500().add("STJ");
		getSNP500().add("STT");
		getSNP500().add("STX");
		getSNP500().add("STZ");
		getSNP500().add("SWK");
		getSNP500().add("SWN");
		getSNP500().add("SWY");
		getSNP500().add("SYK");
		getSNP500().add("SYMC");
		getSNP500().add("SYY");
		getSNP500().add("T");
		getSNP500().add("TAP");
		getSNP500().add("TDC");
		getSNP500().add("TE");
		getSNP500().add("TEG");
		getSNP500().add("TEL");
		getSNP500().add("TER");
		getSNP500().add("TGT");
		getSNP500().add("THC");
		getSNP500().add("TIE");
		getSNP500().add("TIF");
		getSNP500().add("TJX");
		getSNP500().add("TMK");
		getSNP500().add("TMO");
		getSNP500().add("TRIP");
		getSNP500().add("TROW");
		getSNP500().add("TRV");
		getSNP500().add("TSN");
		getSNP500().add("TSO");
		getSNP500().add("TSS");
		getSNP500().add("TWC");
		getSNP500().add("TWX");
		getSNP500().add("TXN");
		getSNP500().add("TXT");
		getSNP500().add("TYC");
		getSNP500().add("UNH");
		getSNP500().add("UNM");
		getSNP500().add("UNP");
		getSNP500().add("UPS");
		getSNP500().add("URBN");
		getSNP500().add("USB");
		getSNP500().add("UTX");
		getSNP500().add("V");
		getSNP500().add("VAR");
		getSNP500().add("VFC");
		getSNP500().add("VIAB");
		getSNP500().add("VLO");
		getSNP500().add("VMC");
		getSNP500().add("VNO");
		getSNP500().add("VRSN");
		getSNP500().add("VTR");
		getSNP500().add("VZ");
		getSNP500().add("WAG");
		getSNP500().add("WAT");
		getSNP500().add("WDC");
		getSNP500().add("WEC");
		getSNP500().add("WFC");
		getSNP500().add("WFM");
		getSNP500().add("WHR");
		getSNP500().add("WIN");
		getSNP500().add("WLP");
		getSNP500().add("WM");
		getSNP500().add("WMB");
		getSNP500().add("WMT");
		getSNP500().add("WPI");
		getSNP500().add("WPO");
		getSNP500().add("WPX");
		getSNP500().add("WU");
		getSNP500().add("WY");
		getSNP500().add("WYN");
		getSNP500().add("WYNN");
		getSNP500().add("X");
		getSNP500().add("XEL");
		getSNP500().add("XL");
		getSNP500().add("XLNX");
		getSNP500().add("XOM");
		getSNP500().add("XRAY");
		getSNP500().add("XRX");
		getSNP500().add("XYL");
		getSNP500().add("YHOO");
		getSNP500().add("YUM");
		getSNP500().add("ZION");
		getSNP500().add("ZMH");
	}

	// SET GET
	private List<String> getIndices() {
		return indices;
	}

	private void setIndices(List<String> indices) {
		this.indices = indices;
	}

	private List<String> getSNP500() {
		return SNP500;
	}

	private void setSNP500(List<String> sNP500) {
		SNP500 = sNP500;
	}

	public Portfolio getMarketPortfolio() {
		return marketPortfolio;
	}

	private void setMarketPortfolio(Portfolio marketPortfolio) {
		this.marketPortfolio = marketPortfolio;
	}
	
}
