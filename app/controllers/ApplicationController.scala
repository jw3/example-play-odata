package controllers

import akka.util.Timeout
import com.sdl.odata.controller.AbstractODataController
import play.api.mvc.{Action, Controller}

import scala.concurrent.duration.DurationInt


class ApplicationController() extends AbstractODataController with Controller {
  implicit val timeout = Timeout(10 seconds)

}
