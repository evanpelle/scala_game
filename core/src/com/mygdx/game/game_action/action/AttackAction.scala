package com.mygdx.game.game_action.action

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.Combatable

/**
  * Created by evan on 3/7/16.
  */
class AttackAction(val attacker: ITile with Combatable,
                   val attacked: ITile with Combatable,
                   gameMap: IGameMap,
                   gem: GameEventManager) extends GameAction {

  override def execute: Boolean = {
    println(attacker.toString() + " attacked: " + attacked)
    attacker.anim.onAttack(attacker.loc, attacked.loc)
    //attacked.combat.currHealth -= attacker.combat.attack/attacked.combat.defense * 10
    attacked.combat.currHealth -= 60
    println(s"curr health: ${attacked.combat.currHealth}")
    if(attacked.combat.currHealth <= 0) {
      new DeathAction(attacked, gameMap, gem).execute
    }
    true
  }

  override def getDisplayName: String = "attack"


  override def isValid: Boolean = true
}
