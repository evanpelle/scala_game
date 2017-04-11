
import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.GameBuilder
import com.mygdx.game.animation.factory.{NullAnimationFactory, AnimationFactory}
import com.mygdx.game.game_action.action.ActionNode.build_action.BuildAction
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.mechanics.map_traversal.{WalkTile, WalkPath, MapTraversal}
import com.mygdx.game.mechanics.player.{NullPlayer, Human, IPlayer}
import com.mygdx.game.tile.{ITile, TileLoader}
import com.mygdx.game.tile.tile_traits.{Ownable, Boardable}
import junit.framework.Assert
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{TurnManager, ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;

/**
  * Created by evan on 4/23/16.
  */
class MapTraversalTest {

  val player = new NullPlayer("t1", "red")

  val (gameMap, tileFactory, gem) = new GameBuilder().buildInvisibleGame


  @Test
  def searchTest = {
    val worker = tileFactory.buildWorker(player)
    gameMap.setTile(worker, Location(3,3))
    val walkList = new MapTraversal(gameMap).AStar(worker, Location(5,5))
    println(walkList.get)
    assertTrue(walkList.isDefined)
    assertEquals(4, walkList.get.path.size)
  }

  @Test
  def walkPathTest = {
    val wp = new WalkPath(List(
      new WalkTile(Location(1,1), 0),
      new WalkTile(Location(1,2), 1),
      new WalkTile(Location(1,3), 2),
      new WalkTile(Location(1,4), 3)
    ))
    assertEquals(Location(1,2), wp.getNext(Location(1,1)).get.loc)
    assertEquals(None, wp.getNext(Location(1,4)))
    assertEquals(None, wp.getNext(Location(10, 10)))
  }





}
