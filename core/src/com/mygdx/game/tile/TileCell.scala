package com.mygdx.game.game_map.tile

import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map._
import com.mygdx.game.tile.ITile

/**
  * Created by evan on 2/21/16.
  */
class TileCell(val landscape: Option[ITile],
               val terrain: Option[ITile],
               val structure: Option[ITile],
               val person: Option[ITile]) {

  val loc = landscape.get.loc


  def getTile(layer: Layer): Option[ITile] = layer match {
    case Layer.landscape => landscape
    case Layer.terrain => terrain
    case Layer.structure => structure
    case Layer.person => person
  }

  def hasTile(layer: Layer) : Boolean = this.getTile(layer) != None

  def hasLandscape: Boolean = landscape != None
  def hasTerrain: Boolean = terrain != None
  def hasStructure: Boolean = structure != None
  def hasPerson: Boolean = person != None



//
//  def pathEffort(): Option[Double] = {
//    if(this.canWalkOn || this.canSailOn) {
//      Some(
//        this.getAllTiles()
//        .filterNot(_ == None)
//        .map(_.get.movement.pathEffort)
//        .fold(1.0) {(l, r) => l * r}
//      )
//    } else None
//  }

//  def getBordableTiles = {
//    this.getAllTiles()
//      .filter(_.isDefined)
//      .map(_.get)
//      .filter(_.cargo.canBeBoarded)
//  }

  //def canBoard = this.getBordableTiles.nonEmpty



  def getAllTiles() = List[Option[ITile]](landscape, terrain, structure, person)

  override def toString = {
    var s = ""
    this.getAllTiles.foreach(t =>
      if (t == None) {
        s += "| None "
      } else {
        s += "| " + t.get + " "
      }
    )
    s
  }

}
