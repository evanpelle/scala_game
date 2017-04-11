package com.mygdx.game.game_overlay

import com.badlogic.gdx.scenes.scene2d.ui.{Label, VerticalGroup, Table}
import com.mygdx.game.mechanics.player.IPlayer

/**
  * Created by evan on 5/15/16.
  */
class ResourceMenu(player: IPlayer) extends Table {
  this.setBounds(1100, 950, 200, 200)
  val skin = MySkin.get
  this.setSkin(skin)

  val resRow = new VerticalGroup()
  this.addActor(resRow)


  val goldLabel = new Label("", skin)
  val woodLabel = new Label("", skin)
  val stoneLabel = new Label("", skin)
  val ironLabel = new Label("", skin)

  resRow.addActor(goldLabel)
  resRow.addActor(woodLabel)
  resRow.addActor(stoneLabel)
  resRow.addActor(ironLabel)

  this.refreshLabels()

  override def act(dt: Float) = {
    this.refreshLabels()
  }

  def refreshLabels() = {
    goldLabel.setText(s"gold: ${player.resource.gold}")
    woodLabel.setText(s"wood: ${player.resource.wood}")
    stoneLabel.setText(s"stone: ${player.resource.stone}")
    ironLabel.setText(s"iron: ${player.resource.iron}")
  }






}
