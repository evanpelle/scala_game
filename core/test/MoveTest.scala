package com.mygdx.game;

import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.animation.factory.{AnimationFactory, NullAnimationFactory}
import com.mygdx.game.game_action.action.ActionNode.build_action.BuildAction
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.mechanics.player.{Human, IPlayer}
import com.mygdx.game.tile.TileLoader
import junit.framework.Assert
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;



class MoveTest {

  val (gameMap, tileFactory, gem) = new GameBuilder().buildInvisibleGame
  val player = new Human("human", "blue")

  @Test
  def testMove = {
    val soldier = tileFactory.buildSoldier(player)
    val mountain = tileFactory.buildMountain
    gameMap.setTile(mountain, Location(20,20))
    assertTrue(soldier.move.canMoveOn(gameMap.getTile(Location(1,1), Layer.landscape).get))
    assertTrue(!soldier.move.canMoveOn(gameMap.getTile(Location(20,20), Layer.terrain).get))
    assertTrue(gameMap.getAdjacentTiles(Location(20,20)).exists(soldier.move.canMoveOn))
  }
}