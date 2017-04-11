package com.mygdx.game.animation.factory

import com.mygdx.game.animation.GameAnimation.{GameAnimation, NullAnimation}

/**
  * Created by evan on 4/14/16.
  * used for testing
  */
class NullAnimationFactory extends IAnimationFactory {

  override def buildStatic(name: String): GameAnimation = new NullAnimation

  override def buildMove(name: String): GameAnimation = new NullAnimation

  override def buildMarker(color: String): GameAnimation = new NullAnimation

  override def buildLoop(name: String): GameAnimation = new NullAnimation

  override def buildPerson(name: String): GameAnimation = new NullAnimation
}
