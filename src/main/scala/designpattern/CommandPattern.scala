package designpattern

import java.time._

object CommandPattern {

  /*
  操作をオブジェクトとして表現し、実行・取り消し・履歴管理を可能にするパターン

  構造
  - Command: 実行する命令
  - Receiver: 実処理を持つ命令の対象
  - Invoker: コマンドを呼び出す側
  - Client: CommandとReceiverを組み立てる
   */

  case class Shift(
    id:    Long,
    date:  LocalDate,
    start: LocalTime,
    end:   LocalTime
  )

  class ShiftRepository {
    private var db: Map[Long, Shift] = Map.empty

    def save(shift: Shift): Unit =
      db = db + (shift.id -> shift)

    def delete(id: Long): Option[Shift] = {
      val old = db.get(id)
      old.foreach { old =>
        db = db - old.id
      }
      old
    }

    def find(id: Long): Option[Shift] =
      db.get(id)
  }

  trait Command {
    def execute(): Unit
    def undo(): Unit
  }

  class AddShiftCommand(
    repository: ShiftRepository,
    shift:      Shift
  ) extends Command {
    override def execute(): Unit =
      repository.save(shift)

    override def undo(): Unit =
      repository.delete(shift.id)
  }

  class DeleteShiftCommand(
    repository: ShiftRepository,
    id:         Long
  ) extends Command {
    private var deletedShift: Option[Shift] = None

    override def execute(): Unit =
      deletedShift = repository.delete(id)

    override def undo(): Unit =
      deletedShift.foreach(repository.save)
  }

  class ShiftCommandManager {
    private var undoStack: List[Command] = Nil
    private var redoStack: List[Command] = Nil

    def execute(command: Command): Unit = {
      command.execute()
      undoStack = command :: undoStack
    }

    def undo(): Unit =
      undoStack match {
        case command :: rest =>
          command.undo()
          undoStack = rest
        case Nil             =>
          println("Nothing to undo")
      }
  }

}
