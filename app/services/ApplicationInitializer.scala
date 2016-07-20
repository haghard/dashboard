package services

import javax.inject._
import java.time.{ Clock, Instant}
import play.api.{Configuration, Logger}
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future

@Singleton
class ApplicationInitializer @Inject()(clock: Clock, appLifecycle: ApplicationLifecycle, conf: Configuration) {
  val start: Instant = clock.instant

  Logger.info(s"Starting application at $start")

  appLifecycle.addStopHook { () =>
    val stop: Instant = clock.instant
    val runningTime: Long = stop.getEpochSecond - start.getEpochSecond
    Logger.info(s"Stopping application at ${clock.instant} after ${runningTime}s.")
    Future.successful(())
  }
}