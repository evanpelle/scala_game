import com.mygdx.game.game_map.{Direction, Location, GameMap}
import org.junit.Test
import org.junit.Assert._

/**
  * Created by evan on 4/28/16.
  */
class LocationTest {

  @Test
  def getDirectionTest(): Unit = {
    val dir1 = Location(0,0).getDirection(Location(1, 0))
    assertEquals(Direction.SouthEast, dir1.get)

    val dir2 = Location(0,0).getDirection(Location(1, 1))
    assertEquals(Direction.NorthEast, dir2.get)

    val dir3 = Location(1,1).getDirection(Location(0, 0))
    assertEquals(Direction.SouthWest, dir3.get)

    val dir4 = Location(1,1).getDirection(Location(0, 1))
    assertEquals(Direction.NorthWest, dir4.get)

    val dir5 = Location(0, 1).getDirection(Location(0,0))
    assertEquals(Direction.South, dir5.get)

    val dir6 = Location(0,0).getDirection(Location(0, 1))
    assertEquals(Direction.North, dir6.get)

    val dir7 = Location(2, 2).getDirection(Location(5,5))
    assertEquals(None, dir7)
  }

  @Test
  def getNeighborTest(): Unit = {
    val loc1 = Location(0,0).getNeighbor(Direction.NorthEast)
    assertEquals(Location(1, 1), loc1)

    val loc2 = Location(0,0).getNeighbor(Direction.SouthEast)
    assertEquals(Location(1,0), loc2)
  }

  @Test
  def adjacentTest(): Unit = {
    assertTrue(Location(0,0).isAdjacent(Location(0,1)))
    assertTrue(Location(0,0).isAdjacent(Location(1,1)))
    assertTrue(Location(1,1).isAdjacent(Location(0,0)))
    assertTrue(Location(0,0).isAdjacent(Location(0, 1)))
    Location(2,2).getAllNeighbors.foreach(Location(2,2).isAdjacent)

    assertTrue(!Location(3,3).isAdjacent(Location(0,0)))
    assertTrue(!Location(0,0).isAdjacent(Location(1, 2)))
  }



  @Test
  def testDistance1 = {
    val (l1, l2) = (Location(0,0), Location(0,1))
    val dis = l1.distance(l2)
    assertEquals(1, dis)
  }

  @Test
  def testDistance2 = {
    val (l1, l2) = (Location(0,0), Location(1, 0))
    val dis = l1.distance(l2)
    assertEquals(1, dis)
  }

  @Test
  def testDistance3 = {
    val (l1, l2) = (Location(0,0), Location(1, 1))
    val dis = l1.distance(l2)
    assertEquals(1, dis)
  }

  @Test
  def testDistance4 = {
    val (l1, l2) = (Location(1,1), Location(0, 0))
    val dis = l1.distance(l2)
    assertEquals(1, dis)
  }

  @Test
  def testDistance5 = {
    val (l1, l2) = (Location(0,0), Location(2, 2))
    val dis = l1.distance(l2)
    assertEquals(3, dis)
  }

  @Test
  def testDistance6 = {
    val dis1 = Location(20, 20).distance(Location(25, 25))
    val dis2 = Location(20, 20).distance(Location(25, 26))
    assertTrue(dis2 >= dis1)
  }

  @Test
  def testDistance7 = {
    val dis1 = Location(17, 17).distance(Location(24, 24))
    val dis2 = Location(17, 17).distance(Location(24, 25))
    assertTrue(dis2 >= dis1)
  }



}
