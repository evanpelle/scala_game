package com.mygdx.game.game_action.action.ActionNode.build_action

import com.mygdx.game.game_action.action.ActionNode.ActionNode
import com.mygdx.game.game_action.action.GameAction
import com.mygdx.game.game_action.event.{EventName, GameEventManager}
import com.mygdx.game.game_map.{Layer, IGameMap}
import com.mygdx.game.game_map.event.GameEvent
import com.mygdx.game.game_map.tile.TileFactory
import com.mygdx.game.mechanics.player.{NullPlayer, IPlayer}
import com.mygdx.game.tile.Tile
import com.mygdx.game.tile.Tile.Name
import com.mygdx.game.tile.{Tile, ITile}
import com.mygdx.game.tile.tile_traits.{Constructable, Ownable}

/**
  * Created by evan on 3/7/16.
  */
class BuildAction(builder: ITile with Constructable with Ownable,
                  val toBuildName: Name,
                  gameMap: IGameMap,
                  tf: TileFactory,
                  gem: GameEventManager) extends GameAction {


  override def execute: Boolean = {
    val toBuild = this.build
    builder.owner.resource -= toBuild.construct.resToBuild
    gameMap.setTile(toBuild, builder.loc)
    println(s"building person: $toBuild")
    true
  }

  def isValid = this.canPlace && this.hasEnoughResToBuild && spaceEmtpy

  private def spaceEmtpy = {
    gameMap.getTile(builder.loc, this.build(NullPlayer()).layer).isEmpty
  }

  private def canPlace: Boolean = toBuildName match {
    case Tile.city => true
    case Tile.fort => true
    case Tile.soldier => true
    case Tile.worker => true
    case Tile.mill => gameMap.getTile(builder.loc, Layer.terrain) match {
      case Some(t) => t.name == Tile.forest
      case None => false
    }
    case _ => false
  }

  private def hasEnoughResToBuild = {
    val toBuild =  this.build(NullPlayer())
    builder.owner.resource >= toBuild.construct.resToBuild
  }



  private def build(owner: IPlayer): ITile with Ownable with Constructable = toBuildName match {
    case Tile.city => tf.buildCity(owner)
    case Tile.fort => tf.buildFort(owner)
    case Tile.mill => tf.buildMill(owner)
    case Tile.soldier => tf.buildSoldier(owner)
    case Tile.worker => tf.buildWorker(owner)
  }

  private def build: ITile with Ownable with Constructable = this.build(builder.owner)

  def getDisplayName: String = toBuildName.toString
}

object BuildAction {
  def buildNode(builder: ITile with Constructable with Ownable,
                gameMap: IGameMap, tileFactory: TileFactory, gem: GameEventManager) = {

    val bn = new ActionNode("build")
    builder.construct.canBuild
      .map(new BuildAction(builder, _ , gameMap, tileFactory, gem))
      .foreach(bn.add)
    bn
  }
}
