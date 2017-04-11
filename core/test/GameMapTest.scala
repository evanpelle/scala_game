package com.mygdx.game;

import com.mygdx.game.animation.factory.{NullAnimationFactory, AnimationFactory}
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.mechanics.player.Human
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;



class GameMapTest {
  val (gameMap, tileFactory, gem) = new GameBuilder().buildInvisibleGame

  val player = new Human("human", "red")

  @Test
  def testMapBuilder = {

    val t = tileFactory.buildWorker(player)
    val loc = Location(4,4)
    assertEquals(None, gameMap.getTile(loc, Layer.person))
    gameMap.setTile(t, loc)
    assertEquals(Some(t), gameMap.getTile(loc, Layer.person))

    val tResult = gameMap.popTile(loc, Layer.person)
    assertEquals(Some(t), tResult)
    assertEquals(-1, tResult.get.loc.x)
    assertEquals(Layer.person, tResult.get.layer)

    assertEquals(None, gameMap.getTile(loc, Layer.person))
  }
}