package com.biermacht.brews.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import com.biermacht.brews.database.DatabaseInterface;
import com.biermacht.brews.frontend.AddNewRecipeActivity;
import com.biermacht.brews.frontend.MainActivity;
import com.biermacht.brews.ingredient.Ingredient;
import com.biermacht.brews.recipe.BeerStyle;
import com.biermacht.brews.recipe.Recipe;
import com.biermacht.brews.utils.comparators.RecipeComparator;

public class Utils {
		
	// New items, and objects of type "other"
	public static BeerStyle BEERSTYLE_OTHER = new BeerStyle("Other");
    public static Recipe NEW_RECIPE = new Recipe("New Recipe");

	// Intent put values
	public static String INTENT_RECIPE_ID = "biermacht.brews.recipe.id";
	public static String INTENT_PROFILE_ID = "biermacht.brews.profile.id";
	public static String INTENT_INGREDIENT_ID = "biermacht.brews.ingredient.id";

    // Constants
    public static int INVALID_ID = -1; // TODO: Throw exceptions in get methods if this is passed
	
	/**
	 * Returns a list of strings corresponding to ingredient objects
	 * @return
	 */
	public static ArrayList<String> getIngredientStringList(ArrayList<Ingredient> list)
	{
		ArrayList<Ingredient> listA = list;
		ArrayList<String> listToReturn = new ArrayList<String>();
		
		for (Ingredient b : listA)
		{
			listToReturn.add(b.toString());
		}	
		return listToReturn;
	}
	
	/**
	 * Determins if the given value is within the given range
	 * @param a
	 * @param low
	 * @param high
	 * @return
	 */
	public static boolean isWithinRange(double a, double low, double high)
	{
		if (a <= high && a >= low)
			return true;
		else
			return false;
	}
	
	public static DatabaseInterface getDatabaseInterface()
	{
	    return MainActivity.databaseInterface;
	}

    public static int getHours(double time, String units)
    {
        int num_hours = 0;
        if (units.equals(Units.MINUTES))
            for (int i = 60; i <= time; i += 60)
                num_hours++;
        if (units.equals(Units.HOURS))
            num_hours = (int) time;
        return num_hours;
    }

    public static int getMinutes(double time, String units)
    {
        int num_minutes = 0;
        int num_hours = 0;

        if (units.equals(Units.HOURS))
        {
            time = 60 * time;
            units = Units.MINUTES;
        }

        if (units.equals(Units.MINUTES))
        {
            num_hours = getHours(time, units);
            num_minutes = (int) (time - (60 * num_hours));
        }

        return num_minutes;
    }

	// Methods for dealing with the Database
	/**
	 * Get all recipes in the database
	 * @param dbi
	 * @return
	 */
	public static ArrayList<Recipe> getRecipeList(DatabaseInterface dbi)
	{
		ArrayList<Recipe> list = dbi.getRecipeList();
		
		for (Recipe r : list)
			r.update();
		
		Collections.sort(list, new RecipeComparator<Recipe>());
		
		return list;
	}
	
	/**
	 * Create a recipe with the given name
	 * @param name
	 * @return
	 */
	public static Recipe createRecipeWithName(String name)
	{
		Recipe r = new Recipe(name);
		
		long id = MainActivity.databaseInterface.addRecipeToDatabase(r);
		r = MainActivity.databaseInterface.getRecipeWithId(id);
		
		return r;
	}
	
	/**
	 * Create a recipe from existing recipe
	 * @param r
	 * @return
	 */
	public static Recipe createRecipeFromExisting(Recipe r)
	{
		long id = MainActivity.databaseInterface.addRecipeToDatabase(r);
		r = MainActivity.databaseInterface.getRecipeWithId(id);
		
		return r;
	}
	
	/**
	 * Updates existing recipe to match the given recipe
	 * @param r
	 * @return
	 */
	public static boolean updateRecipe(Recipe r)
	{
		r.update();
		return MainActivity.databaseInterface.updateExistingRecipe(r);
	}
	
	/**
	 * Updates the existing ingredient in the database
	 * @param i
	 * @return
	 */
	public static boolean updateIngredient(Ingredient i)
	{
		return MainActivity.databaseInterface.updateExistingIngredient(i);
	}
	
	/**
	 * Deletes the given recipe from the database
	 * @param r
	 * @return
	 */
	public static boolean deleteRecipe(Recipe r)
	{
		return MainActivity.databaseInterface.deleteRecipeIfExists(r.getId());
	}
	
	public static boolean deleteAllRecipes()
	{
		boolean bool = true;
		
		for (Recipe r : getRecipeList(MainActivity.databaseInterface))
		{
			for (Ingredient i : r.getIngredientList())
			{
				deleteIngredient(i);
			}
			bool = deleteRecipe(r);
		}
		return bool;
	}
	
	/**
	 * Deletes the given ingredient from database
	 * @param i
	 * @return
	 */
	public static boolean deleteIngredient(Ingredient i)
	{
		return MainActivity.databaseInterface.deleteIngredientIfExists(i.getId());
	}
	
	/**
	 * Gets recipe with given ID from database
	 * @param id
	 * @return
	 */
	public static Recipe getRecipeWithId(long id)
	{
		return MainActivity.databaseInterface.getRecipeWithId(id);
	}
	
	/**
	 * Gets ingredient with given ID from database
	 * @param id
	 * @return
	 */
	public static Ingredient getIngredientWithId(long id)
	{
		return MainActivity.databaseInterface.getIngredientWithId(id);
	}
	
	/**
	 * 
	 * @return Beer XML standard version in use
	 */
	public static int getXmlVersion()
	{
		return 1;
	}
		
	public static Recipe scaleRecipe(Recipe r, double newVolume)
	{
		double oldVolume = r.getDisplayBatchSize();
		double ratio = newVolume / oldVolume;
		
		r.setDisplayBatchSize(newVolume);
		r.setDisplayBoilSize(r.getDisplayBoilSize() * ratio);
		
		for (Ingredient i : r.getIngredientList())
		{
			i.setDisplayAmount(i.getDisplayAmount() * ratio);
			updateIngredient(i);
		}
		
		r.update();
		updateRecipe(r);
		return r;
	}
}
