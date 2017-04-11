package com.mygdx.game.mechanics

/**
  * Created by evan on 3/27/16.
  * handles holding resources
  */
class ResContainer(var currRes: Resource, var maxRes: Resource) extends IResContainer {


  def canHoldRes: Boolean = true

  /**
    *
    * @param toAdd amount of resource to add
    * @return true if successful,
    *         false if toAdd + currRes > maxRes
    */
  def addRes(toAdd: Resource): Boolean = {
    val newRes = this.currRes + toAdd
    if(newRes <= this.maxRes) {
      currRes = newRes
      true
    } else false
  }

  /**
    *
    * @param toRemove amount of res to remove
    * @return true if removal was successful,
    *         if toRemove larger than current resource, false
    */
   def removeRes(toRemove: Resource): Boolean = {
    val newRes = this.currRes - toRemove
    if(newRes >= Resource(0,0,0,0)) {
      currRes = newRes
      true
    } else false
  }

  def setRes(toSet: Resource): Boolean = {
    if(toSet < this.maxRes) {
      currRes = toSet
      true
    } else {
      false
    }
  }
}

object ResContainer {
  def apply() = {
    new ResContainer(Resource(), Resource())
  }

  def apply(startRes: Resource, maxRes: Resource) = new ResContainer(startRes, maxRes)
}
