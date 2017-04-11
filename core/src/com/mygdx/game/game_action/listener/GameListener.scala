package com.mygdx.game.game_map.listener

import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.game_map.event._

/**
  * Created by evan on 3/13/16.
  */
trait GameListener {

  /**
    * when map gets clicked
    */
  var onClick = (event: LocationEvent) => ()

  /**
    * when mouse moves over tile
    */
  var onHover = (event: LocationEvent) => ()

  /**
    * when a game object fires an event
    */
  var onEvent = (event: GameEvent) => ()

  /**
    * every time a frame is render (60 times a second)
    */
  var onUpdate = (event: UpdateEvent) => ()


  /**
    * fired when turn ends
    */
  var onTurn = (event: TurnEvent) => ()

}
