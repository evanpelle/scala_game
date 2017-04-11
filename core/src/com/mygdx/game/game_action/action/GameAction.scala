package com.mygdx.game.game_action.action

/**
  * Created by evan on 3/6/16.
  */
trait GameAction {

  def execute: Boolean

  def isValid: Boolean

  def getDisplayName: String

}
