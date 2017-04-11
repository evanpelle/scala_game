package com.mygdx.game.animation.GameAnimation

import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.mygdx.game.game_map.Location

/**
  * Created by evan on 5/4/16.
  */
class LoopAnimation(val duration: Int, val textures: Array[GameAnimation]) extends GameAnimation {

//  val libGDXArray = new com.badlogic.gdx.utils.Array(textures.toArray)
//  val anim = new Animation(2, libGDXArray)

  var currFrame = 0
  var timeOnFrame = 0
  var hasCompletedOneLoop = false


  override def getTextureRegion(loc: Location): TextureRegion = {
    this.tick()
    textures(currFrame).getTextureRegion(loc)
  }

  def tick() = {
    timeOnFrame += 1
    if(timeOnFrame >= duration) {
      this.incrementFrame()
      this.timeOnFrame = 0
    }
  }

  def incrementFrame() = {
    currFrame += 1
    if(currFrame >= textures.size) {
      this.hasCompletedOneLoop = true
      currFrame = 0
    }
  }

  def reset() = {
    currFrame = 0
    timeOnFrame = 0
    hasCompletedOneLoop = false
  }
}

object LoopAnimation {
  def apply(duration: Int, textures: Array[GameAnimation]) =
    new LoopAnimation(duration, textures)

  def apply(duration: Int, textures: Array[TextureRegion]) =
    new LoopAnimation(duration, textures.map(new StaticTileAnimation(_)))
}
