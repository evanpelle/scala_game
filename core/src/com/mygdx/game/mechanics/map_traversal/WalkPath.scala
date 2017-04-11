package com.mygdx.game.mechanics.map_traversal

import com.mygdx.game.game_map.Location


/**
  * Created by evan on 4/4/16.
  */

class WalkPath(val path: List[WalkTile]) {
  def getEndTile: WalkTile = path.last
  def getStartTile = path.head

  def totalPathEffort = this.getEndTile.pathEffort

  override def toString = path.foldLeft("")((l,r) => l + r + " ")

  def length = path.length

  def getNext(loc: Location) = {
    if(path.exists(wt => wt.loc.equals(loc))) {
      path.dropWhile(wt => !wt.loc.equals(loc)).tail.headOption
    } else None
  }

}