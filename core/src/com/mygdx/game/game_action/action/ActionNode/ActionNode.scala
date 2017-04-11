package com.mygdx.game.game_action.action.ActionNode

import com.mygdx.game.game_action.action.GameAction

/**
  * Created by evan on 3/6/16.
  *
  */
class ActionNode(val name: String) {

  private var childNodes: List[ActionNode] = List()

  private var childActions: List[GameAction] = List()

  var parent: Option[ActionNode] = None

  def getNodes = this.childNodes
  def getActions = this.childActions

  def add(actionNode: ActionNode): Unit = {
    actionNode.parent = Some(this)
    childNodes :+= actionNode
  }
  def add(action: GameAction): Unit = if(action.isValid) childActions :+= action

  def hasNodes = this.childActions.nonEmpty
  def hasActions = this.childNodes.nonEmpty
  def nonEmpty = this.hasActions || this.hasNodes

}

object ActionNode {

  def apply(name: String) = {
    new ActionNode(name)
  }

//  def apply(name: String, childNodes: List[ActionNode]) = {
//    val an = new ActionNode(name)
//    an.childNodes = childNodes
//    an
//  }

  def apply(name: String, childActions: List[GameAction]) = {
    val an = new ActionNode(name)
    an.childActions = childActions
    an
  }


}
