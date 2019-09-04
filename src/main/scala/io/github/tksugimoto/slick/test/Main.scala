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
  final case class Age(value: Int) extends AnyVal

  def main(args: Array[String]): Unit = {
    import Tables.profile.api._

    val db =
      Database.forConfig("io.github.tksugimoto.slick.test.persistence.db")

    val query = for {
      user <- Tables.Users
    } yield (
      user.id.mapTo[UserId],
      user.name.mapTo[Name],
      user.age.map(_.mapTo[Age]),
    )

    val action: DBIO[Seq[(UserId, Name, Option[Age])]] = query.result

    val resultFuture: Future[Seq[(UserId, Name, Option[Age])]] = db.run(action)

    // 本番で使うべからず
    import scala.concurrent.ExecutionContext.Implicits.global

    (for {
      _ <- {
        val name = Name(s"Dummy ${LocalDateTime.now()}")
        val age = Age(20)
        db.run(
          Tables.Users.map { user =>
            (
              user.name.mapTo[Name],
              user.age.map(_.mapTo[Age]),
            )
          } += {
            (
              name,
              Option(age),
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
