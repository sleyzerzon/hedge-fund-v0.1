package com.onenow.summit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeSpotInstanceRequestsRequest;
import com.amazonaws.services.ec2.model.DescribeSpotInstanceRequestsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.SpotInstanceRequest;
import com.amazonaws.services.ec2.model.StateReason;
import com.amazonaws.services.elasticmapreduce.model.DescribeClusterRequest;
import com.amazonaws.services.elasticmapreduce.model.DescribeClusterResult;
import com.amazonaws.services.elasticmapreduce.model.Instance;
import com.amazonaws.services.elasticmapreduce.model.InstanceGroup;
import com.amazonaws.services.elasticmapreduce.model.InstanceStatus;
import com.amazonaws.services.elasticmapreduce.model.InstanceTimeline;
import com.onenow.summit.AwsPricing.RegionPricing;
import com.onenow.summit.AwsPricing.Size;

public class SpendPoller {

	public SpendPoller() {
		// TODO Auto-generated constructor stub
	}

	public static void addTick(List<Tick> ticks, Tick tick) throws IOException {
		ticks.add(tick);
		Map<String, String> tags = new HashMap<String, String>();
		tags.put("user", tick.getUserId());
		tags.put("region", tick.getRegion());
		tags.put("zone", tick.getAz());
		tags.put("cluster", tick.getClusterId());
		tags.put("inst_type", tick.getInstType().replace(".", "_"));
		tags.put("inst_id", tick.getInstId());
		tags.put("price_type", tick.getPriceType());
		OpenTsdbConnection.getInstance().send(
				tick.getTime().toInstant().getMillis(), "spent",
				tick.getCost(), tags);

	}

	public static void main(String[] a) throws Exception {
		YakConfig config = Utils.readConfig();
		OpenTsdbConnection.getInstance().connect(config.getOpenTSDBHost(),
				config.getOpenTSDBPort());

		//AccountCloud acct = config.getAccounts().get(0).getCloud();
		AwsPricing awsPricing = Yak.readPricing();
		String access = "";// acct.getAccess();
		String secret = "";//acct.getSecret();
		Logger.log("Logging in with " + access + " and " + secret);
		EmrFacade emr = new EmrFacade(access, secret);
		String[] clusters = new String[] { "j-3HRPLTDZCE2C7", "j-3EQKELQA7E8YQ" };

		while (true) {
			DateTime now = new DateTime();
			for (String cluster : clusters) {
				Map<String, List<Tick>> ticks = new HashMap<String, List<Tick>>();
				emr.setClusterId(cluster);
				DescribeClusterResult descCluRes = emr.getEmr()
						.describeCluster(
								new DescribeClusterRequest()
										.withClusterId(cluster));
				String az = descCluRes.getCluster().getEc2InstanceAttributes()
						.getEc2AvailabilityZone();
				String region = "us-east-1";
				RegionPricing regionPricing = awsPricing.getConfig()
						.findRegion(region);
				Logger.log("Looking at " + descCluRes.getCluster().getName()
						+ " (" + cluster + ")");

				List<Instance> instances = emr.getTaskInstances();
				InstanceGroup taskGroup = emr.getTaskGroup();
				String tgState = taskGroup.getStatus().getState();

				boolean spot = taskGroup.getMarket().equalsIgnoreCase("SPOT");
				String instType = taskGroup.getInstanceType();
				String state = "unknown";
				String resInstReasonCode = "";
				for (Instance inst : instances) {
					InstanceStatus status = inst.getStatus();
					InstanceTimeline timeline = status.getTimeline();
					DateTime startTime = new DateTime(
							timeline.getCreationDateTime());
					DateTime endTime = new DateTime(timeline.getEndDateTime());
					String ec2Id = inst.getEc2InstanceId();
					Logger.log("Processing " + ec2Id);
					Filter idFilter = new Filter().withName("instance-id")
							.withValues(ec2Id);

					DescribeInstancesResult descInstRes = emr.getEC2Client()
							.describeInstances(
									new DescribeInstancesRequest()
											.withFilters(idFilter));
					List<Reservation> reservations = descInstRes
							.getReservations();

					com.amazonaws.services.ec2.model.Instance resInst = null;
					if (reservations.size() > 0) {
						Reservation res = reservations.get(0);
						List<com.amazonaws.services.ec2.model.Instance> resInstances = res
								.getInstances();
						if (reservations.size() > 1 || resInstances.size() > 1) {
							throw new RuntimeException(
									"Unexpected Spanish Inquisition");
						}
						resInst = resInstances.get(0);

						StateReason stateReason = resInst.getStateReason();
						if (stateReason != null) {
							Logger.log(stateReason.getMessage());
						}
						if (resInst != null) {
							state = resInst.getState().getName();
							if (resInst.getStateReason() != null) {
								resInstReasonCode = resInst.getStateReason()
										.getCode();
							}
						}
					}
					List<Tick> curTicks = ticks.get(ec2Id);
					if (curTicks == null) {
						curTicks = new ArrayList<Tick>();
						ticks.put(ec2Id, curTicks);
					}
					Tick lastTick = null;
					if (curTicks.size() > 0) {
						lastTick = curTicks.get(curTicks.size() - 1);
					}

					if (!spot) {
						DateTime tickTime = startTime;
						Size size = regionPricing.findSize(instType);
						String cost = size.getValueColumns().get(0).getPrices()
								.getUsd();

						Logger.log("State: " + state);
						if (curTicks.size() == 0) {
							addTick(curTicks, new Tick("onenow", cluster,
									region, az, ec2Id, instType, cost, false,
									tickTime, "ondemand"));
						}
						while (true) {
							DateTime lastTime = curTicks.get(
									curTicks.size() - 1).getTime();
							DateTime nextTime = lastTime.plusHours(1);
							if ((state.equals("terminated") || state
									.equals("unknown"))
									&& nextTime.isAfter(endTime)) {
								break;
							}
							switch (state) {
							case "terminated":
							case "unknown":
							case "running":
								break;
							default:
								Logger.log(state);
							}
							if (nextTime.isBefore(now)) {
								addTick(curTicks, new Tick("onenow", cluster,
										region, az, ec2Id, instType, cost,
										false, nextTime, "ondemand"));
							} else {
								break;
							}
						}
					} else {

						Logger.log("Instance "
								+ inst.getId()
								+ ": "
								+ inst.getStatus().getState()
								+ "; Reservation: "
								+ (resInst == null ? "[]" : resInst
										.getInstanceType()));
						if (resInst == null) {

							switch (tgState) {
							case "PROVISIONING":
							case "RESIZING":
								break;
							// case "RUNNING":
							// throw new RuntimeException("WTF state is "
							// + tgState + " but no resInst???");
							default:
								Logger.log(taskGroup.getStatus().getState());
							}

							continue;
						}
						Logger.log("Instance " + inst.getId() + ": "
								+ inst.getStatus().getState());
						switch (inst.getStatus().getState()) {

						case "PROVISIONING":
						case "RESIZING":
							break;
						case "RUNNING":
							break;
						case "TERMINATED":
							break;
						default:
							break;
						}

						String spotRequestId = resInst
								.getSpotInstanceRequestId();
						DescribeSpotInstanceRequestsRequest descSpotInstReq = new DescribeSpotInstanceRequestsRequest()
								.withSpotInstanceRequestIds(spotRequestId);
						DescribeSpotInstanceRequestsResult descSpotInstReqRes = emr
								.getEC2Client().describeSpotInstanceRequests(
										descSpotInstReq);
						List<SpotInstanceRequest> spotInstReqs = descSpotInstReqRes
								.getSpotInstanceRequests();
						for (SpotInstanceRequest spotInstReq : spotInstReqs) {
							String code = spotInstReq.getStatus().getCode();
							if (code.equals("fulfilled")) {
								DateTime tickTime = startTime;
								Size size = regionPricing.findSize(instType);

								while (true) {
									DateTime lastTime = startTime;
									if (curTicks.size() > 0) {
										lastTime = curTicks.get(
												curTicks.size() - 1).getTime();
									}
									DateTime nextTime = lastTime.plusHours(1);
									if (state.equals("terminated")
											&& nextTime.isAfter(endTime)) {
										break;
									}
									String cost = spotInstReq.getSpotPrice();
									if (nextTime.isBefore(now)) {
										addTick(curTicks, new Tick("onenow",
												cluster, region, az, ec2Id,
												instType, cost, false,
												nextTime, "spot"));
									} else {
										break;
									}
								}
							} else {
								Logger.log("SPOT CODE: " + code);
							}

						}
						Logger.log("Ticks for " + cluster + ": " + ticks);
					}

				}
				// InstanceGroup instGrp = emr.getTaskGroup();
				//
				// InstanceGroupTimeline instGrpTimeline = instGrp.getStatus()
				// .getTimeline();
				// Date created = instGrpTimeline.getCreationDateTime();
				// Date destroyed = instGrpTimeline.getEndDateTime();

				Thread.sleep(1);
			}
		}
	}
}
