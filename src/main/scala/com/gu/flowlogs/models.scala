package com.gu.flowlogs


case class FullFlowLog(
  version: Int,
  accountId: String,
  interfaceId: String,
  srcaddr: String,
  dstaddr: String,
  srcport: Int,
  dstport: Int,
  protocol: Int,
  packets: Long,
  bytes: Long,
  start: Long,
  end: Long,
  action: String,
  logStatus: String,

  // these fields are only used in in different format versions
  //  vpcId: String,
  //  subnetId: String,
  //  instanceId: String,
  //  tcpFlags: Int,
  //  trafficType: String,
  //  pktSrcaddr: String,
  //  pktDstaddr: String,
  //  region: String,
  //  azId: String,
  //  sublocationType: String,
  //  sublocationId: String,
  //  pktSrcAwsService: String,
  //  pktDstAwsService: String,
  //  flowDirection: String,
  //  trafficPath: Int,
)
