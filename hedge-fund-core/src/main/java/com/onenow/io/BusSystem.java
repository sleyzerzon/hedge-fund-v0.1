package com.onenow.io;

import java.util.HashMap;
import java.util.logging.Level;

import com.amazonaws.regions.Region;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;
import com.onenow.admin.InitAmazon;
import com.onenow.admin.NetworkConfig;
import com.onenow.constant.PriceType;
import com.onenow.constant.StreamName;
import com.onenow.data.EventActivity;
import com.onenow.io.Lookup;

public class BusSystem {

	private static HashMap<String, Kinesis> kinesisMap = new HashMap<String, Kinesis>();
	private static HashMap<Kinesis, Region> kinesisRegion = new HashMap<Kinesis, Region>();


	public BusSystem() {
		
	}
			
	// KINESIS
	public static Kinesis getKinesis() {
		
		Region region = InitAmazon.defaultRegion;
		
		return getKinesis(region);
	}
	
	public static Kinesis getKinesis(Region region) {
				
		Kinesis kin = null;
		
		String key = Lookup.getKinesisKey(region);
		
		if(kinesisMap.get(key)==null) {
			
			kin = new Kinesis(region);
			kinesisMap.put(key, kin);
			kinesisRegion.put(kin, region);
			
		} else {
			kin = kinesisMap.get(key);
		}
		
		Watchr.log(Level.INFO, "Kinesis map: " + kinesisMap.toString());
		Watchr.log(Level.INFO, "Kinesis region map: " + kinesisRegion.toString());

		return kin;
	}
	
	public static Region getRegion(Kinesis kinesis) {
		Region region = null;
		
		region = kinesisRegion.get(kinesis);
		
		if(region==null){
			region = InitAmazon.defaultRegion; 
		}
		
		// Watchr.log(Level.INFO, "Kinesis region is: " + region);
		
		return region;
	}
	
	private static boolean createStreamIfNotExists(StreamName name) {
		
		getKinesis().createStreamIfNotExists(name, getNumShards(name));
		
		return true;
	}

	/** 
	 * Set right number of shards for a given StreamName
	 * @param name
	 * @return
	 */
	private static int getNumShards(StreamName name) {
		Integer shards = 1;
//		if(name.equals(StreamName.REALTIME)) {
//			shards = 2;
//		}
		return shards;
	}

	public static boolean createStream(StreamName streamName, Integer numShards) {

		getKinesis().createStreamIfNotExists(streamName, numShards);
		
		return true;
	}
	
	public static boolean writeToBusIndefnitely(StreamName streamName) {
		String s = "Hola World! ";
		Integer i=0;
		while(true) {
			s = s + i;
			// TODO validate / create the stream?
			try {
				getKinesis().sendObject((Object) s, streamName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			TimeParser.sleep(1);
			i++;
		}
		
//		return true;
	}

	public static void write(EventActivity activityToSend, StreamName streamName) {
		
		boolean success = false;
		int maxTries = 3;
		
		int tries = 0;
		while(!success) {
			try {
				tries++;
				success = true;
				validateStream(streamName);
				createStreamIfNotExists(streamName);
				getKinesis().sendObject(activityToSend, streamName);
			} catch (Exception e) {
				success = false;
				// SERVICE: AMAZONKINESIS; STATUS CODE: 400; ERROR CODE: LIMITEXCEEDEDEXCEPTION
				if(tries>maxTries) {
					Watchr.log(Level.SEVERE, "Write to Kinesis failed after re-tries");
					return;
				}
			}
		}
	}
			
	// http://blogs.aws.amazon.com/bigdata/blog/author/Ian+Meyers
	// http://docs.aws.amazon.com/general/latest/gr/rande.html
	public static boolean read(		StreamName streamName, IRecordProcessorFactory recordProcessorFactory,
									InitialPositionInStream initPosition) {
		
		validateStream(streamName);
		
		String applicationName = "Investor" + "-" + streamName;
		Long workerId = TimeParser.getTimeMilisecondsNow();  //  String workerId = String.valueOf(UUID.randomUUID());

       
		KinesisClientLibConfiguration readClientConfig = null;
		
		readClientConfig = new KinesisClientLibConfiguration(	applicationName, 
																streamName.toString(), 
																InitAmazon.getCredentialsProvider(), 
																workerId.toString());
		
		// http://docs.aws.amazon.com/kinesis/latest/APIReference/API_GetShardIterator.html
		readClientConfig.withCommonClientConfig(InitAmazon.getClientConfig());
		readClientConfig.withRegionName(InitAmazon.defaultRegion.getName());
		readClientConfig.withInitialPositionInStream(initPosition);		
		
		Worker kinesysWorker = new Worker(recordProcessorFactory, readClientConfig);
		Watchr.log(Level.INFO, 	"Created kinesis read worker: " + kinesysWorker.toString() + " " + 
								"for kinesis: " + getKinesis());
		
		// TODO: start only when the stream is created & active
		// isActive(kinesis.describeStream(streamName)
		runProcessor(kinesysWorker);
		
        return true;

	}

	private static void validateStream(StreamName streamName) {
		if(	!isPrimaryStream(streamName) && 
			!isStandbyStream(streamName) &&
			!isRealtimeStream(streamName) &&
			!isHistoryStream(streamName) && 
			!isStreamingStream(streamName)) {
			Watchr.log(Level.SEVERE, "Invalid stream name in kinesis bus read");
		}
	}
	
	public static boolean isPrimaryStream(StreamName name) {
		if (name.equals(StreamName.PRIMARY_STAGING) || name.equals(StreamName.PRIMARY_PRODUCTION) || name.equals(StreamName.PRIMARY_DEVELOPMENT)) {
			return true;
		}
		return false;
	}

	public static boolean isStandbyStream(StreamName name) {
		if (name.equals(StreamName.STANDBY_STAGING) || name.equals(StreamName.STANDBY_PRODUCTION) || name.equals(StreamName.STANDBY_DEVELOPMENT)) {
			return true;
		}
		return false;
	}

	public static boolean isRealtimeStream(StreamName name) {
		if (name.equals(StreamName.REALTIME_STAGING) || name.equals(StreamName.REALTIME_PRODUCTION) || name.equals(StreamName.REALTIME_DEVELOPMENT)) {
			return true;
		}
		return false;
	}
	
	public static boolean isHistoryStream(StreamName name) {
		if (name.equals(StreamName.HISTORY_STAGING) || name.equals(StreamName.HISTORY_PRODUCTION) || name.equals(StreamName.HISTORY_DEVELOPMENT)) {
			return true;
		}
		return false;
	}

	public static boolean isStreamingStream(StreamName name) {
		if (name.equals(StreamName.STREAMING_STAGING) || name.equals(StreamName.STREAMING_PRODUCTION) || name.equals(StreamName.STREAMING_DEVELOPMENT)) {
			return true;
		}
		return false;
	}

	// TODO: break down non-mac into staging / production
    public static StreamName getStreamName(String key) {
    	
    	StreamName stream = null;
    	
		if(key.equals("PRIMARY")) {
			stream = StreamName.PRIMARY_DEVELOPMENT; 
			if(!NetworkConfig.isMac()) {
				stream = StreamName.PRIMARY_STAGING;
			}
		}
		if(key.equals("STANDBY")) {
			stream = StreamName.STANDBY_DEVELOPMENT;
				if(!NetworkConfig.isMac()) {
					stream = StreamName.STANDBY_STAGING;
				}
		}
		if(key.equals("REALTIME")) {
			stream = StreamName.REALTIME_DEVELOPMENT;
					if(!NetworkConfig.isMac()) {
						stream = StreamName.REALTIME_STAGING;
					}
		}
		if(key.equals("HISTORY")) {
			stream = StreamName.HISTORY_DEVELOPMENT;
					if(!NetworkConfig.isMac()) {
						stream = StreamName.HISTORY_STAGING;
					}
		}
		if(key.equals("STREAMING")) {
			stream = StreamName.STREAMING_DEVELOPMENT;
					if(!NetworkConfig.isMac()) {
						stream = StreamName.STREAMING_STAGING;
					}
		}
		
		Watchr.log(Level.INFO, "The chosen stream name: " + stream);
		
		if(stream==null) {
			Watchr.log(Level.SEVERE, "Invalid Key to StreamName");
		}
		
		return stream;

    }

	private static boolean runProcessor(Worker kinesysWorker) {
		try {
			Watchr.log(Level.INFO, "Running kinesis read worker: " + kinesysWorker.toString());			
            kinesysWorker.run();
        } catch (Throwable t) {
        	String log = "Caught throwable while processing data." + t;
        	Watchr.log(Level.SEVERE, log);
            return false;
        }        
		return true;
	}
}
