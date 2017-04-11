package com.mygdx.game;

import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.animation.factory.NullAnimationFactory
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.mechanics.player.{AIPlayer, Human, IPlayer}
import com.mygdx.game.tile.TileLoader
import com.mygdx.game.tile.tile_traits.{Ownable, Treadable}
import junit.framework.Assert
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;



class TileLoaderTest {

  val (gameMap, tileFactory, gem) = new GameBuilder().buildInvisibleGame


  val p1 = new Human("p1", "blue")
  val p2 = new AIPlayer("p2", "red", gameMap, gem)

  val tl = new TileLoader(Set(p1, p2), tileFactory)

  @Test
  def testGetOwnerFromString: Unit = {
    assertEquals(p1, tl.getOwner("p1"))
    assertEquals(p2, tl.getOwner("p2"))

    try {
      tl.getOwner("bad")
      Assert.fail("didn't throw exception")
    } catch {case ex: Exception => {}}
  }

  @Test
  def testGetOwnerFromMP: Unit = {
    val mp1 = new MapProperties
    mp1.put("owner", "p1")
    assertEquals(p1, tl.getOwner(mp1))

    val mp2 = new MapProperties
    try {
      tl.getOwner(mp2)
      Assert.fail("didn't throw exception")
    } catch {case ex: Exception => {}}

  }


  @Test
  def loadGrass: Unit = {
    val grassMP = new MapProperties()
    grassMP.put("name", "grass")
    val grassLoaded = tl.build(grassMP)
    val grassFactory = tileFactory.buildGrass
    assertEquals(grassFactory.tread.canWalkOn, grassLoaded.asInstanceOf[Treadable].tread.canWalkOn)
  }

  @Test
  def loadSoldier: Unit = {
    val soldierMP = new MapProperties()
    soldierMP.put("name", "soldier")
    soldierMP.put("owner", "p1")
    val soldierLoaded = tl.build(soldierMP)
    val soldierFactoried = tileFactory.buildSoldier(p1)
    assertEquals(soldierFactoried.owner, soldierLoaded.asInstanceOf[Ownable].owner)
  }


}