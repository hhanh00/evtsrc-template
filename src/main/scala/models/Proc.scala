package models

import akka.actor._
import org.eligosource.eventsourced.core._
import org.eligosource.eventsourced.journal.leveldb.LeveldbJournalProps
import java.io.File

trait ProcT { this: Actor =>
  val r: ActorRef
  def receive(): Receive = {
    case m: Message => r ! m.event
  }
}

class R extends Actor {
  def receive = {
    case Proc.Beep(x) => println(x)
  }
}

class Proc(ra: ActorRef) extends ProcT with Eventsourced with Actor {
  val r = ra
  val id = 1
}

object Proc {
  case class Beep(text: String)
}

object ProcApp extends App {
  implicit val system = ActorSystem()
  val journal = LeveldbJournalProps(new File("target/data"), native = false).createJournal
  val extension = EventsourcingExtension(system, journal)
  
  val r = system.actorOf(Props(classOf[R]))
  val proc = extension.processorOf(Props(classOf[Proc], r))
  extension.recover()
  proc ! Message(Proc.Beep("hello"))
}