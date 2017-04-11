package com.mygdx.game;

import ActionNode.ActionFinder
import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.animation.factory.{NullAnimationFactory, AnimationFactory}
import com.mygdx.game.game_action.action.ActionNode.build_action.BuildAction
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.mechanics.player.{Human, IPlayer}
import com.mygdx.game.tile.tile_traits.Constructable
import com.mygdx.game.tile.{Tile, ITile, TileLoader}
import junit.framework.Assert
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{TurnManager, ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;



class BuildTest {


  val (gameMap, tileFactory, gem) = new GameBuilder().buildInvisibleGame

  val player = new Human("player", "blue")
  val tm = new TurnManager(List(player), gem)


  @Test
  def constructionTest = {
    val builder = tileFactory.buildWorker(player)
    gameMap.setTile(tileFactory.buildForest, Location(5, 5))
    gameMap.setTile(builder, Location(5, 5))
    player.resource = Resource.all(500)
    val ba = new BuildAction(builder, Tile.mill, gameMap, tileFactory, gem)
    assertTrue(ba.isValid)
    ba.execute
    assertEquals(Tile.mill, gameMap.getTile(Location(5,5), Layer.structure).get.name)
    val resShouldHave = Resource.all(500) - tileFactory.buildMill(player).construct.resToBuild
    assertEquals(resShouldHave, player.resource)
  }


  @Test
  def testBuildFort: Unit = {
//    val city = tileFactory.buildCity(player)
//    city.construction.resCont.setRes(Resource(30,30,30,30))
//    gameMap.setTile(city, Location(10,10))
//
//    val worker = tileFactory.buildWorker(player, gameMap, gem)
//    gameMap.setTile(worker, Location(8,8))
//
//    val fortToBuild = tileFactory.buildFort
//    fortToBuild.construction.resourceToBuild = Resource(10,10,10,10)
//
//    //assertTrue(worker.construction.canBuild(fortToBuild.construction))
//
//    val buildAction = new BuildAction(worker, fortToBuild, gameMap)
//    buildAction.execute
//
//    assertEquals(fortToBuild, gameMap.getTile(Location(8,8, Layer.structure)).get)
//    assertEquals(Resource(20,20,20,20), city.construction.resCont.currRes)

  }

}