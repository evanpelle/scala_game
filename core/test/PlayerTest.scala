package com.mygdx.game;

import com.mygdx.game.animation.factory.NullAnimationFactory
import com.mygdx.game.game_map.event.TurnEvent
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.mechanics.player.{AIPlayer, Human, IPlayer}
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{TurnManager, ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;



class PlayerTest {


  val(gameMap, tf, gem) = new GameBuilder().buildInvisibleGame


  val player = new IPlayer("p1", "blue") {
    override def onTurnStart(turnManager: TurnManager): Unit = {}
  }
  val NPC1 = new IPlayer("p2", "red") {
    override def onTurnStart(turnManager: TurnManager): Unit = {}
  }
  val NPC2 = new IPlayer("p3", "orange") {
      override def onTurnStart(turnManager: TurnManager): Unit = {}
  }

  val soldier = tf.buildSoldier(player)

  val city1 = tf.buildCity(player)
  val city2 = tf.buildCity(player)
  val city3 = tf.buildCity(player)

  gameMap.setTile(city1, Location(5,5))
  gameMap.setTile(city2, Location(1,1))
  gameMap.setTile(city3, Location(15,15))




//  @Test
//  def ClosestCity: Unit = {
//    gameMap.setTile(soldier, Location(6,6))
//    val cc1 = player.getNearestCity(soldier.loc)
//    assertEquals(city1, cc1.get._1)
//
//    gameMap.setTile(soldier, Location(2,2))
//    val cc2 = player.getNearestCity(soldier.loc)
//    assertEquals(city2, cc2.get._1)
//
//    gameMap.setTile(soldier, Location(12, 12))
//    val cc3 = player.getNearestCity(soldier.loc)
//    assertEquals(city3, cc3.get._1)
//  }

  @Test
  def playerManagerTest = {
    val pManager = new TurnManager(List(player, NPC1, NPC2), gem)
    val tl = tf.buildSoldier(player)
    gem.addTurnListener(tl)
    var playerStartedTurn = false
    var playerEndedTurn = false
    var NPC1StartedTurn = false
    tl.onTurn = (te: TurnEvent) => {
      if(te.player == player) {
        if(te.isStartTurn) playerStartedTurn = true
        else playerEndedTurn = true
      }
      if(te.player == NPC1 && te.isStartTurn) NPC1StartedTurn = true
    }
    assertTrue(!pManager.isInTurn)
    pManager.startTurn
    assertTrue(pManager.isInTurn)
    assertTrue(playerStartedTurn)
    assertEquals(player, pManager.getPlayerCurrTurn.get)
    pManager.endTurn
    assertTrue(!pManager.isInTurn)
    assertTrue(playerEndedTurn)
    assertEquals(None, pManager.getPlayerCurrTurn)
    pManager.startTurn
    assertTrue(NPC1StartedTurn)
    pManager.endTurn
    pManager.startTurn
    assertEquals(NPC2, pManager.getPlayerCurrTurn.get)
    pManager.endTurn
    pManager.startTurn
    assertEquals(player, pManager.getPlayerCurrTurn.get)
  }



}