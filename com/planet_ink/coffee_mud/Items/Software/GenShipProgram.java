package com.planet_ink.coffee_mud.Items.Software;
import com.planet_ink.coffee_mud.Items.Basic.StdItem;
import com.planet_ink.coffee_mud.Items.BasicTech.GenElecItem;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.Technical.TechType;
import com.planet_ink.coffee_mud.Libraries.interfaces.GenericBuilder;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;

/*
   Copyright 2013-2022 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public class GenShipProgram extends GenSoftware
{
	@Override
	public String ID()
	{
		return "GenShipProgram";
	}

	protected static final int DEFAULT_ACT_8_SEC_COUNTDOWN = 100;

	protected volatile long nextPowerCycleTmr = System.currentTimeMillis()+(8*1000);
	protected volatile int	activationCounter = DEFAULT_ACT_8_SEC_COUNTDOWN;

	protected String	readableText	= "";

	protected volatile List<ShipEngine>		engines				= null;
	protected volatile List<TechComponent>	sensors				= null;
	protected volatile List<TechComponent>	weapons				= null;
	protected volatile List<TechComponent>	shields				= null;
	protected volatile List<TechComponent>	components			= null;
	protected volatile List<TechComponent>	dampers				= null;

	protected final static PrioritizingLimitedMap<String,TechComponent> cachedComponents = new PrioritizingLimitedMap<String,TechComponent>(1000,60000,600000,0);

	public GenShipProgram()
	{
		super();
		setName("a software disk");
		setDisplayText("a small disk sits here.");
		setDescription("It appears to be a general software program.");
	}

	@Override
	public TechType getTechType()
	{
		return TechType.SHIP_SOFTWARE;
	}

	@Override
	public boolean isActivationString(final String word)
	{
		return super.isActivationString(word);
	}

	@Override
	public boolean isDeActivationString(final String word)
	{
		return super.isDeActivationString(word);
	}

	@Override
	public boolean isCommandString(final String word, final boolean isActive)
	{
		return super.isCommandString(word, isActive);
	}

	@Override
	public String getActivationMenu()
	{
		return super.getActivationMenu();
	}

	@Override
	protected boolean checkActivate(final MOB mob, final String message)
	{
		return super.checkActivate(mob, message);
	}

	@Override
	protected boolean checkDeactivate(final MOB mob, final String message)
	{
		return super.checkDeactivate(mob, message);
	}

	@Override
	protected boolean checkTyping(final MOB mob, final String message)
	{
		return super.checkTyping(mob, message);
	}

	@Override
	protected boolean checkPowerCurrent(final int value)
	{
		return super.checkPowerCurrent(value);
	}

	@Override
	protected void onActivate(final MOB mob, final String message)
	{
		super.onActivate(mob, message);
	}

	@Override
	protected void onDeactivate(final MOB mob, final String message)
	{
		super.onDeactivate(mob, message);
	}

	@Override
	protected void onTyping(final MOB mob, final String message)
	{
		super.onTyping(mob, message);
	}

	@Override
	protected void onPowerCurrent(final int value)
	{
		super.onPowerCurrent(value);
		if (System.currentTimeMillis() > nextPowerCycleTmr)
		{
			onPowerTimer(value);
			nextPowerCycleTmr = System.currentTimeMillis() + (8 * 1000);
			if(--activationCounter <= 0) // so--- every 800 seconds?
			{
				activationCounter = DEFAULT_ACT_8_SEC_COUNTDOWN;
				onActivationTimer(value);
			}
		}
	}

	@Override
	public void executeMsg(final Environmental host, final CMMsg msg)
	{
		if(msg.amITarget(this))
		{
			switch(msg.targetMinor())
			{
			case CMMsg.TYP_GET:
			case CMMsg.TYP_PUSH:
			case CMMsg.TYP_PULL:
			case CMMsg.TYP_PUT:
			case CMMsg.TYP_INSTALL:
				decache();
				break;
			}
		}
		else
		if((container() instanceof Computer)
		&&(msg.target() == container())
		&&(msg.targetMinor() == CMMsg.TYP_DEACTIVATE))
			decache();
		super.executeMsg(host, msg);
	}

	protected void trySendMsgToItem(final MOB mob, final Item targetE, final CMMsg msg)
	{
		if(targetE.owner() instanceof Room)
		{
			if(((Room)targetE.owner()).okMessage(mob, msg))
				((Room)targetE.owner()).send(mob, msg);
		}
		else
		if(targetE.okMessage(mob, msg))
			targetE.executeMsg(mob, msg);
	}


	protected void decache()
	{
		engines 	= null;
		sensors		= null;
		weapons		= null;
		shields		= null;
		components	= null;
		dampers		= null;
	}

	protected synchronized List<TechComponent> getComponent(final TechType type)
	{
		List<TechComponent> components;
		if(circuitKey.length()==0)
			return components=new Vector<TechComponent>(0);
		else
		{
			final List<Electronics> electronics=CMLib.tech().getMakeRegisteredElectronics(circuitKey);
			components=new Vector<TechComponent>(1);
			for(final Electronics E : electronics)
			{
				if ((E instanceof TechComponent)
				&& (E.getTechType()== type))
					components.add((TechComponent)E);
			}
		}
		return components;
	}

	protected synchronized List<ShipEngine> getEngines()
	{
		List<ShipEngine> engines = this.engines;
		if(engines == null)
		{
			engines=new Vector<ShipEngine>(1);
			final List<TechComponent> stuff=getTechComponents();
			for(final Electronics E : stuff)
			{
				if(E instanceof ShipEngine)
					engines.add((ShipEngine)E);
			}
			this.engines = engines;
		}
		return engines;
	}

	protected synchronized List<TechComponent> getTechComponents()
	{
		if(components == null)
		{
			if(circuitKey.length()==0)
				components=new Vector<TechComponent>(0);
			else
			{
				final List<Electronics> electronics=CMLib.tech().getMakeRegisteredElectronics(circuitKey);
				components=new Vector<TechComponent>(1);
				for(final Electronics E : electronics)
				{
					if(E instanceof TechComponent)
						components.add((TechComponent)E);
				}
			}
		}
		return components;
	}

	protected synchronized List<TechComponent> getShipSensors()
	{
		if(sensors == null)
		{
			final List<TechComponent> stuff=getTechComponents();
			sensors=new Vector<TechComponent>(1);
			for(final TechComponent E : stuff)
			{
				if(E.getTechType()==TechType.SHIP_SENSOR)
					sensors.add(E);
			}
		}
		return sensors;
	}

	protected boolean isWeaponLauncher(final TechComponent E)
	{
		if(E.getTechType()==TechType.SHIP_LAUNCHER)
		{
			if(!(E instanceof Container))
				return false;
			final List<Item> contents = ((Container)E).getContents();
			if(contents.size()==0)
				return true;
			if(contents.get(0) instanceof Weapon)
				return true;
			return false;
		}
		return false;
	}

	protected synchronized List<TechComponent> getShipWeapons()
	{
		if(weapons == null)
		{
			final List<TechComponent> stuff=getTechComponents();
			weapons=new Vector<TechComponent>(1);
			for(final TechComponent E : stuff)
			{
				if((E.getTechType()==TechType.SHIP_WEAPON)
				||(isWeaponLauncher(E)))
					weapons.add(E);
			}
		}
		return weapons;
	}

	protected synchronized List<TechComponent> getShipShields()
	{
		if(shields == null)
		{
			final List<TechComponent> stuff=getTechComponents();
			shields=new Vector<TechComponent>(1);
			for(final TechComponent E : stuff)
			{
				if(E.getTechType()==TechType.SHIP_SHIELD)
					shields.add(E);
			}
		}
		return shields;
	}

	protected synchronized List<TechComponent> getDampeners()
	{
		if(dampers == null)
		{
			final List<TechComponent> stuff=getTechComponents();
			dampers=new Vector<TechComponent>(1);
			for(final TechComponent E : stuff)
			{
				if(E.getTechType()==TechType.SHIP_DAMPENER)
					dampers.add(E);
			}
		}
		return dampers;
	}

	protected ShipEngine findEngineByName(final String name)
	{
		return (ShipEngine)findComponentByName(getEngines(), "ENGINE", name);
	}

	protected TechComponent findSensorByName(final String name)
	{
		return findComponentByName(getShipSensors(), "SENSOR", name);
	}

	protected TechComponent findWeaponByName(final String name)
	{
		return findComponentByName(getShipWeapons(), "WEAPON", name);
	}

	protected ShipWarComponent findShieldByName(final String name)
	{
		return (ShipWarComponent)findComponentByName(getShipShields(), "SHIELD", name);
	}

	protected ShipEngine findEngineByPort(final ShipDirectional.ShipDir portdir)
	{
		for(final ShipEngine E : getEngines())
		{
			if(CMParms.contains(E.getAvailPorts(), portdir))
				return E;
		}
		return null;
	}

	protected TechComponent findComponentByName(final List<? extends TechComponent> list, final String prefix, String name)
	{
		if(list.size()==0)
			return null;
		name=name.toUpperCase();
		if(name.startsWith(prefix))
		{
			final String numStr=name.substring(6);
			if(!CMath.isInteger(numStr))
				return null;
			final int num=CMath.s_int(numStr);
			if((num>0)&&(num<=list.size()))
				return list.get(num-1);
			return null;
		}
		TechComponent E=(TechComponent)CMLib.english().fetchEnvironmental(list, name, true);
		if(E==null)
			E=(TechComponent)CMLib.english().fetchEnvironmental(list, name, false);
		return E;
	}

	protected TechComponent findComponentByID(final List<? extends TechComponent> list, final String id)
	{
		if(list.size()==0)
			return null;
		if(cachedComponents.containsKey(id))
			return cachedComponents.get(id);
		for(final TechComponent C : list)
		{
			if((""+C).equalsIgnoreCase(id))
			{
				cachedComponents.put(id, C);
				return C;
			}
		}
		return null;
	}

	protected void onPowerTimer(final int value)
	{
		engines = null; // why only this?!
	}

	protected void onActivationTimer(final int value)
	{

	}
}
