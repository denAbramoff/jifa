/********************************************************************************
 * Copyright (c) 2023 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/
export default {
  gclogFile: 'GC Log File',
  navigation: 'Navigation',
  navToTop: 'Top',
  gcCause: 'GC Cause',
  clickToChooseTime: 'Click To Custom',
  gclogFilePlaceholder:
    'Fill with the URL of analysis page of the file. To choose the time range of analysis, please config it in the page of that file in advance. You can compare two time ranges of one GC LOG.',
  stwTooltip: 'The Java program stops the world in this phase',
  analysisConfig: 'Analysis Config',
  gclogCompare: 'Compare GC Logs',
  logTimeRange: 'Time Range Covered By The Log',
  analysisTimeRange: 'Current Analysis Time Range',
  duration: 'Duration',
  analysisTimeRangeChooseHint: 'Choose a time range you are concerned with to analyze',
  analysisTimeRangeLength: 'Duration of analysis time range',
  baselineFile: 'Baseline Log',
  targetFile: 'Target Log',
  metric: 'Metric',
  metricValueOfBaseline: 'Baseline Value',
  metricValueOfTarget: 'Target Value',
  metricValueDifference: 'Difference(%)',
  metricCompare:
    "Target VS BaseLine (<font color='#9ad969'>Green</font> means better. <font color='#E74C3C'>Red</font> means worse)",
  betterPerformance: 'Better Performance',
  worsePerformance: 'Worse Performance',
  parallelGCThreads: 'Parallel GC Threads',
  parallelGCThreadsHint:
    'Parallel GC threads(correspond to option -XX:ParallelGCThreads) deals with work in pause phases, Increasing parallel gc threads number may decrease pause time. Remember not to set it more than the number of CPUs.',
  concurrentGCThreads: 'Concurrent GC Threads',
  concurrentGCThreadsHint:
    'Concurrent GC threads(correspond to option-XX:ConcGCThreads) deals with work in current phases. Increasing concurrent gc threads may accelerate concurrent gc work but increase cpu ',
  gcDetail: 'GC Details',
  applyTimeToConfig: 'Apply current time range of graph to the whole page',
  noDatestampPreunified:
    'Datestamp is not printed in the log so we have to choose time based on the uptime，It is recommended to add option -XX:+PrintGCDateStamps to print datestamp.',
  noDatestampUnified:
    'Datestamp is not printed in the log so we have to choose time based on the uptime. It is recommended to set gc log printing option like "-Xlog:gc*:file=gc.log:time,uptime,level,tags" (add "time" to decoration) to print datestamp..',
  longPauseThreshold: 'Long Pause Threshold(ms)',
  longPauseThresholdHint: 'Pause phases with duration longer than this will be seen as long pause',
  youngGCFrequentIntervalThreshold: 'Young GC Interval Threshold(ms)',
  youngGCFrequentIntervalThresholdHint:
    'Young GC will be seen as frequent if the interval of two adjacent Young GC is shorter than this time.',
  oldGCFrequentIntervalThreshold: 'Old GC Interval Threshold(ms)',
  oldGCFrequentIntervalThresholdHint:
    'Old GC will be seen as frequent if the interval of two adjacent Old GC is shorter than this time.',
  fullGCFrequentIntervalThreshold: 'Full GC Interval Threshold(ms)',
  fullGCFrequentIntervalThresholdHint:
    'Full GC will be seen as frequent if the interval of two adjacent Full GC is shorter than this time.',
  fullGCForNongenerational: 'All gcs are regarded as Full GC because {gc} is not generational.',

  basicInfo: 'Basic Information',
  collector: 'Collector',
  objectStats: 'Object Statistics',
  objectCreationSpeed: 'Object Creation Speed',
  objectPromotionSpeed: 'Object Promotion Speed',
  objectPromotionAvg: 'Average Object Promotion',
  objectPromotionMax: 'Max Object Promotion',

  pauseInfo: {
    pauseInfo: 'Pause Info',
    pauseDistribution: 'Pause Time Distribution',
    throughput: 'GC Throughput',
    throughputHint: 'GC throughput is time spend in real transaction/total time. Higher gc throughput means lower gc overhead.',
    pauseAvg: 'Average Pause',
    pauseMax: 'Max Pause',
    pauseMedian: 'Median Pause',
    pauseP99: 'Pause P99',
    pauseP999: 'Pause P999',
    pauseTime: 'Pause Time',
    pauseCount: 'Pause Count'
  },

  memoryStats: {
    memoryStats: 'Heap And Metaspace',
    memoryArea: 'Memory Area',
    capacityAvg: 'Avg Capacity',
    capacityAvgHint:
      'Capacity of an area is total size of objects can be allocated in this area. Average capacity may be smaller than max used because capacity can be dynamic. For example, when -Xmx!=-Xms, the heap may shrink or expand and young and generation will shrink or expanding accordingly.',
    g1DynamicCapacity:
      'In addition, for G1 even if -Xmx=-Xms, if -Xmn is not set(and -Xmn is not recommended to set), capacity of young and old generation are dynamic.',
    usedMax: 'Max Used',
    usedMaxHint:
      'Can be used to evaluate the RSS of the Java process. The RSS of heap is close to the max used memory of heap.',
    usedAvgAfterFullGC: 'Avg Used After Full GC',
    usedAvgAfterFullGCHint:
      'Can be used to evaluate the live set of this area. A high value could mean a memory leak.',
    usedAvgAfterOldGC: 'Avg Used After Old GC',
    usedAvgAfterOldGCHint:
      'Can be used to evaluate the live set of this area. A high value could mean a memory leak.',
    metaspaceCapacity:
      'Notice that information printed in original gc log like "Metaspace: 1792694K->291615K(698368K)" means the reserve space of metaspace is 698368K rather than capacity of metaspace.',
    youngCapacityAvg: 'Young Gen Avg Capacity',
    youngUsedMax: 'Old Gen Max Used',
    oldCapacityAvg: 'Old Gen Avg Capacity',
    oldUsedMax: 'Old Gen Max Used',
    oldUsedAvgAfterFullGC: 'Old Gen Avg Used After Full GC',
    oldUsedAvgAfterOldGC: 'Old Gen Avg Used After Old GC',
    humongousUsedMax: 'Humongous Max Used',
    humongousUsedAvgAfterFullGC: 'Humongous Avg Used After Full GC',
    humongousUsedAvgAfterOldGC: 'Humongous Avg Used After Old GC',
    heapCapacityAvg: 'Heap Avg Capacity',
    heapUsedMax: 'Heap Max Used',
    heapUsedAvgAfterFullGC: 'Heap Avg Used After Full GC',
    heapUsedAvgAfterOldGC: 'Heap Avg Used After Old GC',
    metaspaceCapacityAvg: 'Metaspace Avg Capacity',
    metaspaceUsedMax: 'Metaspace Max Used',
    metaspaceUsedAvgAfterFullGC: 'Metaspace Avg Used After Full GC',
    metaspaceUsedAvgAfterOldGC: 'Metaspace Avg Used After Old GC'
  },

  phaseStats: {
    phaseStatsAndCause: 'Phase And Cause',
    phaseStats: 'Phase Statistics',
    importantMode: 'Important Phases Only',
    pauseMode: 'Pause Phases Only',
    structuredMode: 'Display By Phase Hierarchy',
    causeMode: 'Display By GC Cause',
    name: 'Name',
    count: 'Count',
    intervalAvg: 'Avg Interval',
    intervalMin: 'Min Interval',
    durationAvg: 'Avg Duration',
    durationMax: 'Max Duration',
    durationTotal: 'Total Duration',
    youngGCCount: 'Young GC Count',
    youngGCIntervalAvg: 'Young GC Avg Interval',
    youngGCDurationAvg: 'Young GC Avg Duration',
    youngGCDurationMax: 'Young GC Max Duration',
    mixedGCCount: 'Mixed GC Count',
    mixedGCIntervalAvg: 'Mixed GC Avg Interval',
    mixedGCDurationAvg: 'Mixed GC Avg Duration',
    mixedGCDurationMax: 'Mixed GC Max Duration',
    oldGCCount: 'Old GC Count',
    oldGCIntervalAvg: 'Old GC Avg Interval',
    oldGCDurationAvg: 'Old GC Avg Duration',
    oldGCDurationMax: 'Old GC Max Duration',
    fullGCCount: 'Full GC Count',
    fullGCIntervalAvg: 'Full GC Avg Interval',
    fullGCDurationAvg: 'Full GC Avg Duration',
    fullGCDurationMax: 'Full GC Max Duration'
  },

  generation: {
    young: 'Young Gen',
    old: 'Old Gen',
    humongous: 'Humongous Region',
    humongousHint:
      'Humongous objects in G1 refer to objects with size larger than half region size(correspond to option -XX:G1HeapRegionSize). Too many humongous objects may lead to problems and the solution is to enlarge region size or create less humongous object.',
    archive: 'Archive',
    heap: 'Heap',
    metaspace: 'Metaspace'
  },

  vmOptions: {
    vmOptions: 'JVM Options',
    unknown: 'Not reported in the log',
    gcRelatedOptions: 'GC Related Options',
    otherOptions: 'Other Options'
  },

  timeGraph: {
    timeGraph: 'Time Graph',
    fullScreen: 'Toggle full screen display',
    durationOf: '{type} Duration',
    youngCapacity: 'Young Gen Capacity',
    oldUsed: 'Old Gen Used',
    oldCapacity: 'Old Gen Capacity',
    humongousUsed: 'Humongous Used',
    heapUsed: 'Heap Used',
    heapCapacity: 'Heap Capacity',
    metaspaceUsed: 'Metaspace Used',
    metaspaceCapacity: 'Metaspace Capacuty',
    reclamation: 'Reclamation',
    promotion: 'Promotion',
    memory: 'Memory',
    time: 'Time'
  },

  cause: {
    archiveShare: 'Force a full GC with a single thread before writing heap archive regions',
    cmsFinalRemark:
      'If -XX:+CMSScavengeBeforeRemark option is set，a Young GC will be triggered at the beginning of CMS Final Remark phase. This option may help reduce longest pause time of remark.',
    systemgc: 'Triggered when System.gc() or Runtime.getRuntime().gc() is called.',
    jvmti: 'Triggered whenForceGarbageCollection is called using JVMTI.',
    gclocker:
      'When a thread is in JNI critical section and a gc is triggered，GC Locker will prevent this gc from executing and prevent other threads from entering critical section.  When the last thread exits the critical section, GCLocker Initiated GC is triggered. Heavy use of GC Locker may prevent gcs from occurring timely, lead to long pause or even Full GC.',
    heapInspection: 'Triggered by heap inspection operation(such as jmap).',
    heapDump: 'Triggered by heap dump operation.',
    allocationFail: 'Triggered when there is insufficient space to allocate object.',
    metaspace:
      'Full gc is triggered when there is insufficient space in Metaspace. Notice that allocation request in metaspace may fail even if it seems not full because of fragmentation in metaspace.',
    ergonomics: 'Triggered in order to adjust heap size dynamically to meet a specified goal such as minimum pause time or throughput.',
    g1Evacuation: 'Triggered when there is insufficient space to allocate object.',
    humongous: 'Full GC is triggered when there is insufficient space to allocate humongous object.',
    lastDitch:
      'After Metadata GC Threshold GC, JVM will trigger another Full GC and clear soft reference if allocation request in metaspace failed.',
    promotionFail:
      'During young gc，if there is not sufficient space in tenured generation for promotion, this gc will become an expensive Full GC.',
    toSpaceExhausted:
      'To-space exhausted means there is not sufficient region for promotion or as to space during young/mixed gc. This may lead to failure to reclaim space during gc, high object promotion and long gc pause',
    proactive: 'JVM actively triggers a gc to reduce heap usage. Typically triggered when object allocation rate is low.',
    allocationRate: 'Triggered when JVM estimates that heap will be used up according to the current object allocation rate.',
    timer:
      'If -XX:ZCollectionInterval is set，a periodic gc is triggered when no gc has been triggered for such time.',
    allocationStall: 'Triggered when heap is used up.',
    highUsage: 'Triggered when heap usage is higher than a specific percent.',
    warmup:
      'After JVM starts up, if no other gcs are triggered, three gcs will triggered when heap usage is higher than 10%, 20% and 30%.',
    metaspaceClearSoftRef:
      'After Metadata GC Threshold GC, JVM will trigger another Full GC and clear soft reference if allocation request in metaspace failed.',
    g1Periodic:
      'If -XX:G1PeriodicGCInterval is set，a periodic gc is triggered when no gc has been triggered for such time. This feature is typically used to reclaim heap memory.',
    dcmd: 'Full gc triggered by jcmd',
    g1Compaction: 'Triggered when there is insufficient space to allocate object.',
    g1Preventive: 'Triggered to prevent "to-space exhausted".'
  },

  phase: {
    fullGC: 'Full GC collects memory from the entire heap and usually pause the application for long time.',
    concMarkAbort:
      'Concurrent Mark Abort means a Full GC happened in the process of Concurrent Cycle and Concurrent Cycle was aborted. Attention should be paid to the cause of Full GC.',
    concModeFailure:
      'Concurrent Mode Failure means a Full GC happened in the process of CMS GC and CMS GC was aborted. Attention should be paid to the cause of Full GC',
    concModeInterrupt:
      'Concurrent Mode Interrupted means a Full GC of System.gc() happened in the process of CMS GC and CMS GC was aborted. Attention should be paid to the cause of Full GC',
    allocationStall: 'Allocation Stall means the current thread can not allocate more objects and is stalled until the end of GC.',
    cmReset:
      'Concurrent Mark Reset For Overflow means the global mark stack is full and concurrent mark has to be restarted. This may lead to extremely long Concurrent Cycle time. It is recommended to increase -XX:MarkStackSize(default: 4M).',
    oom: 'Out of memory means no more objects can be allocated in the heap even after gc. An OutOfMemoryError will be thrown.',
    initialMarkSituation:
      'Initial Mark means this Young GC starts the marking process in addition to performing a normal young-only collection.',
    prepareMixedSituation: 'Prepare Mixed means Mixed GC will start after this Young GC.'
  },

  badHint: {
    badThroughput: 'Current throughput is low. Throughput of a health application should be at least 90%.',
    badPause: 'Current pause time is long.',
    badUsageAfterGC: 'Usage is still high after an GC. This may indicate capacity is set too low or there is a memory leak.',
    youngTooSmall: 'Young generation Capacity is too small. This may lead to frequent Young GC and affect throughput.',
    oldTooSmall: 'Old generation Capacity is too small. This may lead to Full GC.',
    badObjectAllocSpeed: 'Object allocation speed is fast.',
    badPromotionSpeed: 'Object promotion speed is fast.',
    badSinglePromotion: 'Too many objects promoted in one GC. This may lead to long Young GC or Full GC.',
    badInterval: '{name} happens frequently.',
    badDuration: 'The duration of {name} is long.',
    badCauseCount: '{name} happens too many times.',
    badPhaseCount: '{name} happens too many times.',
    badCause: 'We should try to avoid {name}.',
    badCauseFull: 'We should try to avoid Full GC led by {name}.',
    badPhase: 'We should try to avoid {name}'
  },

  diagnose: {
    diagnose: 'Diagnose',
    noProblem: 'No problem detected',
    problemTemplate: 'In {time}, there is {problem}.',
    solution: 'Common ways for troubleshooting or tuning:',
    abnormal: {
      outOfMemory: 'Out of Memory',
      allocationStall: 'Allocation Stall',
      metaspaceFullGC: 'Full GC caused by metaspace',
      heapMemoryFullGC: 'Full GC caused by heap',
      longYoungGCPause: 'Young GC long pause',
      systemGC: 'Full GC caused by System.gc()',
      frequentYoungGC: 'frequent Young GC',
      longG1Remark: 'Remark long pause',
      longCMSRemark: 'Final Remark long pause',

      badDuration: 'Duration of this {param} is long',
      badEventType: 'We should try to avoid {param}',
      badCauseFullGC: 'We should try to avoid Full GC led by {param}',
      badInterval: 'Interval of this {param} is short',
      badPromotion: 'Too many objects promoted during this {param}',
      smallYoungGen: 'Young generation capacity is too small.',
      smallOldGen: 'Old generation capacity is too small.',
      highHumongousUsed: 'Too humongous objects is high after gc',
      highHeapUsed: 'Heap usage is high after gc',
      highOldUsed: 'Old generation usage is high after gc',
      highMetaspaceUsed: 'Metaspace usage is high after gc',
      badSys: 'Too much time spent in kernel mode',
      badUsr: 'Too few time spent in user mode',
      toSpaceExhausted: 'We should try to avoid To-space Exhausted'
    },
    suggestion: {
      upgradeTo11G1FullGC:
        'If there is no way to eliminate Full GC or Full GC is acceptable，upgrading to G1 in JDk11 may reduce pause time with the help of multi thread optimization in Full GC.',
      checkSystemGC: 'Check where System.gc() is called and if it is necessary',
      disableSystemGC: 'Add option -XX:+DisableExplicitGC to disable System.gc()',
      oldSystemGC:
        'Or add option -XX:+ExplicitGCInvokesConcurrent to make System.gc() call trigger a concurrent Old GC',
      checkMetaspace: 'Check what is metaspace filled with',
      enlargeMetaspace: 'Increase -XX:MetaspaceSize -XX:MaxMetaspaceSize to enlarge metaspace',
      enlargeHeap: 'Increase -Xmx -Xms to enlarge metaspace',
      increaseConcGCThreads: 'Increase -XX:ConcGCThreads to use more concurrent gc threads',
      decreaseIHOP: 'Decrease option -XX:InitiatingHeapOccupancyPercent to trigger Old GC earlier',
      decreaseCMSIOF: 'Decrease option -XX:CMSInitiatingOccupancyFraction to trigger Old GC earlier',
      increaseZAllocationSpikeTolerance:
        'Increase -XX:ZAllocationSpikeTolerance to trigger GC earlier',
      checkLiveObjects:
        'If too many objects survived after gc, check application logic to see why there are so many survivors.',
      checkReferenceGC:
        'If reference processing time is long，enable -XX:+ParallelRefProcEnabled for multi thread optimization(enabled by default after JDK11)，or check why there are so many weak/soft/phantom references',
      checkCPUTime:
        'Check CPU Time. If real is long but usr，sys are short，this may be owing to disk I/O or interference between processes or containers.',
      shrinkYoungGen: 'Decrease -Xmn to shrink young generation capacity',
      shrinkYoungGenG1: 'Decrease -XX:G1MaxNewSizePercent to shrink young generation capacity',
      checkEvacuationFailure:
        'Check if there is To-space exhausted during. If there is, one way is not to set -Xmn option and the other way is to check if there is too many humongous object allocation, memory leak or early promotion.',
      checkMemoryLeak: 'Check if there is memory leak by means of Heap Dump etc.',
      checkFastPromotion:
        'If memory used in old generation increases rapidly before the full gc，check if there is early promotion led by long life cycle objects by means of Heap Dump before Full GC.',
      checkRescan: 'If Rescan time is long，add option -XX:+CMSScavengeBeforeRemark to optimize.',
      checkClassUnloading:
        'If Class Unloading time is long，try to optimize code to decrease the number of class loaders.',
      expandYoungGen: 'Increase option -Xmn to expand young generation',
      expandYoungGenG1:
        'Expand young generation. G1 adjusts young generation size dynamically to meet pause time target. If current pause time is long, consider optimizing pause time, or increase pause time target(increase -XX:MaxGCPauseMillis, default: 200ms). You can also increase the minimum size of young generation(set -XX:+UnlockExperimentalVMOptions，increase -XX:G1NewSizePercent, default: 5，young generation is at least 5% of the heap).',
      checkFastObjectAllocation: 'Optimize code that allocates objects fast.',
      useMoreDetailedLoggingUnified:
        'If available information is still not enough to find the cause of the problem, consider using debug level GC log for more details (change gc* to gc*=debug in option -XLOG. It is not recommended to keep using debug level because it prints too many logs.).'
    }
  },

  detail: {
    filters: 'Filters',
    eventType: 'GC Event Type',
    logTime: 'Log Time',
    pauseTimeGE: 'Pause Time (ms) ≥',
    cputime: 'CPU Time',
    interval: 'Interval',
    memory: 'Memory',
    event: 'GC Events',
    startTime: 'Start Time',
    endTime: 'End Time'
  },

  details: 'Details',
  algorithm: 'GC Algorithm',

  header: {
    showDetails: 'Show Details',
    backToMainView: 'Back to Main View'
  }
}