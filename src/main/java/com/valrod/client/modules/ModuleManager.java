package com.valrod.client.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.valrod.client.events.Event;

public class ModuleManager {

	private List<Module> modules;

	public ModuleManager() {
		this.modules = new ArrayList<Module>();
	}

	public void register(Module module) {
		this.modules.add(module);
	}

	public List<Module> getModules(){
		return this.modules;
	}
	
	public boolean isModuleOn(Module mod) {
		for(Module m : this.modules) {
			if(m.getName().equals(mod.getName()) && m.isEnabled()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isModuleOn(String mod) {
		for(Module m : this.modules) {
			if(m.getName().equals(mod) && m.isEnabled()) {
				return true;
			}
		}
		return false;
	}
	
	public void toggleModule(String name) {
		for(Module m : this.modules) {
			if(m.getName().equals(name)) {
				m.toggle();
			}
		}
	}
	
	public void toggleModule(Module module) {
		for(Module m : this.modules) {
			if(m.getName().equals(module.getName())) {
				m.toggle();
			}
		}
	}
	
	public void enableModule(String name) {
		for(Module m : this.modules) {
			if(m.getName().equals(name) && !m.isEnabled()) {
				m.toggle();
			}
		}
	}

	public Module[] getEnabledModules(){
		List<Module> modules = new ArrayList<Module>();

		for(Module m : this.modules) {
			if(m.isEnabled()) {
				modules.add(m);
			}
		}
		Module[] mods = new Module[modules.size()];
		
		int cpt=0;
		for(Module m : modules) {
			mods[cpt++] = m;
		}
		
		Arrays.sort(mods);
		return mods;
	}
	
	public Module[] getModulesByCategory(Category category){
		List<Module> modules = new ArrayList<Module>();

		for(Module m : this.modules) {
			if(m.getCategory() == category) {
				modules.add(m);
			}
		}
		Module[] mods = new Module[modules.size()];
		
		int cpt=0;
		for(Module m : modules) {
			mods[cpt++] = m;
		}
		
		Arrays.sort(mods);
		return mods;
	}

	public void onKeyPressed(int keyCode) {
		for(Module m : this.modules) {
			if(m.getKeyCode() == keyCode) {
				m.toggle();
			}
		}
	}

	public void onEvent(Event event) {
		for(Module m : this.modules) {
			if(m.isEnabled()) {
				m.onUpdate(event);
			}
		}
	}

	public void enableAll() {
		for(Module m : this.modules) {
			if(!m.isEnabled()) {
				m.toggle();
			}
		}
	}

}
