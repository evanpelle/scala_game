package com.mygdx.game.animation.GameAnimation

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.game_map.Location
import com.mygdx.game.mechanics.map_traversal.WalkPath

/**
  * Created by evan on 5/1/16.
  */

abstract class GameAnimation() {

  var posX = 0.0 // used for object layers
  var posY = 0.0 // used for object layers

  var offsetX = 0.0 // used for tile layers
  var offsetY = 0.0 // used for tile layers

  def getOffsetX = offsetX.toFloat

  def getOffsetY = offsetY.toFloat

  def setOffsetY(offsetY: Float): Unit = {
    this.offsetY = offsetY
  }

  def setOffsetX(offsetX: Float): Unit = {
    this.offsetX = offsetX
  }

  def getTextureRegion(loc: Location): TextureRegion

  def justMoved(toWalk: WalkPath) = {}

  def onAttack(myLoc: Location, targetLoc: Location) = {}
}