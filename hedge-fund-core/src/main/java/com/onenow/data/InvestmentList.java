package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.instrument.Underlying;

public class InvestmentList {
	
	// official
	public static List<String> snp500Names = getSNP500();
	public static List<String> indexNames = getMainIndices();
	public static List<String> futureNames = getFutures();
	public static List<String> optionNames = getOptions();	

	// subsets
//	public static List<String> someStocks = getSomeStocks();
	public static List<String> justApple = getAAPLStock();
	public static List<String> someIndices = getSomeIndices();

	public InvestmentList() {
	}
	
	public static List<Underlying> getUnderlying(List<String> names) {
		
		List<Underlying> unders = new ArrayList<Underlying>();
		
		for(String name:names) {
			Underlying under = new Underlying(name);
			unders.add(under); 			
		}
		
		return unders;
	}
	
	private static List<String> getMainIndices() {
		List<String> list = new ArrayList<String>();
		list.add("SPX");
		list.add("NDX");
		list.add("RUT");
		return list;
	}
	
	private static List<String> getFutures() {
		List<String> list = new ArrayList<String>();
		list.add("ES");
		return list;
	}

	private static List<String> getOptions() {
		List<String> list = new ArrayList<String>();
		list.add("SPX");
		return list;
	}

	private static List<String> getSomeIndices() {
		List<String> list = new ArrayList<String>();
		list.add("SPX");
		return list;
	}

	
	public static List<String> getSomeStocks() {
		List<String> list = new ArrayList<String>();
		int count = 0;
		int maxNum = 3;
		
		list.add("SPY");
		
		for(String stock:snp500Names){
			list.add(stock);
			count++;
			if(count>maxNum) {
				return list;
			}
		}
		return list;
	}
		
	private static List<String> getAAPLStock() {
		List<String> list = new ArrayList<String>();
		list.add("AAPL");
		return list;
	}

	private static List<String> getSNP500() {

		List<String> list = new ArrayList<String>();
		
		list.add("A");
		list.add("AA");
		list.add("AAPL");
		list.add("ABC");
		list.add("ABT");
		list.add("ACE");
		list.add("ACN");
		list.add("ADBE");
		list.add("ADI");
		list.add("ADM");
		list.add("ADP");
		list.add("ADSK");
		list.add("ADT");
		list.add("AEE");
		list.add("AEP");
		list.add("AES");
		list.add("AET");
		list.add("AFL");
		list.add("AGN");
		list.add("AIG");
		list.add("AIV");
		list.add("AIZ");
		list.add("AKAM");
		list.add("ALL");
		list.add("ALTR");
		list.add("ALXN");
		list.add("AMAT");
		list.add("AMD");
		list.add("AMGN");
		list.add("AMP");
		list.add("AMT");
		list.add("AMZN");
		list.add("AN");
		list.add("ANF");
		list.add("AON");
		list.add("APA");
		list.add("APC");
		list.add("APD");
		list.add("APH");
		list.add("APOL");
		list.add("ARG");
		list.add("ATI");
		list.add("AVB");
		list.add("AVP");
		list.add("AVY");
		list.add("AXP");
		list.add("AZO");
		
		list.add("BA");
		list.add("BAC");
		list.add("BAX");
		list.add("BBBY");
		list.add("BBT");
		list.add("BBY");
		list.add("BCR");
		list.add("BDX");
//		list.add("BEAM"); TODO
		list.add("BEN");
//		list.add("BF.B"); TODO
		list.add("BHI");
		list.add("BIG");
		list.add("BIIB");
		list.add("BK");
		list.add("BLK");
		list.add("BLL");
//		list.add("BMC"); TODO
		list.add("BMS");
		list.add("BMY");
		list.add("BRCM");
//		list.add("BRK.B"); TODO
		list.add("BSX");
		list.add("BTU");
		list.add("BWA");
		list.add("BXP");
		
		list.add("C");
		list.add("CA");
		list.add("CAG");
		list.add("CAH");
		list.add("CAM");
		list.add("CAT");
		list.add("CB");
		list.add("CBG");
		list.add("CBS");
		list.add("CCE");
		list.add("CCI");
		list.add("CCL");
		list.add("CELG");
		list.add("CERN");
		list.add("CF");
		list.add("CFN");
		list.add("CHK");
		list.add("CHRW");
		list.add("CI");
		list.add("CINF");
		list.add("CL");
		list.add("CLF");
		list.add("CLX");
		list.add("CMA");
		list.add("CMCSA");
		list.add("CME");
		list.add("CMG");
		list.add("CMI");
		list.add("CMS");
		list.add("CNP");
		list.add("CNX");
		list.add("COF");
		list.add("COG");
		list.add("COH");
		list.add("COL");
		list.add("COP");
		list.add("COST");
		list.add("COV");
		list.add("CPB");
		list.add("CRM");
		list.add("CSC");
		list.add("CSCO");
		list.add("CSX");
		list.add("CTAS");
		list.add("CTL");
		list.add("CTSH");
		list.add("CTXS");
		list.add("CVC");
		list.add("CVH");
		list.add("CVS");
		list.add("CVX");
		list.add("D");
		list.add("DD");
		list.add("DE");
		list.add("DELL");
		list.add("DF");
		list.add("DFS");
		list.add("DG");
		list.add("DGX");
		list.add("DHI");
		list.add("DHR");
		list.add("DIS");
		list.add("DISCA");
		list.add("DLTR");
		list.add("DNB");
		list.add("DNR");
		list.add("DO");
		list.add("DOV");
		list.add("DOW");
		list.add("DPS");
		list.add("DRI");
		list.add("DTE");
		list.add("DTV");
		list.add("DUK");
		list.add("DVA");
		list.add("DVN");
		list.add("EA");
		list.add("EBAY");
		list.add("ECL");
		list.add("ED");
		list.add("EFX");
		list.add("EIX");
		list.add("EL");
		list.add("EMC");
		list.add("EMN");
		list.add("EMR");
		list.add("EOG");
		list.add("EQR");
		list.add("EQT");
		list.add("ESRX");
		list.add("ESV");
		list.add("ETFC");
		list.add("ETN");
		list.add("ETR");
		list.add("EW");
		list.add("EXC");
		list.add("EXPD");
		list.add("EXPE");
		list.add("F");
		list.add("FAST");
		list.add("FCX");
		list.add("FDO");
		list.add("FDX");
		list.add("FE");
		list.add("FFIV");
		list.add("FHN");
		list.add("FII");
		list.add("FIS");
		list.add("FISV");
		list.add("FITB");
		list.add("FLIR");
		list.add("FLR");
		list.add("FLS");
		list.add("FMC");
		list.add("FOSL");
		list.add("FRX");
		list.add("FSLR");
		list.add("FTI");
		list.add("FTR");
		list.add("GAS");
		list.add("GCI");
		list.add("GD");
		list.add("GE");
		list.add("GILD");
		list.add("GIS");
		list.add("GLW");
		list.add("GME");
		list.add("GNW");
		list.add("GOOG");
		list.add("GPC");
		list.add("GPS");
		list.add("GS");
		list.add("GT");
		list.add("GWW");
		list.add("HAL");
		list.add("HAR");
		list.add("HAS");
		list.add("HBAN");
		list.add("HCBK");
		list.add("HCN");
		list.add("HCP");
		list.add("HD");
		list.add("HES");
		list.add("HIG");
		list.add("HNZ");
		list.add("HOG");
		list.add("HON");
		list.add("HOT");
		list.add("HP");
		list.add("HPQ");
		list.add("HRB");
		list.add("HRL");
		list.add("HRS");
		list.add("HSP");
		list.add("HST");
		list.add("HSY");
		list.add("HUM");
		list.add("IBM");
		list.add("ICE");
		list.add("IFF");
		list.add("IGT");
		list.add("INTC");
		list.add("INTU");
		list.add("IP");
		list.add("IPG");
		list.add("IR");
		list.add("IRM");
		list.add("ISRG");
		list.add("ITW");
		list.add("IVZ");
		list.add("JBL");
		list.add("JCI");
		list.add("JCP");
		list.add("JDSU");
		list.add("JEC");
		list.add("JNJ");
		list.add("JNPR");
		list.add("JOY");
		list.add("JPM");
		list.add("JWN");
		list.add("K");
		list.add("KEY");
		list.add("KIM");
		list.add("KLAC");
		list.add("KMB");
		list.add("KMI");
		list.add("KMX");
		list.add("KO");
		list.add("KR");
		list.add("KRFT");
		list.add("KSS");
		list.add("L");
		list.add("LEG");
		list.add("LEN");
		list.add("LH");
		list.add("LIFE");
		list.add("LLL");
		list.add("LLTC");
		list.add("LLY");
		list.add("LM");
		list.add("LMT");
		list.add("LNC");
		list.add("LO");
		list.add("LOW");
		list.add("LRCX");
		list.add("LSI");
		list.add("LTD");
		list.add("LUK");
		list.add("LUV");
		list.add("LYB");
		list.add("M");
		list.add("MA");
		list.add("MAR");
		list.add("MAS");
		list.add("MAT");
		list.add("MCD");
		list.add("MCHP");
		list.add("MCK");
		list.add("MCO");
		list.add("MDLZ");
		list.add("MDT");
		list.add("MET");
		list.add("MHP");
		list.add("MJN");
		list.add("MKC");
		list.add("MMC");
		list.add("MMM");
		list.add("MNST");
		list.add("MO");
		list.add("MOLX");
		list.add("MON");
		list.add("MOS");
		list.add("MPC");
		list.add("MRK");
		list.add("MRO");
		list.add("MS");
		list.add("MSFT");
		list.add("MSI");
		list.add("MTB");
		list.add("MU");
		list.add("MUR");
		list.add("MWV");
		list.add("MYL");
		list.add("NBL");
		list.add("NBR");
		list.add("NDAQ");
		list.add("NE");
		list.add("NEE");
		list.add("NEM");
		list.add("NFLX");
		list.add("NFX");
		list.add("NI");
		list.add("NKE");
		list.add("NOC");
		list.add("NOV");
		list.add("NRG");
		list.add("NSC");
		list.add("NTAP");
		list.add("NTRS");
		list.add("NU");
		list.add("NUE");
		list.add("NVDA");
		list.add("NWL");
		list.add("NWSA");
		list.add("NYX");
		list.add("OI");
		list.add("OKE");
		list.add("OMC");
		list.add("ORCL");
		list.add("ORLY");
		list.add("OXY");
		list.add("PAYX");
		list.add("PBCT");
		list.add("PBI");
		list.add("PCAR");
		list.add("PCG");
		list.add("PCL");
		list.add("PCLN");
		list.add("PCP");
		list.add("PCS");
		list.add("PDCO");
		list.add("PEG");
		list.add("PEP");
		list.add("PETM");
		list.add("PFE");
		list.add("PFG");
		list.add("PG");
		list.add("PGR");
		list.add("PH");
		list.add("PHM");
		list.add("PKI");
		list.add("PLD");
		list.add("PLL");
		list.add("PM");
		list.add("PNC");
		list.add("PNR");
		list.add("PNW");
		list.add("POM");
		list.add("PPG");
		list.add("PPL");
		list.add("PRGO");
		list.add("PRU");
		list.add("PSA");
		list.add("PSX");
		list.add("PWR");
		list.add("PX");
		list.add("PXD");
		list.add("QCOM");
		list.add("QEP");
		list.add("R");
		list.add("RAI");
		list.add("RDC");
		list.add("RF");
		list.add("RHI");
		list.add("RHT");
		list.add("RL");
		list.add("ROK");
		list.add("ROP");
		list.add("ROST");
		list.add("RRC");
		list.add("RRD");
		list.add("RSG");
		list.add("RTN");
		list.add("S");
		list.add("SAI");
		list.add("SBUX");
		list.add("SCG");
		list.add("SCHW");
		list.add("SE");
		list.add("SEE");
		list.add("SHW");
		list.add("SIAL");
		list.add("SJM");
		list.add("SLB");
		list.add("SLM");
		list.add("SNA");
		list.add("SNDK");
		list.add("SNI");
		list.add("SO");
		list.add("SPG");
		list.add("SPLS");
		list.add("SRCL");
		list.add("SRE");
		list.add("STI");
		list.add("STJ");
		list.add("STT");
		list.add("STX");
		list.add("STZ");
		list.add("SWK");
		list.add("SWN");
		list.add("SWY");
		list.add("SYK");
		list.add("SYMC");
		list.add("SYY");
		list.add("T");
		list.add("TAP");
		list.add("TDC");
		list.add("TE");
		list.add("TEG");
		list.add("TEL");
		list.add("TER");
		list.add("TGT");
		list.add("THC");
		list.add("TIE");
		list.add("TIF");
		list.add("TJX");
		list.add("TMK");
		list.add("TMO");
		list.add("TRIP");
		list.add("TROW");
		list.add("TRV");
		list.add("TSN");
		list.add("TSO");
		list.add("TSS");
		list.add("TWC");
		list.add("TWX");
		list.add("TXN");
		list.add("TXT");
		list.add("TYC");
		list.add("UNH");
		list.add("UNM");
		list.add("UNP");
		list.add("UPS");
		list.add("URBN");
		list.add("USB");
		list.add("UTX");
		list.add("V");
		list.add("VAR");
		list.add("VFC");
		list.add("VIAB");
		list.add("VLO");
		list.add("VMC");
		list.add("VNO");
		list.add("VRSN");
		list.add("VTR");
		list.add("VZ");
		list.add("WAG");
		list.add("WAT");
		list.add("WDC");
		list.add("WEC");
		list.add("WFC");
		list.add("WFM");
		list.add("WHR");
		list.add("WIN");
		list.add("WLP");
		list.add("WM");
		list.add("WMB");
		list.add("WMT");
		list.add("WPI");
		list.add("WPO");
		list.add("WPX");
		list.add("WU");
		list.add("WY");
		list.add("WYN");
		list.add("WYNN");
		list.add("X");
		list.add("XEL");
		list.add("XL");
		list.add("XLNX");
		list.add("XOM");
		list.add("XRAY");
		list.add("XRX");
		list.add("XYL");
		list.add("YHOO");
		list.add("YUM");
		list.add("ZION");
		list.add("ZMH");
		
		return list;
	}

	
	
}
