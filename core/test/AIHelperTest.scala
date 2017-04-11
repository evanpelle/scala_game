import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.GameBuilder
import com.mygdx.game.animation.factory.{NullAnimationFactory, AnimationFactory}
import com.mygdx.game.game_action.action.WalkAction
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.mechanics.map_traversal.{WalkPath, MapTraversal}
import com.mygdx.game.mechanics.player.{AIPlayer, NullPlayer, Human, IPlayer}
import com.mygdx.game.tile.{ITile, TileLoader}
import com.mygdx.game.tile.tile_traits.{Ownable, Boardable}

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{AIHelper, TurnManager, ResContainer, Resource}
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals



class AIHelperTest {

  val (gameMap, tileFactory, gem) =  new GameBuilder().buildInvisibleGame
  val aiPlayer = new NullPlayer("ai", "red")
  val player2 = new NullPlayer("p2", "green")
  val turnManager = new TurnManager(List(aiPlayer, player2), gem)
  val soldier = tileFactory.buildSoldier(aiPlayer)
  val aiHelper = new AIHelper(gameMap, gem)


  @Test
  def toWalkablPathEmtpyTest = {
    val walkPath = new WalkPath(List())
    assertTrue(aiHelper.toWalkablePath(soldier, walkPath).length == 0)
  }

  @Test
  def toWalkablePathTest = {
    turnManager.startTurn()
    gameMap.setTile(soldier, Location(10, 10))
    val walkPath = new MapTraversal(gameMap).AStar(soldier, Location(50, 50)).get
    val toWalk = aiHelper.toWalkablePath(soldier, walkPath)
    assertTrue(toWalk.getStartTile.loc.equals(soldier.loc))
    assertTrue(toWalk.totalPathEffort <= soldier.move.moveLeft)
    val wa = new WalkAction(soldier, toWalk, gameMap, gem)
    assertTrue(wa.isValid)
  }

  def findNearestPersonTest = {
    val n1 = aiHelper.findNearestPerson(Location(5, 5), player2)
    assertTrue(n1.isEmpty)

    gameMap.setTile(soldier, Location(10, 10))
    val n2 = aiHelper.findNearestPerson(Location(15, 15), aiPlayer)
    assertTrue(n2.isDefined)
    assertTrue(n2.get == soldier)

    val soldier2 = tileFactory.buildSoldier(aiPlayer)
    gameMap.setTile(soldier2, Location(50, 50))
    val n3 = aiHelper.findNearestPerson(Location(40, 40), aiPlayer)
    assertTrue(n3.isDefined)
    assertTrue(n3.get == soldier2)

    val soldier3 = tileFactory.buildSoldier(aiPlayer)
    val soldier4 = tileFactory.buildSoldier(aiPlayer)
    val soldier5 = tileFactory.buildSoldier(aiPlayer)
    gameMap.setTile(soldier3, Location(45, 45))
    gameMap.setTile(soldier4, Location(43, 45))
    gameMap.setTile(soldier5, Location(48, 43))
    val result = aiHelper.findNearestPerson(Location(49, 49), aiPlayer)
    assertEquals(soldier2, result)
  }

  def listPropertyByDistanceTest = {
    player2.property.foreach(gameMap.popTile(_))
    aiPlayer.property.foreach(gameMap.popTile(_))
    gameMap.setTile(soldier, Location(10, 10))
    val city = tileFactory.buildCity(player2)
    gameMap.setTile(city, Location(10, 11))
    val worker = tileFactory.buildWorker(player2)
    gameMap.setTile(worker, Location(15, 15))
    val mill = tileFactory.buildMill(player2)
    gameMap.setTile(mill, Location(20, 20))
    val l = aiHelper.listPropertyByDistance(soldier.loc, player2)
    assertEquals(city, l.head)
    assertEquals(worker, l(1))
    assertEquals(mill, l.tail)

  }



}
