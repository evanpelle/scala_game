package com.mygdx.game.game_action.event

import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.game_map.event._
import com.mygdx.game.game_map.listener.GameListener

/**
  * Created by evan on 3/13/16.
  */
class GameEventManager {

  private var clickListeners: Set[GameListener] = Set()
  private var hoverListeners: Set[GameListener] = Set()
  private var gameEventListeners: Set[GameListener] = Set()
  private var turnListeners: Set[GameListener] = Set()

  //called every time frame is updated (used mostly for animation)
  private var updateListeners: Set[GameListener] = Set()


  def fireEvent(le: LocationEvent) = le.name match {
    case "click" => clickListeners.foreach{cl => cl.onClick(le)}
    case "hover" => hoverListeners.foreach{hl => hl.onHover(le)}
  }

  def fireEvent(event: GameEvent) = gameEventListeners.foreach(gel => gel.onEvent(event))
  def fireEvent(event: UpdateEvent) = updateListeners.foreach(ul => ul.onUpdate(event))
  def fireEvent(event: TurnEvent) = turnListeners.foreach(ul => ul.onTurn(event))

  def removeAllListener(all: GameListener) = {
    clickListeners -= all
    hoverListeners -= all
    gameEventListeners -= all
    updateListeners -= all
  }


  def addClickListener(click: GameListener): Unit = {
    clickListeners += click
  }
  def removeClickListener(click: GameListener): Unit = {
    clickListeners -= click
  }


  def addHoverListener(hover: GameListener): Unit = {
    hoverListeners += hover
  }
  def removeHoverListener(hover: GameListener): Unit = {
    hoverListeners -= hover
  }


  def addGameEventListener(game: GameListener): Unit = {
    gameEventListeners += game
  }
  def removeGameEventListener(game: GameListener): Unit = {
    gameEventListeners -= game
  }

  def addUpdateListener(update: GameListener): Unit = {
    updateListeners += update
  }
  def removeUpdateListener(update: GameListener): Unit = {
    updateListeners -= update
  }

  def addTurnListener(update: GameListener): Unit = {
     turnListeners += update
  }
  def removeTurnListener(update: GameListener): Unit = {
    turnListeners -= update
  }

}
