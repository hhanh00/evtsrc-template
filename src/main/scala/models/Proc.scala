package models

import akka.actor._
import org.eligosource.eventsourced.core._
import org.eligosource.eventsourced.journal.leveldb.LeveldbJournalProps
import java.io.File

class Proc extends Actor {
  def receive = {
    case Proc.Beep(text) => println(text)
  }
}

object Proc {
  case class Beep(text: String)
}

object ProcApp extends App {
  implicit val system = ActorSystem()
  val journal = LeveldbJournalProps(new File("target/data"), native = false).createJournal
  val extension = EventsourcingExtension(system, journal)
  
  val proc = extension.processorOf(Props(new Proc with Receiver with Eventsourced {val id = 1}))
  extension.recover()
  proc ! Message(Proc.Beep("hello"))
}