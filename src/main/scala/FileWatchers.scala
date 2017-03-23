/**
  * Created by venkat on 3/16/17.
  */

import scala.language.postfixOps
import akka.actor.{ActorRef, ActorSystem}
import better.files._, FileWatcher._
import java.nio.file.{StandardWatchEventKinds => EventType}

object FileWatchers extends App {
  val home = "/home/venkat"
  implicit val system = ActorSystem("mySystem")
  var counter: Int = 0

  try {
    val watcher: ActorRef = File("/home/venkat/Documents").newWatcher(recursive = true)
    // register partial function for an event
    watcher ! on(EventType.ENTRY_DELETE) {
      case file => println(s"$file got deleted")
    }

    // watch for multiple events
    watcher ! when(events = EventType.ENTRY_CREATE) {
      case (EventType.ENTRY_CREATE, file) => {
        counter += 1
        println(s"$file got created and the count files count is "+ counter)}
      case (EventType.ENTRY_MODIFY, file) => println(s"$file got modified " + file)
    }
  }catch {
    case e : MatchError => println("match error")
  }

}