package com.mygdx.game.tile

import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.game_map.tile.TileFactory
import com.mygdx.game.mechanics.player.IPlayer

/**
  * Created by evan on 4/3/16.
  * loads tiles from maps
  */
class TileLoader(players: Set[IPlayer], tileFactory: TileFactory) {


  def build(mp: MapProperties): ITile = {
    val name: String = this.getName(mp)
    name match {
      case "grass" => tileFactory.buildGrass //GrassTile()
      case "water" => tileFactory.buildWater
      case "mountain" => tileFactory.buildMountain
      case "forest" => tileFactory.buildForest
      case "fort" => tileFactory.buildFort(this.getOwner(mp))
      case "ship" => tileFactory.buildShip(this.getOwner(mp))
      case "worker" => tileFactory.buildWorker(this.getOwner(mp))
      case "soldier" => tileFactory.buildSoldier(this.getOwner(mp))
      case "city" => tileFactory.buildCity(this.getOwner(mp))
      case _ => throw new Exception("uh oh " + name + " doesn't exist")
    }
  }

  def getName(mp: MapProperties): String = {
    if(mp.containsKey("name")) {
      mp.get("name").toString
    } else {
      throw new Exception("cannot find name in map properties")
    }
  }

  def getOwner(mp: MapProperties): IPlayer = {
    if(mp.containsKey("owner")) {
      val playerName = mp.get("owner").toString
      this.getOwner(playerName)
    } else {
      throw new Exception("tile doesn't have an owner")
    }
  }

  def getOwner(playerName: String): IPlayer = {
    val playersWithName = players.filter(p => p.name == playerName)
    if(playersWithName.isEmpty) {
      throw new Exception("could find player with name: " + playerName)
    } else if(playersWithName.size > 1) {
      throw new Exception("found two players with same name of: " + playerName)
    }
    playersWithName.head
  }

}
