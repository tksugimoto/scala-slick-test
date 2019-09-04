package io.github.tksugimoto.slick.test

import java.time.LocalDateTime

import io.github.tksugimoto.slick.test.models.Tables
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.util.Failure

object Main {

  private val logger = LoggerFactory.getLogger(this.getClass)

  final case class UserId(value: Long) extends AnyVal
  final case class Name(value: String) extends AnyVal

  def main(args: Array[String]): Unit = {
    import Tables.profile.api._

    val db =
      Database.forConfig("io.github.tksugimoto.slick.test.persistence.db")

    val query = for {
      user <- Tables.Users
    } yield (
      user.id.mapTo[UserId],
      user.name.mapTo[Name],
      user.age,
    )

    val action: DBIO[Seq[(UserId, Name, Option[Int])]] = query.result

    val resultFuture: Future[Seq[(UserId, Name, Option[Int])]] = db.run(action)

    // 本番で使うべからず
    import scala.concurrent.ExecutionContext.Implicits.global

    (for {
      _ <- {
        val name = Name(s"Dummy ${LocalDateTime.now()}")
        db.run(
          Tables.Users.map { user =>
            (
              user.name.mapTo[Name],
              user.age,
            )
          } += {
            (
              name,
              Option(20),
            )
          },
        )
      }
      _ <- resultFuture map { users =>
        users.foreach { user =>
          logger.info("user: {}", user)
        }
      }
    } yield ())
      .andThen {
        case Failure(ex) => ex.printStackTrace()
      }
      .andThen {
        case _ => db.close()
      }
  }
}
