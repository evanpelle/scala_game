package com.mygdx.game.animation.factory

import com.mygdx.game.animation.GameAnimation.{GameAnimation, MoveAnimation, StaticTileAnimation}

/**
  * Created by evan on 4/14/16.
  */
trait IAnimationFactory {

  def buildStatic(name: String): GameAnimation

  def buildMove(name: String): GameAnimation

  def buildLoop(name: String): GameAnimation

  def buildPerson(name: String): GameAnimation

  def buildMarker(color: String): GameAnimation

}
