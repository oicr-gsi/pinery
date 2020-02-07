package ca.on.oicr.pinery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Conditional(CacheScheduledCondition.class)
public class CacheScheduler {

  @Autowired private Cache cache;

  @Scheduled(initialDelay = 0, fixedDelayString = "${pinery.cache.interval}")
  public void scheduleCacheUpdate() {
    cache.update();
  }
}
