import sun.reflect.generics.reflectiveObjects.NotImplementedException

/**
  * Created by venkat on 3/21/17.
  */
object example extends App{
  sealed trait Stream[+A]{
    def h: () => A
    def t: () => Stream[A]
  }
  case object Empty extends Stream[Nothing] {
    override def h: () => Nothing = throw new NotImplementedException()

    override def t: () => Stream[Nothing] = throw new NotImplementedException()
  }
  case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

  object Stream {

    def cons[A](hd: => A, t1: => Stream[A]): Stream[A] = {
      println("head in cons is "+ hd)
      lazy val head = hd
      lazy val tail = t1
      Cons(() => head, () => tail)
    }

    def empty[A]: Stream[A] = Empty

    def apply[A](as: A*): Stream[A] =
    {
      println("in apply head is "+ as.head)
      if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
    }

  }

  val s = Stream(2,3,4,5)

  println(s.h())

  println(s.t())

  val kv = "(abc)[12];(cd)[13];(ae)[12]"
  val regex = """\((.+)\)\[([0-9]+)\]""".r
  val kvs = kv.split(";").map {
    a_kv => val regex(word, value) = a_kv
      (word, value.toInt)
  }




}
