package pontinisistemas.doctorbrewer.base.ext

import android.content.Intent
import android.os.Bundle
import java.util.ArrayList

val ID_INGREDIENT = "ID_INGREDIENT"

val ID_RECIPE = "ID_RECIPE"
val ID_RECIPE_EXECUTE = "ID_RECIPE_EXECUTE"
val DEFAULT_VALUE = -99
val VIEW_TYPE = "VIEW_TYPE"
val ITEM_INGREDIENT_MALT_RESULT = "ITEM_INGREDIENT_MALT_RESULT"
val ITEM_INGREDIENT_HOP_RESULT = "ITEM_INGREDIENT_MALT_RESULT"
val ITEM_INGREDIENT_YEAST_RESULT = "ITEM_INGREDIENT_YEAST_RESULT"
val ID_MASH = "ID_MASH"
val ID_ITEM_MASH_STEP = "ID_ITEM_MASH_STEP"
val ITEM_INGREDINET_MALT_LIST = "ITEM_INGREDINET_MALT_LIST"
val ITEM_INGREDINET_HOP_LIST = "ITEM_INGREDINET_HOP_LIST"
val ITEM_INGREDINET_WATER_LIST = "ITEM_INGREDINET_WATER_LIST"
val ITEM_INGREDINET_YEAST_LIST = "ITEM_INGREDINET_YEAST_LIST"
val RECYCLE_ITEM_LIST = "RECYCLE_ITEM_LIST"


fun getIdIngredient(intent: Intent): Int {
    return intent.getIntExtra(ID_INGREDIENT, DEFAULT_VALUE)
}

fun getIdRecipe(intent: Intent): Int {
    return intent.getIntExtra(ID_RECIPE, DEFAULT_VALUE)
}

fun getIdRecipe(arguments: Bundle): Long {
    return arguments.getLong(ID_RECIPE)
}

fun getIdIngredient(arguments: Bundle): Long {
    return arguments.getLong(ID_INGREDIENT)
}

fun getIdMash(arguments: Bundle): Long {
    return arguments.getLong(ID_MASH)
}

fun getIdItemMashStep(arguments: Bundle): Long {
    return arguments.getLong(ID_ITEM_MASH_STEP)
}

fun getIdItemMashStep(arguments: Intent): Long {
    return arguments.getLongExtra(ID_ITEM_MASH_STEP,-1)
}


fun addIdRecipe(intent: Intent, recipeId: Long) {
    intent.putExtra(ID_RECIPE, recipeId)
}
fun addIdRecipeExecute(intent: Intent, recipeExecuteId: Long) {
    intent.putExtra(ID_RECIPE_EXECUTE, recipeExecuteId)
}

fun getIdRecipeExecute(arguments: Bundle): Long {
    return arguments.getLong(ID_RECIPE_EXECUTE)
}

fun addIdRecipe(arguments: Bundle, recipeId: Long) {
    arguments.putSerializable(ID_RECIPE, recipeId)
}

fun addIdIngredient(intent: Intent, idIngredient: Long) {
    intent.putExtra(ID_INGREDIENT, idIngredient)
}

/*
fun addItemIngredientMaltResultList(arguments: Intent, itemIngredientMaltResult: List<ItemIngredientMaltResult>) {
    arguments.putExtra(ITEM_INGREDIENT_MALT_RESULT, itemIngredientMaltResult as ArrayList)
}

fun addItemIngredientHoptResultList(arguments: Intent, itemIngredientHopResult: List<ItemIngredientHopResult>) {
    arguments.putExtra(ITEM_INGREDIENT_HOP_RESULT, itemIngredientHopResult as ArrayList)
}
fun addItemIngredientYeasttResultList(arguments: Intent, itemIngredientYeastResult: List<ItemIngredientYeastResult>) {
    arguments.putExtra(ITEM_INGREDIENT_YEAST_RESULT, itemIngredientYeastResult as ArrayList)
}
fun getItemIngredientHopResultList(data: Intent): List<ItemIngredientHopResult> {
    return data.getSerializableExtra(ITEM_INGREDIENT_HOP_RESULT) as List<ItemIngredientHopResult>
}


fun getItemIngredientMaltResultList(data: Intent): List<ItemIngredientMaltResult> {
    return data.getSerializableExtra(ITEM_INGREDIENT_MALT_RESULT) as List<ItemIngredientMaltResult>
}

fun getItemIngredientYeastResultList(data: Intent): List<ItemIngredientYeastResult> {
    return data.getSerializableExtra(ITEM_INGREDIENT_YEAST_RESULT) as List<ItemIngredientYeastResult>
}
*/


fun addIdIngredient(arguments: Bundle, idIngredient: Long) {
    arguments.putLong(ID_INGREDIENT, idIngredient)
}

fun addIdMash(arguments: Bundle, idMash: Long) {
    arguments.putLong(ID_MASH, idMash)
}

fun addIdMash(arguments: Intent, idMash: Long) {
    arguments.putExtra(ID_MASH, idMash)
}

fun addIdItemMashStep(arguments: Intent, id: Long) {
    arguments.putExtra(ID_ITEM_MASH_STEP, id)
}

fun addViewType(intent: Intent, viewType: Int) {
    intent.putExtra(VIEW_TYPE, viewType)
}

fun addViewType(arguments: Bundle, viewType: Int) {
    arguments.putInt(VIEW_TYPE, viewType)
}


fun getViewType(intent: Intent): Int {
    return intent.getIntExtra(VIEW_TYPE, DEFAULT_VALUE)
}

fun getViewType(arguments: Bundle): Int {
    return arguments.getInt(VIEW_TYPE)
}

/*
fun addIdIngredient(arguments: Bundle, enumInsertOrUpdate: EnumInsertOrUpdate) {}

fun addItemIngredientList(arguments: Bundle, itemIngredientList: List<ItemIngredient>) {
    arguments.putParcelableArrayList(ITEM_INGREDINET_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientList(arguments: Intent, itemIngredientList: List<ItemIngredient>) {
    arguments.putExtra(ITEM_INGREDINET_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientMaltList(arguments: Bundle, itemIngredientList: List<ItemIngredientMalt>) {
    arguments.putParcelableArrayList(ITEM_INGREDINET_MALT_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientWaterList(arguments: Bundle, itemIngredientList: List<ItemIngredientWater>) {

    arguments.putParcelableArrayList(ITEM_INGREDINET_WATER_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientHopList(arguments: Bundle, itemIngredientList: List<ItemIngredientHop>) {

    arguments.putParcelableArrayList(ITEM_INGREDINET_HOP_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientYeastList(arguments: Bundle, itemIngredientList: List<ItemIngredientYeast>) {
    arguments.putParcelableArrayList(ITEM_INGREDINET_YEAST_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientMaltList(arguments: Intent, itemIngredientList: List<ItemIngredientMalt>) {
    arguments.putExtra(ITEM_INGREDINET_MALT_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientYeastList(arguments: Intent, itemIngredientList: List<ItemIngredientYeast>) {
    arguments.putExtra(ITEM_INGREDINET_YEAST_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientHopList(arguments: Intent, itemIngredientList: List<ItemIngredientHop>) {
    arguments.putExtra(ITEM_INGREDINET_HOP_LIST, itemIngredientList as ArrayList<*>)
}

fun addItemIngredientWaterList(arguments: Intent, itemIngredientList: List<ItemIngredientWater>) {
    arguments.putExtra(ITEM_INGREDINET_WATER_LIST, itemIngredientList as ArrayList<*>)
}


fun addRecycleItemList(arguments: Bundle, recyclerItemList: List<RecyclerItem>) {
    arguments.putParcelableArrayList(RECYCLE_ITEM_LIST, recyclerItemList as ArrayList<*>)
}

fun getItemIngredientList(data: Intent): List<ItemIngredient> {
    return data.getSerializableExtra(ITEM_INGREDINET_LIST) as List<ItemIngredient>
}

fun getItemIngredientMaltList(data: Intent): List<ItemIngredientMalt> {
    return data.getSerializableExtra(ITEM_INGREDINET_MALT_LIST) as List<ItemIngredientMalt>
}


fun getRecycleItemList(data: Intent): List<RecyclerItem> {
    return data.getSerializableExtra(RECYCLE_ITEM_LIST) as List<RecyclerItem>
}


fun getItemIngredientHopList(data: Intent): List<ItemIngredientHop> {
    return data.getSerializableExtra(ITEM_INGREDINET_HOP_LIST) as List<ItemIngredientHop>
}

fun getItemIngredientYeastList(data: Intent): List<ItemIngredientYeast> {
    return data.getSerializableExtra(ITEM_INGREDINET_YEAST_LIST) as List<ItemIngredientYeast>
}

fun getItemIngredientWaterList(data: Intent): List<ItemIngredientWater> {
    return data.getSerializableExtra(ITEM_INGREDINET_WATER_LIST) as List<ItemIngredientWater>
}

*/
