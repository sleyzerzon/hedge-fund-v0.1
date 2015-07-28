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
import com.onenow.constant.StreamName;
import com.onenow.constant.DataType;
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
		
//		Watchr.log(Level.INFO, "Kinesis map: " + kinesisMap.toString());
//		Watchr.log(Level.INFO, "Kinesis region map: " + kinesisRegion.toString());

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

	// CRITICAL PATH
	public static void writeThreadActivityThroughRing(final EventActivity event) {
		new Thread () {
			@Override public void run () {
				try {
					// TODO: FAST WRITE TO RING		
					BusSystem.write(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void write(EventActivity activityToSend) {
		
		StreamName streamName = getStreamName(activityToSend.streamingData);
		Watchr.log(Level.INFO, "Cache writing into Stream " + "<" + streamName + ">" + " OBJECT: " + activityToSend.toString());

		boolean success = false;
		int maxTries = 3;
		
		int tries = 0;
		while(!success) {
			try {
				tries++;
				success = true;
				// TODO: the stream is required to exist ahead of time
				//createStreamIfNotExists(streamName);
				getKinesis().sendObject(activityToSend, streamName);
			} catch (Exception e) {
				success = false;
				// SERVICE: AMAZONKINESIS; STATUS CODE: 400; ERROR CODE: LIMITEXCEEDEDEXCEPTION
				if(tries>maxTries) {
					Watchr.log(Level.SEVERE, "Write to Kinesis failed after re-tries: <" + streamName + "> FOR " + activityToSend.toString());
					return;
				}
				e.printStackTrace();
			}
		}
	}
			
	// http://blogs.aws.amazon.com/bigdata/blog/author/Ian+Meyers
	// http://docs.aws.amazon.com/general/latest/gr/rande.html
	public static boolean read(		StreamName streamName, IRecordProcessorFactory recordProcessorFactory,
									InitialPositionInStream initPosition) {
				
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
		
		Worker recordProcessor = new Worker(recordProcessorFactory, readClientConfig);
		Watchr.log(Level.INFO, 	"Created kinesis read worker: " + recordProcessor.toString() + " " + 
								"for kinesis: " + getKinesis());
		
		// TODO: start only when the stream is created & active
		// isActive(kinesis.describeStream(streamName)
		runProcessor(recordProcessor);
		
        return true;

	}
	
	// ALL
	public static void readAllStreams(InitialPositionInStream initialPosition) {
		BusSystem.readPriceHistory(initialPosition);
		BusSystem.readGreekHistory(initialPosition);
		BusSystem.readPriceSizeRealtime(initialPosition);
		BusSystem.readPriceStreaming(initialPosition);
		BusSystem.readSizeStreaming(initialPosition);
		BusSystem.readGreekStreaming(initialPosition);
		BusSystem.readVolatilityStreaming(initialPosition);
		BusSystem.readGenericStreaming(initialPosition);
	}

	// HISTORY
	public static void readPriceHistory(InitialPositionInStream initialPosition) {
		readStreamingData(DataType.PRICE_HIST, initialPosition);		
	}

	public static void readGreekHistory(InitialPositionInStream initialPosition) {
		readStreamingData(DataType.GREEK_HIST, initialPosition);		
	}

	// REALTIME
	public static void readPriceSizeRealtime(InitialPositionInStream initialPosition) {
		readStreamingData(DataType.PRICESIZE_RT, initialPosition);		
	}

	// STREAMING
	public static void readPriceStreaming(InitialPositionInStream initialPosition) {
		readStreamingData(DataType.PRICE_STREAM, initialPosition);		
	}

	public static void readSizeStreaming(InitialPositionInStream initialPosition) {
		readStreamingData(DataType.SIZE_STREAM, initialPosition);		
	}
	
	public static void readGreekStreaming(InitialPositionInStream initialPosition) {
		readStreamingData(DataType.GREEK_STREAM, initialPosition);		
	}

	public static void readVolatilityStreaming(InitialPositionInStream initialPosition) {
		readStreamingData(DataType.VOLATILITY_STREAM, initialPosition);		
	}

	public static void readGenericStreaming(InitialPositionInStream initialPosition) {
		readStreamingData(DataType.GENERIC_STREAM, initialPosition);		
	}
	
	
	private static void readStreamingData(	final DataType streamingData, 
											final InitialPositionInStream initialPosition) {	
		new Thread () {
			@Override public void run () {
				try {
					Watchr.info("Will read <" + streamingData + "> from " + initialPosition.toString());
					BusSystem.read(	getStreamName(streamingData), 
									getRecordProcessorFactory(streamingData),
									initialPosition);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

	}
	
	private static IRecordProcessorFactory getRecordProcessorFactory(DataType streamingData) {
		IRecordProcessorFactory factory = null;
		StreamName streamName = getStreamName(streamingData);

		// HISTORY
		if(streamName.equals(StreamName.PRICE_HISTORY_STAGING)) {
			factory = BusProcessingFactory.createProcessorFactoryEventPriceHistory(streamName);
		}
		if(streamName.equals(StreamName.GREEK_HISTORY_STAGING)) {
			factory = BusProcessingFactory.createProcessorFactoryEventGreekHistory(streamName);
		}
		// REALTIME
		if(streamName.equals(StreamName.PRICESIZE_REALTIME_STAGING)) {
			factory = BusProcessingFactory.createProcessorFactoryEventPriceSizeRealtime(streamName);
		}
		// STREAMING
		if(streamName.equals(StreamName.PRICE_STREAMING_STAGING)) {
			factory = BusProcessingFactory.createProcessorFactoryEventPriceStreaming(streamName);
		}
		if(streamName.equals(StreamName.SIZE_STREAMING_STAGING)) {
			factory = BusProcessingFactory.createProcessorFactoryEventSizeStreaming(streamName);
		}
		if(streamName.equals(StreamName.GREEK_STREAMING_STAGING)) {
			factory = BusProcessingFactory.createProcessorFactoryEventGreekStreaming(streamName);
		}
		if(streamName.equals(StreamName.GENERIC_STREAMING_STAGING)) {
			factory = BusProcessingFactory.createProcessorFactoryEventGenericStreaming(streamName);
		}
		
		return factory;
	}
	
	// TODO: break down non-mac into staging / production
    public static StreamName getStreamName(DataType key) {
    	
    	StreamName stream = null;

		// HISTORY
		if(key.equals(DataType.PRICE_HIST)) {
				if(!NetworkConfig.isMac()) {
					stream = StreamName.PRICE_HISTORY_STAGING;		
				}
		}

		if(key.equals(DataType.GREEK_HIST)) {
			if(!NetworkConfig.isMac()) {
				stream = StreamName.GREEK_HISTORY_STAGING;
			}
		}

		// REALTIME
		if(key.equals(DataType.PRICESIZE_RT)) {
				if(!NetworkConfig.isMac()) {
					stream = StreamName.PRICESIZE_REALTIME_STAGING;
				}
		}

		if(key.equals(DataType.PRICE_STREAM)) {
			if(!NetworkConfig.isMac()) {
				stream = StreamName.PRICE_STREAMING_STAGING;
			}
		}

		if(key.equals(DataType.SIZE_STREAM)) {
			if(!NetworkConfig.isMac()) {
				stream = StreamName.SIZE_STREAMING_STAGING;
			}
		}

		if(key.equals(DataType.GREEK_STREAM)) {
			if(!NetworkConfig.isMac()) {
				stream = StreamName.GREEK_STREAMING_STAGING;
			}
		}

		if(key.equals(DataType.VOLATILITY_STREAM)) {
			if(!NetworkConfig.isMac()) {
				stream = StreamName.VOLATILITY_STREAMING_STAGING;
			}
		}

		if(key.equals(DataType.GENERIC_STREAM)) {
			if(!NetworkConfig.isMac()) {
				stream = StreamName.GENERIC_STREAMING_STAGING;
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
