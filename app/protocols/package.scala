import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}


package object protocols extends DefaultJsonProtocol with SprayJsonSupport {
  import models._

  implicit val machineFormat: RootJsonFormat[Machine] = jsonFormat(Machine, "id", "DnsName", "CurrentRegistrationState", "CurrentPowerState", "CurrentSessionCount")
}
