package controllers

import java.nio.charset.StandardCharsets
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.http.scaladsl.model.MediaTypes
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.google.common.collect.Lists
import com.sdl.odata.api.parser.{EntitySetPath, ODataUri, ResourcePathUri}
import com.sdl.odata.api.processor.ProcessorResult
import com.sdl.odata.api.processor.query.QueryResult
import com.sdl.odata.api.renderer.ODataRenderer
import com.sdl.odata.api.service._
import com.sdl.odata.edm.registry.ODataEdmRegistryImpl
import com.sdl.odata.renderer.atom.AtomRenderer
import com.sdl.odata.renderer.batch.ODataBatchRequestRenderer
import com.sdl.odata.renderer.metadata.MetadataDocumentRenderer
import play.api.mvc.{Action, _}
import protocols._
import services.streams
import services.streams._

import scala.collection.JavaConverters._


class ODataController @Inject()(implicit system: ActorSystem) extends Controller {
  implicit val mat = ActorMaterializer()

  val renders: ODataRenderer = new AtomRenderer
  val oDataEdmRegistry = {
    val r = new ODataEdmRegistryImpl
    r.registerClasses(Lists.newArrayList(classOf[beans.Machine]))
    r
  }


  val conn = streams.connection("localhost", 3000)


  def machines(id:String) = Action.async { r ⇒
    get(s"/api/Machines").via(conn).via(response[List[models.Machine]]).map(_.right.get).map { l ⇒
      val builder = new ODataRequest.Builder
      builder.setMethod(ODataRequest.Method.valueOf(r.method))
      println(r.uri)
      r.queryString.foreach(println)
      builder.setUri(r.uri)
      r.headers.headers.foreach(h ⇒ builder.setHeader(h._1, h._2))

      builder.setHeader("Content-Type", "application/atom+xml")

      val mbeans = l.map(m ⇒ new beans.Machine(m.id, m.dnsName, m.currentRegistrationState, m.currentPowerState, m.currentSessionCount))

      // body

      val uri = ODataUri(r.uri, ResourcePathUri(EntitySetPath("Machines", None), List()))
      val oDataRequest = builder.build()
      val ctx = new ODataRequestContext(oDataRequest, oDataEdmRegistry.getEntityDataModel).withUri(uri)
      val responseBuilder = new ODataResponse.Builder
      responseBuilder.setHeaders(r.headers.toSimpleMap.asJava)
      renders.render(ctx, QueryResult.from(mbeans.asJava), responseBuilder)
      responseBuilder.setContentType(MediaType.ATOM_SVC_XML)
      responseBuilder.setStatus(ODataResponse.Status.OK)
      val b = responseBuilder.build()
      val x = b.getBody.mkString
      val s = b.getBodyText(StandardCharsets.UTF_8.name)
      b

    }.map(b ⇒ Ok(b.getBodyText(StandardCharsets.UTF_8.name)))
    .runWith(Sink.head)
  }

  def meta(path: String) = Action { r ⇒
    val builder = new ODataRequest.Builder
    builder.setMethod(ODataRequest.Method.valueOf(r.method))
    println(r.uri)
    r.queryString.foreach(println)
    builder.setUri(r.uri)
    r.headers.headers.foreach(h ⇒ builder.setHeader(h._1, h._2))

    builder.setHeader("Content-Type", "application/atom+xml")

    val uri = ODataUri(r.uri, ResourcePathUri(EntitySetPath(path, None), List()))
    val oDataRequest = builder.build()



    val ctx = new ODataRequestContext(oDataRequest, oDataEdmRegistry.getEntityDataModel).withUri(uri)

    val metarenders: MetadataDocumentRenderer = new MetadataDocumentRenderer()


    val responseBuilder = new ODataResponse.Builder
    metarenders.render(ctx, QueryResult.from(Lists.newArrayList()), responseBuilder)
    val b = responseBuilder.build()
    val t = b.getBodyText(StandardCharsets.UTF_8.name)
    Ok(b.getBodyText(StandardCharsets.UTF_8.name))
  }

  def datapath = Action { r ⇒


    Ok
  }

  def debugpath(path: String) = Action { r ⇒
    r.headers.headers.foreach(h ⇒ println(s"header: $h"))

    Ok
  }

  // request all machines

  // map to class

  // flow into new builder using the r

  //    val builder = new ODataRequest.Builder
  //    builder.setMethod(ODataRequest.Method.valueOf(r.method))
  //    builder.setUri(r.uri)
  //
  //
  //
  //
  //    r.headers.headers.foreach(h ⇒ builder.setHeader(h._1, h._2))
  //
  //    import scala.collection.JavaConverters._
  //
  //
  //    // body
  //
  //    val m1 = new Machine(1, "foo", 0, 0, 0)
  //
  //    val uri = ODataUri(r.uri, ResourcePathUri(EntitySetPath("Machines", None), List()))
  //
  //    val oDataRequest = builder.build()
  //    val ctx = new ODataRequestContext(oDataRequest, oDataEdmRegistry.getEntityDataModel).withUri(uri)
  //
  //    val res = new ProcessorResult(ODataResponse.Status.OK, QueryResult.from(Lists.newArrayList(m1)), r.headers.toSimpleMap.asJava, ctx)
  //
  //    val responseBuilder = new ODataResponse.Builder
  //    renders.render(ctx, QueryResult.from(Lists.newArrayList(res)), responseBuilder)
  //    val odataResponse = responseBuilder.build()
  //
  //Ok(odataResponse.getBodyText(StandardCharsets.UTF_8.name))
  //}
}
