package com.mygdx.game.game_overlay

import _root_.ActionNode.ActionFinder
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{VerticalGroup, TextButton, Skin}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.game_action.action.ActionNode
import com.mygdx.game.game_action.action.ActionNode.{ActionNode}
import com.mygdx.game.game_action.action._
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.event.LocationEvent
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.game_map.{MapInput, IGameMap}
import com.mygdx.game.game_map.tile.{TileFactory, TileCell}
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.Moveable

/**
  * Created by evan on 2/22/16.
  */
class ClickMenu(actionFinder: ActionFinder,
                gameMap: IGameMap,
                tileFactory: TileFactory,
                gem:GameEventManager) extends GameMenu with GameListener {

  this.setBounds(100, 150, 200, 200)

  this.setSkin(MySkin.get)

  var buttonRow = new VerticalGroup()

  def clearButtonRow() = {
    this.removeActor(buttonRow)
    this.buttonRow = new VerticalGroup()
    this.addActor(buttonRow)
  }

  def displayActions(node: ActionNode): Unit = {
    this.clearButtonRow()
    node.getActions.map(actionToButton).foreach(b => buttonRow.addActor(b))
    node.getNodes.map(nodeToButton).foreach(buttonRow.addActor)
    buttonRow.addActor(this.buildBackButton(node.parent))
  }

  def buildBackButton(parent: Option[ActionNode]) = parent match {
    case Some(p) => this.buildButton("back", () => {displayActions(p)})
    case None => this.buildButton("close", () => {clearButtonRow()})
  }

  def actionToButton(gameAction: GameAction): TextButton = {
    this.buildButton(gameAction.getDisplayName, () => {gameAction.execute; this.clearButtonRow()})
  }

  def nodeToButton(node: ActionNode) = {
    this.buildButton(node.name, () => {displayActions(node)})
  }

  def buildButton(name: String, fn: () => Unit) = {
    val button = new TextButton(name, this.getSkin)
    button.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        fn()
      }
    })
    button
  }

  onClick = (le: LocationEvent) => {
    val actions = actionFinder.getActions(gameMap.getTileCell(le.loc).get)
    if(actions.nonEmpty) this.displayActions(actions)
  }

}
