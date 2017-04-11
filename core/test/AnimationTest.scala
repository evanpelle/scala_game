/**
  * Created by evan on 5/4/16.
  */

import com.mygdx.game.animation.GameAnimation.{GameAnimation, NullAnimation, LoopAnimation}
import com.mygdx.game.game_map.Location
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
class AnimationTest {

  @Test
  def testLoopAnimtion = {
    val a1 = new NullAnimation
    val a2 = new NullAnimation
    val ar: Array[GameAnimation] = Array(a1, a2)
    val l = Location(0, 0)

    val la = new LoopAnimation(3, ar)
    assertEquals(false, la.hasCompletedOneLoop)
    assertEquals(0, la.currFrame)
    la.tick()//1
    la.tick() // 2
    assertEquals(0, la.currFrame)
    la.tick() // 3
    la.tick() //4, 1
    assertEquals(1, la.currFrame)
    la.tick() // 5, 2
    assertEquals(1, la.currFrame)
    la.tick() // 6, 3
    la.tick() // 7, 1
    assertEquals(0, la.currFrame)
    assertEquals(true, la.hasCompletedOneLoop)
  }


}
