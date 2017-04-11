package com.mygdx.game.mechanics.player

import com.mygdx.game.game_map.Location
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.mechanics.{Resource, ResContainer, TurnManager}
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.Ownable

/**
  * Created by evan on 3/31/16.
  */
abstract class IPlayer(val name: String, val color: String) extends GameListener {

  var property: Set[ITile with Ownable] = Set()
  var resource = Resource()


  def onTurnStart(turnManager: TurnManager)


  def addProperty(toAdd: ITile with Ownable) = {
    property += toAdd
  }

  def removeProperty(toRemove: ITile with Ownable) = {
    property -= toRemove
  }

  //def addCity(toAdd: City) = {this.cities += toAdd; toAdd.setOwner(this)}


  def getNearestCity(loc: Location): Option[(ITile with Ownable, Int)] = {
    None
//    if(cities.isEmpty) return None
//    val closest: City = cities.foldLeft(cities.head){(c1: City, c2: City) =>
//      if(c1.loc.getDistance(loc) < c2.loc.getDistance(loc)) c1
//      else c2
//    }
//    return Some((closest, closest.loc.getDistance(loc)))
  }

}
