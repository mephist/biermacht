package com.biermacht.brews.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.biermacht.brews.ingredient.Fermentable;
import com.biermacht.brews.recipe.*;
import java.util.*;
import com.biermacht.brews.ingredient.*;
import com.biermacht.brews.utils.*;

public class RecipeHandler extends DefaultHandler {

	// Types of things
	private static int RECIPES = 1;
	private static int RECIPE = 2;
	private static int HOPS = 3;
	private static int FERMENTABLES = 4;
	private static int HOP = 5;
	private static int FERMENTABLE = 6;
	private static int YEAST = 7;
	private static int YEASTS = 8;
	private static int MISCS = 9;
	private static int MISC = 10;
	private static int STYLE = 11;
	private static int STYLES = 12;
	
	// Hold the current elements
    boolean currentElement = false;
    String currentValue = null;

	// Lists to store all the things
	ArrayList<Recipe> list = new ArrayList<Recipe>();
	ArrayList<Fermentable> fermList = new ArrayList<Fermentable>();
	ArrayList<Hop> hopList = new ArrayList<Hop>();	
	ArrayList<Yeast> yeastList = new ArrayList<Yeast>();
	ArrayList<Misc> miscList = new ArrayList<Misc>();
	ArrayList<BeerStyle> beerStyleList = new ArrayList<BeerStyle>();

	// Objects for each type of thing
	Recipe r = null;
    Fermentable f = null;
	Hop h = null;
	Yeast y = null;
	Misc misc = null;
	BeerStyle style = null;
	
	// How we know what thing we're looking at
	int thingType = 0;

	/**
	* The return methods that will return lists of all of the elements
	* that have been parsed in the current file.
	*/
    public ArrayList<Recipe> getRecipes() {
        return list;
    }
	
	public ArrayList<Fermentable> getFermentables(){
		return fermList;
	}
	
	public ArrayList<Hop> getHops() {
		return hopList;
	}
	
	public ArrayList<Yeast> getYeasts() {
		return yeastList;
	}
	
	public ArrayList<Misc> getMiscs() {
		return miscList;
	}

	public ArrayList<BeerStyle> getBeerStyles()
	{
		return beerStyleList;
	}
	/**
	* This gets called whenever we encounter a new start element.  In this function
	* we create the new object to be populated and set the type of what we are looking at
	* so we can properly parse the following tags
	*/
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        currentElement = true;

		// We encounter a new recipe
        if (qName.equalsIgnoreCase("RECIPES"))
        {
			thingType = RECIPES;
        }
		
		// We encounter a new recipe
		if (qName.equalsIgnoreCase("RECIPE"))
		{
			thingType = RECIPE;
			r = new Recipe("");
		}
		
		// We encounter a new fermentables list
		if (qName.equalsIgnoreCase("FERMENTABLES"))
		{
			thingType = FERMENTABLES;
		}
		
		// We encounter a new hops list
		if (qName.equalsIgnoreCase("HOPS"))
		{
			thingType = HOPS;
		}
		
		// We encounter a new hop
		if (qName.equalsIgnoreCase("HOP"))
		{
			thingType = HOP;
			h = new Hop("");
		}
		// We encounter a new fermentable
		if (qName.equalsIgnoreCase("FERMENTABLE"))
		{
			thingType = FERMENTABLE;
			f = new Fermentable("");
		}
		// We encounter a new yeast list
		if (qName.equalsIgnoreCase("YEASTS"))
		{
			thingType = YEASTS;
		}

		// We encounter a new yeast
		if (qName.equalsIgnoreCase("YEAST"))
		{
			thingType = YEAST;
			y = new Yeast("");
		}
		
		// Encounter new misc
		if (qName.equalsIgnoreCase("MISC"))
		{
			thingType = MISC;
			misc = new Misc("");
		}
		
		// Encounter new miscs list
		if (qName.equalsIgnoreCase("MISCS"))
		{
			thingType = MISCS;
		}
		
		// Encounter new style
		if (qName.equalsIgnoreCase("STYLE"))
		{
			thingType = STYLE;
			style = new BeerStyle("");
		}
		
		// Encounter new style list
		if (qName.equalsIgnoreCase("STYLES"))
		{
			thingType = STYLES;
		}
		
    }

	/**
	* This gets called when we run into an end element.  The value of the element is stored in
	* the variable 'currentValue' and is assigned appropriately based on thingType.
	*/
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        currentElement = false;

		if (qName.equalsIgnoreCase("RECIPE"))
		// We've finished a new recipe
		{
			Log.e("RecipeHandler", "New recipe from xml: " + r.getRecipeName());
			list.add(r);
			thingType = 0;
			return;
		}
		else if (qName.equalsIgnoreCase("HOPS"))
		// We have finished a list of hops.
		{
			thingType = 0;
			return;
		}
		else if (qName.equalsIgnoreCase("FERMENTABLES"))
		// We have finished a list of fermentables
		{
			thingType = 0;
			return;
		}
		else if (qName.equalsIgnoreCase("YEASTS"))
		// We have finished a list of yeasts
		{
			thingType = 0;
			return;
		}
		else if (qName.equalsIgnoreCase("MISCS"))
		// We have finished a list of miscs
		{
			thingType = 0;
			return;
		}
		else if (qName.equalsIgnoreCase("STYLES"))
		// We have finished a list of styles
		{
			thingType = 0;
			return;
		}
		else if (qName.equalsIgnoreCase("FERMENTABLE"))
		// Finished a fermentable.  Add it to recipe and fermentables list.
		{
			thingType = 0;
			r.addIngredient(f);
			fermList.add(f);
			return;
		}
		else if (qName.equalsIgnoreCase("HOP"))
		// Finished a hop.  Add to recipe and list
		{
			thingType = 0;
			r.addIngredient(h);
			hopList.add(h);
			return;
		}
		else if (qName.equalsIgnoreCase("YEAST"))
		// Finished a yeast. Add to recipe and list
		{
			thingType = 0;
			r.addIngredient(y);
			yeastList.add(y);
			return;
		}
		else if (qName.equalsIgnoreCase("MISC"))
		// Finished a misc.  Add to recipe and list
		{
			thingType = 0;
			r.addIngredient(misc);
			miscList.add(misc);
			return;
		}
		else if (qName.equalsIgnoreCase("STYLE"))
		// Finished a style.  Add to recipe and list
		{
			thingType = 0;
			r.setStyle(style.toString());
			beerStyleList.add(style);
			return;
		}
		
		/************************************************************
		* Handle individual types of ingredients / things below.  We check
		* the "thingType" and base our actions accordingly
		************************************************************/
		if (thingType == RECIPE)
		{
			if (qName.equalsIgnoreCase("NAME"))
			{
				r.setRecipeName(currentValue);
			}

			else if (qName.equalsIgnoreCase("VERSION"))
			{
				r.setVersion(Integer.parseInt(currentValue));
			}

			else if (qName.equalsIgnoreCase("TYPE"))
			{
				String type = "NULL";

				if (currentValue.equalsIgnoreCase(Recipe.EXTRACT))
					type = Recipe.EXTRACT;
				if (currentValue.contains("Extract"))
					type = Recipe.EXTRACT;
				if (currentValue.equalsIgnoreCase(Recipe.ALL_GRAIN))
					type = Recipe.ALL_GRAIN;
				if (currentValue.equalsIgnoreCase(Recipe.PARTIAL_MASH))
					type = Recipe.PARTIAL_MASH;
					
				r.setType(type);
			}
			
			else if (qName.equalsIgnoreCase("BREWER"))
			{
				r.setBrewer(currentValue);
			}
			
			else if (qName.equalsIgnoreCase("ASST_BREWER"))
			{
				// TODO
			}
			
			else if (qName.equalsIgnoreCase("BATCH_SIZE"))
			{
				double s = Units.litersToGallons(Float.parseFloat(currentValue));
				r.setBatchSize(s);
			}
			
			else if (qName.equalsIgnoreCase("BOIL_SIZE"))
			{
				double s = Units.litersToGallons(Float.parseFloat(currentValue));
				r.setBoilSize(s);
			}
			
			else if (qName.equalsIgnoreCase("BOIL_TIME"))
			{
				r.setBoilTime((int)Float.parseFloat(currentValue));
			}
			
			else if (qName.equalsIgnoreCase("EFFICIENCY"))
			{
				r.setEfficiency(Float.parseFloat(currentValue));
			}
		}
		
		if (thingType == FERMENTABLE)
		// We are looking at a fermentable
		// Do all of the fermentable things below
		// woo.
		{
			if (qName.equalsIgnoreCase("NAME"))
			{
				f.setName(currentValue);
			}

			else if (qName.equalsIgnoreCase("VERSION"))
			{
				// TODO: Set version!
			}

			else if (qName.equalsIgnoreCase("TYPE"))
			{
				String type = "NULL";

				if (currentValue.equalsIgnoreCase(Fermentable.ADJUNCT))
					type = Fermentable.ADJUNCT;
				if (currentValue.equalsIgnoreCase(Fermentable.EXTRACT))
					type = Fermentable.EXTRACT;
				if (currentValue.contains("Extract"))
					type = Fermentable.EXTRACT;
				if (currentValue.equalsIgnoreCase(Fermentable.GRAIN))
					type = Fermentable.GRAIN;
				if (currentValue.equalsIgnoreCase(Fermentable.SUGAR))
					type = Fermentable.SUGAR;

				f.setFermentableType(type);
			}

			else if (qName.equalsIgnoreCase("AMOUNT"))
			{
				double amt = Double.parseDouble(currentValue);
				amt = Units.kilosToPounds(amt);
				f.setAmount(amt);
			}

			else if (qName.equalsIgnoreCase("YIELD"))
			{
				double yield = Double.parseDouble(currentValue);
				f.setYield(yield);
			}

			else if (qName.equalsIgnoreCase("COLOR"))
			{
				double color = Double.parseDouble(currentValue);
				f.setLovibondColor(color);
			}

			else if (qName.equalsIgnoreCase("ADD_AFTER_BOIL"))
			{
				boolean aab = (currentValue.equalsIgnoreCase("FALSE")) ? false : true;
				f.setAddAfterBoil(aab);
			}

			else if (qName.equalsIgnoreCase("ORIGIN"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("SUPPLIER"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("NOTES"))
			{
				f.setShortDescription(currentValue);
			}

			else if (qName.equalsIgnoreCase("COARSE_FINE_DIFF"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("MOISTURE"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("DIASTATIC_POWER"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("PROTEIN"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("MAX_IN_BATCH"))
			{
				if (currentElement != false)
				{
					double maxInBatch = Double.parseDouble(currentValue);
					f.setMaxInBatch(maxInBatch);
				}
			}

			else if (qName.equalsIgnoreCase("RECOMMEND_MASH"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("IBU_GAL_PER_LB"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("DISPLAY_AMOUNT"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("INVENTORY"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("POTENTIAL"))
			{
				// TODO: FUCK THIS SHIT
			}

			else if (qName.equalsIgnoreCase("DISPLAY_COLOR"))
			{
				// TODO: Add support for this field
			}
		}
		
		else if (thingType == HOP)
		// We are looking at a hop
		// Set all the hop things here
		// woo.
		{
			if (qName.equalsIgnoreCase("NAME"))
			{
				h.setName(currentValue);
			}

			else if (qName.equalsIgnoreCase("VERSION"))
			{
				// TODO: Set version!
			}

			else if (qName.equalsIgnoreCase("ORIGIN"))
			{
				h.setOrigin(currentValue);
			}

			else if (qName.equalsIgnoreCase("ALPHA"))
			{
				h.setAlphaAcidContent(Double.parseDouble(currentValue));
			}

			else if (qName.equalsIgnoreCase("AMOUNT"))
			{
				double amt = Double.parseDouble(currentValue);
				amt = Units.kilosToOunces(amt);
				h.setAmount(amt);
			}

			else if (qName.equalsIgnoreCase("USE"))
			{
				String use = "";

				if (currentValue.equalsIgnoreCase(Hop.USE_AROMA))
					use = Hop.USE_AROMA;
				if (currentValue.equalsIgnoreCase(Hop.USE_BOIL));
        		use = Hop.USE_BOIL;
				if (currentValue.equalsIgnoreCase(Hop.USE_DRY_HOP))
					use = Hop.USE_DRY_HOP;
				if (currentValue.equalsIgnoreCase(Hop.USE_MASH))
					use = Hop.USE_MASH;
				if (currentValue.equalsIgnoreCase(Hop.USE_FIRST_WORT))
					use = Hop.USE_FIRST_WORT;

				h.setUse(use);
			}

			else if (qName.equalsIgnoreCase("TIME"))
			{
				h.setTime((int) Double.parseDouble(currentValue));
			}

			else if (qName.equalsIgnoreCase("NOTES"))
			{
				h.setDescription(currentValue);
			}

			else if (qName.equalsIgnoreCase("TYPE"))
			{
				String type = "";

				if (currentValue.equalsIgnoreCase(Hop.TYPE_AROMA))
					type = Hop.TYPE_AROMA;
				if (currentValue.equalsIgnoreCase(Hop.TYPE_BITTERING))
					type = Hop.TYPE_BITTERING;
				if (currentValue.equalsIgnoreCase(Hop.TYPE_BOTH))
					type = Hop.TYPE_BOTH;

				h.setHopType(type);
			}

			else if (qName.equalsIgnoreCase("FORM"))
			{
				String form = "";

				if (currentValue.equalsIgnoreCase(Hop.FORM_PELLET))
					form = Hop.FORM_PELLET;
				if (currentValue.equalsIgnoreCase(Hop.FORM_WHOLE))
					form = Hop.FORM_WHOLE;
				if (currentValue.equalsIgnoreCase(Hop.FORM_PLUG))
					form = Hop.FORM_PLUG;

				h.setForm(form);
			}
		}
		
		else if (thingType == YEAST)
		// We are looking at a yeast
		// Set the yeast fiels here
		// woo.
		{
			if (qName.equalsIgnoreCase("NAME"))
			{
				y.setName(currentValue);
			}

			else if (qName.equalsIgnoreCase("VERSION"))
			{
				int version = Integer.parseInt(currentValue);
				y.setVersion(version);
			}

			else if (qName.equalsIgnoreCase("TYPE"))
			{
				String type = "Invalid Type";

				if (currentValue.equalsIgnoreCase(Yeast.ALE))
					type = Yeast.ALE;
				if (currentValue.equalsIgnoreCase(Yeast.LAGER))
					type = Yeast.LAGER;
				if (currentValue.equalsIgnoreCase(Yeast.WHEAT))
					type = Yeast.WHEAT;
				if (currentValue.equalsIgnoreCase(Yeast.WINE))
					type = Yeast.WINE;
				if (currentValue.equalsIgnoreCase(Yeast.CHAMPAGNE))
					type = Yeast.CHAMPAGNE;

				y.setType(type);
			}

			else if (qName.equalsIgnoreCase("FORM"))
			{
				String form = "Invalid Form";

				if (currentValue.equalsIgnoreCase(Yeast.CULTURE))
					form = Yeast.CULTURE;
				if (currentValue.equalsIgnoreCase(Yeast.DRY))
					form = Yeast.DRY;
				if (currentValue.equalsIgnoreCase(Yeast.LIQUID))
					form = Yeast.LIQUID;

				y.setForm(form);
			}

			else if (qName.equalsIgnoreCase("AMOUNT"))
			{
				double amt = Double.parseDouble(currentValue);
				y.setAmount(amt);
			}

			else if (qName.equalsIgnoreCase("AMOUNT_IS_WEIGHT"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("LABORATORY"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("PRODUCT_ID"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("MIN_TEMPERATURE"))
			{
				double minTemp = Double.parseDouble(currentValue);
				y.setMinTemp(minTemp);
			}

			else if (qName.equalsIgnoreCase("MAX_TEMPERATURE"))
			{
				double maxTemp = Double.parseDouble(currentValue);
				y.setMaxTemp(maxTemp);
			}

			else if (qName.equalsIgnoreCase("FLOCCULATION"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("ATTENUATION"))
			{
				double attenuation = Double.parseDouble(currentValue);
				y.setAttenuation(attenuation);
			}

			else if (qName.equalsIgnoreCase("NOTES"))
			{
				y.setNotes(currentValue);
			}

			else if (qName.equalsIgnoreCase("BEST_FOR"))
			{
				y.setBestFor(currentValue);
			}

			else if (qName.equalsIgnoreCase("MAX_REUSE"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("TIMES_CULTURED"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("ADD_TO_SECONDARY"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("DISPLAY_AMOUNT"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("DISP_MIN_TEMP"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("DISP_MAX_TEMP"))
			{
				// TODO: Add support for this field
			}

			else if (qName.equalsIgnoreCase("INVENTORY"))
			{
				// TODO: FUCK THIS SHIT
			}

			else if (qName.equalsIgnoreCase("CULTURE_DATE"))
			{
				// TODO: Add support for this field
			}
		}
		
		else if (thingType == MISC)
		// We are looking at a misc
		// Do all of the misc things below
		// woo.
		{
			if (qName.equalsIgnoreCase("NAME"))
			{
				misc.setName(currentValue);
			}

			else if (qName.equalsIgnoreCase("VERSION"))
			{
				misc.setVersion(Integer.parseInt(currentValue));
			}

			else if (qName.equalsIgnoreCase("TYPE"))
			{
				String type = "NULL";

				if (currentValue.equalsIgnoreCase(Misc.TYPE_SPICE))
					type = Misc.TYPE_SPICE;
				if (currentValue.equalsIgnoreCase(Misc.TYPE_FINING))
					type = Misc.TYPE_FINING;
				if (currentValue.equalsIgnoreCase(Misc.TYPE_FLAVOR))
					type = Misc.TYPE_FLAVOR;
				if (currentValue.equalsIgnoreCase(Misc.TYPE_HERB))
					type = Misc.TYPE_HERB;
				if (currentValue.equalsIgnoreCase(Misc.TYPE_WATER_AGENT))
					type = Misc.TYPE_WATER_AGENT;
				if (currentValue.equalsIgnoreCase(Misc.TYPE_OTHER))
					type = Misc.TYPE_OTHER;

				misc.setMiscType(type);
			}

			else if (qName.equalsIgnoreCase("AMOUNT"))
			{
				double amt = Double.parseDouble(currentValue);
				if (misc.getAmountIsWeight())
					amt = Units.kilosToOunces(amt);
				else
					amt = Units.litersToGallons(amt);
				misc.setAmount(amt);
			}
			
			else if (qName.equalsIgnoreCase("TIME"))
			{
				misc.setStartTime(0);
				misc.setEndTime((int)Float.parseFloat(currentValue));
			}

			else if (qName.equalsIgnoreCase("USE"))
			{
				String type = "NULL";
				
				if (currentValue.equalsIgnoreCase(Misc.USE_BOIL))
					type = Misc.USE_BOIL;
				if (currentValue.equalsIgnoreCase(Misc.USE_BOTTLING))
					type = Misc.USE_BOTTLING;
				if (currentValue.equalsIgnoreCase(Misc.USE_MASH))
					type = Misc.USE_MASH;
				if (currentValue.equalsIgnoreCase(Misc.USE_PRIMARY))
					type = Misc.USE_PRIMARY;
				if (currentValue.equalsIgnoreCase(Misc.USE_SECONDARY))
					type = Misc.USE_SECONDARY;
					
				misc.setUse(type);
			}

			else if (qName.equalsIgnoreCase("NOTES"))
			{
				misc.setShortDescription(currentValue);
			}

			else if (qName.equalsIgnoreCase("AMOUNT_IS_WEIGHT"))
			{
				if (currentValue.equalsIgnoreCase("TRUE"))
					misc.setAmountIsWeight(true);
				else
					misc.setAmountIsWeight(false);
			}

			else if (qName.equalsIgnoreCase("USE_FOR"))
			{
				misc.setUseFor(currentValue);
			}
		}
    }

    @Override
    public void characters(char[] ch, int start, int length)
    throws SAXException {

        if (currentElement) {
            currentValue = new String(ch, start, length);
            currentElement = false;
        }

    }

}
